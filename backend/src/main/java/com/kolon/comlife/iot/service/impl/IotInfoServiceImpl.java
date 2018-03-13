package com.kolon.comlife.iot.service.impl;

import com.google.common.base.CaseFormat;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.iot.exception.IotInfoNoDataException;
import com.kolon.comlife.iot.model.IotButtonListInfo;
import com.kolon.comlife.iot.model.IotModeListInfo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        buttonList.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( buttonList.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "가져올 버튼 정보가 없습니다." );
        }

        buttonList.setMsg("My IOT의 IOT 버튼 개별 정보 가져오기");

        return buttonList;
    }


    private List convertListMapDataToCamelCase(List inputDataList) {
        List outputDataList = new ArrayList();

        for(Map<String, Object> e : (List<Map<String, Object>>)inputDataList ) {
            Map element = new HashMap();
            for (String s : e.keySet()) {
                element.put( CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s), e.get(s));
            }
            outputDataList.add(element);
        }
        return outputDataList;
    }
}
