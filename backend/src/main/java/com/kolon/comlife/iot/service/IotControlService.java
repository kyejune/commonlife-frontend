package com.kolon.comlife.iot.service;

import com.kolon.comlife.iot.model.*;

import java.util.Map;

public interface IotControlService {

    // 5. or 6. 개별 IOT 버튼의 대표 기능 실행
    IotButtonListInfo executeMyIotButtonPrimeFunction(int complexId, int homeId, String userId, int buttonId)  throws Exception;

    Map executeDevicePrimeFunction( int complexId, int homeId, int deviceId)  throws Exception;

    IotModeListInfo switchToMode(int complexId, int homeId, String modeId) throws Exception;

    IotModeListInfo updateModesOrder(int complexId, int homeId);
    IotModeAutomationInfo updateModeInfo(int complexId, int homeId, int modeId);

    IotAutomationInfo createAutomation(int complexId, int homeId);
    IotAutomationInfo updateAutomation(int complexId, int homeId, int automationId);


    IotSensorListInfo updateSensorOnAutomation(int complexId, int homeId, int automationId, int sensorId);

    EnergyInfo createEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);

    EnergyInfo deleteEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);

    EnergyInfo setEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);
}
