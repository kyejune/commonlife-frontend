package com.kolon.comlife.iot.service;

import com.kolon.comlife.iot.model.IotModeListInfo;

public interface IotInfoService {
    public IotModeListInfo getModeList(int complexId, int homeId) throws Exception;
    public IotModeListInfo getActiveMode(int complexId, int homeId) throws Exception;
}
