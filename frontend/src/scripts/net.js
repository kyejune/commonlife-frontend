import Store from 'scripts/store.js';
import axios from 'axios';
import SimpleJsonFilter from 'simple-json-filter/index';


let sjf = new SimpleJsonFilter();

let host = 'https://clback.cyville.net';
if( window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1' )
    host = 'http://localhost:8080';
if( window.location.protocol === 'file:' && window.location.pathname.includes('CoreSimulator') )
    host = 'http://localhost:8080';


// axios.defaults.baseURL = 'http://localhost:8080'// host;
axios.defaults.headers.common['api_key'] = 'acfc218023f1d7d16ae9a38c31ddd89998f32a9ee15e7424e2c6016a8dbcda70';
axios.defaults.headers.common['Content-Type'] = 'application/json; charset=UTF-8';

console.log( 'location:', window.location );

export default {

    /* Community, Event, News */
    getFeedAll(){
        axios.get( host + '/posts/' )
            .then( response => {

                Store.feed = sjf.filter({ 'boardType':'feed' }).data( response.data.data ).wantArray().exec();
                Store.event = sjf.filter({ 'boardType':'event' }).data( response.data.data ).wantArray().exec();
                Store.news = sjf.filter({ 'boardType':'news' }).data( response.data.data ).wantArray().exec();

            });
    },

    /* Community 글보기 */
    getCardContent( type, index, callback ){
        axios.get( './dummy/feed.json' )
            .then( response => {
                callback( response.data[0] );
                } );
    },

    /* Likey */
    getLikey( id, callback ) {
        axios.get( host + './dummy/likey.json' )
            .then( response => {
            	callback( response.data );
            } );
    },

    setLikey( id, callback ){
        axios.post( host + '/posts/' + id + '/likes/', { usrIdx:1 })
            .then( response => {
               callback( response.data );
            });
    },


    getReservation( id, callback ){
        axios.get( './dummy/reservation.json' )
            .then( response => {
                callback( response.data['0'] );
            } );
    },


    /* Post작성 */
    uploadImg( base64, callback ){
        axios.post( host + '/postFiles/', { file:  base64 } )
            .then( response => {

                console.log( 'uploadeImg:', response );
                callback( response.data );
            })
    },

    makePost( data, callback ){
        axios.post( host + '/posts/', data)
            .then( response => {

                console.log( 'makePost:', response );
                callback( response );

            });
    }


};