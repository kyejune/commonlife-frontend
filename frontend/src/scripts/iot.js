// import Store from 'scripts/store.js';
import axios from 'axios';
import {observable} from 'mobx';
import Store from "./store";
// import SimpleJsonFilter from 'simple-json-filter/index';


// let sjf = new SimpleJsonFilter();


// axios.defaults.baseURL = 'http://localhost:8080'// host;
// axios.defaults.headers.common['api_key'] = 'acfc218023f1d7d16ae9a38c31ddd89998f32a9ee15e7424e2c6016a8dbcda70';
// axios.defaults.headers.common['Content-Type'] = 'application/json; charset=UTF-8';

// axios.interceptors.response.use(null, function(err) {
//     console.log( err.response );
//
//
//     return Promise.reject(err);
// });


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

    getMy( callback ) {
        axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot`)
            .then(response => {
                response.data.data.sort(sortBySortOrder);
                MyIots.replace(response.data.data);

                if( callback ) callback();
            });
    },


    /* 공간별, 기기별 카테고리 출력
    *  @param additional: 전체 목록 or 추가 가능한 목록
    * */
    getDeviceCategories( additional, callback) {
        axios.all([
            axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${ Store.homeId + (additional?'/myiot':'')}/rooms/${ additional?'available':''}`),
            axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${ Store.homeId + (additional?'/myiot':'')}/deviceCategory/${ additional?'available':''}`)
        ]).then(axios.spread((place, device) => {
            callback(place.data.data, device.data.data);
        }));
    },

    /* 공간별( true, roomId), 기기별( false, cateCd )에 카테고리에 따른 기기 목록
    * @params additional 추가 가능한 목록만 가져올것인지 다 가져올것인지...
    * */
    getDevicesByCategory( additional, isRoom, cateId, callback) {
        axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/${ (additional?'myiot/':'') + (isRoom ? 'rooms' : 'deviceCategory')}/${cateId}/devices${ additional?'/available':'' }`)
            .then(response => {
                callback( true, response.data.data);
            })
            .catch( error => {
               callback( false, error);
            });
    },


    /* 기기 추가!! */
    // addDevices( addingIds, callback ){
    //     const data = addingIds.map( id=>{
    //        return { myIotId: id };
    //     });
    //
    //     axios.post( `${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot`, { data: data } )
    //         .then( response => {
    //             this.getMy(); // 추가되면 대시보드 my목록 재로딩
    //             callback( true );
    //         })
    //         .catch( response => {
    //            callback( false );
    //         });
    // },

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


    /* My Iot 정렬 변경 */
    reAlignMyIot(map, callback) {
        axios.put(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot`, {data: map } )
            .then(response => {
                callback();
                this.getMy();
            });
    },

    /* My Iot 삭제 */
    removeMyIot( ids, callback ){
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/myiot/buttons/12

        const deletes = ids.map( id => {
           return axios.delete(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot/buttons/${id}`)
        });

        axios.all(deletes)
            .then( response => {
               this.getMy( callback );
            });
    },


    /* 시나리오, 가치정보의 노출 가능한 놈들 가져오기 : 홈 화면에 빼는 용도 */
    getExposableListOfDashboard( type, callback ){
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/myiot/automation/available
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/myiot/valueInfo/available
        axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot/${type}/available`)
            .then(response => {
                callback( response.data.data );
            });
    },

    /* 26. MyIOT 편집 화면에서 '기기/시나리오/정보'의 신규등록: 홈화면에 노출 추가 */
    setExposeItemOfDashboard( ids, callback ){
        const map = ids.map( id => {
            return { myIotId:id }
        });

        axios.post(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot`, { data: map })
            .then( response=>{
               callback();
            });
    },


    //{{API_HOST}}/iot/complexes/125/homes/1/automation/conditions
    //{{API_HOST}}/iot/complexes/125/homes/1/automation/actors

    /* 시나리오 생성시 사용할 추가할수 있는 기기, 센서*/
    getAddibleItemOfScenario( type, callback ){
        axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/automation/${type}`)
            .then(response => {
                callback(response.data);
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
        const {maxVlu, minVlu} = data;
        const value = bool ? maxVlu : minVlu;

        this.setIotDeviceValue( data, value, callback );
    },

    setIotDeviceValue( data, value, callback) {

        const {deviceId, protcKey} = data;
        let valueName = value.toString();

        let params = `action?protcKey=${encodeURIComponent(protcKey)}&value=${value}`;

        // 기기명 옆에 표시될 바꾸기로 한 값 표시용
        switch( data.moAttr ){
            case 'option':
                valueName = data.option.filter( opt=>{ return opt.VAL == value } )[0].NM;
                break;

            case 'inputtext':
                // 관련장비 기기명 변경은 api주소가 다름
                params = `desc?value=${encodeURIComponent(value)}`;
                break;
        }

        Store.myModal = { status:0, name:data.thingsNm, value: valueName };
        console.log( data, `의 값을 ${value}로 변경`, Store.modeModal );
        axios.put(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/devices/${deviceId}/${params}`)
            .then(response => {
                callback( true );
                Store.myModal = { status:1, name:data.thingsNm, value: valueName };
                Store.hideMyModal();
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

    /* 시나리오에 소속된 device 정보 반환 */
    getDeviceOfScan( scnaId, deviceId, callback ){
        //  - {{API_HOST}}/iot/complexes/125/homes/1/automation/123/actors/11
        axios.get(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/automation/${scnaId}/actors/${deviceId}`)
            .then(response => {
                callback(response.data.data);
            });
    },


    /* Iot 23: 모드 업데이트 */
    updateMode( modeId, data, callback ){
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/automation/128
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/modes/CM01103
        axios.put(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes/${modeId}`, data )
            .then(response => {
                callback(response.data);
            });
    },


    /* 대시보드 myIot 스위치 버튼이나 시나리오 클릭시 */
    setMyIot( type, id, name, value, callback ){

        if( type === 'switch' ) value = value?'ON':'OFF';

        Store.myModal = { status:0, name:name, value: value };

        axios.put(`${Store.api}/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot/buttons/${id}/action`)
            .then(response => {
                callback( true );
                Store.myModal = { status:1, name:name, value: value };
                Store.hideMyModal();

                // 화면 반영을 위해 딜레이 콜
                setTimeout( ()=>{
                    this.getMy();
                }, 1000 );

            })
            .catch( error=>{
               callback( false );
            });
    },

};