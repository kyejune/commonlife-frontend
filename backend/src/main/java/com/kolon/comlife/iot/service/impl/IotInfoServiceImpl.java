package com.kolon.comlife.iot.service.impl;

import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kolon.comlife.iot.exception.IotInfoGeneralException;
import com.kolon.comlife.iot.exception.IotInfoNoDataException;
import com.kolon.comlife.iot.exception.IotInfoUpdateFailedException;
import com.kolon.comlife.iot.model.*;
import com.kolon.comlife.iot.service.IotIconMapperService;
import com.kolon.comlife.iot.service.IotInfoService;
import com.kolon.common.http.HttpDeleteRequester;
import com.kolon.common.http.HttpGetRequester;
import com.kolon.common.http.HttpPostRequester;
import com.kolon.common.prop.ServicePropertiesMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("iotInfoService")
public class IotInfoServiceImpl implements IotInfoService {

    // todo: IotController에서 IotInfoService로 옮길 것
    private static final Logger logger = LoggerFactory.getLogger(IotInfoService.class);

    private static final String IOK_MOBILE_HOST_PROP_GROUP  = "IOK";
    private static final String IOK_MOBILE_HOST_PROP_KEY    = "MOBILE_HOST";

    private static final String IOK_MODES_LIST_PATH                 = "/iokinterface/scenario/modeInfoList";
    private static final String IOK_MYIOT_LIST_PATH                 = "/iokinterface/myiot/myiotList";
    private static final String IOK_MYIOT_AVAILABLE_LIST_PATH       = "/iokinterface/myiot/myiotSetList";
    private static final String IOK_MYIOT_AUTOMATION_AVAILABLE_LIST_PATH  = "/iokinterface/myiot/myiotSetScnaList";
    private static final String IOK_MYIOT_DEVICES_AVAILABLE_LIST_PATH     = "/iokinterface/myiot/myiotSetThingsList";
    private static final String IOK_MYIOT_VALUE_INFO_AVAILABLE_LIST_PATH  = "/iokinterface/myiot/myiotSetValueList";
    private static final String IOK_MYIOT_ADD_PATH                  = "/iokinterface/myiot/saveMyIot";
    private static final String IOK_MYIOT_UPDATE_ORDER_PATH         = "/iokinterface/myiot/saveMyIot";
    private static final String IOK_MYIOT_DELETE_PATH               = "/iokinterface/myiot/saveMyIot";
    private static final String IOK_ROOMS_LIST_PATH                 = "/iokinterface/device/roomList";
    private static final String IOK_DEVICES_LIST_BY_ROOM_PATH       = "/iokinterface/device/roomDeviceList";
    private static final String IOK_DEVICE_DETAIL_BY_DEVICE_ID_PATH = "/iokinterface/device/deviceDetail";
    private static final String IOK_DEVICES_GROUP_LIST_PATH         = "/iokinterface/device/cateList";
    private static final String IOK_DEVICES_LIST_BY_CATEGORY_PATH   = "/iokinterface/device/cateDeviceList";
    private static final String IOK_DEVICES_USAGE_HISTORY_PATH      = "/iokinterface/device/deviceHistList";
    private static final String IOK_MODE_DETAIL_PATH                = "/iokinterface/scenario/scnaDetail";
    private static final String IOK_MODE_ORDER_CHANGE_PATH          = "/iokinterface/scenario/modeModify";
    private static final String IOK_MODE_DETAIL_SAVE_PATH           = "/iokinterface/scenario/saveScnaDetail";
    private static final String IOK_MODE_DETAIL_CONDITONS_PATH      = "/iokinterface/scenario/scnaIfDetail";
    private static final String IOK_MODE_DETAIL_ACTORS_PATH         = "/iokinterface/scenario/scnaThingsDetail";
    private static final String IOK_MODE_DETAIL_ACTOR_DETAIL_PATH   = "/iokinterface/scenario/scnaThingsSetDetail";
    private static final String IOK_MODE_DETAIL_CONDITONS_AVAILABLE_PATH = "/iokinterface/scenario/scnaIfAddList";
    private static final String IOK_MODE_DETAIL_ACTORS_AVAILABLE_PATH    = "/iokinterface/scenario/scnaThingsAddList";
    private static final String IOK_AUTOMATION_LIST_PATH            = "/iokinterface/scenario/scnaList";
    private static final String IOK_AUTOMATION_DETAIL_PATH          = "/iokinterface/scenario/scnaDetail";
    private static final String IOK_AUTOMATION_DETAIL_SAVE_PATH     = "/iokinterface/scenario/saveScnaDetail";
    private static final String IOK_AUTOMATION_DELETE_PATH          = "/iokinterface/scenario/saveScnaDetail";
    private static final String IOK_DEVICE_NAME_UPDATE_PATH         = "/iokinterface/device/updateDevice";

    private static final String USER_SCENARIO_MODE_CODE = "CM01199";

    private static final String AUTOMATION_DEFUALT_ICON = "cl_icon_default"; // todo: IMG/ICON Mapper로 옮길 것


    @Autowired
    private IotIconMapperService iconService;

    @Autowired
    private ServicePropertiesMap serviceProperties;

    @Autowired
    private CloseableHttpClient httpClient;


    /**
     * 1. '모드'의 전체 목록 가져오기 at Dashboard
     */
    public IotModeOrAutomationListInfo getModeList(int complexId, int homeId) throws Exception {
        IotModeOrAutomationListInfo modesList = new IotModeOrAutomationListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODES_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        result = requester.execute();

        // 1. result data 리맵핑
        this.remapResultModeDataList( (List)result.get("DATA") );


        // 2. camelCase로 변환
        modesList.setData(
                convertListMapDataToCamelCase( (List)result.get("DATA") ));

        if( modesList.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "정의된 모드가 없습니다." );
        }

        // 3. 결과 정보 입력 (debug용도)
        modesList.setMsg("모드 전체 목록 가져오기");

