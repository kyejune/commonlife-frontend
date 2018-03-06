package com.kolon.comlife.iot.web;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.iot.model.*;
import com.kolon.comlife.iot.service.IotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource(name = "iotService")
    private IotService iotService;

    /**
     * '모드'의 전체 목록 가져오기 at Dashboard
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
     * '모드'의 전체 목록 가져오기 at Dashboard
     */
    @GetMapping(
            path = "/{complexId}/{homeId}/modes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotModeListInfo> getModesList(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotModeListInfo modesList = new IotModeListInfo();

        modesList.setMsg("목록 가져오기");

        return ResponseEntity.status(HttpStatus.OK).body( modesList );
    }

    /**
     * 현재 적용된 '모드'의 가져오기 at Dashboard
     */
    @GetMapping(
            path = "/{complexId}/{homeId}/modes/active",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotModeInfo> getActiveMode(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotModeInfo activeMode = new IotModeInfo();

        return ResponseEntity.status(HttpStatus.OK).body( activeMode );
    }

    /**
     * MyIOT의 IOT 버튼 목록 가져오기 at Dashboard
     */
    @GetMapping(
            value = "/{complexId}/{homeId}/myiot",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotButtonListInfo>  getMyIotButtonList(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotButtonListInfo buttonList = new IotButtonListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( buttonList );
    }

    /**
     * MyIOT의 IOT 버튼 개별 목록 가져오기 at Dashboard Button
     */
    @GetMapping(
            value = "/{complexId}/{homeId}/myiot/{buttonId}",
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
     * MyIOT 내, 개별 IOT 버튼의 대표 기능 실행 at Dashboard Button
     */
    @PutMapping(
            value = "/{complexId}/{homeId}/myiot/{buttonId}/action",
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
     * 가치정보에서 날씨 화면의 세부 정보 가져오기 at Dashboard Button
     */
    @GetMapping(
            value = "/{complexId}/valueInfo/weather",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map>  getValueInfoOfWeather(
            @PathVariable("complexId") int complexId )
    {
        Map weatherInfo = new HashMap();

        return ResponseEntity.status(HttpStatus.OK).body( weatherInfo );
    }

//   todo: 에너지사용량은 가치정보가 아닌듯.
//    /**
//     * 에너지 사용량 정보 가져오기 at Dashboard Button
//     */
//    @GetMapping(
//            value = "/{complexId}/valueInfo/energy",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map>  getEnergyInfo(
//            @PathVariable("complexId") int complexId )
//    {
//        Map weatherInfo = new HashMap();
//
//        return ResponseEntity.status(HttpStatus.OK).body( weatherInfo );
//    }


    /**
     * 기기의 대표 기능 수행
     */
    @PutMapping(
            value = "/iot/{complexId}/{homeId}/{deviceId}/action",
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
     * 공간 목록 가져오기 at Quick IOT 제어 > 공간별 보기
     */
    @GetMapping(
            value = "/{complexId}/{homeId}/rooms",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotRoomListInfo>  getRoomList(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotRoomListInfo roomList = new IotRoomListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( roomList );
    }

    /**
     * 공간별 '기기 목록' 가져오기 at Quick IOT 제어 > 공간별 보기
     */
    @GetMapping(
            value = "/{complexId}/{homeId}/rooms/devices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotDeviceListInfo>  getRoomsWithDevicesList(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotDeviceListInfo deviceListInfo = new IotDeviceListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( deviceListInfo );
    }

    /**
     * 기기 상세 정보 가져오기 at Quick IOT 제어 > 공간별 보기 or 기기별 보기
     */
    @GetMapping(
            value = "/iot/{complexId}/{homeId}/{deviceId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotDeviceInfo>  getDeviceInfo(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("deviceId")  int deviceId )
    {
        IotDeviceInfo deviceInfo = new IotDeviceInfo();

        return ResponseEntity.status(HttpStatus.OK).body( deviceInfo );
    }

    /**
     * 기기 카테고리 가져오기 at Quick IOT 제어 > 기기별 보기
     */
    @GetMapping(
            value = "/iot/{complexId}/{homeId}/deviceGroup",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotDeviceGroupListInfo>  getDeviceGroupList(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotDeviceGroupListInfo deviceGroupListInfo = new IotDeviceGroupListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( deviceGroupListInfo );
    }

    /**
     * 카테고리 별 기기 목록 가져오기 at Quick IOT 제어 > 기기별 보기
     */
    @GetMapping(
            value = "/iot/{complexId}/{homeId}/deviceGroup/devices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotDeviceListInfo>  getDeviceListByDeviceGroup(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotDeviceListInfo deviceListInfo = new IotDeviceListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( deviceListInfo );
    }

    /**
     * 개별 '모드'의 상세 정보 가져오기 at IOT 모드 실행
     */
    @GetMapping(
            path = "/{complexId}/{homeId}/modes/{modeId}",
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
     * 개별 '모드'로 전환 at IOT 모드 실행
     */
    @PutMapping(
            path = "/{complexId}/{homeId}/modes/{modeId}/switchTo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotModeInfo> switchToMode(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("modeId")    int modeId )
    {
        IotModeInfo modeInfo = new IotModeInfo();

        return ResponseEntity.status(HttpStatus.OK).body( modeInfo );
    }

    /**
     * '모드' 편집에서, 모드 목록 및 순서를 가져오기 at Mode 내용 편집
     */
    @GetMapping(
            path = "/{complexId}/{homeId}/modes/order",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotModeListInfo> getModesOrder(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotModeListInfo modeListInfo = new IotModeListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( modeListInfo );
    }

    /**
     * '모드' 편집에서, 모드 목록 및 순서를 가져오기 at Mode 내용 편집
     */
    @PutMapping(
            path = "/{complexId}/{homeId}/modes/order",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotModeListInfo> updateModesOrder(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotModeListInfo modeListInfo = new IotModeListInfo();

        return ResponseEntity.status(HttpStatus.OK).body( modeListInfo );
    }

    /**
     * '모드' 상세에서 수정한 설정 정보 업데이트하기 at IOT 모드 실행
     */
    @PutMapping(
            path = "/{complexId}/{homeId}/modes/{modeId}",
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
     * MyIOT에 추가할 수 있는 모든 기기/가치정보/오토메이션 목록 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/{complexId}/{homeId}/buttons",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> getButtons(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        Map list = new HashMap();

        return ResponseEntity.status(HttpStatus.OK).body( list );
    }

    /**
     * MyIOT에서 '시나리오/오토메이션'의 상세정보 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/{complexId}/{homeId}/automations/{automationId}",
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
     * MyIOT에서 '시나리오/오토메이션' 생성하기 at MyIOT 추가
     */
    @PostMapping(
            path = "/{complexId}/{homeId}/automations",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IotAutomationInfo> createAutomation(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        IotAutomationInfo automationInfo = new IotAutomationInfo();

        return ResponseEntity.status(HttpStatus.OK).body( automationInfo );
    }

    /**
     * MyIOT에서 '시나리오/오토메이션' 업데이트하기 at MyIOT 추가
     */
    @PutMapping(
            path = "/{complexId}/{homeId}/automations/{automationId}",
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
     * 특정 시나리오의 '센서' 목록 및 속성 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/{complexId}/{homeId}/automations/{automationId}/sensors",
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
     * 특정 시나리오의 '센서' 목록 및 속성 변경하기 at MyIOT 추가
     */
    @PutMapping(
            path = "/{complexId}/{homeId}/automations/{automationId}/sensors/{sensorId}",
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
     * 특정 시나리오의 '기기' 목록 및 속성 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/{complexId}/{homeId}/automations/{automationId}/devices",
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
     * 특정 시나리오의 '기기' 목록 및 속성 변경하기 at MyIOT 추가
     */
    @PutMapping(
            path = "/{complexId}/{homeId}/automations/{automationId}/devices/{deviceId}",
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
     * 에너지 사용량 정보 가져오기 at MyIOT - 에너지사용량 상세 화면
     */
    @GetMapping(
            path = "/{complexId}/{homeId}/energy/{energyType}",
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
     * 에너지사용량 초과 알람 설정하기 at MyIOT - 에너지사용량 상세 화면
     */
    @PostMapping(
            path = "/{complexId}/{homeId}/energy/{energyType}/exceedAlarm",
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
     * 에너지사용량 초과 알람 삭제하기 at MyIOT - 에너지사용량 상세 화면
     */
    @DeleteMapping(
            path = "/{complexId}/{homeId}/energy/{energyType}/exceedAlarm",
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
     * 에너지사용량 알람 변경하기 at MyIOT - 에너지사용량 상세 화면
     */
    @PutMapping(
            path = "/{complexId}/{homeId}/energy/{energyType}/exceedAlarm",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnergyInfo> setEnergyUsageExceedAlarm(
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("energyType") String  energyType)
    {
        EnergyInfo energyInfo = new EnergyInfo();

        return ResponseEntity.status(HttpStatus.OK).body( energyInfo );
    }
}