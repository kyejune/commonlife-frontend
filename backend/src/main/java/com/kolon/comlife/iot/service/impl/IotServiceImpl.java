package com.kolon.comlife.iot.service.impl;

import com.kolon.comlife.iot.model.*;
import com.kolon.comlife.iot.service.IotService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("iotService")
public class IotServiceImpl implements IotService {

    public IotServiceImpl() {
    }

    public IotModeListInfo getModesList(int complexId, int homeId) {
        return null;
    }

    public IotModeInfo getActiveMode(int complexId, int homeId) {
        return null;
    }

    public IotButtonListInfo getMyIotButtonList(int complexId, int homeId) {
        return null;
    }

    public IotButtonInfo getMyIotButton(int complexId, int homeId) {
        return null;
    }

    public IotButtonInfo executeMyIotButtonPrimeFunction(int complexId, int homeId, int buttonId) {
        return null;
    }

    public Map getValueInfoOfWeather(int complexId) {
        return null;
    }
    
    public Map executeDevicePrimeFunction(int complexId, int homeId, int deviceId) {
        return null;
    }

    public IotRoomListInfo getRoomList(int complexId, int homeId) {
        return null;
    }
    
    public IotDeviceListInfo getRoomsWithDevicesList(int complexId, int homeId) {
        return null;
    }

    
    public IotDeviceInfo getDeviceInfo(int complexId, int homeId, int deviceId) {
        return null;
    }

    
    public IotDeviceGroupListInfo getDeviceGroupList(int complexId, int homeId) {
        return null;
    }

    
    public IotDeviceListInfo getDeviceListByDeviceGroup(int complexId, int homeId) {
        return null;
    }

    
    public IotModeInfo getModeInfo(int complexId, int homeId, int modeId) {
        return null;
    }

    
    public IotModeInfo switchToMode(int complexId, int homeId, int modeId) {
        return null;
    }

    
    public IotModeListInfo getModesOrder(int complexId, int homeId) {
        return null;
    }

    
    public IotModeListInfo updateModesOrder(int complexId, int homeId) {
        return null;
    }

    
    public IotModeInfo updateModeInfo(int complexId, int homeId, int modeId) {
        return null;
    }

    
    public Map getButtons(int complexId, int homeId) {
        return null;
    }

    
    public IotAutomationInfo getAutomation(int complexId, int homeId, int automationId) {
        return null;
    }

    
    public IotAutomationInfo createAutomation(int complexId, int homeId) {
        return null;
    }

    
    public IotAutomationInfo updateAutomation(int complexId, int homeId, int automationId) {
        return null;
    }

    
    public IotSensorListInfo getSensorsListOnAutomation(int complexId, int homeId, int automationId) {
        return null;
    }

    
    public IotSensorListInfo updateSensorOnAutomation(int complexId, int homeId, int automationId, int sensorId) {
        return null;
    }

    
    public IotDeviceListInfo getDevicesListOnAutomation(int complexId, int homeId, int automationId) {
        return null;
    }

    
    public IotDeviceInfo getDevicesListOnAutomation(int complexId, int homeId, int automationId, int deviceId) {
        return null;
    }

    
    public EnergyInfo getEnergyUsage(int complexId, int homeId, String energyType) {
        return null;
    }

    
    public EnergyInfo createEnergyUsageExceedAlarm(int complexId, int homeId, String energyType) {
        return null;
    }

    
    public EnergyInfo deleteEnergyUsageExceedAlarm(int complexId, int homeId, String energyType) {
        return null;
    }

    
    public EnergyInfo setEnergyUsageExceedAlarm(int complexId, int homeId, String energyType) {
        return null;
    }
}
