package com.kolon.comlife.iot.service;

import com.kolon.comlife.iot.model.*;

import java.util.Map;

public interface IotService {

    IotModeListInfo getModesList(int complexId, int homeId);
    IotModeInfo getActiveMode(int complexId, int homeId);
    IotButtonListInfo getMyIotButtonList(int complexId, int homeId);
    IotButtonInfo getMyIotButton(int complexId, int homeId);
    IotButtonInfo executeMyIotButtonPrimeFunction(int complexId, int homeId, int buttonId);
    Map getValueInfoOfWeather(int complexId);
    Map executeDevicePrimeFunction( int complexId, int homeId, int deviceId);
    IotRoomListInfo getRoomList(int complexId, int homeId);
    IotDeviceListInfo getRoomsWithDevicesList(int complexId, int homeId);
    IotDeviceInfo getDeviceInfo(int complexId, int homeId, int deviceId);
    IotDeviceGroupListInfo getDeviceGroupList(int complexId, int homeId);
    IotDeviceListInfo getDeviceListByDeviceGroup(int complexId, int homeId);
    IotModeInfo getModeInfo(int complexId, int homeId, int modeId);
    IotModeInfo switchToMode(int complexId, int homeId, int modeId);
    IotModeListInfo getModesOrder(int complexId, int homeId);
    IotModeListInfo updateModesOrder(int complexId, int homeId);
    IotModeInfo updateModeInfo(int complexId, int homeId, int modeId);
    Map getButtons(int complexId, int homeId);
    IotAutomationInfo getAutomation(int complexId, int homeId, int automationId);
    IotAutomationInfo createAutomation(int complexId, int homeId);
    IotAutomationInfo updateAutomation(int complexId, int homeId, int automationId);
    IotSensorListInfo getSensorsListOnAutomation(int complexId, int homeId, int automationId);
    IotSensorListInfo updateSensorOnAutomation(int complexId, int homeId, int automationId, int sensorId);
    IotDeviceListInfo getDevicesListOnAutomation(int complexId, int homeId, int automationId);
    IotDeviceInfo getDevicesListOnAutomation(int complexId, int homeId, int automationId, int deviceId);
    EnergyInfo getEnergyUsage(int complexId, int homeId, String  energyType);
    EnergyInfo createEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);
    EnergyInfo deleteEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);
    EnergyInfo setEnergyUsageExceedAlarm(int complexId, int homeId, String  energyType);

}
