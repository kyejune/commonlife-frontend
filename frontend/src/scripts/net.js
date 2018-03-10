import Store from 'scripts/store.js';
import axios from 'axios';
import SimpleJsonFilter from 'simple-json-filter/index';


let sjf = new SimpleJsonFilter();


// axios.defaults.baseURL = 'http://localhost:8080'// host;
axios.defaults.headers.common['api_key'] = 'acfc218023f1d7d16ae9a38c31ddd89998f32a9ee15e7424e2c6016a8dbcda70';
axios.defaults.headers.common['Content-Type'] = 'application/json; charset=UTF-8';

let communityData = null;


export default {

    /* Community, Event, News */
    getFeedAll( callback ){


        axios.get( Store.api + '/posts/' )
            .then( response => {

                communityData = response.data.data;

                Store.feed = sjf.filter({ 'postType':'feed' }).data( response.data.data ).wantArray().exec();
                Store.event = sjf.filter({ 'postType':'event' }).data( response.data.data ).wantArray().exec();
                Store.news = sjf.filter({ 'postType':'news' }).data( response.data.data ).wantArray().exec();

                console.log('Store update:', response );
                if( callback ) callback();

            });
    },

    /* Community 글보기 */
    getCardContent( type, idx, callback ){

        if( communityData ) callback( sjf.filter({ postType:'feed', postIdx:idx }).data( communityData ).wantArray().exec()[0]);
        else this.getFeedAll( ()=> this.getCardContent( type, idx, callback ));

    },

    /* Likey */
    // getLikey( id, callback ) {
    //     axios.get( Store.api + './dummy/likey.json' )
    //         .then( response => {
    //         	callback( response.data );
    //         } );
    // },

    setLikey( id, callback ){
        axios.post( Store.api + '/posts/' + id + '/likes/', { usrIdx:1 })
            .then( response => {
               callback( response.data );
            });
    },

    getLikesOfPost( postId, callback ){
        axios.get( Store.api + '/posts/'+ postId +'/likes/' )
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
        axios.post( Store.api + '/postFiles/', { file:  base64 } )
            .then( response => {

                console.log( 'uploadeImg:', response );
                callback( response.data );
            });
    },

    makePost( data, callback ){
        axios.post( Store.api + '/posts/', data)
            .then( response => {

                this.getFeedAll();
                callback( response );

            });
    }

};