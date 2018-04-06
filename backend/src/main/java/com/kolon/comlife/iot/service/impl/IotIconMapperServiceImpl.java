package com.kolon.comlife.iot.service.impl;

import com.kolon.comlife.iot.service.IotIconMapperService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IotIconMapperServiceImpl implements IotIconMapperService {
    private static final String IOT_DEFAULT_ICON                 = "cl_iot_default";
    private static final String IOT_ROOM_TYPE_DEFAULT_ICON       = "cl_iot_room_default";
    private static final String IOT_DEVICE_CATEGORY_DEFAULT_ICON = "cl_iot_category_default";
    private static final String IOT_AUTOMATION_DEFAULT_ICON      = "cl_iot_automation_default";
    private static final String IOT_INFORMATION_DEFAULT_ICON     = "cl_iot_information_default";

    private Map<String, String> iconIotMap;
    private Map<String, String> iconRoomMap;
    private Map<String, String> iconCateogryMap;

    public IotIconMapperServiceImpl() {
        this.iconIotMap      = this.initIotIconMapping();
        this.iconRoomMap     = this.initRoomIconMapping();
        this.iconCateogryMap = this.initCategoryIconMapping();
    }

    public String getIok2ClIcon(String imgSrc) {
        String val;

        val = this.iconIotMap.get(imgSrc);
        if( val == null ) {
            return IOT_DEFAULT_ICON;
        }

        return val;
    }

    public String getIconFromRoomType(String typeCd) {
        String val;

        val = this.iconRoomMap.get(typeCd);
        if( val == null ) {
            return IOT_ROOM_TYPE_DEFAULT_ICON;
        }

        return val;
    }

    public String getIconFromDeviceCategory(String cateCd) {
        String val;

        val = this.iconCateogryMap.get(cateCd);
        if( val == null ) {
            return IOT_DEVICE_CATEGORY_DEFAULT_ICON;
        }

        return val;
    }


    public String getIotDefaultIcon() {
        return IOT_DEFAULT_ICON;
    }

    public String getIconAutomationDefault() {
        return IOT_AUTOMATION_DEFAULT_ICON;
    }

    public String getIconInformationDefault() {
        return IOT_INFORMATION_DEFAULT_ICON;
    }



    private Map<String, String> initRoomIconMapping() {
        Map<String, String> iconMap = new HashMap<>();

        iconMap.put("AD00302", "cl_room_9"); // 현관
        iconMap.put("AD00303", "cl_room_2"); // 거실
        iconMap.put("AD00308", "cl_room_1"); // 침실
        iconMap.put("AD00309", "cl_room_6"); // 서재
        iconMap.put("AD00310", "cl_room_5"); // 옷방
        iconMap.put("AD00304", "cl_bath_tap_1"); // 거실화장실
        iconMap.put("AD00305", "cl_bath_tap_2"); // 안방화장실

        return  iconMap;
    }

    private Map<String, String> initCategoryIconMapping() {
        Map<String, String> iconMap = new HashMap<>();

        iconMap.put("HW00201", "cl_device_1"); // 스위치
        iconMap.put("HW00202", "cl_house-4"); // 센서
        iconMap.put("HW00206", "cl_status_3"); // 보일러
        iconMap.put("HW00213", "cl_device_3"); // 가스 밸브
        iconMap.put("HW00216", "cl_device_4"); // 동작감지센서
        iconMap.put("HW00218", "cl_kitchen-7-4"); // 플러그
        iconMap.put("HW00219", "cl_kitchen-6"); // 스마트플러그
        iconMap.put("HW00220", "cl_device_7"); // 가전

        return  iconMap;
    }

    private Map<String, String> initIotIconMapping() {
        Map<String, String> iconMap = new HashMap<>();

        iconMap.put("sub_icon_23_1_1", "cl_mode_2");    // 취침모드
        iconMap.put("sub_icon_25_1_1", "cl_mode_1");    // 외출모드
        iconMap.put("sub_icon_24_1_1", "cl_mode_5");    // 기상모드
        iconMap.put("sub_icon_26_1_1", "cl_mode_6");    // 귀가모드

        iconMap.put("sub_icon_6_1", "cl_device_3");    // 옷방 가스벨브
        iconMap.put("sub_icon_8_1", "cl_status_4");    // 옷방 통합형대기전력차단기
        iconMap.put("sub_icon_5_1", "cl_device_1");    // 현관 스위치_1
        iconMap.put("sub_icon_5_2", "cl_device_1");    // 현관 스위치_1
        iconMap.put("sub_icon_38_1", "cl_house-4");   // 서재 통합실내공기질센서
//        iconMap.put("sub_icon_30_1", "");   // 마그네틱 센서
        iconMap.put("sub_icon_88_1", "cl_life-7");   // 벽걸이 에어컨
        iconMap.put("sub_icon_88_2", "cl_life-7");   // 벽걸이 에어컨
        iconMap.put("sub_icon_84_1", "cl_life-7");   // 공기청정기
        iconMap.put("sub_icon_4_1", "cl_status_4");    // 온도조절기
        iconMap.put("sub_icon_4_2", "cl_status_4");    // 온도조절기
//        iconMap.put("sub_icon_91_1", "");    // 로봇청소기
        iconMap.put("sub_icon_36_1", "cl_status_1");    // 에너지 사용량
//        iconMap.put("sub_icon_29_1", "");    // 동체감시센서
        iconMap.put("main_life_weather_icon_1", "cl_weather-3");    // 날씨 - 가치정보 - 해
        iconMap.put("main_life_weather_icon_2", "cl_weather-5");    // 날씨 - 가치정보 - 해+구름
        iconMap.put("main_life_weather_icon_3", "cl_weather-4");    // 날씨 - 가치정보 - 구름
        iconMap.put("main_life_weather_icon_4", "cl_weather-4");    // 날씨 - 가치정보 - 구름+구름
        iconMap.put("main_life_weather_icon_5", "cl_weather-8");    // 날씨 - 가치정보 - 구름+비
        iconMap.put("main_life_weather_icon_6", "cl_weather-9");    // 날씨 - 가치정보 - 구름+눈
        iconMap.put("main_life_weather_icon_7", "cl_weather-9");    // 날씨 - 가치정보 - 구름+눈+비
        iconMap.put("main_life_weather_icon_8", "cl_weather-4");    // 날씨 - 가치정보 - 먹구름
        iconMap.put("main_life_weather_icon_9", "cl_weather-11");    // 날씨 - 가치정보 - 먹구름 + 번개
        iconMap.put("main_life_weather_icon_10", "cl_weather-11");    // 날씨 - 가치정보 - 먹구름 + 번개 + 비
        iconMap.put("main_life_weather_icon_11", "cl_life-5");    // 날씨 - 가치정보 - 회오리
        iconMap.put("main_life_weather_icon_12", "cl_life-5");    // 날씨 - 가치정보 - 황사
        iconMap.put("main_life_weather_icon_13", "cl_life-5");    // 날씨 - 가치정보 - 안개

        iconMap.put("CM02201", "cl_iot_automation_default");         // 시나리오=오토메이션

        return  iconMap;
    }

}
