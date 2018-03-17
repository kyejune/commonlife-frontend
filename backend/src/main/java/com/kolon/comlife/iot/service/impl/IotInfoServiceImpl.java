package com.kolon.comlife.iot.service.impl;

import com.google.common.base.CaseFormat;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private static final String IOK_ROOMS_LIST_PATH                 = "/iokinterface/device/roomList";
    private static final String IOK_DEVICES_LIST_BY_ROOM_PATH       = "/iokinterface/device/roomDeviceList";
    private static final String IOK_DEVICE_DETAIL_BY_DEVICE_ID_PATH = "/iokinterface/device/deviceDetail";
    private static final String IOK_DEVICES_GROUP_LIST_PATH         = "/iokinterface/device/cateList";
    private static final String IOK_DEVICES_LIST_BY_CATEGORY_PATH   = "/iokinterface/device/cateDeviceList";
    private static final String IOK_DEVICES_USAGE_HISTORY_PATH      = "/iokinterface/device/deviceHistList";
    private static final String IOK_MODE_DETAIL_PATH                = "/iokinterface/scenario/scnaDetail";
    private static final String IOK_MODE_DETAIL_CONDITONS_PATH      = "/iokinterface/scenario/scnaIfDetail";
    private static final String IOK_MODE_DETAIL_ACTORS_PATH         = "/iokinterface/scenario/scnaThingsDetail";
    private static final String IOK_MODE_DETAIL_CONDITONS_AVAILABLE_PATH = "/iokinterface/scenario/scnaIfAddList";
    private static final String IOK_MODE_DETAIL_ACTORS_AVAILABLE_PATH    = "/iokinterface/scenario/scnaThingsAddList";

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

        // 1. result data 리맵핑
        try {
            this.remapResultModeDataList( (List)result.get("DATA") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

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

        // 1. result data 리맵핑
        try {
            this.remapResultModeDataList( foundData );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

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

    /**
     * 3. My IOT의 IOT 버튼 목록 및 정보 가져오기 at Dashboard
     *
     * resultSimplify == true ==> 사용자에게 필요 없는 결과는 삭제하고 반환 (FRONT에 필요한 최소 결과 반환)
     */
    public IotButtonListInfo getMyIotButtonList
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
                this.deleteUnusedResultMyIotButtonDataList( (List)result.get("DATA") );
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

        buttonList.setMsg("My IOT의 IOT 버튼 목록 및 정보 가져오기");

        return buttonList;
    }


    /**
     * 4. My IOT의 IOT 버튼 개별 정보 가져오기
     */
    public IotButtonListInfo getMyIotButtonListById
                (int complexId, int homeId, String userId, int buttonId, boolean resultSimplify) throws Exception {
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
                this.deleteUnusedResultMyIotButtonDataList( (List)result.get("DATA") );
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

        requester = new HttpGetRequester(
                httpClient,
                serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                IOK_MODE_DETAIL_PATH );
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
            this.remapResultScenarioDetail( (List)result.get("SCNA_THINGS") );
            this.remapResultScenarioDetail( (List)result.get("SCNA") );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        // 2. camelCase로 변환
        modeAutoInfo.setScnaIfThings( convertListMapDataToCamelCase((List)result.get("SCNA_IF_THINGS")) );
        modeAutoInfo.setScnaIfSpc( convertListMapDataToCamelCase((List)result.get("SCNA_IF_SPC")) );
        modeAutoInfo.setScnaIfAply( convertListMapDataToCamelCase((List)result.get("SCNA_IF_APLY")) );
        modeAutoInfo.setScnaIfOption( convertListMapDataToCamelCase((List)result.get("SCNA_IF_OPTION")) );
        modeAutoInfo.setScnaThings( convertListMapDataToCamelCase((List)result.get("SCNA_THINGS")) );
        modeAutoInfo.setScna( convertListMapDataToCamelCase((List)result.get("SCNA")) );

        if( modeFlag ) {
            modeAutoInfo.setMsg("개별 '모드'의 조건 정보");
        } else {
            modeAutoInfo.setMsg("'오토메이션'의 조건 정보");
        }

        return modeAutoInfo;
    }

    // 24. MyIOT에 추가가능한 모든 버튼 목록 가져오기
    public IotButtonListInfo getMyIotButtonListAvailable (int complexId, int homeId, String userId) throws Exception {
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
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        buttonListInfo.setData( convertListMapDataToCamelCase((List)result.get("DATA")) );

        buttonListInfo.setMsg("MyIOT에 추가가능한 모든 버튼 목록 가져오기");

        return buttonListInfo;
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

        for(Map<String, Object>e : resultData) {
            replaceMapKeyIfExisted(e, "SEQ_NO", "BT_ID"); // !! SEQ_NO 를 BT_ID로 변환
            replaceMapKeyIfExisted(e, "TITLE", "BT_TITLE");
            replaceMapKeyIfExisted(e, "TITLE_UNIT", "BT_TITLE_UNIT");
            replaceMapKeyIfExisted(e, "SUB_TITLE", "BT_SUB_TITLE");
            replaceMapKeyIfExisted(e, "IMG_SRC", "BT_IMG_SRC");

            // BT_TYPE(btType) 정의
            v = (String) e.get("MY_IOT_GB_CD");
            if( v != null ) {
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
            }

            // BT_RIGHT_ICON_TYPE(btRightIconType) 결정
            if( e.get("BT_TYPE") != null && e.get("BT_TYPE").equals("device") ) {
                if( (e.get("BINARY_YN") != null) && (e.get("BINARY_YN").equals("Y")) ) {
                    if( e.get("STS_CNT").equals(new Integer(1)) ) {
                        e.put("BT_RIGHT_ICON_TYPE", "button");
                    } else {
                        e.put("BT_RIGHT_ICON_TYPE", "detail");
                    }
                } else {
                    e.put("BT_RIGHT_ICON_TYPE", "detail");
                }
            }

//            // MO_THINGS_NM --> DEVICE_NM로 변환
//            this.replaceMapKeyIfExisted(e, "MO_THINGS_NM", "DEVICE_NM" );
//            // MO_THINGS_ID --> DEVICE_ID로 변환
//            this.replaceMapKeyIfExisted(e, "MO_THINGS_ID", "DEVICE_ID" );
//            // MOD_ID --> DEVICE_ID로 변환
//            this.replaceMapKeyIfExisted(e, "MOD_ID", "DEVICE_ID" );
        }
    }

    private void deleteUnusedResultMyIotButtonDataList(List<Map<String, Object>> resultData) {
        String v;

        for(Map<String, Object>e : resultData) {
            // 사용하지 않은 값 삭제
            this.removeMapKeyIfExisted(e, "CLNT_ID");
            this.removeMapKeyIfExisted(e, "CMPLX_ID");
            this.removeMapKeyIfExisted(e, "HOME_ID");
            this.removeMapKeyIfExisted(e, "BINARY_YN");
            this.removeMapKeyIfExisted(e, "MY_IOT_GB_CD");
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
            this.removeMapKeyIfExisted(e, "SORT_ORDER");
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
    }


    private void remapResultModeDataList(List<Map<String, Object>> resultData) {
        String v;

        for(Map<String, Object>e : resultData) {
            // 사용하지 않은 값 삭제
            this.removeMapKeyIfExisted(e, "CMPLX_ID");
            this.removeMapKeyIfExisted(e, "HOME_ID");
            this.removeMapKeyIfExisted(e, "SCNA_ID");
        }
    }

    private void remapResultDeviceDetailList(List<Map<String, Object>> resultData) {
        for (Map<String, Object> e : resultData) {
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
        }
    }

    private void remapResultRoomOrDeviceCategoryList(List<Map<String, Object>> resultData) {
        for (Map<String, Object> e : resultData) {
            this.removeMapKeyIfExisted(e, "CMPLX_ID");
            this.removeMapKeyIfExisted(e, "HOME_ID");
        }
    }

    private void remapResultScenarioDetail(List<Map<String, Object>> resultData) {
        if( (resultData == null) || !(resultData instanceof List) ) {
            return;
        }

        for(Map<String, Object>e : resultData) {
            // MO_THINGS_NM --> DEVICE_NM로 변환
            this.replaceMapKeyIfExisted(e, "MO_THINGS_NM", "DEVICE_NM" );
            // MO_THINGS_ID --> DEVICE_ID로 변환
            this.replaceMapKeyIfExisted(e, "MO_THINGS_ID", "DEVICE_ID" );
            // MOD_ID --> DEVICE_ID로 변환
            this.replaceMapKeyIfExisted(e, "MOD_ID", "DEVICE_ID" );

            // 사용하지 않은 값 삭제
            this.removeMapKeyIfExisted(e, "CMPLX_ID");
            this.removeMapKeyIfExisted(e, "HOME_ID");
            this.removeMapKeyIfExisted(e, "MY_IOT_GB_CD");
            this.removeMapKeyIfExisted(e, "KIND_CD");
            this.removeMapKeyIfExisted(e, "MO_CLNT_ID");
            this.removeMapKeyIfExisted(e, "THINGS_ID");
            this.removeMapKeyIfExisted(e, "THINGS_NM");
            this.removeMapKeyIfExisted(e, "USER_ID");
            this.removeMapKeyIfExisted(e, "CLNT_ID");
        }
    }

    private void remapResultDeviceUsageHistory(List<Map<String, Object>> resultData) {
        String v;

        for(Map<String, Object>e : resultData) {
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
            Map element = new TreeMap(); // for DEBUG : 결과 값이 key값 기준으로 ABC.. 정렬되어 표시
            for (String s : e.keySet()) {
                element.put( CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s), e.get(s));
            }
            outputDataList.add(element);
        }
        return outputDataList;
    }
}
