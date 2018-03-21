// import Store from 'scripts/store.js';
import axios from 'axios';
import {observable} from 'mobx';
import Store from "./store";
// import SimpleJsonFilter from 'simple-json-filter/index';


// let sjf = new SimpleJsonFilter();


// axios.defaults.baseURL = 'http://localhost:8080'// host;
axios.defaults.headers.common['api_key'] = 'acfc218023f1d7d16ae9a38c31ddd89998f32a9ee15e7424e2c6016a8dbcda70';
axios.defaults.headers.common['Content-Type'] = 'application/json; charset=UTF-8';


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
export let MyIots = observable([]);


export default {

    /* Iot */
    getIotAll() {

        axios.all([
            axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes`),
            axios.get('./dummy/iot-my.json')
        ]).then(axios.spread((modeRes, iotRes) => {

            // 편의상 순서 맞추는건 이쪽에서 해서 보내줌
            modeRes.data.data.sort(sortBySortOrder);
            iotRes.data.data.sort(sortBySortOrder);

            Modes.replace(modeRes.data.data);
            MyIots.replace(iotRes.data.data);
        }));
    },

    getMode(){
        axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes`)
            .then( response=>{
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
    changeIotMode( modeId, value, callback) {
        console.log(`changeIotMode: ${modeId}의 값을 ${value}로 변경`);

        axios.put(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes/${modeId}/switchTo`)
            .then( response =>{
               callback();
            });
    },

    /* 모드 정렬 변경 */
    reAlignIotMode( map, callback ){
      axios.post(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes/order`, map )
          .then( response => {
             callback();
          });
    },
    
    /* 모드 상세 가져오기 */
    getModeDetail( mode, callback ){
        axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes/${mode}`)
            .then( response => {
               callback( response.data );
            });
    },


    /* Iot장비의 값을 변경 */
    setIotDevice(deviceName, value, callback) {

        console.log(`${deviceName}의 값을 ${value}로 변경`);
        setTimeout(() => callback(), 4000);

    },

    // /* Iot모드의 값을 변경 */
    // setIotMode(modeName, value) {
    //     console.log(`${modeName}의 값을 ${value}로 변경`);
    // }
    // ,


}
;