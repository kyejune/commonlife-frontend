package com.kolon.comlife.iot.service;

import com.kolon.comlife.iot.model.*;

public interface IotControlService {
    IotButtonListInfo executeMyIotButtonPrimeFunction
            (int complexId, int homeId, String userId, int buttonId)  throws Exception;

    IotDeviceListInfo executeDeviceFunction
            ( int complexId, int homeId, int deviceId, IotDeviceControlMsg ctrlMsg)  throws Exception;

    IotModeOrAutomationListInfo switchToMode
            (int complexId, int homeId, String modeId) throws Exception;

    IotModeOrAutomationListInfo turnOffMode
            (int complexId, int homeId) throws Exception;

    EnergyInfo createEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);

    EnergyInfo deleteEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);

    EnergyInfo setEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);
}
