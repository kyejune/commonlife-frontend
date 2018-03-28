import Store from 'scripts/store.js';
import axios from 'axios';
import SimpleJsonFilter from 'simple-json-filter/index';


let sjf = new SimpleJsonFilter();


// axios.defaults.baseURL = 'http://localhost:8080'// host;
axios.defaults.headers.common['api_key'] = 'acfc218023f1d7d16ae9a38c31ddd89998f32a9ee15e7424e2c6016a8dbcda70';
axios.defaults.headers.common['Content-Type'] = 'application/json; charset=UTF-8';

// Add a request interceptor
axios.interceptors.request.use(function (config) {
    // Do something before request is sent
    document.querySelector('#spinner').classList.add('cl-status--ajax');

    return config;
}, function (error) {
    // Do something with request error
    return Promise.reject(error);
});

// Add a response interceptor
axios.interceptors.response.use(function (response) {
    // Do something with response data
    document.querySelector('#spinner').classList.remove('cl-status--ajax');
    return response;
}, function (error) {

    document.querySelector('#spinner').classList.remove('cl-status--ajax');

    // 에러 상황일 경우 iot모달이 있으면 처리
    if( error.response.status !== 200) {

        if( Store.modeModal !== null ){
            let obj = Store.modeModal;
            obj.status = 2;
            obj.error = error.response.data.msg;
            Store.modeModal = Object.assign( {}, obj );
            Store.hideModeModal();
        }

        if( Store.myModal !== null ){
            let obj = Store.myModal;
            obj.status = 2;
            obj.error = error.response.data.msg;
            Store.myModal = Object.assign( {}, obj );
            Store.hideMyModal();
        }
    }

    // Do something with response error
    return Promise.reject(error);
});


// let communityData = null;



export default {

    getFeed( type, page ){
        axios.get( Store.api + '/posts/', { postType:type, page:page||1 } )
            .then( response =>{
                Store[type] = Store[type].concat( response.data.data );
                console.log( 'get data ', type, page, response.data.data );
            });
    },

    /* Community 글보기 */
    getCardContent( type, postIdx, callback ){

        let data = null;
        let a = Store[type];
        a.some( item=>{
            let bool = (item.postIdx === postIdx);
            if( bool ) data = item;
            return bool;
        });

        if( data ) callback( data );
        else{
            axios.get( `${Store.api}/posts/${postIdx}` )
                .then( response => {
                   callback( response.data );
                });
        }

    },

    /* Likey */
    setLikey( id, bool, callback ){

        if( bool ) {
            axios.post(Store.api + '/posts/' + id + '/likes/', {usrIdx: id})
                .then(response => {
                    callback(response.data);
                });

        }else{
            axios.delete( Store.api + '/posts/' + id + '/likes/', { usrIdx:id })
                .then( response => {
                    callback( response.data );
                });
        }
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