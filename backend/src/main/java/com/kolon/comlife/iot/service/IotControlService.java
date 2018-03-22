package com.kolon.comlife.iot.service;

import com.kolon.comlife.iot.model.*;

public interface IotControlService {

    // 5. or 6. 개별 IOT 버튼의 대표 기능 실행
    IotButtonListInfo executeMyIotButtonPrimeFunction
            (int complexId, int homeId, String userId, int buttonId)  throws Exception;

    // 8. 기기의 기능 수행
    IotDeviceListInfo executeDeviceFunction
            ( int complexId, int homeId, int deviceId, IotDeviceControlMsg ctrlMsg)  throws Exception;

    IotModeOrAutomationListInfo switchToMode
            (int complexId, int homeId, String modeId) throws Exception;

    EnergyInfo createEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);

    EnergyInfo deleteEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);

    EnergyInfo setEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);
}
