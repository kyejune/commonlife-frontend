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
host = 'https://clback.cyville.net'; // 테스트용


const Store = observable({

    api:host,
    cmplxId:'125',
    homeId:'1',


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

