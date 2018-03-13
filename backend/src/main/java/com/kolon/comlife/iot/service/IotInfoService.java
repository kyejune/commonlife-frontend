package com.kolon.comlife.iot.service;

import com.kolon.comlife.iot.model.IotButtonListInfo;
import com.kolon.comlife.iot.model.IotModeListInfo;

public interface IotInfoService {
    IotModeListInfo getModeList(int complexId, int homeId) throws Exception;
    IotModeListInfo getActiveMode(int complexId, int homeId) throws Exception;
    IotButtonListInfo getMyIotButtonList(int complexId, int homeId, String userId) throws Exception;
    IotButtonListInfo getMyIotButtonListById(int complexId, int homeId, String userId, int buttonId) throws Exception;
}
