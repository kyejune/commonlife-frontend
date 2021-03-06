import {observable} from 'mobx';
import { Drawer } from 'react-md';
import config from '../config/config';


const host = config.api_host;
console.log( 'api host: ' + host );

// 생성용 시나리오/자동화 객체
/*
scna:{
    "scna": [{
        "msg": "시나리오 생성 테스트-nobody",
        "scnaNm": "시나리오 생성 테스트-nobody"
    }],

    "scnaIfSpc": [{
    "spcTime": "19:00",
    "monYn": "Y",
    "tueYn": "N",
    "wedYn": "Y",
    "thuYn": "N",
    "friYn": "Y",
    "satYn": "N",
    "sunYn": "Y"
  }]
}
*/
let _testRegex=(value, regex)=>{
    return value.toString().match(regex) !== null;
}

export const VALIDATOR_MSG = {
    accepted       : {message: ':attribute(은/는) 반드시 체크되어야합니다.',    rule: (val) => val === true },
    alpha_num_dash : {message: ':attribute에는 문자, 숫자, - 만이 허용됩니다.', rule: (val) => _testRegex(val,/^[A-Z0-9_-]*$/i) },
    max            : {message: ':attribute값은 :max 보다 크지 않아야 합니다.',  rule: (val, options) => val.length <= options[0], messageReplace: (message, options) => message.replace(':max', options[0]) },
    min            : {message: ':attribute값은 :min 보다 커야합니다..',       rule: (val, options) => val.length >= options[0], messageReplace: (message, options) => message.replace(':min', options[0]) },
    phone          : {message: ':attribute(은/는) 전화번호 형식이 아닙니다.',    rule: (val) => _testRegex(val,/(\+?\d{0,4})?\s?-?\s?(\(?\d{3}\)?)\s?-?\s?(\(?\d{3}\)?)\s?-?\s?(\(?\d{4}\)?)/)},
    required       : {message: ':attribute(은/는) 필수 입력사항입니다.',        rule: (val) => _testRegex(val,/.+/) },
    same           : {message: '패스워드와 값이 동일하지 않습니다.',              rule: (val, options)=>{ return val === options[0]; }},
    mail           : {message: ':attribute(은/는) 이메일 형식에 맞지 않습니다.',  rule: val =>{ return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test( val ); }},
    requires       : {message: ':attribute(은/는) 모두 필수 입력사항입니다.',    rule: vals => { return (vals[0] != 'undefined' && vals[1] != 'undefined');}},
};




export const Scenario = observable({

    scna:[{
        msg:'자동화(Automation)',
        scnaNm: undefined,
    }],

    scnaIfSpc:[],
    scnaIfAply:[],
    scnaIfThings:[],
    scnaThings:[],
});

export const MakingUserData = observable({
    branch: {
        // addr:
        //     "경기 과천시 코오롱로 11 (별양동, 코오롱)",
        // addr1:
        //     "경기",
        // addr2:
        //     "과천시",
        // addrDtl:
        //     "10층 (별양동 코오롱타워 본관)",
        // clLogoImgSrc:
        //     null,
        // clMapSrc:
        //     null,
        // cmplxGrp:
        //     "COMMON Life",
        // cmplxId:
        //     127,
        // cmplxNm:
        //     "따복하우스1차 베니트 개발",
    },
    houseHolder:{
        name:'',//'김영헌',
        phone:'',//'01050447244',
        certId:'',
        dong: '',// '0101',
        ho:'',//2803',
    },

    user: {
        name:'',
        phone:'',
        certId:'',
        mail:'',
        id:'',
        password:'',
        passwordConfirm:'',
    }
});



const Store = observable({

    firebaseToken: null,

    alert: msg => {
        if( navigator.notification ){
            navigator.notification.alert( msg, ()=>{}, 'CommonLife', '확인' );
        }else {
            alert(msg);
        }
    },

    isAuthorized: false,

    api:host,
    gcm:'dvCS7UlJeXA:APA91bEBwTT8oS8uHwFS1yzZrzUPt2p3IhsYlHW_N1onsJCqNoSX4jkNwR_KsH1kmJzmLIXjivF7l8O99JfvCjt8siZkNpIFHQFHQfFlLAi0CrF7TUmAwVKOEYmswggq6yTo4EFmxgeb',
    deviceId:'0b5a32e31439a5ce3d0b6511900a03b7',


    cmplxId:'127',
    homeId:'1',
    communityCmplxId:'127',

    // 모든 지점 목록
    complexes: [],
    complexMap:{},

    auth:{
      name: '아무개',
    },


    /* Community별 CardItem 데이터 */
    feed: [],
    event: [],
    news: [],

	/* likey */
	likey: [],

    /* IoT */
    iot: [],

    /* 현재 열려있는 Drawer */
    drawer: [],
    drawerListener:[],

    addDrawerListener: listener => {
      let idx = Store.drawerListener.indexOf( listener );
      if( idx < 0 ) Store.drawerListener.push( listener );
    },

    removeDrawerListener: listener => {
        let idx = Store.drawerListener.indexOf( listener );
        if( idx >= 0 ) Store.drawerListener.splice( idx, 1 );
    },

    hasDrawer: key => {
        let has = Store.drawer.some( drawerInfo => {
            return drawerInfo.key === key;
        });

        return has;
    },

    getDrawerData: key => {
        let data = null;
        Store.drawer.some( drawerInfo => {
            if( drawerInfo.key === key ) data = drawerInfo.data;
            return drawerInfo.key === key;
        });

        return data;
    },

    getDrawerIndex: key => {
        let idx = -1;
        Store.drawer.some( ( drawerInfo, index ) => {
            let match = (drawerInfo.key === key);
            if( match ) idx = index;
            return match;
        });

        return idx;
    },

    pushDrawer: (key, data)=> {
        // console.log( 'pushDrawer:', key, data );
        Store.drawer.push( { key:key, data:data } );
    },

    popDrawer: () =>{
        let d = Store.drawer.pop();

        Store.drawerListener.forEach( listener =>{
           listener( { key: d.key, action:'POP' } );
        });
    },

    clearDrawer: () =>{
        Store.drawer.length = 0;
    },


    /* 모달 생성용 */

    // 기기제어용 모달
    modeModal: null, // { name:'모달이름', data:'prpos에 넘길 데이터'}
    hideModeModal:()=>{
        setTimeout( function(){ Store.modeModal = null }, 2000 );
    },

    // My Iot변경 제어용 모달
    myModal: null, // { name:'모달이름', data:'prpos에 넘길 데이터'}
    hideMyModal:()=>{
        setTimeout( function(){ Store.myModal = null }, 2000 );
    },


    /* Drawer에 기본적으로 넘겨주는 파라미터 */
    customDrawerProps: {
        type:Drawer.DrawerTypes.TEMPORARY,
        onVisibilityChange: ()=>{},
        defaultMedia: 'mobile',
        portal: true,
        position: 'right',
    },


    now: new Date(),

});


window.Store = Store;
window.Scenerio = Scenario;


export default Store;
