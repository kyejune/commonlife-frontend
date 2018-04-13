package com.kolon.comlife.iot.service.impl;

import com.kolon.comlife.iot.service.IotIconMapperService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IotIconMapperServiceImpl implements IotIconMapperService {
    private static final String IOT_DEFAULT_ICON                 = "cl_iot_default";
    private static final String IOT_ROOM_TYPE_DEFAULT_ICON       = "cl_iot_room-default";
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

        iconMap.put("AD00300", "cl_house-1"); // 아파트 공용
        iconMap.put("AD00301", "cl_device-7"); // 공용부
        iconMap.put("AD00302", "cl_life-1"); // 현관
        iconMap.put("AD00303", "cl_room-1"); // 거실
        iconMap.put("AD00304", "cl_bath-4"); // 화장실1/거실화장실
        iconMap.put("AD00305", "cl_bath-2"); // 화장실2/안방화장실
        iconMap.put("AD00306", "cl_bath-1"); // 화장실3
        iconMap.put("AD00307", "cl_room-4"); // 안방
        iconMap.put("AD00308", "cl_room-2"); // 방1/침실
        iconMap.put("AD00309", "cl_room-6"); // 방2/서재
        iconMap.put("AD00310", "cl_room-5"); // 방3/옷방
        iconMap.put("AD00311", "cl_room-7"); // 방4/침실
        iconMap.put("AD00312", "cl_device-29"); // 베란다1
        iconMap.put("AD00313", "cl_device-30"); // 베란다2
        iconMap.put("AD00314", "cl_device-30"); // 베란다3
        iconMap.put("AD00315", "cl_room-3"); // 주방
        iconMap.put("AD00316", "cl_room-8"); // 창고
        iconMap.put("AD00317", "cl_device-24"); // 실외기실
        iconMap.put("AD00318", "cl_room-10"); // 창문
        iconMap.put("AD00319", "cl_room-7"); // 다용도실

        return  iconMap;
    }

    private Map<String, String> initCategoryIconMapping() {
        Map<String, String> iconMap = new HashMap<>();

        iconMap.put("HW00201", "cl_device-1"); // 스위치
        iconMap.put("HW00202", "cl_house-4"); // 센서
        iconMap.put("HW00206", "cl_status-3"); // 보일러
        iconMap.put("HW00213", "cl_device-3"); // 가스 밸브
        iconMap.put("HW00216", "cl_device-4"); // 동작감지센서
        iconMap.put("HW00218", "cl_kitchen-7-4"); // 플러그
        iconMap.put("HW00219", "cl_kitchen-6"); // 스마트플러그
        iconMap.put("HW00220", "cl_device-7"); // 가전

        return  iconMap;
    }

    private Map<String, String> initIotIconMapping() {
        Map<String, String> iconMap = new HashMap<>();

        iconMap.put("sub_icon_23_1_1", "cl_mode_2");    // 취침모드
        iconMap.put("sub_icon_25_1_1", "cl_mode_1");    // 외출모드
        iconMap.put("sub_icon_24_1_1", "cl_mode_5");    // 기상모드
        iconMap.put("sub_icon_26_1_1", "cl_mode_6");    // 귀가모드

        iconMap.put("sub_icon_6_1", "cl_device-3");    // 옷방 가스벨브
        iconMap.put("sub_icon_8_1", "cl_status-4");    // 옷방 통합형대기전력차단기
        iconMap.put("sub_icon_5_1", "cl_device-1");    // 현관 스위치_1
        iconMap.put("sub_icon_5_2", "cl_device-1");    // 현관 스위치_1
        iconMap.put("sub_icon_38_1", "cl_house-4");   // 서재 통합실내공기질센서
        iconMap.put("sub_icon_30_1", "cl_device-25");   // 마그네틱 센서
        iconMap.put("sub_icon_88_1", "cl_life-7");   // 벽걸이 에어컨
        iconMap.put("sub_icon_88_2", "cl_life-7");   // 벽걸이 에어컨
        iconMap.put("sub_icon_84_1", "cl_device-13");   // 공기청정기
        iconMap.put("sub_icon_4_1", "cl_device-2");    // 보일러/온도조절기
        iconMap.put("sub_icon_4_2", "cl_status-4");    // 온도조절기
        iconMap.put("sub_icon_36_1", "cl_status-1");    // 에너지 사용량
        iconMap.put("sub_icon_29_1", "cl_device-7");    // 동체감시센서, 다중센서
        iconMap.put("sub_icon_49_1", "cl_status-5");    // 온도센서, 다중센서
        iconMap.put("sub_icon_50_1", "cl_status-2");    // 습도센서, 다중센서
        iconMap.put("sub_icon_60_1", "cl_device-9");    // 불꽃센서, 다중센서
        iconMap.put("sub_icon_36_1", "cl_device-3");    // METERS
        iconMap.put("sub_icon_31_1", "cl_life-5");    // 온도계
        iconMap.put("sub_icon_3_1", "cl_device-5");    // 도어락
        iconMap.put("icon_s8_rehome", "cl_device-8");    // 재실센서
        iconMap.put("icon_d5_speaker", "cl_device-10");    // 스피커
        iconMap.put("sub_icon_10_1", "cl_device-27");    // 커튼
        iconMap.put("sub_icon_6_1", "cl_device-6");    // 가스
        iconMap.put("sub_icon_7_1", "cl_life-7");    // 환기

        iconMap.put("sub_icon_10_1", "cl_device-27");    // 커튼
        iconMap.put("sub_icon_6_1", "cl_device-6");    // 가스
        iconMap.put("sub_icon_7_1", "cl_life-7");    // 환기
        iconMap.put("sub_icon_8_1", "cl_device-26");    // 플러그/ 아울렛플러그 / 터치플러그 / 스마트플러그
        iconMap.put("sub_icon_91_1", "cl_device-14");    // 로봇청소기
        iconMap.put("sub_icon_86_1", "cl_device-18");    // 스텐드에어콘

        iconMap.put("sub_icon_90_1", "cl_device-16");    // 냉장고
        iconMap.put("sub_icon_87_1", "cl_device-20");    // 레인지 / 오븐
        iconMap.put("sub_icon_92_1", "cl_device-17");    // 세탁기
        iconMap.put("sub_icon_101   _1", "cl_house-8");    // 엘리베이터

        iconMap.put("main_life_weather_icon_1", "cl_weather-3");    // 날씨 - 가치정보 - 해
        iconMap.put("main_life_weather_icon_2", "cl_weather-5");    // 날씨 - 가치정보 - 해+구름
        iconMap.put("main_life_weather_icon_3", "cl_weather-15");    // 날씨 - 가치정보 - 구름
        iconMap.put("main_life_weather_icon_4", "cl_weather-4");    // 날씨 - 가치정보 - 구름+구름
        iconMap.put("main_life_weather_icon_5", "cl_weather-8");    // 날씨 - 가치정보 - 구름+비
        iconMap.put("main_life_weather_icon_6", "cl_weather-9");    // 날씨 - 가치정보 - 구름+눈
        iconMap.put("main_life_weather_icon_7", "cl_weather-16");    // 날씨 - 가치정보 - 구름+눈+비
        iconMap.put("main_life_weather_icon_8", "cl_weather-17");    // 날씨 - 가치정보 - 먹구름
        iconMap.put("main_life_weather_icon_9", "cl_weather-11");    // 날씨 - 가치정보 - 먹구름 + 번개
        iconMap.put("main_life_weather_icon_10", "cl_weather-18");    // 날씨 - 가치정보 - 먹구름 + 번개 + 비
        iconMap.put("main_life_weather_icon_11", "cl_weather-19");    // 날씨 - 가치정보 - 회오리
        iconMap.put("main_life_weather_icon_12", "cl_weather-20");    // 날씨 - 가치정보 - 황사
        iconMap.put("main_life_weather_icon_13", "cl_weather-21");    // 날씨 - 가치정보 - 안개

        iconMap.put("CM02201", "cl_iot_automation_default");         // 시나리오=오토메이션

        return  iconMap;
    }

}
