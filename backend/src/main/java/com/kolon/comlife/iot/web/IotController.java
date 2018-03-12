package com.kolon.comlife.iot.web;

import com.google.common.base.CaseFormat;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.iot.exception.IotControlOperationFailedException;
import com.kolon.comlife.iot.exception.IotControlTimeoutException;
import com.kolon.comlife.iot.exception.IotInfoNoDataException;
import com.kolon.comlife.iot.model.*;
import com.kolon.comlife.iot.service.IotControlService;
import com.kolon.comlife.iot.service.IotInfoService;
import com.kolon.common.http.HttpGetRequester;
import com.kolon.common.http.HttpRequestFailedException;
import com.kolon.common.prop.ServicePropertiesMap;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example Controller
 */
@RestController
@RequestMapping("/iot")
public class IotController {
    private static final Logger logger = LoggerFactory.getLogger(IotController.class);

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
    private IotInfoService iotInfoService;

    @Autowired
    private CloseableHttpClient httpClient;

    /**
     * Controller Test Code
     */
    @GetMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> getIotStatus()
    {
        Map result = new HashMap();
        result.put("msg", "ok!");

        return ResponseEntity.status(HttpStatus.OK).body( result );
    }

    /**
     * 1. '모드'의 전체 목록 가져오기 at Dashboard
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getModesList(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotModeListInfo modesList;

        try {
            modesList = iotInfoService.getModeList(complexId, homeId);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( modesList );
    }

    /**
     * 2. 현재 적용된 '모드'의 가져오기 at Dashboard
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/active",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getActiveMode(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotModeListInfo modesList = new IotModeListInfo();
        IotModeListInfo activeModeList = new IotModeListInfo();
        List            foundData = new ArrayList();
        HttpGetRequester requester;
        Map<String, Map> result;

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                    IOK_MODES_LIST_PATH );
            requester.setParameter("cmplxId", String.valueOf(complexId) );
            requester.setParameter("homeId", String.valueOf(homeId) );
            result = requester.execute();
        }  catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

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
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("현재 적용된 모드가 없습니다."));
        }

        activeModeList.setData(
                convertListMapDataToCamelCase(foundData));

        activeModeList.setMsg("현재 동작중인 모드 가져오기");

        return ResponseEntity.status(HttpStatus.OK).body( activeModeList );
    }

    /**
     * 3. My IOT의 IOT 버튼 목록 및 정보 가져오기 at Dashboard
     */
    @GetMapping(
            value = "/complexes/{complexId}/homes/{homeId}/myiot",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyIotButtonList(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotButtonListInfo buttonList = new IotButtonListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;
        
        String userId;

        // todo: userId is retrieved from the user's token.
        userId = "baek";

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                    IOK_MYIOT_LIST_PATH );
            requester.setParameter("cmplxId", String.valueOf(complexId) );
            requester.setParameter("homeId", String.valueOf(homeId) );
            requester.setParameter("userId", userId );
            result = requester.execute();
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        buttonList.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( buttonList.getData().isEmpty() )
        {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("가져올 버튼 정보가 없습니다."));
        }

        buttonList.setMsg("My IOT의 IOT 버튼 목록 및 정보 가져오기");

