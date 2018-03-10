// import Store from 'scripts/store.js';
import axios from 'axios';
import SimpleJsonFilter from 'simple-json-filter/index';


let sjf = new SimpleJsonFilter();


// axios.defaults.baseURL = 'http://localhost:8080'// host;
axios.defaults.headers.common['api_key'] = 'acfc218023f1d7d16ae9a38c31ddd89998f32a9ee15e7424e2c6016a8dbcda70';
axios.defaults.headers.common['Content-Type'] = 'application/json; charset=UTF-8';



export default {

	/* Iot */
	getIot( callback ){
		axios.get( './dummy/iot.json' )
			.then( response => {
				console.log( response );
				callback( response );
			} );
	}


};