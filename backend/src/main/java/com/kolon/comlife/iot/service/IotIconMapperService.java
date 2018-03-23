package com.kolon.comlife.iot.service;

public interface IotIconMapperService {

    String getIok2ClIcon(String imgSrc);

    String getIconFromRoomType(String typeCd);

    String getIconFromDeviceCategory(String cateCd);

    String getIotDefaultIcon();

    String getIconAutomationDefault();

    String getIconInformationDefault();

}
