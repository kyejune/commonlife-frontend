import { observable } from 'mobx';
import { Drawer } from 'react-md';

let host = 'https://clback.cyville.net';
if( window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1' )
    host = 'http://localhost:8080';

// 디바이스 테스트용 주소
if( window.location.protocol === 'file:' )// && window.location.pathname.includes('CoreSimulator') )
    host = 'http://192.168.0.100:8080';

// function getUserIP(onNewIP) { //  onNewIp - your listener function for new IPs
//     //compatibility for firefox and chrome
//     var myPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
//     var pc = new myPeerConnection({
//             iceServers: []
//         }),
//         noop = function() {},
//         localIPs = {},
//         ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g,
//         key;
//
//     function iterateIP(ip) {
//         if (!localIPs[ip]) onNewIP(ip);
//         localIPs[ip] = true;
//     }
//
//     //create a bogus data channel
//     pc.createDataChannel("");
//
//     // create offer and set local description
//     pc.createOffer(function(sdp) {
//         sdp.sdp.split('\n').forEach(function(line) {
//             if (line.indexOf('candidate') < 0) return;
//             line.match(ipRegex).forEach(iterateIP);
//         });
//
//         pc.setLocalDescription(sdp, noop, noop);
//     }, noop);
//
//     //listen for candidate events
//     pc.onicecandidate = function(ice) {
//         if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return;
//         ice.candidate.candidate.match(ipRegex).forEach(iterateIP);
//     };
// }
//
// getUserIP( ip => {
//     console.log( 'ip:', ip );
// });

const Store = observable({

    api:host,

    /* Community별 CardItem 데이터 */
    feed: [],
    event: [],
    news: [],

	/* likey */
	likey: [],

    /* 현재 열려있는 Drawer */
    drawer: [],
    drawerOptions: null,

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