        return modesList;
    }

    /**
     * 2. 현재 적용된 '모드'의 가져오기 at Dashboard
     */
    public IotModeOrAutomationListInfo getActiveMode(int complexId, int homeId) throws Exception {
        IotModeOrAutomationListInfo activeModeList = new IotModeOrAutomationListInfo();
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

        // 1. result data 리맵핑
        this.remapResultModeDataList( foundData );

        if( foundData.isEmpty() )
        {
            throw new IotInfoNoDataException( "현재 적용된 모드가 없습니다." );
        }

        // 2. camelCase로 변환
        activeModeList.setData(
                convertListMapDataToCamelCase(foundData));

        activeModeList.setMsg("현재 동작중인 모드 가져오기");

        return activeModeList;
    }


    private IotButtonListInfo getMyIotButtonListInternal
            (int complexId, int homeId, String userId, boolean resultSimplify) throws Exception {
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

        // 1. result data 리맵핑
        try {
            this.remapResultMyIotButtonDataList( (List)result.get("DATA") );
            if( resultSimplify ) {
                this.deleteUnusedResultMyIotButtonDataList( (List)result.get("DATA"), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        buttonList.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( buttonList.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "가져올 버튼 정보가 없습니다." );
        }

        return buttonList;
    }

    /**
     * 3. My IOT의 IOT 버튼 목록 및 정보 가져오기 at Dashboard
     *
     * resultSimplify == true ==> 사용자에게 필요 없는 결과는 삭제하고 반환 (FRONT에 필요한 최소 결과 반환)
     */
    public IotButtonListInfo getMyIotButtonList
                (int complexId, int homeId, String userId, boolean resultSimplify) throws Exception {
        IotButtonListInfo buttonList;

        try {
            buttonList = this.getMyIotButtonListInternal(complexId, homeId, userId, resultSimplify);
        } catch( IotInfoNoDataException e ) {
            // 가져올 데이터가 없는 경우, 빈 값을 반환 함
            buttonList = new IotButtonListInfo();
        }

        buttonList.setMsg("My IOT의 IOT 버튼 목록 및 정보 가져오기");

        return buttonList;
    }



    /**
     * My IOT의 IOT 버튼 개별 정보 가져오기 (Internal/private)
     */
    private IotButtonListInfo getMyIotButtonListByIdInternal
            (int complexId, int homeId, String userId, int buttonId, boolean resultSimplify) throws Exception
    {
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
        requester.setParameter("seqNo", String.valueOf(buttonId) ); // !! seqNo <-- buttonId
        result = requester.execute();

        // 1. result data 리맵핑
        try {
            this.remapResultMyIotButtonDataList( (List)result.get("DATA") );
            if( resultSimplify ) {
                this.deleteUnusedResultMyIotButtonDataList( (List)result.get("DATA"), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        buttonList.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( buttonList.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "가져올 버튼 정보가 없습니다." );
        }

        return buttonList;
    }

    /**
     * 4. My IOT의 IOT 버튼 개별 정보 가져오기
     */
    public IotButtonListInfo getMyIotButtonListById
                (int complexId, int homeId, String userId, int buttonId, boolean resultSimplify) throws Exception {
        IotButtonListInfo buttonList = new IotButtonListInfo();

        buttonList = this.getMyIotButtonListByIdInternal(complexId, homeId, userId, buttonId, resultSimplify);
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

        // 1. result data 리맵핑
        try {
            this.remapResultRoomOrDeviceCategoryList( (List)result.get("DATA") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
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

        // 1. result data 리맵핑
        try {
            this.remapResultDeviceList( (List)result.get("DATA") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
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
    public IotDeviceListInfo getDeviceInfo(int complexId, int homeId, int deviceId) throws Exception {
        IotDeviceListInfo deviceListInfo = new IotDeviceListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_DEVICE_DETAIL_BY_DEVICE_ID_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("moThingsId", String.valueOf(deviceId) ); // !! deviceId ==> moThingsId
        result = requester.execute();

        // 1. result data 리맵핑
        try {
            this.remapResultDeviceDetailList( (List)result.get("DATA") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
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

        // 1. result data 리맵핑
        try {
            this.remapResultRoomOrDeviceCategoryList( (List)result.get("DATA") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
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

        // 1. result data 리맵핑
        try {
            this.remapResultDeviceList( (List)result.get("DATA") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        deviceListInfo.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( deviceListInfo.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "기기 정보가 없습니다." );
        }

        deviceListInfo.setMsg("카테고리 별 기기 목록 가져오기");

        return deviceListInfo;
    }

    // 15-a. 기기의 '사용내역' 정보 가져오기
    public IotDeviceListInfo getDevicesUsageHistory(int complexId, int homeId, int pageNo, int pageRow)
            throws Exception
    {
        IotDeviceListInfo deviceListInfo = new IotDeviceListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_DEVICES_USAGE_HISTORY_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("pageNo", String.valueOf(pageNo) );
        requester.setParameter("pageRow", String.valueOf(pageRow) );
        result = requester.execute();

        // 1. result data 리맵핑
        try {
            this.remapResultDeviceUsageHistory( (List)result.get("DATA") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        deviceListInfo.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( deviceListInfo.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "사용 내역이 없습니다." );
        }

        deviceListInfo.setMsg("'사용내역' 정보 가져오기");

        return deviceListInfo;
    }

    // 16. 개별 '모드'의 상세 정보
    // modeOrAutomationId == scnaId
    // (modeFlag == true) ==>  Mode
    // (modeFlag == false ) ==> Automation
    public IotModeAutomationInfo getModeOrAutomationDetail(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception {
        IotModeAutomationInfo modeAutoInfo = new IotModeAutomationInfo();
        HttpGetRequester requester;
        Map<String, Map> result;
        Map<String, Object> scnaInfo;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODE_DETAIL_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("scnaId", String.valueOf(modeOrAutomationId) );
        result = requester.execute();

        // Validation

        if(result.get("SCNA") == null || ((List)result.get("SCNA")).size() < 1) {
            if( modeFlag ) {
                throw new IotInfoNoDataException("해당 '모드' 정보가 없습니다.");
            } else {
                throw new IotInfoNoDataException("해당 '자동화' 정보가 없습니다.");
            }
        }
        scnaInfo = (Map)((List)result.get("SCNA")).get(0);

        // Population & mapping

        // 아이콘 체크
        if( scnaInfo.get("icon") == null || "".equals( scnaInfo.get("icon") )) {
            scnaInfo.put("icon", AUTOMATION_DEFUALT_ICON);
        }

        try {
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_THINGS") );
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_SPC") );
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_APLY") );
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_OPTION") );
            this.remapResultScenarioDetail( (List)result.get("SCNA_THINGS") );
            this.remapResultScenarioDetail( (List)result.get("SCNA") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        modeAutoInfo.setScnaIfThings( convertListMapDataToCamelCase((List)result.get("SCNA_IF_THINGS")) );
        modeAutoInfo.setScnaIfSpc( convertListMapDataToCamelCase((List)result.get("SCNA_IF_SPC")) );
        modeAutoInfo.setScnaIfAply( convertListMapDataToCamelCase((List)result.get("SCNA_IF_APLY")) );
        modeAutoInfo.setScnaIfOption( convertListMapDataToCamelCase((List)result.get("SCNA_IF_OPTION")) );
        modeAutoInfo.setScnaThings( convertListMapDataToCamelCase((List)result.get("SCNA_THINGS")) );
        modeAutoInfo.setScna( convertListMapDataToCamelCase((List)result.get("SCNA")) );

        if( modeFlag ) {
            modeAutoInfo.setMsg("개별 '모드'의 상세 정보");
        } else {
            modeAutoInfo.setMsg("'오토메이션'의 상세 정보");
        }

        return modeAutoInfo;
    }

    // 22. '모드' 편집에서, 모드 목록의 순서를 변경하기
    public IotModeOrAutomationListInfo updateModesOrder(int complexId, int homeId, IotModeOrAutomationListInfo modeList) throws Exception {
        HttpPostRequester         requester;
        List<Map<String, Object>> data = modeList.getData();
        Map<String, String>       result;  // e.g. { "msg":"...", "resFlag": "false or true" }
        ObjectMapper              mapper = new ObjectMapper();

        // Populate the body of request
        for(Map<String, Object> e : data) {
            e.put("cmplxId", String.valueOf(complexId));
            e.put("homeId", String.valueOf(homeId));
        }

        // Send a POST request to...
        String reqBodyStr = mapper.writeValueAsString(data);
        logger.debug(">>>>> " +reqBodyStr);
        requester = new HttpPostRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODE_ORDER_CHANGE_PATH );
        requester.setBody(reqBodyStr);
        result = requester.execute();

        logger.debug(">>>> " + result.toString());
        if( result.get("msg") == null || !result.get("msg").equals("설정이 완료되었습니다.") ) {
            throw new IotInfoUpdateFailedException("모드의 순서 변경이 실패하였습니다.");
        }

        modeList.setMsg((result.get("msg")));

        return modeList;
    }


    private IotButtonListInfo getMyIotButtonListAvailableInternal(int complexId, int homeId, String userId) throws Exception {
        IotButtonListInfo   buttonListInfo = new IotButtonListInfo();
        HttpGetRequester    requester;
        Map<String, Map>    result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MYIOT_AVAILABLE_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId));
        requester.setParameter("homeId", String.valueOf(homeId));
        requester.setParameter("userId", userId);
        result = requester.execute();

        try {
            this.remapResultMyIotButtonDataList( (List)result.get("DATA") );
            this.deleteUnusedResultMyIotButtonDataList( (List)result.get("DATA"), true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        buttonListInfo.setData( convertListMapDataToCamelCase((List)result.get("DATA")) );

        return buttonListInfo;
    }


    // 24. MyIOT에 추가가능한 모든 버튼 목록 가져오기
    public IotButtonListInfo getMyIotButtonListAvailable (int complexId, int homeId, String userId) throws Exception {
        IotButtonListInfo   buttonListInfo;

        buttonListInfo = getMyIotButtonListAvailableInternal(complexId, homeId, userId);

        buttonListInfo.setMsg("MyIOT에 추가가능한 모든 버튼 목록 가져오기");
        return buttonListInfo;
    }

    // 24-2. MyIOT내, 추가 가능한 시나리오 목록 가져오기
    public IotButtonListInfo getMyIotAutomationListAvailable
            (int complexId, int homeId, String userId) throws Exception {
        IotButtonListInfo   buttonListInfo = new IotButtonListInfo();
        HttpGetRequester    requester;
        Map<String, Map>    result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MYIOT_AUTOMATION_AVAILABLE_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId));
        requester.setParameter("homeId", String.valueOf(homeId));
        requester.setParameter("userId", userId);
        result = requester.execute();

        try {
            this.remapResultMyIotButtonDataList( (List)result.get("DATA") );
            this.deleteUnusedResultMyIotButtonDataList( (List)result.get("DATA"), true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        buttonListInfo.setData( convertListMapDataToCamelCase((List)result.get("DATA")) );

        buttonListInfo.setMsg("MyIOT에 추가가능한 자동화 목록 가져오기");

        return buttonListInfo;
    }

    // 24-3. MyIOT내, 추가 가능한 기기(공간+카테고리 정보 포함) 목록 가져오기
    private IotButtonListInfo getMyIotDevicesListAvailableInternal
            (int complexId, int homeId, String userId) throws Exception {
        IotButtonListInfo   buttonListInfo = new IotButtonListInfo();
        HttpGetRequester    requester;
        Map<String, Map>    result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MYIOT_DEVICES_AVAILABLE_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId));
        requester.setParameter("homeId", String.valueOf(homeId));
        requester.setParameter("userId", userId);
        result = requester.execute();

        try {
            this.remapResultMyIotButtonDataList( (List)result.get("DATA") );
            this.deleteUnusedResultMyIotButtonDataList( (List)result.get("DATA"), true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        buttonListInfo.setData( convertListMapDataToCamelCase((List)result.get("DATA")) );

//        buttonListInfo.setMsg("MyIOT에 추가가능한 기기 목록 가져오기");

        return buttonListInfo;
    }


    // 24-3-1. MyIOT내, 추가 가능한 기기의 "공간 목록" 가져오기
    public IotRoomListInfo getMyIotRoomsListAvailable(int complexId, int homeId, String userId) throws Exception {
        IotButtonListInfo           buttonList;
        List<Map<String, Object>>   buttonListData;
        Map<String, Map>            roomsMap = new TreeMap<>();
        List                        roomList = new ArrayList();
        IotRoomListInfo             roomListInfo = new IotRoomListInfo();

        buttonList = this.getMyIotDevicesListAvailableInternal(complexId, homeId, userId);

        buttonListData = buttonList.getData();
        if( buttonListData.size() < 1 ){
            throw new IotInfoNoDataException("공간 목록이 없습니다.");
        }

        for( Map<String, Object> e : buttonListData ) {
            Map v = roomsMap.get( String.valueOf(e.get("roomId")) );
            logger.debug(">>>> roomId:" + e.get("roomId"));
            if( v == null ) {
                Map newValue = new TreeMap();
                newValue.put("roomId", e.get("roomId"));
                newValue.put("roomNm", e.get("roomNm"));
                newValue.put("typeCd", e.get("typeCd"));
                newValue.put("imgSrc", iconService.getIconFromRoomType((String)e.get("typeCd")));

                roomsMap.put( String.valueOf(e.get("roomId")), newValue );
            }
        }

        for( String k : roomsMap.keySet() ) {
            logger.debug(">>>> " + roomsMap.get(k));
            roomList.add( roomsMap.get(k) );
        }

        roomListInfo.setData(roomList);
        roomListInfo.setMsg("추가 가능한 기기의 '공간 목록' 가져오기");

        return roomListInfo;
    }

    // 24-3-2. MyIOT내, 추가 가능한 기기 목록 가져오기 / 공간별
    public IotButtonListInfo getMyIotDevicesListByRoomAvailable (int complexId, int homeId, String userId, int roomId) throws Exception {
        IotButtonListInfo           buttonList;
        List<Map<String, Object>>   buttonListData;

        buttonList = this.getMyIotDevicesListAvailableInternal(complexId, homeId, userId);

        buttonListData = buttonList.getData();
        if( buttonListData.size() < 1 ){
            throw new IotInfoNoDataException("기기 정보가 없습니다.");
        }

        // Filtering
        Iterator iter = buttonListData.iterator();
        while( iter.hasNext() ) {
            Map v = (Map)iter.next();
            if(String.valueOf(v.get("roomId")).equals(String.valueOf(roomId))) {
                // keep
            } else {
                iter.remove();
            }
        }

        if( buttonListData.size() < 1 ) {
            throw new IotInfoNoDataException("기기 정보가 없습니다.");
        }

        buttonList.setMsg("추가 가능한 기기 목록 가져오기 / 공간별");

        return buttonList;
    }

    // 24-3-3. MyIOT내, 추가 가능한 기기의 "기기카테고리 목록" 가져오기
    public IotDeviceGroupListInfo getMyIotDeviceCategoryListAvailable (int complexId, int homeId, String userId) throws Exception {
        IotButtonListInfo           buttonList;
        List<Map<String, Object>>   buttonListData;
        Map<String, Map>            categoryMap = new TreeMap<>();
        List                        categoryList = new ArrayList();
        IotDeviceGroupListInfo      categoryListInfo = new IotDeviceGroupListInfo();

        buttonList = this.getMyIotDevicesListAvailableInternal(complexId, homeId, userId);

        buttonListData = buttonList.getData();
        if( buttonListData.size() < 1 ){
            throw new IotInfoNoDataException("기기 카테고리 목록이 없습니다.");
        }

        for( Map<String, Object> e : buttonListData ) {
            Map v = categoryMap.get( String.valueOf(e.get("cateCd")) );
            logger.debug(">>>> cateCd:" + e.get("cateCd"));
            if( v == null ) {
                Map newValue = new TreeMap();
                newValue.put("cateNm", e.get("cateNm"));
                newValue.put("cateCd", e.get("cateCd"));
                newValue.put("imgSrc", iconService.getIconFromDeviceCategory((String)e.get("cateCd")));

                categoryMap.put( String.valueOf(e.get("cateCd")), newValue );
            }
        }

        for( String k : categoryMap.keySet() ) {
            logger.debug(">>>> " + categoryMap.get(k));
            categoryList.add( categoryMap.get(k) );
        }

        categoryListInfo.setData(categoryList);
        categoryListInfo.setMsg("추가 가능한 기기의 '기기 카테고리 목록' 가져오기");

        return categoryListInfo;
    }

    // 24-3-4. MyIOT내, 추가 가능한 기기 목록 가져오기 / 기기카테고리별
    public IotButtonListInfo getMyIotDevicesListByCategoryAvailable (int complexId, int homeId, String userId, String categoryCode) throws Exception {
        IotButtonListInfo           buttonList;
        List<Map<String, Object>>   buttonListData;

        buttonList = this.getMyIotDevicesListAvailableInternal(complexId, homeId, userId);

        buttonListData = buttonList.getData();
        if( buttonListData.size() < 1 ){
            throw new IotInfoNoDataException("기기 정보가 없습니다.");
        }

        // Filtering
        Iterator iter = buttonListData.iterator();
        while( iter.hasNext() ) {
            Map v = (Map)iter.next();
            if(String.valueOf(v.get("cateCd")).equals(categoryCode)) {
                // keep
            } else {
                iter.remove();
            }
        }

        if( buttonListData.size() < 1 ) {
            throw new IotInfoNoDataException("기기 정보가 없습니다.");
        }

        buttonList.setMsg("추가 가능한 기기 목록 가져오기 / 기기카테고리별");

        return buttonList;
    }


    // 24-4. MyIOT내, 추가 가능한 가치정보 목록 가져오기
    public IotButtonListInfo getMyIotValueInfoListAvailable
            (int complexId, int homeId, String userId) throws Exception {
        IotButtonListInfo   buttonListInfo = new IotButtonListInfo();
        HttpGetRequester    requester;
        Map<String, Map>    result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MYIOT_VALUE_INFO_AVAILABLE_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId));
        requester.setParameter("homeId", String.valueOf(homeId));
        requester.setParameter("userId", userId);
        result = requester.execute();

        try {
            this.remapResultMyIotButtonDataList( (List)result.get("DATA") );
            this.deleteUnusedResultMyIotButtonDataList( (List)result.get("DATA"), true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        buttonListInfo.setData( convertListMapDataToCamelCase((List)result.get("DATA")) );

        buttonListInfo.setMsg("MyIOT에 추가가능한 모든 버튼 목록 가져오기");

        return buttonListInfo;
    }


    // 26. MyIOT 편집 화면에서 '기기/시나리오/정보'의 신규등록
    public IotButtonListInfo addMyIotButtonByMyIotID
            (int complexId, int homeId, String userId, List<Map<String, Object>> myIotIdList) throws Exception {
        IotButtonListInfo   addedButtonList = new IotButtonListInfo();
        IotButtonListInfo   availableButtonList;
        HttpPostRequester   requester;
        Map<String, Map>    result;

        Map<String, String> mapperMidToGcCode = new HashMap<>();

        // 1.
        availableButtonList = getMyIotButtonListAvailableInternal(complexId, homeId, userId);
        for(Map<String, Object> e : availableButtonList.getData()) {
            logger.debug(">>>> mId/myIotGbCd: " + e.get("mId") + "/" + e.get("myIotGbCd"));
            mapperMidToGcCode.put( (String)e.get("mId"), (String)e.get("myIotGbCd"));
        }

        Iterator<Map<String, Object>> iter = myIotIdList.iterator();
        while( iter.hasNext() )
        {
            Map<String, Object> e = iter.next();

            e.put("cmplxId", String.valueOf(complexId));
            e.put("homeId", String.valueOf(homeId));
            e.put("userId", userId);
            if( mapperMidToGcCode.get(e.get("myIotId")) == null ) {
                // 이미 추가된 기기/시나리오의 경우, 무시함
                iter.remove();
                continue;
            }
            e.put("myIotGbCd", mapperMidToGcCode.get(String.valueOf(e.get("myIotId"))));
            e.put("mid", e.get("myIotId"));  // !! myIotId ---> mid
            e.remove("myIotId");
            e.put("seqNo", "");    // 생성시 => ":
        }

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String gsonout = gson.toJson(myIotIdList);
        logger.debug( ">>>>>> " + gsonout);

        // 2.
        requester = new HttpPostRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MYIOT_ADD_PATH );
        requester.setBody(gsonout);
        result = requester.execute();
        logger.debug(">>> msg: " + result.get("msg"));
        logger.debug(">>> resFlag: " + result.get("resFlag"));  // BUGBUG: 항상 false로 반환 됨

        addedButtonList.setData(myIotIdList);
        addedButtonList.setMsg("MyIOT에 선택 항목을 추가하였습니다.");

        return addedButtonList;
    }

    // 26-2. MyIOT 편집 화면에서 '기기/시나리오/정보'의 순서를 변경하기
    public IotButtonListInfo updateMyIotButtonOrder
            (int complexId, int homeId, String userId, List<Map<String, Object>> myIotIdOrderList) throws Exception {
        IotButtonListInfo   buttonList;
        IotButtonListInfo   availableButtonList;
        HttpPostRequester   requester;
        Map<String, Map>    result;
        Map<String, String> mapperMidToGcCode = new HashMap<>();

        // 1. 전체 목록 가져오기
        buttonList = this.getMyIotButtonListInternal(complexId, homeId, userId, false);
        for(Map<String, Object> e : buttonList.getData()) {
            logger.debug(">>>> seqNo/myIotGbCd: " + e.get("btId") + "/" + e.get("myIotGbCd"));
            mapperMidToGcCode.put( String.valueOf(e.get("btId")), (String)e.get("myIotGbCd"));
        }

        Iterator<Map<String, Object>> iter = myIotIdOrderList.iterator();
        while( iter.hasNext() )
        {
            String btId;
            String myIotGbCd;
            Map<String, Object> e = iter.next();

            e.put("cmplxId", String.valueOf(complexId));
            e.put("homeId", String.valueOf(homeId));
            e.put("userId", userId);

            btId = String.valueOf(e.get("btId"));
            myIotGbCd = mapperMidToGcCode.get(btId);
            logger.debug(">>>>>> btId/myIotGbCd:" + btId + "/" + myIotGbCd);
            if( myIotGbCd == null ) {
                // 잘못된 입력 값
                throw new IotInfoUpdateFailedException("순서 변경이 실패하였습니다. 입력 값을 다시 확인하세요.");
            }
            e.put("myIotGbCd", myIotGbCd);
            e.put("seqNo", btId);
        }

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String gsonout = gson.toJson(myIotIdOrderList);
        logger.debug( ">>>>>> " + gsonout);

        // 2. 업데이트 수행
        requester = new HttpPostRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MYIOT_UPDATE_ORDER_PATH );
        requester.setBody(gsonout);
        result = requester.execute();
        logger.debug(">>> msg: " + result.get("msg"));
        logger.debug(">>> resFlag: " + result.get("resFlag"));  // BUGBUG: 항상 false로 반환 됨

        buttonList.setData(myIotIdOrderList);
        buttonList.setMsg("MyIOT 버튼의 순서를 변경하였습니다.");

        return buttonList;
    }


    public IotButtonListInfo simplifyMyIotButtonListResult( IotButtonListInfo result ) throws Exception {
        this.deleteUnusedResultMyIotButtonDataList( (List)result.getData(), false);
        return result;
    }

    // 27. MyIOT 편집 화면에서 '기기/시나리오/정보'를 목록에서 제거하기
    public IotButtonListInfo deleteMyIotButtonListById
            (int complexId, int homeId, String userId, int buttonId, boolean resultSimplify) throws Exception
    {
        IotButtonListInfo   buttonListInfo;
        HttpDeleteRequester requester;
        Map<String, String> result;
        String bodyStr;

        try {
            buttonListInfo = getMyIotButtonListByIdInternal(complexId, homeId, userId, buttonId, resultSimplify);
        } catch( IotInfoNoDataException e ) {
            // 예외 메시지 변환
            throw new IotInfoNoDataException("해당하는 MyIOT의 항목이 없습니다.");
        }

        requester = new HttpDeleteRequester(
            httpClient,
            serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
            IOK_MYIOT_DELETE_PATH );

        Map msg = new HashMap();
        msg.put("cmplxId", String.valueOf(complexId));
        msg.put("homeId", String.valueOf(homeId));
        msg.put("userId", userId);
        msg.put("seqNo", buttonId); // buttonId == seqNo

        ObjectMapper mapper = new ObjectMapper();
        List msgList = new ArrayList();
        msgList.add(msg);
        bodyStr = mapper.writeValueAsString(msgList);
        logger.debug(">>> msg:" + bodyStr);

        requester.setBody(bodyStr);
        result = requester.execute();
        logger.debug(">>> msg: " + result.get("msg"));
        logger.debug(">>> resFlag: " + result.get("resFlag"));  // BUGBUG: 항상 false로 반환 됨

        buttonListInfo.setMsg("MyIOT의 버튼 삭제하기 : " + result.get("msg"));

        return buttonListInfo;
    }

    // 29. MyIOT에서 '시나리오/오토메이션' 생성하기
    public IotModeAutomationIdInfo createAutomation(
            int complexId, int homeId, String userId, IotModeAutomationInfo automationInfo) throws Exception
    {
        HttpPostRequester    requester;
        Map<String, String>  result;
        IotModeAutomationIdInfo createdAutomationInfo = new IotModeAutomationIdInfo();
        Map<String, String>  scnaInfo;

        // validation
        if( automationInfo.getScna() == null || automationInfo.getScna().size() < 1 ) {
            throw new IotInfoUpdateFailedException("자동화 입력값이 잘못되었습니다. 입력 내용을 다시 확인하세요.");
        }

        scnaInfo =(Map)automationInfo.getScna().get(0);

        // 시나리오 생성시에 scna.scnaId 값이 설정되어있으면 IOK api는 생성 대신, 업데이트로 동작합니다. 따라서, scnaId가 입력되면 에러를 반환합니다.
        if( scnaInfo.get("scnaId") != null ) {
            throw new IotInfoGeneralException("입력된 정보가 잘못되었습니다. 새로운 자동화를 생성할 때, ID를 포함하면 안됩니다.");
        }

        //
        if( (automationInfo.getScnaIfSpc() != null) && (automationInfo.getScnaIfSpc().size() > 0) &&
            (automationInfo.getScnaIfAply() != null) && (automationInfo.getScnaIfAply().size() > 0) ){
            // IfSpc && IfAply는 같이 사용할 수 없음
            throw new IotInfoGeneralException("'특정시간'조건과 '구간시간' 조건은 같이 사용할 수 없습니다.");
        }

        // population
        if(automationInfo.getScnaIfSpc() == null) {
            automationInfo.setScnaIfSpc(new ArrayList<Map<String, Object>>());
        }

        if(automationInfo.getScnaIfAply() == null) {
            automationInfo.setScnaIfAply(new ArrayList<Map<String, Object>>());
        }

        if(automationInfo.getScnaIfThings() == null) {
            automationInfo.setScnaIfThings(new ArrayList<Map<String, Object>>());
        }

        if(automationInfo.getScnaThings() == null) {
            automationInfo.setScnaThings(new ArrayList<Map<String, Object>>());
        }

        // 사용자 시나리오/오토메이션 생성시, "mode":"CM01199"를 전달해야 함
        scnaInfo.put("mode", USER_SCENARIO_MODE_CODE);
        scnaInfo.put("useYn", "Y");
        scnaInfo.put("userId", (userId != null) ? userId : "");
        if( scnaInfo.get("icon") == null ) {
            scnaInfo.put("icon", AUTOMATION_DEFUALT_ICON);
        }

        Map populateData = new HashMap();
        populateData.put("cmplxId", String.valueOf(complexId));
        populateData.put("homeId", String.valueOf(homeId));

        this.remapResultScenarioDetailReverse(automationInfo.getScna(), populateData);
        this.remapResultScenarioDetailReverse(automationInfo.getScnaIfThings(), populateData);
        this.remapResultScenarioDetailReverse(automationInfo.getScnaThings(), populateData);

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String gsonout = gson.toJson(automationInfo);
        logger.debug( ">>>>>> " + gsonout);

        // call!
        requester = new HttpPostRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_AUTOMATION_DETAIL_SAVE_PATH );
        requester.setBody( gsonout );
        result = requester.execute();

        if( result.get("scnaId") != null && (result.get("scnaId") instanceof String) ) {
            // Success
            createdAutomationInfo.setAutomationId( Integer.parseInt((String)result.get("scnaId")) );
            createdAutomationInfo.setMsg("'자동화'를 생성하였습니다.");
        }

        return createdAutomationInfo;
    }

    // 30. MyIOT에서 '시나리오/오토메이션' 업데이트하기
    public IotModeAutomationIdInfo updateAutomation(
            int                     complexId,
            int                     homeId,
            int                     automationId,
            String                  userId,
            IotModeAutomationInfo   automationInfo,
            boolean                 modeFlag            ) throws Exception
    {
        HttpPostRequester    requester;
        Map<String, String>  result;
        IotModeAutomationIdInfo createdAutomationInfo = new IotModeAutomationIdInfo();
        Map<String, String>  scnaInfo;

        // validation
        if( (automationInfo.getScna() ==  null) || (automationInfo.getScna().size() < 1) ) {
            if( modeFlag ) {
                throw new IotInfoGeneralException("'모드' 기본정보가 입력되지 않았습니다. 입력을 다시 확인하세요.");
            } else {
                throw new IotInfoGeneralException("'자동화' 기본정보가 입력되지 않았습니다. 입력을 다시 확인하세요.");
            }
        }
        scnaInfo = (Map)automationInfo.getScna().get(0);

        if( scnaInfo.get("mode") != null ) {
            // 업데이트 시에는 모드 값이 없어야 함
            // mode값이 들어오면 IOK-API에서 시나리오 !생성!하기 때문
            if( modeFlag ) {
                throw new IotInfoGeneralException("'모드' 기본정보 입력이 잘못되었습니다. 입력값을 다시 확인하세요.");
            } else {
                throw new IotInfoGeneralException("'자동화' 기본정보 입력이 잘못되었습니다. 입력값을 다시 확인하세요.");
            }
        }

        logger.debug(">>> automationId: " + automationId);
        logger.debug(">>> automationInfo.getScna().size(): " + automationInfo.getScna().size());
        logger.debug(">>> automationInfo.getScnaIfSpc(): " + automationInfo.getScnaIfSpc());
        logger.debug(">>> automationInfo.getScnaIfAply(): " + automationInfo.getScnaIfAply());
        logger.debug(">>> getScna().size(): " + automationInfo.getScna().size());

        if( (automationInfo.getScnaIfSpc() != null) && (automationInfo.getScnaIfSpc().size() > 0) &&
            (automationInfo.getScnaIfAply() != null) && (automationInfo.getScnaIfAply().size() > 0) ){
            logger.debug(">>> automationInfo.getScnaIfSpc().size(): " + automationInfo.getScnaIfSpc().size());
            logger.debug(">>> automationInfo.getScnaIfAply().size(): " + automationInfo.getScnaIfAply().size());
            logger.debug(">>> automationInfo.getScnaIfSpc().get(0).get(\"chk\"): " + automationInfo.getScnaIfSpc().get(0).get("chk"));
            logger.debug(">>> automationInfo.getScnaIfAply().get(0).get(\"chk\"): " + automationInfo.getScnaIfAply().get(0).get("chk"));

            throw new IotInfoGeneralException("'특정시간'조건과 '구간시간' 조건은 같이 사용할 수 없습니다.");
        }

        // population
        // 시나리오 아이디 셋팅
        scnaInfo.put("scnaId", String.valueOf(automationId));
        scnaInfo.put("userId", (userId != null) ? userId : "");

        if(automationInfo.getScnaIfSpc() == null) {
            automationInfo.setScnaIfSpc(new ArrayList<Map<String, Object>>());
        }

        if(automationInfo.getScnaIfAply() == null) {
            automationInfo.setScnaIfAply(new ArrayList<Map<String, Object>>());
        }

        if(automationInfo.getScnaIfThings() == null) {
            automationInfo.setScnaIfThings(new ArrayList<Map<String, Object>>());
        }

        if(automationInfo.getScnaThings() == null) {
            automationInfo.setScnaThings(new ArrayList<Map<String, Object>>());
        }

        scnaInfo.put("useYn", "Y");

        Map populateData = new HashMap();
        populateData.put("cmplxId", String.valueOf(complexId));
        populateData.put("homeId", String.valueOf(homeId));
        populateData.put("scnaId", String.valueOf(automationId));

        this.remapResultScenarioDetailReverse(automationInfo.getScna(), populateData);
        this.remapResultScenarioDetailReverse(automationInfo.getScnaIfThings(), populateData);
        this.remapResultScenarioDetailReverse(automationInfo.getScnaThings(), populateData);

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String gsonout = gson.toJson(automationInfo);
        logger.debug( ">>>>>> " + gsonout);

        // execution
        requester = new HttpPostRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_AUTOMATION_DETAIL_SAVE_PATH );
        requester.setBody( gsonout );
        result = requester.execute();

        if( result.get("scnaId") != null && (result.get("scnaId") instanceof String) ) {
            // Success
            createdAutomationInfo.setAutomationId( Integer.parseInt((String)result.get("scnaId")) );
            if( modeFlag ) {
                createdAutomationInfo.setMsg("'모드'를 업데이트하였습니다.");
            } else {
                createdAutomationInfo.setMsg("'자동화'를 업데이트하였습니다.");
            }
        }

        return createdAutomationInfo;
    }

    // 31. 특정 시나리오의 '조건(IF)' 목록 및 속성 가져오기
    public IotModeAutomationInfo getModeOrAutomationConditions(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception {
        IotModeAutomationInfo conditionsInfo = new IotModeAutomationInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODE_DETAIL_CONDITONS_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("scnaId", String.valueOf(modeOrAutomationId) );
        result = requester.execute();

        // 1. result data 리맵핑
        try {
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_THINGS") );
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_SPC") );
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_APLY") );
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_OPTION") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        conditionsInfo.setScnaIfThings( convertListMapDataToCamelCase((List)result.get("SCNA_IF_THINGS")) );
        conditionsInfo.setScnaIfSpc( convertListMapDataToCamelCase((List)result.get("SCNA_IF_SPC")) );
        conditionsInfo.setScnaIfAply( convertListMapDataToCamelCase((List)result.get("SCNA_IF_APLY")) );
        conditionsInfo.setScnaIfOption( convertListMapDataToCamelCase((List)result.get("SCNA_IF_OPTION")) );

        if( modeFlag ) {
            conditionsInfo.setMsg("개별 '모드'의 조건(IF) 정보");
        } else {
            conditionsInfo.setMsg("'오토메이션'의 조건(IF) 정보");
        }

        return conditionsInfo;
    }

    // 33. 특정 시나리오의 '작동기기(ACTOR)' 목록 및 속성 가져오기
    public IotModeAutomationInfo getModeOrAutomationActors(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception {
        IotModeAutomationInfo actorsInfo = new IotModeAutomationInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODE_DETAIL_ACTORS_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("scnaId", String.valueOf(modeOrAutomationId) );
        result = requester.execute();

        // 1. result data 리맵핑
        try {
            this.remapResultScenarioDetail( (List)result.get("SCNA_THINGS") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        actorsInfo.setScnaThings( convertListMapDataToCamelCase((List)result.get("SCNA_THINGS")) );

        if( modeFlag ) {
            actorsInfo.setMsg("개별 '모드'의 작동기기(ACTOR) 정보");
        } else {
            actorsInfo.setMsg("'오토메이션'의 작동기기(ACTOR) 정보");
        }

        return actorsInfo;
    }

    // 33-2. 특정 시나리오의 '작동기기' 상세조회
    public IotDeviceListInfo getModeOrAutomationActorDetail(
            int complexId, int homeId, int modeOrAutomationId, int deviceId, boolean modeFlag) throws Exception {
        IotDeviceListInfo actorDetailInfo = new IotDeviceListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODE_DETAIL_ACTOR_DETAIL_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("scnaId", String.valueOf(modeOrAutomationId) );
        requester.setParameter("moThingsId", String.valueOf(deviceId) );  // !! deviceId --> moThingsId
        result = requester.execute();

        // 1. result data 리맵핑
        try {
            this.remapResultDeviceDetailList( (List)result.get("DATA") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        actorDetailInfo.setData( convertListMapDataToCamelCase((List)result.get("DATA")) );

        if( modeFlag ) {
            actorDetailInfo.setMsg("'모드'의 작동기기(ACTOR) 상세 정보");
        } else {
            actorDetailInfo.setMsg("'자동화'의 작동기기(ACTOR) 상세 정보");
        }

        return actorDetailInfo;
    }

    // 39. 특정 시나리오에서 추가조건(IF) 목록(리스트)를 가져오기 at MyIOT 추가
    public IotModeAutomationInfo getModeOrAutomationConditionsAvailable(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception {
        IotModeAutomationInfo conditionsInfo = new IotModeAutomationInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODE_DETAIL_CONDITONS_AVAILABLE_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("scnaId", String.valueOf(modeOrAutomationId) );
        result = requester.execute();

        // 1. result data 리맵핑
        try {
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_THINGS") );
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_SPC") );
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_APLY") );
            this.remapResultScenarioDetail( (List)result.get("SCNA_IF_OPTION") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        conditionsInfo.setScnaIfThings( convertListMapDataToCamelCase((List)result.get("SCNA_IF_THINGS")) );
        conditionsInfo.setScnaIfSpc( convertListMapDataToCamelCase((List)result.get("SCNA_IF_SPC")) );
        conditionsInfo.setScnaIfAply( convertListMapDataToCamelCase((List)result.get("SCNA_IF_APLY")) );
        conditionsInfo.setScnaIfOption( convertListMapDataToCamelCase((List)result.get("SCNA_IF_OPTION")) );

        if( modeFlag ) {
            conditionsInfo.setMsg("개별 '모드'의 추가조건(IF) 목록 정보");
        } else {
            conditionsInfo.setMsg("'오토메이션'의 추가조건(IF) 목록 정보");
        }

        return conditionsInfo;
    }


    // 40. 특정 시나리오에서 추가작동기기(ACTOR) 목록(리스트)를 가져오기 at MyIOT 추가
    public IotModeAutomationInfo getModeOrAutomationActorsAvailable(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception {
        IotModeAutomationInfo actorsInfo = new IotModeAutomationInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODE_DETAIL_ACTORS_AVAILABLE_PATH);
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("scnaId", String.valueOf(modeOrAutomationId) );
        result = requester.execute();

        // 1. result data 리맵핑
        try {
            this.remapResultScenarioDetail( (List)result.get("SCNA_THINGS") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        actorsInfo.setScnaThings( convertListMapDataToCamelCase((List)result.get("SCNA_THINGS")) );

        if( modeFlag ) {
            actorsInfo.setMsg("개별 '모드'의 추가작동기기 목록 정보");
        } else {
            actorsInfo.setMsg("'오토메이션'의 추가작동기기 목록 정보");
        }

        return actorsInfo;
    }


    // 42. 관련장비 이름 수정
    public IotDeviceListInfo updateDeviceDesc
        (int complexId, int homeId, int deviceId, String description) throws Exception {
        IotDeviceListInfo deviceInfo = new IotDeviceListInfo();
        HttpGetRequester          requester;
        Map<String, String>       result;  // e.g. { "msg":"...", "resFlag": "false or true" }
        ObjectMapper              mapper = new ObjectMapper();

        // Send a POST request to...
        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_DEVICE_NAME_UPDATE_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId));
        requester.setParameter("moThingsId", String.valueOf(deviceId)); // !! deviceId ==> moThingsId
        requester.setParameter("dvcNm", String.valueOf(description));
        result = requester.execute();

        logger.debug(">>>> " + result.toString());
        if( result.get("msg") == null || !result.get("msg").equals("설정이 완료되었습니다.") ) {
            throw new IotInfoUpdateFailedException("모드의 순서 변경이 실패하였습니다.");
        }

        deviceInfo.setMsg((result.get("msg")));

        return deviceInfo;
    }


    // 43. '시나리오/오토메이션' 삭제하기
    public IotModeAutomationIdInfo deleteAutomation(int complexId, int homeId, int automationId) throws Exception {
        IotModeAutomationIdInfo   automationIdInfo = new IotModeAutomationIdInfo();
        HttpDeleteRequester requester;
        Map<String, String> result;
        String bodyStr;


        requester = new HttpDeleteRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_AUTOMATION_DELETE_PATH );

        Map msg = new HashMap();
        Map scnaMsg = new HashMap();
        scnaMsg.put("cmplxId", String.valueOf(complexId));
        scnaMsg.put("homeId", String.valueOf(homeId));
        scnaMsg.put("scnaId", String.valueOf(automationId));

        ObjectMapper mapper = new ObjectMapper();
        List msgList = new ArrayList();
        msgList.add(scnaMsg);
        msg.put("SCNA", msgList);
        bodyStr = mapper.writeValueAsString(msg);
        logger.debug(">>> msg:" + bodyStr);
        requester.setBody(bodyStr);

        result = requester.execute();

        logger.debug(">>> msg: " + result.get("msg"));
        logger.debug(">>> resFlag: " + result.get("resFlag"));  // BUGBUG: 항상 false로 반환 됨

        automationIdInfo.setAutomationId(automationId);
        automationIdInfo.setMsg("'자동화'가 삭제되었습니다.");

        return automationIdInfo;
    }

    // 44. 전체 시나리오 리스트 조회
    public IotModeOrAutomationListInfo getAutomationAll(
            int complexId, int homeId) throws Exception {
        IotModeOrAutomationListInfo automationList = new IotModeOrAutomationListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_AUTOMATION_LIST_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        result = requester.execute();

        // 1. result data 리맵핑
        this.remapResultModeDataList( (List)result.get("DATA") );

        // 2. camelCase로 변환
        automationList.setData(
                convertListMapDataToCamelCase( (List)result.get("DATA") ));

        if( automationList.getData().isEmpty() )
        {
            throw new IotInfoNoDataException( "정의된 '자동화'가 없습니다." );
        }

        // 3. 결과 정보 입력
        automationList.setMsg("'자동화' 전체 목록 가져오기");

        return automationList;

    }

    // 45. 시나리오에서 생성시, 추가조건(IF) 목록(리스트)를 가져오기
    public IotModeAutomationInfo getModeOrAutomationAllConditions(
            int complexId, int homeId, boolean modeFlag) throws Exception {
        IotModeAutomationInfo conditionsInfo = new IotModeAutomationInfo();
        HttpGetRequester      requester;
        Map<String, List>     result;
        String                anyId = "-1"; // 모든 값을 가져오기 위해 없는 값입력

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODE_DETAIL_CONDITONS_AVAILABLE_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("scnaId", anyId);
        result = requester.execute();

        // 1. result data 리맵핑
        try {
            this.remapResultScenarioDetail( result.get("SCNA_IF_THINGS") );
            this.remapResultScenarioDetail( result.get("SCNA_IF_SPC") );
            this.remapResultScenarioDetail( result.get("SCNA_IF_APLY") );

            if( result.get("SCNA_IF_OPTION") == null ) {
                // BUGBUG: Front에서 조건을 표시하고, 값을 가져오는데 SCNA_IF_OPTION이 필요하여 HARDCODE 함
                //  만약, 조건 코드를 변경할 경우, 본 값도 변경해야 함
                List scnaIfOption = new ArrayList();
                Map condi = new HashMap();
                condi.put("CONDI", "CM00801");
                condi.put("COMN_CDNM", "같다");
                scnaIfOption.add(condi);
                condi = new HashMap();
                condi.put("CONDI", "CM00806");
                condi.put("COMN_CDNM", "이상");
                scnaIfOption.add(condi);
                condi = new HashMap();
                condi.put("CONDI", "CM00807");
                condi.put("COMN_CDNM", "이하");
                scnaIfOption.add(condi);
                result.put("SCNA_IF_OPTION", scnaIfOption);
            } else {
                this.remapResultScenarioDetail( result.get("SCNA_IF_OPTION") );
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        conditionsInfo.setScnaIfThings( convertListMapDataToCamelCase(result.get("SCNA_IF_THINGS")) );
        conditionsInfo.setScnaIfSpc( convertListMapDataToCamelCase(result.get("SCNA_IF_SPC")) );
        conditionsInfo.setScnaIfAply( convertListMapDataToCamelCase(result.get("SCNA_IF_APLY")) );
        conditionsInfo.setScnaIfOption( convertListMapDataToCamelCase(result.get("SCNA_IF_OPTION")) );

        if( modeFlag ) {
            conditionsInfo.setMsg("'모드'의 추가조건(IF) 정보");
        } else {
            conditionsInfo.setMsg("'자동화'의 추가조건(IF) 정보");
        }

        return conditionsInfo;
    }

    // 46. 시나리오에서 생성시, 작동기기(ACTOR) 목록(리스트)를 가져오기
    public IotModeAutomationInfo getModeOrAutomationAllActors(
            int complexId, int homeId, boolean modeFlag) throws Exception {
        IotModeAutomationInfo actorsInfo = new IotModeAutomationInfo();
        HttpGetRequester      requester;
        Map<String, Map>      result;
        String                anyId = "-1"; // 모든 값을 가져오기 위해 없는 값입력

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODE_DETAIL_ACTORS_AVAILABLE_PATH );
        requester.setParameter("cmplxId", String.valueOf(complexId) );
        requester.setParameter("homeId", String.valueOf(homeId) );
        requester.setParameter("scnaId", anyId );
        result = requester.execute();

        // 1. result data 리맵핑
        try {
            this.remapResultScenarioDetail( (List)result.get("SCNA_THINGS") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        actorsInfo.setScnaThings( convertListMapDataToCamelCase((List)result.get("SCNA_THINGS")) );

        if( modeFlag ) {
            actorsInfo.setMsg("'모드'의 추가작동기기(ACTOR) 정보");
        } else {
            actorsInfo.setMsg("'자동화'의 추가작동기기(ACTOR) 정보");
        }

        return actorsInfo;
    }


    /////// 공통 메소드 //////////////////////////////////////////////////////////////////////////////
    private void removeMapKeyIfExisted(Map map, String key) {
        Object v = map.get(key);
        if( v != null ) {
            map.remove(key);
        }
    }

    private void replaceMapKey(Map map, String oldKey, String newKey) {
        Object v = map.remove(oldKey);
        map.put(newKey, v);
    }

    private void replaceMapKeyIfExisted(Map map, String oldKey, String newKey) {
        Object v = map.get(oldKey);
        if( v != null ) {
            map.put(newKey, v);
            map.remove(oldKey);
        }
    }


    /**
     * 결과 값을 CommonLife 용도로 변환합니다. 사용하지 않는 값은 제거하고, 의미에 따라 값을 생성하거나 맵핑을 수행합니다.
     */
    /////// 공통 메소드 - 결과값 remap ////////////////////////////////////////////////////////////////
    private void remapResultMyIotButtonDataList(List<Map<String, Object>> resultData) {
        String v;

        if( (resultData == null) || !(resultData instanceof List) ) {
            return;
        }

        for(Map<String, Object>e : resultData) {
            replaceMapKeyIfExisted(e, "SEQ_NO", "BT_ID"); // !! SEQ_NO 를 BT_ID로 변환
            replaceMapKeyIfExisted(e, "TITLE", "BT_TITLE");
            replaceMapKeyIfExisted(e, "TITLE_UNIT", "BT_TITLE_UNIT");
            replaceMapKeyIfExisted(e, "SUB_TITLE", "BT_SUB_TITLE");

            // 아이콘/이미지 맵핑
            replaceMapKeyIfExisted(e, "IMG_SRC", "BT_IMG_SRC");
            if( e.get("BT_IMG_SRC") != null ) {
                String icon;
                icon = iconService.getIok2ClIcon( (String)e.get("BT_IMG_SRC") );
                e.put("BT_IMG_SRC", icon);
            }

            // 기기별 속성 설정
            v = (String) e.get("MY_IOT_GB_CD");
            if( v != null ) {
                switch(v) {
                    case "MB01701":     // device
                        e.put("BT_TYPE", "device");
                        e.put("MY_IOT_ID", String.valueOf(e.get("M_ID")));
                        if(e.get("BT_IMG_SRC") == null) {
                            e.put("BT_IMG_SRC", iconService.getIotDefaultIcon());
                        }
                        e.put("DEVICE_ID", e.get("MO_THINGS_ID"));
                        // BT_RIGHT_ICON_TYPE(btRightIconType) 결정
                        if( (e.get("BINARY_YN") != null) && (e.get("BINARY_YN").equals("Y")) ) {
                            if( e.get("STS_CNT").equals(new Integer(1)) ) {
                                e.put("BT_RIGHT_ICON_TYPE", "button");
                            } else {
                                e.put("BT_RIGHT_ICON_TYPE", "detail");
                            }
                        } else {
                            e.put("BT_RIGHT_ICON_TYPE", "detail");
                        }
                        break;
                    case "MB01702":     // automation
                        e.put("BT_TYPE", "automation");
                        if( e.get("M_ID") == null ) {
                            e.put("MY_IOT_ID", String.valueOf(e.get("SCNA_ID")));
                        } else {
                            e.put("MY_IOT_ID", String.valueOf(e.get("M_ID")));
                        }
                        if( e.get("M_NM") == null ) {
                            e.put("M_NM", e.get("SCNA_NM"));
                        }
                        if(e.get("BT_IMG_SRC") == null) {
                            e.put("BT_IMG_SRC", iconService.getIconAutomationDefault());
                        }
                        // 자동화는 모두 버튼 모양
                        e.put("BT_RIGHT_ICON_TYPE", "button");
                        e.put("BT_RIGHT_TEXT", "실행");
                        break;
                    case "MB01703":     // information
                        e.put("BT_TYPE", "information");
                        if( e.get("M_ID") == null ) {
                            e.put("MY_IOT_ID", e.get("VALUE_CD"));
                        } else {
                            e.put("MY_IOT_ID", String.valueOf(e.get("M_ID")));
                        }
                        if( e.get("M_NM") == null ) {
                            e.put("M_NM", e.get("VALUE_NM"));
                        }
                        if(e.get("BT_IMG_SRC") == null) {
                            e.put("BT_IMG_SRC", iconService.getIconInformationDefault());
                        }
                        break;
                    default:
                        e.put("BT_TYPE", "unknown");
                        break;
                }
            }
        }
    }

    /**
     *
     * @param resultData
     * @param resultKeyUnderscore
     *         == true: resultData에 포함된 Map의 모든 키는 대문자+underscore로 표현
     *         == false: resultData에 포함된 Map의 모든 키는 소문자+camelCase로 표현
     *
     */
    private void deleteUnusedResultMyIotButtonDataList
            (List<Map<String, Object>> resultData, boolean resultKeyUnderscore) {
        if( (resultData == null) || !(resultData instanceof List) ) {
            return;
        }

        if( resultKeyUnderscore ) {
            for(Map<String, Object>e : resultData) {
                // 사용하지 않은 값 삭제
                this.removeMapKeyIfExisted(e, "CLNT_ID");
                this.removeMapKeyIfExisted(e, "CMPLX_ID");
                this.removeMapKeyIfExisted(e, "HOME_ID");
                this.removeMapKeyIfExisted(e, "BINARY_YN");
                this.removeMapKeyIfExisted(e, "KIND_CD");
                this.removeMapKeyIfExisted(e, "MO_CLNT_ID");
                this.removeMapKeyIfExisted(e, "THINGS_ID");
                this.removeMapKeyIfExisted(e, "THINGS_NM");
                this.removeMapKeyIfExisted(e, "MO_THINGS_ID");
                this.removeMapKeyIfExisted(e, "MO_THINGS_NM");
                this.removeMapKeyIfExisted(e, "USER_ID");
                this.removeMapKeyIfExisted(e, "CURR_STS");
                this.removeMapKeyIfExisted(e, "MAX_LINK_YN");
                this.removeMapKeyIfExisted(e, "MAX_VLU");
                this.removeMapKeyIfExisted(e, "MIN_LINK_YN");
                this.removeMapKeyIfExisted(e, "MIN_VLU");
                this.removeMapKeyIfExisted(e, "PROTC_KEY");
                this.removeMapKeyIfExisted(e, "STS_CNT");
                this.removeMapKeyIfExisted(e, "STS_ID");
                this.removeMapKeyIfExisted(e, "STS_NM");
                this.removeMapKeyIfExisted(e, "SCNA_ID");
                this.removeMapKeyIfExisted(e, "SCNA_NM");
                this.removeMapKeyIfExisted(e, "VALUE_CD");
                this.removeMapKeyIfExisted(e, "MAX_CURR_STS");
                this.removeMapKeyIfExisted(e, "MAX_LINK_PROTC_KEY");
                this.removeMapKeyIfExisted(e, "MIN_CURR_STS");
                this.removeMapKeyIfExisted(e, "MIN_LINK_PROTC_KEY");
            }
        } else {
            for (Map<String, Object> e : resultData) {
                // 사용하지 않은 값 삭제
                this.removeMapKeyIfExisted(e, "clntId");
                this.removeMapKeyIfExisted(e, "cmplxId");
                this.removeMapKeyIfExisted(e, "homeId");
                this.removeMapKeyIfExisted(e, "binaryYn");
                this.removeMapKeyIfExisted(e, "kindCd");
                this.removeMapKeyIfExisted(e, "moClntId");
                this.removeMapKeyIfExisted(e, "thingsId");
                this.removeMapKeyIfExisted(e, "thingsNm");
                this.removeMapKeyIfExisted(e, "moThingsId");
                this.removeMapKeyIfExisted(e, "moThingsNm");
                this.removeMapKeyIfExisted(e, "userId");
                this.removeMapKeyIfExisted(e, "currSts");
                this.removeMapKeyIfExisted(e, "maxLinkYn");
                this.removeMapKeyIfExisted(e, "maxVlu");
                this.removeMapKeyIfExisted(e, "minLinkYn");
                this.removeMapKeyIfExisted(e, "minVlu");
                this.removeMapKeyIfExisted(e, "protcKey");
                this.removeMapKeyIfExisted(e, "stsCnt");
                this.removeMapKeyIfExisted(e, "stsId");
                this.removeMapKeyIfExisted(e, "stsNm");
                this.removeMapKeyIfExisted(e, "scnaId");
                this.removeMapKeyIfExisted(e, "scnaNm");
                this.removeMapKeyIfExisted(e, "valueCd");
                this.removeMapKeyIfExisted(e, "maxCurrSts");
                this.removeMapKeyIfExisted(e, "maxLinkProtcKey");
                this.removeMapKeyIfExisted(e, "minCurrSts");
                this.removeMapKeyIfExisted(e, "minLinkProtcKey");
            }
        }
    }

    private void remapResultModeDataList(List<Map<String, Object>> resultData) {
        if( (resultData == null) || !(resultData instanceof List) ) {
            return;
        }

        for(Map<String, Object>e : resultData) {
            e.put("IMG_SRC", iconService.getIok2ClIcon((String)e.get("IMG_SRC")));

            // 사용하지 않은 값 삭제
            this.removeMapKeyIfExisted(e, "CMPLX_ID");
            this.removeMapKeyIfExisted(e, "HOME_ID");
        }
    }

    private void remapResultDeviceList(List<Map<String, Object>> resultData) {
        String imgSrc;
        if( (resultData == null) || !(resultData instanceof List) ) {
            return;
        }

        for (Map<String, Object> e : resultData) {
            imgSrc = (String)e.get("IMG_SRC");
            e.put("IMG_SRC", iconService.getIok2ClIcon(imgSrc));

            // MO_THINGS_NM --> DEVICE_NM로 변환
            this.replaceMapKeyIfExisted(e, "MO_THINGS_NM", "DEVICE_NM" );
            // MO_THINGS_ID --> DEVICE_ID로 변환
            this.replaceMapKeyIfExisted(e, "MO_THINGS_ID", "DEVICE_ID" );
            // MOD_ID --> DEVICE_ID로 변환
            this.replaceMapKeyIfExisted(e, "MOD_ID", "DEVICE_ID" );

            this.removeMapKeyIfExisted(e, "CMPLX_ID");
            this.removeMapKeyIfExisted(e, "HOME_ID");
            this.removeMapKeyIfExisted(e, "KIND_CD");
            this.removeMapKeyIfExisted(e, "THINGS_ID");

            if( (e.get("BINARY_YN") != null) && (e.get("STS_CNT") != null) ) {
                if( e.get("BINARY_YN").equals("Y") &&
                        e.get("STS_CNT").equals(new Integer(1)) )
                {
                    e.put("DEVICE_TYPE", "button");
                } else {
                    e.put("DEVICE_TYPE", "detail");
                }
            } else {
                e.put("DEVICE_TYPE", "detail");
            }

        }
    }

    private void remapResultDeviceDetailList(List<Map<String, Object>> resultData) {
        String imgSrc;
        if( (resultData == null) || !(resultData instanceof List) ) {
            return;
        }

        for (Map<String, Object> e : resultData) {
            // MO_THINGS_NM --> DEVICE_NM로 변환
            this.replaceMapKeyIfExisted(e, "MO_THINGS_NM", "DEVICE_NM" );
            // MO_THINGS_ID --> DEVICE_ID로 변환
            this.replaceMapKeyIfExisted(e, "MO_THINGS_ID", "DEVICE_ID" );
            // MOD_ID --> DEVICE_ID로 변환
            this.replaceMapKeyIfExisted(e, "MOD_ID", "DEVICE_ID" );

            // '자동화 - 동작기기'의 동작 속성 값의 설정 가능 여부를 표시하기 위해 사용
            if( (e.get("CHK") != null) &&
                ( "0".equals(e.get("PRIME_YN")) ||
                  "1".equals(e.get("PRIME_YN"))) ) {
                if( "binary".equals(e.get("MO_ATTR")) ||
                    "level".equals(e.get("MO_ATTR")) ||
                    "option".equals(e.get("MO_ATTR"))  ||
                    "button".equals(e.get("MO_ATTR")) ) {
                    e.put("ADD_BTN_YN", "Y");
                } else {
                    e.put("ADD_BTN_YN", "N");
                }
            }

            this.removeMapKeyIfExisted(e, "CMPLX_ID");
            this.removeMapKeyIfExisted(e, "HOME_ID");
            this.removeMapKeyIfExisted(e, "KIND_CD");

            imgSrc = (String)e.get("IMG_SRC");
            if( imgSrc != null && !"".equals(imgSrc)) {
                e.put("IMG_SRC", iconService.getIok2ClIcon(imgSrc));
            }
        }
    }

    private void remapResultRoomOrDeviceCategoryList(List<Map<String, Object>> resultData) {
        String imgSrc;
        String cateCd; // DEVICE CATEGORY
        String typeCd; // ROOM TYPE
        if( (resultData == null) || !(resultData instanceof List) ) {
            return;
        }

        for (Map<String, Object> e : resultData) {
            imgSrc = (String)e.get("IMG_SRC");
            typeCd = (String)e.get("TYPE_CD");
            cateCd = (String)e.get("CATE_CD");

            if( imgSrc == null && typeCd != null ) {
                e.put("IMG_SRC", iconService.getIconFromRoomType( typeCd ));
            } else if( imgSrc == null && cateCd != null ) {
                e.put("IMG_SRC", iconService.getIconFromDeviceCategory( cateCd ));
            } else {
                e.put("IMG_SRC", iconService.getIok2ClIcon( imgSrc ));
            }

            this.removeMapKeyIfExisted(e, "CMPLX_ID");
            this.removeMapKeyIfExisted(e, "HOME_ID");
        }
    }

    private void remapResultScenarioDetail(List<Map<String, Object>> resultData) {
        String imgSrc;
        if( (resultData == null) || !(resultData instanceof List) ) {
            return;
        }

        for(Map<String, Object>e : resultData) {
            imgSrc = (String)e.get("IMG_SRC");
            if(imgSrc != null) {
                e.put("IMG_SRC", iconService.getIok2ClIcon(imgSrc));
            } else {
                // 시간 조건인 경우, 이미지를 추가
                if(e.get("SPC_TIME_YN") != null) {
                    e.put("IMG_SRC", "cl_status_4" );
                } else if (e.get("APLY_TIME_YN") != null ) {
                    e.put("IMG_SRC", "cl_status_4" );
                }
            }

            // MO_THINGS_NM --> DEVICE_NM로 변환
            this.replaceMapKeyIfExisted(e, "MO_THINGS_NM", "DEVICE_NM" );
            // MO_THINGS_ID --> DEVICE_ID로 변환
            this.replaceMapKeyIfExisted(e, "MO_THINGS_ID", "DEVICE_ID" );
            // MOD_ID --> DEVICE_ID로 변환
            this.replaceMapKeyIfExisted(e, "MOD_ID", "DEVICE_ID" );


            if( (e.get("BINARY_YN") != null) && (e.get("STS_CNT") != null) ) {
                if( e.get("BINARY_YN").equals("Y") &&
                        e.get("STS_CNT").equals(new Integer(1)) )
                {
                    e.put("DEVICE_TYPE", "button");
                } else {
                    // 센서의 예외 처리 - BINARY_YN=N인데, BINARY처리가 필요한 센서가 있음(인산화탄소, 문열림센서 등등)
                    if( StringUtils.isNumeric((String)e.get("minVal")) &&
                        StringUtils.isNumeric((String)e.get("maxVal")) ) {
                        e.put("DEVICE_TYPE", "detail");
                    } else {
                        if( e.get("STS_CNT").equals(new Integer(1)) ) {
                            e.put("DEVICE_TYPE", "button");
                        }
                        else {
                            e.put("DEVICE_TYPE", "detail");
                        }
                    }
                }
            }

            // 사용하지 않은 값 삭제
            this.removeMapKeyIfExisted(e, "CMPLX_ID");
            this.removeMapKeyIfExisted(e, "HOME_ID");
            this.removeMapKeyIfExisted(e, "MY_IOT_GB_CD");
            this.removeMapKeyIfExisted(e, "KIND_CD");
            this.removeMapKeyIfExisted(e, "MO_CLNT_ID");
            this.removeMapKeyIfExisted(e, "THINGS_NM");
            this.removeMapKeyIfExisted(e, "USER_ID");
            this.removeMapKeyIfExisted(e, "CLNT_ID");
        }
    }

    private void remapResultScenarioDetailReverse(List<Map<String, Object>> resultData, Map<String, Object> addSet) {
        if( (resultData == null) || !(resultData instanceof List) ) {
            return;
        }

        for(Map<String, Object>e : resultData) {
            // MOD_ID --> DEVICE_ID로 변환
            this.replaceMapKeyIfExisted(e, "deviceId", "modId");
            for( String key : addSet.keySet()) {
                e.put(key, addSet.get(key));
            }
        }
    }

    private void remapResultDeviceUsageHistory(List<Map<String, Object>> resultData) {
        String imgSrc;
        if( (resultData == null) || !(resultData instanceof List) ) {
            return;
        }

        for(Map<String, Object>e : resultData) {
            imgSrc = (String)e.get("IMG_SRC");
            e.put("IMG_SRC", iconService.getIok2ClIcon(imgSrc));

            // 사용하지 않은 값 삭제
            this.removeMapKeyIfExisted(e, "CMPLX_ID");
            this.removeMapKeyIfExisted(e, "HOME_ID");
            this.removeMapKeyIfExisted(e, "THINGS_NM");
            this.removeMapKeyIfExisted(e, "CURR_STS");
            this.removeMapKeyIfExisted(e, "STS_NM");
        }
    }

    private List convertListMapDataToCamelCase(List inputDataList) {
        List outputDataList;

        if( inputDataList == null ) {
            return null;
        }

        outputDataList = new ArrayList();

        for(Map<String, Object> e : (List<Map<String, Object>>)inputDataList ) {
//            Map element = new HashMap();
            Map element = new TreeMap(); // todo:DEBUG: 결과 값이 key값 기준으로 ABC.. 정렬되어 표시
            for (String s : e.keySet()) {
                element.put( CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s), e.get(s));
            }
            outputDataList.add(element);
        }
        return outputDataList;
    }
}
