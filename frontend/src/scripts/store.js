import {observable, isObservable} from 'mobx';
import { Drawer } from 'react-md';

let host = 'https://clback.cyville.net';
if( window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1' )
    host = 'http://localhost:8080';
    
// 디바이스 테스트용 주소
if( window.location.protocol === 'file:' && window.location.pathname.includes('CoreSimulator') )
    host = 'http://192.168.0.100:8080';


host = 'https://clback.cyville.net';


const Store = observable({

    api:host,

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
    drawerOptions: null,


    /* 모달 생성용 */
    ipo: null, // { name:'모달이름', data:'prpos에 넘길 데이터'}
    imc: null, // { name:'모달이름', data:'prpos에 넘길 데이터'}


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


export default Store;

