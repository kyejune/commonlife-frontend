// import Store from 'scripts/store.js';
import axios from 'axios';
import {observable} from 'mobx';
import Store from "./store";
// import SimpleJsonFilter from 'simple-json-filter/index';


// let sjf = new SimpleJsonFilter();


// axios.defaults.baseURL = 'http://localhost:8080'// host;
axios.defaults.headers.common['api_key'] = 'acfc218023f1d7d16ae9a38c31ddd89998f32a9ee15e7424e2c6016a8dbcda70';
axios.defaults.headers.common['Content-Type'] = 'application/json; charset=UTF-8';

axios.interceptors.response.use(null, function(err) {
    console.log( err.response );
    if( err.response.status !== 200) {

        if( Store.ipo !== null ){
            let obj = Store.ipo;
                obj.status = 2;
                obj.error = err.response.data.msg;
            Store.ipo = Object.assign( {}, obj );
            Store.hideIpo();
        }
    }

    return Promise.reject(err);
});


/*
* name: 출력할 이름
* cmd: iot.js 명령호출할때 쓸 이름
* type: 0 = 토글, 1 = 특정값 지정
* */

export const Devices = {
    living1: {cmd: '', name: '거실 중앙등', type: 0},
    main1: {cmd: '', name: '안방 조명1', type: 0},
    main2: {cmd: '', name: '안방 조명2', type: 0},
    boiler1: {cmd: '', name: '중앙 보일러', type: 1},
    // 계속해서 추가해야함다...
};

export const ModeChanges = {
    outMode: {cmd: '', name: '외출모드', type: 0},
};


const sortBySortOrder = (a, b) => {
    return a.sortOrder - b.sortOrder;
}


export let Modes = observable([]);
export let MyIots = observable([
    {
        "btId": 1,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-15 23:57:06"
    },
    {
        "btId": 18,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-22 15:46:24"
    },
    {
        "btId": 2,
        "btImgSrc": "CM02201",
        "btRightIcon": "",
        "btRightText": "",
        "btSubTitle": "",
        "btTitle": "단지시나테스-3a",
        "btTitleUnit": "",
        "btType": "automation",
        "mNm": "단지시나테스-3a",
        "myIotGbCd": "MB01702",
        "myIotId": "99",
        "regDt": "2018-03-15 23:57:06"
    },
    {
        "btId": 8,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-17 20:51:47"
    },
    {
        "btId": 19,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-22 15:46:24"
    },
    {
        "btId": 23,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-22 16:08:19"
    },
    {
        "btId": 10,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-17 20:52:01"
    },
    {
        "btId": 11,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-19 20:17:20"
    },
    {
        "btId": 13,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-19 20:18:41"
    },
    {
        "btId": 15,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-19 20:18:53"
    },
    {
        "btId": 4,
        "btImgSrc": "sub_icon_91_1",
        "btLeft": "청소기제어",
        "btRightIcon": "",
        "btRightIconType": "detail",
        "btRightText": "Auto",
        "btSubTitle": "",
        "btTitle": "Test_로봇청소기",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-15 23:57:06"
    },
    {
        "btId": 7,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-15 23:57:06"
    },
    {
        "btId": 9,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-17 20:51:47"
    },
    {
        "btId": 12,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-19 20:17:20"
    },
    {
        "btId": 14,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-19 20:18:41"
    },
    {
        "btId": 16,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-19 20:18:53"
    },
    {
        "btId": 5,
        "btImgSrc": "sub_icon_36_1",
        "btLeft": "전기",
        "btRightIcon": "",
        "btRightText": "",
        "btSubTitle": "에너지 사용량",
        "btTitle": "12",
        "btTitleUnit": "Kw",
        "btType": "information",
        "mNm": null,
        "myIotGbCd": "MB01703",
        "myIotId": "CM023009",
        "regDt": "2018-03-15 23:57:06"
    },
    {
        "btId": 20,
        "btImgSrc": "sub_icon_29_1",
        "btLeft": "동작감지",
        "btRightIcon": "",
        "btRightIconType": "detail",
        "btRightText": "undetected",
        "btSubTitle": "",
        "btTitle": "동체감시센서",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-22 16:06:59"
    },
    {
        "btId": 24,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-22 16:10:23"
    },
    {
        "btId": 21,
        "btImgSrc": "sub_icon_29_1",
        "btLeft": "동작감지",
        "btRightIcon": "",
        "btRightIconType": "detail",
        "btRightText": "undetected",
        "btSubTitle": "",
        "btTitle": "동체감시센서",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-22 16:07:07"
    },
    {
        "btId": 22,
        "btImgSrc": "sub_icon_5_1",
        "btLeft": "전원",
        "btRightIcon": "",
        "btRightIconType": "button",
        "btRightText": "off",
        "btSubTitle": "",
        "btTitle": "멀티스위치4단",
        "btTitleUnit": "",
        "btType": "device",
        "myIotGbCd": "MB01701",
        "myIotId": "null",
        "regDt": "2018-03-22 16:08:19"
    },
    {
        "btId": 25,
        "btImgSrc": "main_life_weather_icon_3",
        "btLeft": "구름많음",
        "btRightIcon": "",
        "btRightText": "현재습도 70%",
        "btSubTitle": "경기도 안양시동안구 부림동",
        "btTitle": "4.9",
        "btTitleUnit": "℃",
        "btType": "information",
        "mNm": null,
        "myIotGbCd": "MB01703",
        "myIotId": "CM023005",
        "regDt": "2018-03-23 18:14:07"
    },
    {
        "btId": 26,
        "btRightIcon": "",
        "btRightText": "",
        "btSubTitle": "",
        "btTitle": "hoooray-2",
        "btTitleUnit": "",
        "btType": "automation",
        "mNm": "hoooray-2",
        "myIotGbCd": "MB01702",
        "myIotId": "131",
        "regDt": "2018-03-23 18:14:07"
    }
]);

