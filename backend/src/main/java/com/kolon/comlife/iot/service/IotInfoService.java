package com.kolon.comlife.iot.service;

import com.kolon.comlife.iot.model.*;

public interface IotInfoService {
    IotModeListInfo getModeList
            (int complexId, int homeId) throws Exception;

    IotModeListInfo getActiveMode
            (int complexId, int homeId) throws Exception;

    IotButtonListInfo getMyIotButtonList
            (int complexId, int homeId, String userId, boolean resultSimplify) throws Exception;

    IotButtonListInfo getMyIotButtonListById
            (int complexId, int homeId, String userId, int buttonId, boolean resultSimplify) throws Exception;

    IotButtonListInfo deleteMyIotButtonListById
            (int complexId, int homeId, String userId, int buttonId, boolean resultSimplify) throws Exception;

    IotButtonListInfo getMyIotButtonListAvailable
            (int complexId, int homeId, String userId) throws Exception;

    IotButtonListInfo simplifyMyIotButtonListResult( IotButtonListInfo result ) throws Exception;

    IotRoomListInfo getRoomList
            (int complexId, int homeId) throws Exception;

    IotDeviceListInfo getRoomsWithDevicesList
            (int complexId, int homeId, int roomId) throws Exception;

    IotDeviceListInfo getDeviceInfo
            (int complexId, int homeId, int deviceId) throws Exception;

    IotDeviceGroupListInfo getDeviceGroupList
            (int complexId, int homeId ) throws Exception;

    IotDeviceListInfo getDeviceListByDeviceGroup
            (int complexId, int homeId, String categoryCode) throws Exception;

    IotDeviceListInfo getDevicesUsageHistory
            (int complexId, int homeId, int pageNo, int pageRow) throws Exception;

    IotModeAutomationInfo getModeOrAutomationDetail
            (int complexId, int homeId, int modeOrAutomationId , boolean modeFlag) throws Exception;

    IotModeAutomationInfo getModeOrAutomationActors(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception;

    IotModeAutomationInfo getModeOrAutomationConditions(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception;

    IotModeAutomationInfo getModeOrAutomationConditionsAvailable(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception;

    IotModeAutomationInfo getModeOrAutomationActorsAvailable(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception;

    IotModeListInfo updateModesOrder(int complexId, int homeId, IotModeListInfo modeList) throws Exception;

    IotModeAutomationInfo createAutomation(int complexId, int homeId, IotModeAutomationInfo automationInfo) throws Exception;

    IotDeviceListInfo updateDeviceDesc(int complexId, int homeId, int deviceId, String desc) throws Exception;

}
