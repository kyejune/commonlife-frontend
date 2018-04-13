import {observable} from 'mobx';
import { Drawer } from 'react-md';

let host = 'https://clback.cyville.net';
if( window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1' )
    host = 'http://localhost:8080';
    
// 아이폰 디바이스 테스트용 주소
if( window.location.protocol === 'file:' && window.location.pathname.includes('CoreSimulator') )
    host = 'http://192.168.0.100:8080';


// 덮어쓰기
// host = 'https://cl-stage.cyville.net'; // 클라 전달용
// host = 'http://localhost:8080';      // ykim 로컬 테스트용
// host = 'https://cl-stage.cyville.net'; // 클라 전달용
host = 'https://clback.cyville.net'; // 테스트용


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
        addr:
            "경기 과천시 코오롱로 11 (별양동, 코오롱)",
        addr1:
            "경기",
        addr2:
            "과천시",
        addrDtl:
            "10층 (별양동 코오롱타워 본관)",
        clLogoImgSrc:
            null,
        clMapSrc:
            null,
        cmplxGrp:
            "COMMON Life",
        cmplxId:
            127,
        cmplxNm:
            "따복하우스1차 베니트 개발",
    },
    houseHolder:{
        name:'김영헌',
        phone:'01050447244',
        certId:'',
        dong: '0101',
        ho:'2803',
    },

    user: {
        name:'',
        phone:'',
        certId:'',
        mail:'',
        id:'aaaa',
        password:'aaaaaa',
        passwordConfirm:'',
    }
});



const Store = observable({

    isAuthorized: false,

    api:host,
    gcm:'dvCS7UlJeXA:APA91bEBwTT8oS8uHwFS1yzZrzUPt2p3IhsYlHW_N1onsJCqNoSX4jkNwR_KsH1kmJzmLIXjivF7l8O99JfvCjt8siZkNpIFHQFHQfFlLAi0CrF7TUmAwVKOEYmswggq6yTo4EFmxgeb',
    deviceId:'0b5a32e31439a5ce3d0b6511900a03b7',


    cmplxId:'127',
    homeId:'1',

    // 모든 지점 목록
    complexes: [],

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

    hasDrawer: key => {
        let has = Store.drawer.some( drawerInfo => {
            return drawerInfo.key === key;
        });

        return has;
    },

    getDrawerData: key => {
        let data = null;
        let has = Store.drawer.some( drawerInfo => {
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
        console.log( 'pushDrawer:', key, data );
        Store.drawer.push( { key:key, data:data } );
    },

    popDrawer: () =>{
        Store.drawer.pop();
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


export default Store;