export default {

    /* Iot */
    getIotAll() {

        axios.all([
            axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes`),
            axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot`)
        ]).then(axios.spread((modeRes, iotRes) => {

            // 편의상 순서 맞추는건 이쪽에서 해서 보내줌
            modeRes.data.data.sort(sortBySortOrder);
            iotRes.data.data.sort(sortBySortOrder);

            Modes.replace(modeRes.data.data);
            MyIots.replace(iotRes.data.data);
        }));
    },

    getMode() {
        axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes`)
            .then(response => {
                response.data.data.sort(sortBySortOrder);
                Modes.replace(response.data.data);
            });
    },


    /* 공간별, 기기별 카테고리 출력 */
    getDeviceCategories(callback) {
        axios.all([
            axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/rooms/`),
            axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/deviceCategory/`)
        ]).then(axios.spread((place, device) => {
            callback(place.data.data, device.data.data);
        }));
    },

    /* 공간별( true, roomId), 기기별( false, cateCd )에 카테고리에 따른 기기 목록 */
    getDevicesByCategory(isRoom, cateId, callback) {
        axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/${isRoom ? 'rooms' : 'deviceCategory'}/${cateId}/devices`)
            .then(response => {
                callback(response.data.data);
            });
    },


    getSensors() {

    },

    /* 모드 버튼 클릭시 on/off 변경 */
    changeIotMode(modeId, value, callback) {
        console.log(`changeIotMode: ${modeId}의 값을 ${value}로 변경`);

        axios.put(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes/${modeId}/switchTo`)
            .then(response => {
                callback();
            });
    },

    /* 모드 정렬 변경 */
    reAlignIotMode(map, callback) {
        axios.post(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes/order`, map)
            .then(response => {
                callback();
            });
    },

    /* 모드 상세 가져오기 */
    getModeDetail(mode, callback) {
        axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes/${mode}`)
            .then(response => {
                callback(response.data);
            });
    },


    /* Iot장비의 값을 변경 */
    setIotToggleDevice(data, bool, callback) {

        const {deviceId, protcKey, maxVlu, minVlu} = data;

        const value = bool ? maxVlu : minVlu;
        Store.ipo = { status:0, name:data.thingsNm, value: value.toUpperCase() };

        console.log( data, `의 값을 ${value}로 변경`, Store.ipo );
        axios.put(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/devices/${deviceId}/action?protcKey=${encodeURIComponent(protcKey)}&value=${value}`)
            .then(response => {
                callback( true );
                Store.ipo = { status:1, name:data.thingsNm, value: value.toUpperCase() };
                Store.hideIpo();
            })
            .catch(error=>{
               callback( false );
            });
    },

    getDeviceInfo( deviceId, callback) {
        console.log('deviceInfo:', deviceId);
        axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/devices/${deviceId}`)
            .then(response => {
                callback(response.data.data);
            });
    },

    // /* Iot모드의 값을 변경 */
    // setIotMode(modeName, value) {
    //     console.log(`${modeName}의 값을 ${value}로 변경`);
    // }
    // ,


};