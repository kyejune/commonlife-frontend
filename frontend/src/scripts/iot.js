// import Store from 'scripts/store.js';
import axios from 'axios';
import {observable} from 'mobx';
import Store from "./store";

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
    getIotAll( callback ) {

        axios.all([
            axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes`),
            axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot`)
        ]).then(axios.spread((modeRes, iotRes) => {

            // 편의상 순서 맞추는건 이쪽에서 해서 보내줌
            modeRes.data.data.sort(sortBySortOrder);
            iotRes.data.data.sort(sortBySortOrder);

            Modes.replace(modeRes.data.data);
            MyIots.replace(iotRes.data.data);

            if( callback ) callback();
        }));
    },

    getMode() {
        axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes`)
            .then(response => {
                response.data.data.sort(sortBySortOrder);
                Modes.replace(response.data.data);
            });
    },

    getMy( callback ) {
        axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot`)
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
            axios.get(`/iot/complexes/${Store.cmplxId}/homes/${ Store.homeId + (additional?'/myiot':'')}/rooms/${ additional?'available':''}`),
            axios.get(`/iot/complexes/${Store.cmplxId}/homes/${ Store.homeId + (additional?'/myiot':'')}/deviceCategory/${ additional?'available':''}`)
        ]).then(axios.spread((place, device) => {
            callback(place.data.data, device.data.data);
        }));
    },

    /* 공간별( true, roomId), 기기별( false, cateCd )에 카테고리에 따른 기기 목록
    * @params additional 추가 가능한 목록만 가져올것인지 다 가져올것인지...
    * */
    getDevicesByCategory( additional, isRoom, cateId, callback) {
        axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/${ (additional?'myiot/':'') + (isRoom ? 'rooms' : 'deviceCategory')}/${cateId}/devices${ additional?'/available':'' }`)
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
    //     axios.post( `/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot`, { data: data } )
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
        //Store.modeModal

        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/modes/turnOff : 끌때 사용
        let path = '';
        if( value ) path = `/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes/${modeId}/switchTo`;
        else        path = `/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes/turnOff`;

        axios.put( path )
            .then(response => {
                callback();
            });
    },

    /* 모드 정렬 변경 */
    reAlignIotMode(map, callback) {
        axios.post(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/modes/order`, map)
            .then(response => {
                callback();
            });
    },


    /* My Iot 정렬 변경 */
    reAlignMyIot(map, callback) {
        axios.put(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot`, {data: map } )
            .then(response => {
                callback();
                this.getMy();
            });
    },

    /* My Iot 삭제 */
    removeMyIot( ids, callback ){
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/myiot/buttons/12

        const deletes = ids.map( id => {
           return axios.delete(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot/buttons/${id}`)
        });

        axios.all(deletes)
            .then( () => {
               this.getMy( callback );
            });
    },


    /* 시나리오, 가치정보의 노출 가능한 놈들 가져오기 : 홈 화면에 빼는 용도 */
    getExposableListOfDashboard( type, callback ){
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/myiot/automation/available
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/myiot/valueInfo/available
        axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot/${type}/available`)
            .then(response => {
                callback( response.data.data );
            });
    },

    /* 26. MyIOT 편집 화면에서 '기기/시나리오/정보'의 신규등록: 홈화면에 노출 추가 */
    setExposeItemOfDashboard( ids, callback ){
        const map = ids.map( id => {
            return { myIotId:id }
        });

        axios.post(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot`, { data: map })
            .then( response=>{
               callback();
            });
    },


    //{{API_HOST}}/iot/complexes/125/homes/1/automation/conditions
    //{{API_HOST}}/iot/complexes/125/homes/1/automation/actors
    /* 시나리오 생성시 사용할 추가할수 있는 기기, 센서*/
    getAddibleItemOfScenario( type, callback ){
        axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/automation/${type}`)
            .then(response => {
                callback(response.data);
            });
    },

    // 시나리오 편집시 생성 가능한 목록만 가져오기 기기, 센서
    //{{API_HOST}}/iot/complexes/127/homes/1/automation/1/conditions/available
    //{{API_HOST}}/iot/complexes/127/homes/1/automation/1/actors/available
    getAddibleItemOfEdigingScenario( type, scenerioId, callback ){
        axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/automation/${scenerioId}/${type}/available`)
            .then(response => {
                callback(response.data);
            });
    },

    /* 모드 상세 가져오기 */
    //{{API_HOST}}/iot/complexes/125/homes/1/automation/140
    getScenarioDetail( modeString, id, callback) {
        axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/${modeString}/${id}`)
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

        let {deviceId, protcKey, maxLinkYn, minLinkYn} = data;
        let valueName = value.toString();
        let method = axios.put;

        // 로봇청소기는 protcKey값이 달라진다.
        if( maxLinkYn === 'Y' && minLinkYn === 'Y' )
            protcKey = data.option.filter( opt=>{ return opt.VAL == value } )[0].LINK_PROTC_KEY;

        let params = `action?protcKey=${encodeURIComponent(protcKey)}&value=${value}`;

        // 기기명 옆에 표시될 바꾸기로 한 값 표시용
        switch( data.moAttr ){
            case 'option':
                valueName = data.option.filter( opt=>{ return opt.VAL == value } )[0].NM;
                break;

            case 'inputtext':
                // 관련장비 기기명 변경은 api주소가 다름
                method = axios.post;
                params = `desc?value=${encodeURIComponent(value)}`;
                break;
        }

        Store.myModal = { status:0, name:data.thingsNm, value: valueName };
        console.log( data, `의 값을 ${value}로 변경`, Store.modeModal, params );

        method(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/devices/${deviceId}/${params}`)
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
        axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/devices/${deviceId}`)
            .then(response => {
                callback(response.data.data);
            });
    },

    /* 시나리오에 소속된 device 정보 반환 */
    getDeviceOfScan( scnaId, deviceId, callback ){
        //  - {{API_HOST}}/iot/complexes/125/homes/1/automation/123/actors/11
        axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/automation/${scnaId}/actors/${deviceId}`)
            .then(response => {
                callback(response.data.data);
            });
    },


    /* Iot 23: 모드 업데이트 */
    /* Iot 30: 오토메이션 업데이트 */
    updateAutomation( mode, modeId, data, callback ){
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/automation/128
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/modes/CM01103
        axios.put(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/${mode}/${modeId}`, data )
            .then(response => {
                callback(response.data);
            });
    },

    // 29: 시나리오/오토메이션 생성
    createAutomation( data, callback ){
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/automation
        axios.post(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/automation`, data )
            .then(response => {
                callback(response.data);
            });
    },

    // 43: 오토메이션 삭제
    removeAutomation( id, callback ){
        // {{API_HOST}}/iot/complexes/125/homes/1/automation/190
        axios.delete(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/automation/${id}` )
            .then(response => {
                callback(response.data);
            });
    },

    // 44. 전체 시나리오 리스트 조회
    getScenarioes( callback ){
        //{{API_HOST}}/iot/complexes/{{cmplxId}}/homes/{{homeId}}/automation
        axios.get(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/automation` )
            .then(response => {
                callback(response.data);
            });
    },




    /* 대시보드 myIot 스위치 버튼이나 시나리오 클릭시 */
    setMyIot( type, id, name, value, callback ){

        if( type === 'switch' ) value = value?'ON':'OFF';

        Store.myModal = { status:0, name:name, value: value };

        axios.put(`/iot/complexes/${Store.cmplxId}/homes/${Store.homeId}/myiot/buttons/${id}/action`)
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