        return ResponseEntity.status(HttpStatus.OK).body( buttonList );
    }

    /**
     * 4. MyIOT의 IOT 버튼 개별 목록 가져오기 at Dashboard Button
     */
    @GetMapping(
            value = "/complexes/{complexId}/homes/{homeId}/myiot/buttons/{buttonId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotButtonInfo>  getMyIotButton(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("buttonId")  int buttonId )
    {
        IotButtonInfo buttonInfo = new IotButtonInfo();

        return ResponseEntity.status(HttpStatus.OK).body( buttonInfo );
    }

    /**
     * 5. MyIOT 내, 개별 IOT 버튼의 대표 기능 실행 at Dashboard Button
     */
    @PutMapping(
            value = "/complexes/{complexId}/homes/{homeId}/myiot/buttons/{buttonId}/action",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotButtonInfo>  executeMyIotButtonPrimeFunction(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("buttonId")  int buttonId )
    {
        IotButtonInfo buttonInfo = new IotButtonInfo();

        return ResponseEntity.status(HttpStatus.OK).body( buttonInfo );
    }

    /**
     * 6. 가치정보에서 날씨 화면의 세부 정보 가져오기 at Dashboard Button
     */
    @GetMapping(
            value = "/complexes/{complexId}/valueInfo/weather",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map>  getValueInfoOfWeather(
            @PathVariable("complexId") int complexId )
    {
        Map weatherInfo = new HashMap();

        return ResponseEntity.status(HttpStatus.OK).body( weatherInfo );
    }

//   todo: 에너지사용량은 가치정보가 아닌듯.
//    /**
//     * 7. 에너지 사용량 정보 가져오기 at Dashboard Button
//     */
//    @GetMapping(
//            value = "/complexes/{complexId}/valueInfo/energy",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map>  getEnergyInfo(
//            @PathVariable("complexId") int complexId )
//    {
//        Map weatherInfo = new HashMap();
//
//        return ResponseEntity.status(HttpStatus.OK).body( weatherInfo );
//    }


    /**
     * 8. 기기의 대표 기능 수행 - AWS Lambda 호출
     */
    @PutMapping(
            value = "/complexes/{complexId}/homes/{homeId}/devices/{deviceId}/action",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map>  executeDevicePrimeFunction(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("deviceId")  int deviceId )
    {
        Map ret = new HashMap();

        return ResponseEntity.status(HttpStatus.OK).body( ret );
    }

    /**
     * 10. 공간 목록 가져오기 at Quick IOT 제어 > 공간별 보기
     */
    @GetMapping(
            value = "/complexes/{complexId}/homes/{homeId}/rooms",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRoomList(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotRoomListInfo roomList = new IotRoomListInfo();
        HttpGetRequester requester;
        Map<String, Map> result;
        

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                    IOK_ROOMS_LIST_PATH );
            requester.setParameter("cmplxId", String.valueOf(complexId) );
            requester.setParameter("homeId", String.valueOf(homeId) );
            result = requester.execute();
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        roomList.setData(
            convertListMapDataToCamelCase((List)result.get("DATA")));

        if( roomList.getData().isEmpty() )
        {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("정의된 모드가 없습니다."));
        }

        roomList.setMsg("공간 목록 가져오기");

        return ResponseEntity.status(HttpStatus.OK).body( roomList );
    }

    /**
     * 11. 공간별 '기기 목록' 가져오기 at Quick IOT 제어 > 공간별 보기
     */
    @GetMapping(
            value = "/complexes/{complexId}/homes/{homeId}/rooms/{roomId}/devices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRoomsWithDevicesList(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("roomId")    int roomId)
    {
        IotDeviceListInfo deviceListInfo = new IotDeviceListInfo();

        HttpGetRequester requester;
        Map<String, Map> result;
        

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                    IOK_DEVICES_LIST_BY_ROOM_PATH );
            requester.setParameter("cmplxId", String.valueOf(complexId) );
            requester.setParameter("homeId", String.valueOf(homeId) );
            requester.setParameter("roomId", String.valueOf(roomId) );
            result = requester.execute();
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        deviceListInfo.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( deviceListInfo.getData().isEmpty() )
        {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("정의된 모드가 없습니다."));
        }

        deviceListInfo.setMsg("공간별 기기 목록 가져오기");

        return ResponseEntity.status(HttpStatus.OK).body( deviceListInfo );
    }

    /**
     * 12. 기기 상세 정보 가져오기 at Quick IOT 제어 > 공간별 보기 or 기기별 보기
     */
    @GetMapping(
            value = "/complexes/{complexId}/homes/{homeId}/devices/{deviceId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDeviceInfo(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("deviceId")  String deviceId )
    {
        IotDeviceInfo deviceInfo = new IotDeviceInfo();

        HttpGetRequester requester;
        Map<String, Map> result;
        

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                    IOK_DEVICE_DETAIL_BY_DEVICE_ID_PATH );
            requester.setParameter("cmplxId", String.valueOf(complexId) );
            requester.setParameter("homeId", String.valueOf(homeId) );
            requester.setParameter("deviceId", String.valueOf(deviceId) );
            result = requester.execute();
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        deviceInfo.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( deviceInfo.getData().isEmpty() )
        {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("정의된 모드가 없습니다."));
        }

        deviceInfo.setMsg("공간별 기기 목록 가져오기");

        return ResponseEntity.status(HttpStatus.OK).body( deviceInfo );
    }

    /**
     * 13. 기기 카테고리 가져오기 at Quick IOT 제어 > 기기별 보기
     */
    @GetMapping(
            value = "/complexes/{complexId}/homes/{homeId}/deviceCategory",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDeviceGroupList(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotDeviceGroupListInfo deviceGroupListInfo = new IotDeviceGroupListInfo();

        HttpGetRequester requester;
        Map<String, Map> result;
        

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                    IOK_DEVICES_GROUP_LIST_PATH );
            requester.setParameter("cmplxId", String.valueOf(complexId) );
            requester.setParameter("homeId", String.valueOf(homeId) );
            result = requester.execute();
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        deviceGroupListInfo.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( deviceGroupListInfo.getData().isEmpty() )
        {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("정의된 모드가 없습니다."));
        }

        deviceGroupListInfo.setMsg("기기 카테고리 가져오기");

        return ResponseEntity.status(HttpStatus.OK).body( deviceGroupListInfo );
    }

    /**
     * 14. 카테고리 별 기기 목록 가져오기 at Quick IOT 제어 > 기기별 보기
     */
    @GetMapping(
            value = "/complexes/{complexId}/homes/{homeId}/deviceCategory/{categoryCode}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDeviceListByDeviceGroup(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("categoryCode")    String categoryCode)
    {
        IotDeviceListInfo deviceListInfo = new IotDeviceListInfo();

        HttpGetRequester requester;
        Map<String, Map> result;
        

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    serviceProperties.getByKey(IOK_MOBILE_HOST_PROP_GROUP, IOK_MOBILE_HOST_PROP_KEY),
                    IOK_DEVICES_LIST_BY_CATEGORY_PATH );
            requester.setParameter("cmplxId", String.valueOf(complexId) );
            requester.setParameter("homeId", String.valueOf(homeId) );
            requester.setParameter("cateCd", String.valueOf(categoryCode) );

            result = requester.execute();
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        deviceListInfo.setData(
                convertListMapDataToCamelCase((List)result.get("DATA")));

        if( deviceListInfo.getData().isEmpty() )
        {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("정의된 모드가 없습니다."));
        }

        deviceListInfo.setMsg("카테고리 별 기기 목록 가져오기");

        return ResponseEntity.status(HttpStatus.OK).body( deviceListInfo );
    }

    /**
     * 16. 개별 '모드'의 상세 정보 가져오기 at IOT 모드 실행
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/{modeId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotModeInfo> getModeInfo(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("modeId")    int modeId )
    {
        IotModeInfo modeInfo = new IotModeInfo();

        return ResponseEntity.status(HttpStatus.OK).body( modeInfo );
    }

    /**
     * 17. 개별 '모드'로 전환 at IOT 모드 실행
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/{modeId}/switchTo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity switchToMode(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("modeId")    String modeId )
    {
        IotModeListInfo changedMode;

        try {
            changedMode = iotControlService.switchToMode( complexId, homeId, modeId );
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( IotControlTimeoutException timoutEx ) {
                return ResponseEntity
                        .status(HttpStatus.REQUEST_TIMEOUT)
                        .body(new SimpleErrorInfo("동작 시간이 초과하였습니다."));
            } catch( IotControlOperationFailedException failEx ) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo(failEx.getMessage()));
            } catch( IotInfoNoDataException nodataEx ) {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(new SimpleErrorInfo("모드 변경이 가능하지 않습니다. 잠시후에 다시 시도하세요."));
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        changedMode.setMsg("개별 '모드' 전환");

        return ResponseEntity.status(HttpStatus.OK).body( changedMode );
    }

    /**
     * 21. '모드' 편집에서, 모드 목록 및 순서를 가져오기 at Mode 내용 편집
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/order",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotModeListInfo> getModesOrder(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotModeListInfo modeListInfo = new IotModeListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( modeListInfo );
    }

    /**
     * 22. '모드' 편집에서, 모드 목록 및 순서를 가져오기 at Mode 내용 편집
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/order",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotModeListInfo> updateModesOrder(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotModeListInfo modeListInfo = new IotModeListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( modeListInfo );
    }

    /**
     * 23. '모드' 상세에서 수정한 설정 정보 업데이트하기 at IOT 모드 실행
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/{modeId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotModeInfo> updateModeInfo(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("modeId")    int modeId )
    {
        IotModeInfo modeInfo = new IotModeInfo();

        return ResponseEntity.status(HttpStatus.OK).body( modeInfo );
    }

    /**
     * 24. MyIOT에 추가할 수 있는 모든 기기/가치정보/오토메이션 목록 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/buttons",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> getButtons(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        Map list = new HashMap();

        return ResponseEntity.status(HttpStatus.OK).body( list );
    }

    /**
     * 28. MyIOT에서 '시나리오/오토메이션'의 상세정보 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotAutomationInfo> getAutomation(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId )
    {
        IotAutomationInfo automationInfo = new IotAutomationInfo();

        return ResponseEntity.status(HttpStatus.OK).body( automationInfo );
    }


    /**
     * 29. MyIOT에서 '시나리오/오토메이션' 생성하기 at MyIOT 추가
     */
    @PostMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotAutomationInfo> createAutomation(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotAutomationInfo automationInfo = new IotAutomationInfo();

        return ResponseEntity.status(HttpStatus.OK).body( automationInfo );
    }

    /**
     * 30. MyIOT에서 '시나리오/오토메이션' 업데이트하기 at MyIOT 추가
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotAutomationInfo> updateAutomation(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId )
    {
        IotAutomationInfo automationInfo = new IotAutomationInfo();

        return ResponseEntity.status(HttpStatus.OK).body( automationInfo );
    }

    /**
     * 31. 특정 시나리오의 '센서' 목록 및 속성 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}/sensors",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotSensorListInfo> getSensorsListOnAutomation(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId )
    {
        IotSensorListInfo sensorsList = new IotSensorListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( sensorsList );
    }

    /**
     * 32. 특정 시나리오의 '센서' 목록 및 속성 변경하기 at MyIOT 추가
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}/sensors/{sensorId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotSensorListInfo> updateSensorOnAutomation(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId,
            @PathVariable("sensorId") int sensorId)
    {
        IotSensorListInfo sensorsList = new IotSensorListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( sensorsList );
    }

    /**
     * 33. 특정 시나리오의 '기기' 목록 및 속성 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}/devices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotDeviceListInfo> getDevicesListOnAutomation(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId )
    {
        IotDeviceListInfo deviceList = new IotDeviceListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( deviceList );
    }

    /**
     * 34. 특정 시나리오의 '기기' 목록 및 속성 변경하기 at MyIOT 추가
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}/devices/{deviceId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotDeviceInfo> getDevicesListOnAutomation(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId,
            @PathVariable("automationId") int deviceId )
    {
        IotDeviceInfo deviceInfo = new IotDeviceInfo();

        return ResponseEntity.status(HttpStatus.OK).body( deviceInfo );
    }

    /**
     * 35. 에너지 사용량 정보 가져오기 at MyIOT - 에너지사용량 상세 화면
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/energy/{energyType}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnergyInfo> getEnergyUsage(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("energyType") String  energyType)
    {
        EnergyInfo energyInfo = new EnergyInfo();

        return ResponseEntity.status(HttpStatus.OK).body( energyInfo );
    }

    /**
     * 36. 에너지사용량 초과 알람 설정하기 at MyIOT - 에너지사용량 상세 화면
     */
    @PostMapping(
            path = "/complexes/{complexId}/homes/{homeId}/energy/{energyType}/exceedAlarm",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnergyInfo> createEnergyUsageExceedAlarm(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("energyType") String  energyType)
    {
        EnergyInfo energyInfo = new EnergyInfo();

        return ResponseEntity.status(HttpStatus.OK).body( energyInfo );
    }

    /**
     * 37. 에너지사용량 초과 알람 삭제하기 at MyIOT - 에너지사용량 상세 화면
     */
    @DeleteMapping(
            path = "/complexes/{complexId}/homes/{homeId}/energy/{energyType}/exceedAlarm",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnergyInfo> deleteEnergyUsageExceedAlarm(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("energyType") String  energyType)
    {
        EnergyInfo energyInfo = new EnergyInfo();

        return ResponseEntity.status(HttpStatus.OK).body( energyInfo );
    }

    /**
     * 38. 에너지사용량 알람 변경하기 at MyIOT - 에너지사용량 상세 화면
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/energy/{energyType}/exceedAlarm",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnergyInfo> setEnergyUsageExceedAlarm(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("energyType") String  energyType)
    {
        EnergyInfo energyInfo = new EnergyInfo();

        return ResponseEntity.status(HttpStatus.OK).body( energyInfo );
    }

    private ResponseEntity commonExceptionHandler(Exception e) throws Exception {
        if( e instanceof  URISyntaxException ) {
            logger.error( "Invalid URI: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
        } else if( e instanceof IOException ) {
            logger.error( "Failed to request: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        } else if( e instanceof HttpRequestFailedException ) {
            HttpRequestFailedException httpException = (HttpRequestFailedException)e;
            logger.error( "Failed response[StatusCode:" + httpException.getStatusCode() + "]:" + e.getMessage() );
            if (httpException.getStatusCode() >= 500 ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
            }
        } else if( e instanceof IotInfoNoDataException) {
            logger.error( "No data : " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body(new SimpleErrorInfo(e.getMessage()));
        } else {
            // 공통적으로 처리되지 않는 exception은 별도 처리
            throw e;
        }
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