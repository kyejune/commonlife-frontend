package com.kolon.comlife.iot.service;

import com.kolon.comlife.iot.model.*;
import org.springframework.http.ResponseEntity;

public interface IotInfoService {
    IotModeListInfo getModeList(int complexId, int homeId) throws Exception;
    IotModeListInfo getActiveMode(int complexId, int homeId) throws Exception;
    IotButtonListInfo getMyIotButtonList(int complexId, int homeId, String userId) throws Exception;
    IotButtonListInfo getMyIotButtonListById(int complexId, int homeId, String userId, int buttonId) throws Exception;
    IotRoomListInfo getRoomList(int complexId, int homeId) throws Exception;
    IotDeviceListInfo getRoomsWithDevicesList(int complexId, int homeId, int roomId) throws Exception;
    IotDeviceListInfo getDeviceInfo(int complexId, int homeId, String deviceId) throws Exception;
    IotDeviceGroupListInfo getDeviceGroupList(int complexId, int homeId ) throws Exception;
    IotDeviceListInfo getDeviceListByDeviceGroup(int complexId, int homeId, String categoryCode) throws Exception;
    IotDeviceListInfo getDevicesUsageHistory(int complexId, int homeId, int pageNo, int pageRow) throws Exception;
    IotModeInfo getModeDetail(int complexId, int homeId, int modeId) throws Exception;
}
