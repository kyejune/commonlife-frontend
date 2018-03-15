package com.kolon.comlife.iot.web;

import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.iot.exception.IotControlOperationFailedException;
import com.kolon.comlife.iot.exception.IotControlTimeoutException;
import com.kolon.comlife.iot.exception.IotInfoNoDataException;
import com.kolon.comlife.iot.model.*;
import com.kolon.comlife.iot.service.IotControlService;
import com.kolon.comlife.iot.service.IotInfoService;
import com.kolon.common.http.HttpRequestFailedException;
import com.kolon.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Example Controller
 */
@RestController
@RequestMapping("/iot")
public class IotController {
    private static final Logger logger = LoggerFactory.getLogger(IotController.class);

    @Autowired
    private IotControlService iotControlService;

    @Autowired
    private IotInfoService iotInfoService;

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
        IotModeListInfo activeModeList;

        try {
            activeModeList = iotInfoService.getActiveMode(complexId, homeId);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

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
        IotButtonListInfo buttonList;
        String            userId;

        // todo: userId is retrieved from the user's token.
        userId = "baek";

        try {
            buttonList = iotInfoService.getMyIotButtonList(complexId, homeId, userId);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( buttonList );
    }

    /**
     * 4. MyIOT의 IOT 버튼 개별 목록 가져오기 at Dashboard Button
     */
    @GetMapping(
            value = "/complexes/{complexId}/homes/{homeId}/myiot/buttons/{buttonId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyIotButton(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("buttonId")  int buttonId )
    {
        IotButtonListInfo buttonList;
        String            userId;

        // todo: userId is retrieved from the user's token.
        userId = "baek";

        try {
            buttonList = iotInfoService.getMyIotButtonListById(complexId, homeId, userId, buttonId);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( buttonList );
    }

    /**
     * 5. MyIOT 내, 개별 IOT 버튼의 대표 기능 실행 at Dashboard Button
     */
    @PutMapping(
            value = "/complexes/{complexId}/homes/{homeId}/myiot/buttons/{buttonId}/action",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity executeMyIotButtonPrimeFunction(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("buttonId")  int buttonId )
    {
        IotButtonListInfo buttonInfo;
        String            userId;

        // todo: userId is retrieved from the user's token.
        userId = "baek";

        try {
            buttonInfo = iotControlService.executeMyIotButtonPrimeFunction(complexId, homeId, userId, buttonId);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler(e);
            } catch( IotInfoNoDataException nodataEx ) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo( nodataEx.getMessage() ));
            } catch( IotControlOperationFailedException opfailEx ) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo( opfailEx.getMessage() ));
            } catch( Exception unhandledEx ) {
                e.printStackTrace();
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

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
        IotRoomListInfo roomList;

        try {
            roomList = iotInfoService.getRoomList(complexId, homeId);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

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
        IotDeviceListInfo deviceListInfo;

        try {
            deviceListInfo = iotInfoService.getRoomsWithDevicesList(complexId, homeId, roomId);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

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
        IotDeviceListInfo deviceListInfo;

        try {
            deviceListInfo = iotInfoService.getDeviceInfo(complexId, homeId, deviceId);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceListInfo );
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
        IotDeviceGroupListInfo deviceGroupListInfo;

        try {
            deviceGroupListInfo = iotInfoService.getDeviceGroupList(complexId, homeId);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceGroupListInfo );
    }

    /**
     * 14. 카테고리 별 기기 목록 가져오기 at Quick IOT 제어 > 기기별 보기
     */
    @GetMapping(
            value = "/complexes/{complexId}/homes/{homeId}/deviceCategory/{categoryCode}/devices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDeviceListByDeviceGroup(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("categoryCode")    String categoryCode)
    {
        IotDeviceListInfo deviceListInfo;

        try {
            deviceListInfo = iotInfoService.getDeviceListByDeviceGroup(complexId, homeId, categoryCode);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceListInfo );
    }

    /**
     * 15-a. 기기의 '사용내역' 정보 가져오기
     */
    @GetMapping(
            value = "/complexes/{complexId}/homes/{homeId}/devices/usageHistory",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDevicesUsageHistory(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        int pageNo;
        int pageRow;
        IotDeviceListInfo deviceListInfo;

        pageNo = StringUtil.parseInt(request.getParameter("pageNo"), 1);
        pageRow = StringUtil.parseInt(request.getParameter("pageRow"), 10);

        try {
            deviceListInfo = iotInfoService.getDevicesUsageHistory(complexId, homeId, pageNo, pageRow);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceListInfo );
    }

    /**
     * 16. 개별 '모드'의 상세 정보 가져오기 at IOT 모드 실행
     *
     * modeId == scnaId
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/{modeId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getModeInfo(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("modeId")    int modeId )
    {
        IotModeAutomationInfo modeInfo;

        try {
            modeInfo = iotInfoService.getModeOrAutomationDetail(complexId, homeId, modeId, true);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

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
    public ResponseEntity<IotModeAutomationInfo> updateModeInfo(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("modeId")    int modeId )
    {
        IotModeAutomationInfo modeInfo = new IotModeAutomationInfo();

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
    public ResponseEntity getAutomation(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId )
    {
        IotModeAutomationInfo automationInfo;

        try {
            automationInfo = iotInfoService.getModeOrAutomationDetail(complexId, homeId, automationId, false);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

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
}