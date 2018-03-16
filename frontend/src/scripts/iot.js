// import Store from 'scripts/store.js';
import axios from 'axios';
import SimpleJsonFilter from 'simple-json-filter/index';


let sjf = new SimpleJsonFilter();


// axios.defaults.baseURL = 'http://localhost:8080'// host;
axios.defaults.headers.common['api_key'] = 'acfc218023f1d7d16ae9a38c31ddd89998f32a9ee15e7424e2c6016a8dbcda70';
axios.defaults.headers.common['Content-Type'] = 'application/json; charset=UTF-8';


/*
* name: 출력할 이름
* cmd: iot.js 명령호출할때 쓸 이름
* type: 0 = 토글, 1 = 특정값 지정
* */

export const Devices = {
    living1:{ cmd:'', name:'거실 중앙등', type:0 },
    main1:{ cmd:'', name:'안방 조명1', type:0 },
    main2:{ cmd:'', name:'안방 조명2', type:0 },
    boiler1:{ cmd:'', name:'중앙 보일러', type:1 },
	// 계속해서 추가해야함다...
};

export const ModeChanges = {
	outMode:{ cmd:'', name:'외출모드', type:0 },
};


const sortBySortOrder = ( a, b )=>{
    return a.sortOrder - b.sortOrder;
}



export default {

//     axios.all([
//     axios.get('http://google.com'),
//     axios.get('http://apple.com')
// ])
//     .then(axios.spread((googleRes, appleRes) => {
//         // do something with both responses
//     });

	/* Iot */
	getIot( callback ){

		axios.all([
			axios.get('./dummy/iot-mode.json' ),
			axios.get('./dummy/iot-my.json')
		]).then( axios.spread( ( modeRes, iotRes ) => {

			// 편의상 순서 맞추는건 이쪽에서 해서 보내줌
			modeRes.data.data.sort( sortBySortOrder );
            iotRes.data.data.sort( sortBySortOrder );

			callback( { mode:modeRes.data.data, iot:iotRes.data.data } );
        }));



		// axios.get( './dummy/iot.json' ) // 서버 예제 적용 버전
		// 	.then( response => {
		// 		callback( response.data );
		// 	} );
	},


	/* Iot장비의 값을 변경 */
	setIotDevice( deviceName, value, callback ){

		console.log(`${deviceName}의 값을 ${value}로 변경`);
		setTimeout( ()=> callback(), 4000 );

	},

	/* Iot모드의 값을 변경 */
	setIotMode( modeName, value ){
		console.log(`${modeName}의 값을 ${value}로 변경`);
	},

	changeIotMode( modeName, value, callback ){
		console.log(`${modeName}의 값을 ${value}로 변경`);
		setTimeout( ()=> callback(), 3000 );
	}


};