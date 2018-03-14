package com.kolon.comlife.iot.service.impl;

import com.google.common.base.CaseFormat;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.iot.exception.IotInfoNoDataException;
import com.kolon.comlife.iot.model.*;
import com.kolon.comlife.iot.service.IotControlService;
import com.kolon.comlife.iot.service.IotInfoService;
import com.kolon.common.http.HttpGetRequester;
import com.kolon.common.prop.ServicePropertiesMap;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("iotInfoService")
public class IotInfoServiceImpl implements IotInfoService {

    // todo: IotController에서 IotInfoService로 옮길 것
    private static final Logger logger = LoggerFactory.getLogger(IotInfoService.class);

    private static final String IOK_MOBILE_HOST_PROP_GROUP = "IOK";
    private static final String IOK_MOBILE_HOST_PROP_KEY = "MOBILE_HOST";
    private static final String IOK_MODES_LIST_PATH = "/iokinterface/scenario/modeInfoList";
    private static final String IOK_MYIOT_LIST_PATH = "/iokinterface/myiot/myiotList";
    private static final String IOK_ROOMS_LIST_PATH = "/iokinterface/device/roomList";
    private static final String IOK_DEVICES_LIST_BY_ROOM_PATH = "/iokinterface/device/roomDeviceList";
    private static final String IOK_DEVICE_DETAIL_BY_DEVICE_ID_PATH = "/iokinterface/device/deviceDetail";
    private static final String IOK_DEVICES_GROUP_LIST_PATH = "/iokinterface/device/cateList";
    private static final String IOK_DEVICES_LIST_BY_CATEGORY_PATH = "/iokinterface/device/cateDeviceList";


    @Resource(name = "servicePropertiesMap")
    private ServicePropertiesMap serviceProperties;

    @Resource(name = "iotControlService")
    private IotControlService iotControlService;

    @Autowired
    private CloseableHttpClient httpClient;


    /**
     * 1. '모드'의 전체 목록 가져오기 at Dashboard
     */
    public IotModeListInfo getModeList(int complexId, int homeId) throws Exception {

        IotModeListInfo modesList = new IotModeListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODES_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        result = requester.execute();

        modesList.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( modesList.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "정의된 모드가 없습니다." );
        }

        modesList.setMsg("모드 전체 목록 가져오기");

