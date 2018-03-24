package com.kolon.comlife.iot.service;

import com.kolon.comlife.iot.model.*;

import java.util.List;
import java.util.Map;

public interface IotInfoService {
    IotModeOrAutomationListInfo getModeList
            (int complexId, int homeId) throws Exception;

    IotModeOrAutomationListInfo getActiveMode
            (int complexId, int homeId) throws Exception;

    IotButtonListInfo getMyIotButtonList
            (int complexId, int homeId, String userId, boolean resultSimplify) throws Exception;

    IotButtonListInfo getMyIotButtonListById
            (int complexId, int homeId, String userId, int buttonId, boolean resultSimplify) throws Exception;

    IotButtonListInfo deleteMyIotButtonListById
            (int complexId, int homeId, String userId, int buttonId, boolean resultSimplify) throws Exception;

    IotButtonListInfo getMyIotButtonListAvailable
            (int complexId, int homeId, String userId) throws Exception;

    IotButtonListInfo getMyIotAutomationListAvailable
            (int complexId, int homeId, String userId) throws Exception;

    IotButtonListInfo getMyIotDevicesListAvailable
            (int complexId, int homeId, String userId) throws Exception;

    IotButtonListInfo getMyIotValueInfoListAvailable
            (int complexId, int homeId, String userId) throws Exception;

    IotButtonListInfo addMyIotButtonByMyIotID
            (int complexId, int homeId, String userId, List<Map<String, Object>> myIotIdList) throws Exception;

    IotButtonListInfo updateMyIotButtonOrder
            (int complexId, int homeId, String userId, List<Map<String, Object>> myIotIdOrderList) throws Exception;

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

    IotDeviceListInfo getModeOrAutomationActorDetail(
            int complexId, int homeId, int modeOrAutomationId, int deviceId, boolean modeFlag) throws Exception;

    IotModeAutomationInfo getModeOrAutomationConditions(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception;

    IotModeAutomationInfo getModeOrAutomationConditionsAvailable(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception;

    IotModeAutomationInfo getModeOrAutomationActorsAvailable(
            int complexId, int homeId, int modeOrAutomationId, boolean modeFlag) throws Exception;

    IotModeOrAutomationListInfo updateModesOrder(int complexId, int homeId, IotModeOrAutomationListInfo modeList) throws Exception;

    IotModeAutomationIdInfo createAutomation(
            int complexId, int homeId, String userId, IotModeAutomationInfo automationInfo) throws Exception;

    IotModeAutomationIdInfo updateAutomation(
            int complexId, int homeId, int automationId, String userId, IotModeAutomationInfo automationInfo, boolean modeFlag) throws Exception;

    IotDeviceListInfo updateDeviceDesc(int complexId, int homeId, int deviceId, String desc) throws Exception;
    
    IotModeAutomationIdInfo deleteAutomation(int complexId, int homeId, int automationId) throws Exception;

    IotModeOrAutomationListInfo getAutomationAll(int complexId, int homeId) throws Exception;

    IotModeAutomationInfo getModeOrAutomationAllConditions(
            int complexId, int homeId, boolean modeFlag) throws Exception;

    IotModeAutomationInfo getModeOrAutomationAllActors(
            int complexId, int homeId, boolean modeFlag) throws Exception;
}