        return modesList;
    }

    /**
     * 2. 현재 적용된 '모드'의 가져오기 at Dashboard
     */
    public IotModeListInfo getActiveMode(int complexId, int homeId) throws Exception {
        IotModeListInfo activeModeList = new IotModeListInfo();
        List            foundData = new ArrayList();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODES_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        result = requester.execute();

        // 현재 적용상태인 모드를 검색
        for(Map<String, Object>e : (List<Map<String, Object>>)result.get("DATA")) {
            String value = (String)e.get("EXEC_YN");
            if(value != null) {
                if(value.toUpperCase().equals("Y")) {
                    logger.debug(">>>> found EXEC_YN == Y");
                    foundData.add(e);
                    break;
                }
            }
        }

        if( foundData.isEmpty() )
        {
            throw new IotInfoNoDataException( "현재 적용된 모드가 없습니다." );
        }

        activeModeList.setData(
                convertListMapDataToCamelCase(foundData));

        activeModeList.setMsg("현재 동작중인 모드 가져오기");

        return activeModeList;
    }

    private void replaceMapKey(Map map, String oldKey, String newKey) {
        String v = (String) map.remove(oldKey);
        map.put(newKey, v);
    }

    private void convertResultMyIotButtonList(Map<String, Map> result) {
        String v;
        for(Map<String, Object>e : (List<Map<String, Object>>)result.get("DATA")) {
            replaceMapKey(e, "TITLE", "BT_TITLE");
            replaceMapKey(e, "SUB_TITLE", "BT_SUB_TITLE");
            replaceMapKey(e, "IMG_SRC", "BT_IMG_SRC");

            // BT_TYPE(btType) 정의
            v = (String)e.remove("MY_IOT_GB_CD");
            switch(v) {
                case "MB01701":
                    e.put("BT_TYPE", "device");
                    break;
                case "MB01702":
                    e.put("BT_TYPE", "automation");
                    break;
                case "MB01703":
                    e.put("BT_TYPE", "information");
                    break;
                default:
                    e.put("BT_TYPE", "unknown");
                    break;
            }
            // BT_RIGHT_ICON_TYPE(btRightIconType) 결정

            if((e.get("BINARY_YN") != null) && (e.get("BINARY_YN").equals("Y"))) {
                if (e.get("STS_CNT").equals("1")) {
                    e.put("BT_RIGHT_ICON_TYPE", "button");
                } else {
                    e.put("BT_RIGHT_ICON_TYPE", "detail");
                }
            } else {
                e.put("BT_RIGHT_ICON_TYPE", "button");
            }

            // 사용하지 않은 값 삭제
            e.remove("CMPLX_ID");
            e.remove("HOME_ID");
            e.remove("BINARY_YN");
            e.remove("MY_IOT_GB_CD");
        }
    }

    /**
     * 3. My IOT의 IOT 버튼 목록 및 정보 가져오기 at Dashboard
     */
    public IotButtonListInfo getMyIotButtonList(int complexId, int homeId, String userId) throws Exception {
        IotButtonListInfo buttonList = new IotButtonListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MYIOT_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("userId", userId );
        result = requester.execute();

        // 반환값을 COMMONLife에 맞게 변환
        this.convertResultMyIotButtonList(result);

        buttonList.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( buttonList.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "가져올 버튼 정보가 없습니다." );
        }

        buttonList.setMsg("My IOT의 IOT 버튼 목록 및 정보 가져오기");

        return buttonList;
    }


    /**
     * 4. My IOT의 IOT 버튼 개별 정보 가져오기
     */
    public IotButtonListInfo getMyIotButtonListById(int complexId, int homeId, String userId, int buttonId) throws Exception {
        IotButtonListInfo buttonList = new IotButtonListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MYIOT_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("userId", userId );
        requester.setParameter("seqNo", String.valueOf(buttonId) );
        result = requester.execute();

        // 반환값을 COMMONLife에 맞게 변환
        this.convertResultMyIotButtonList(result);

        buttonList.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( buttonList.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "가져올 버튼 정보가 없습니다." );
        }

        buttonList.setMsg("My IOT의 IOT 버튼 개별 정보 가져오기");

        return buttonList;
    }

    /**
     * 10. 공간 목록 가져오기 at Quick IOT 제어 > 공간별 보기
     */
    public IotRoomListInfo getRoomList(int complexId, int homeId) throws Exception {
        IotRoomListInfo roomList = new IotRoomListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_ROOMS_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        result = requester.execute();

        roomList.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( roomList.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "공간 정보가 없습니다." );
        }

        roomList.setMsg("공간 목록 가져오기");

        return roomList;
    }

    /**
     * 11. 공간별 '기기 목록' 가져오기 at Quick IOT 제어 > 공간별 보기
     */
    public IotDeviceListInfo getRoomsWithDevicesList(int complexId, int homeId, int roomId) throws Exception {
        IotDeviceListInfo deviceListInfo = new IotDeviceListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_DEVICES_LIST_BY_ROOM_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("roomId", String.valueOf(roomId) );
        result = requester.execute();

        deviceListInfo.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( deviceListInfo.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "해당 공간에 기기가 없습니다." );
        }

        deviceListInfo.setMsg("공간별 기기 목록 가져오기");

        return deviceListInfo;
    }

    /**
     * 12. 기기 상세 정보 가져오기 at Quick IOT 제어 > 공간별 보기 or 기기별 보기
     */
    public IotDeviceListInfo getDeviceInfo(int complexId, int homeId, String deviceId) throws Exception {
        IotDeviceListInfo deviceListInfo = new IotDeviceListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_DEVICE_DETAIL_BY_DEVICE_ID_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("deviceId", String.valueOf(deviceId) );
        result = requester.execute();

        deviceListInfo.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));


        if( deviceListInfo.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "기기 정보가 없습니다." );
        }

        deviceListInfo.setMsg("기기 상세 정보 가져오기");

        return deviceListInfo;
    }

    /**
     * 13. 기기 카테고리 가져오기 at Quick IOT 제어 > 기기별 보기
     */
    public IotDeviceGroupListInfo getDeviceGroupList(int complexId, int homeId ) throws Exception
    {
        IotDeviceGroupListInfo deviceGroupListInfo = new IotDeviceGroupListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_DEVICES_GROUP_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        result = requester.execute();

        deviceGroupListInfo.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( deviceGroupListInfo.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "카테고리 정보가 없습니다." );
        }

        deviceGroupListInfo.setMsg("기기 카테고리 가져오기");

        return deviceGroupListInfo;
    }


    /**
     * 14. 카테고리 별 기기 목록 가져오기 at Quick IOT 제어 > 기기별 보기
     */
    public IotDeviceListInfo getDeviceListByDeviceGroup(int complexId, int homeId, String categoryCode) throws Exception
    {
        IotDeviceListInfo deviceListInfo = new IotDeviceListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_DEVICES_LIST_BY_CATEGORY_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("cateCd", String.valueOf(categoryCode) );

        result = requester.execute();

        deviceListInfo.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( deviceListInfo.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "기기 정보가 없습니다." );
        }

        deviceListInfo.setMsg("카테고리 별 기기 목록 가져오기");

        return deviceListInfo;
    }


    private List convertListMapDataToCamelCase(List inputDataList) {
        List outputDataList = new ArrayList();

        for(Map<String, Object> e : (List<Map<String, Object>>)inputDataList ) {
//            Map element = new HashMap();
            Map element = new TreeMap(); // for DEBUG : 결과 값이 key값 기준으로 ABC.. 정렬되어 표시
            for (String s : e.keySet()) {
                element.put( CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s), e.get(s));
            }
            outputDataList.add(element);
        }
        return outputDataList;
    }
}
