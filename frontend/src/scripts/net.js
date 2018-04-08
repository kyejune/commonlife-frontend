import Store from 'scripts/store.js';
import axios from 'axios';

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
    if( error.response === undefined || error.response.status !== 200) {

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

    alert( (error.response?error.response.data.msg:error) || '잠시 후 다시 이용해 주시기 바랍니다.');

    // Do something with response error
    return Promise.reject(error);
});



export default {
///posts/?postType=feed&page=2
    getFeed( type, page, callback ){
        axios.get( `${Store.api}/posts/?postType=${type}&page=${(page+1)||1}`)
            .then( response =>{
                Store[type] = Store[type].concat(( response.data.data )); //  추후 페이지별로 삽입 시켜주기
                console.log( 'get data ', type, page, response.data.data );

                if( callback ) callback( response.data );

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
            axios.post(Store.api + '/posts/' + id + '/likes/')
                .then(response => {
                    callback(response.data);
                });

        }else{
            axios.delete( Store.api + '/posts/' + id + '/likes/')
                .then( response => {
                    callback( response.data );
                });
        }
    },


    /* event 참여 */
    setJoin( id, bool, callback ){

        if( bool ){
            axios.post( `${Store.api}/posts/${id}/rsv/` )
                .then( response => {
                   callback( response.data );
                });
        }else{
            axios.delete( `${Store.api}/posts/${id}/rsv/` )
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

    getComplexes( callback ) {
        axios.get( Store.api + '/complexes/' )
            .then( response => {
                callback( response.data );
            } );
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
        axios.post( Store.api + '/posts/', data )
            .then( response => {
                callback( true, response );
            })
            .catch( error => {
                callback( false, error );
            });
    },



    /* Auth */

    // 등록 지점 가져오기
    getBranch( callback ){
        // {{API_HOST}}/users/registration/complexes/
        axios.get( Store.api + '/users/registration/complexes' )
            .then( response => {
                callback( response.data.data );
            });
    },

    // 동 목록 가져오기
    getDongsInBranch( branchId, callback ){
        axios.get( `${Store.api}/users/registration/complexes/${branchId}` )
            .then( response => {
               callback( response.data.data );
            });
    },

    // 호 목록 가져오기
    getNumbersInBranch( branchId, dongId, callback ){
        axios.get( `${Store.api}/users/registration/complexes/${branchId}/${dongId}` )
            .then( response => {
                callback( response.data.data );
            });
    },

    // 세대주 휴대폰 인증번호 요청
    requestHouseHolderPhoneAuthNumber( branchId, dongId, hoId, name, phone, callback ){
        axios.get(`${Store.api}/users/registration/certHeadCellNo?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${encodeURIComponent(name)}&headCell=${phone}`)
            .then( response => {
                callback( response.data );
            });
    },

    // 사용자 휴대폰 인증번호 요청
    requestUserPhoneAuthNumber( branchId, dongId, hoId, hhname, hhphone, name, phone, certNo, callback ){
        axios.get(`${Store.api}/users/registration/certHeadCellNo?
                    cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${encodeURIComponent(hhname)}&headCell=${hhphone}
                    &userCertId=${certNo}&userNm=${name}&userCell=${phone}`)
            .then( response => {
                callback( response.data );
            });
    },

    // 세대주 휴대폰 인증번호 확인
    confirmHouseHolderPhoneAuthNumber( branchId, dongId, hoId, name, phone, certNo, hhCertNo, callback ){
        // {{API_HOST}}/users/registration/certHeadCellNo?cmplxId=132&dong=101&ho=101&headNm=김영헌&headCell=01050447244&userCertId=37&headCertNum=652038
        axios.post(`${Store.api}/users/registration/certHeadCellNo?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${encodeURIComponent(name)}
                                                                   &headCell=${phone}&userCetId=${certNo}&headCertNum=${hhCertNo}`)
            .then( response => {
                callback( response.data );
            });
    },

    confirmUserPhoneAuthNumber( branchId, dongId, hoId, name, phone, certNo, hhCertNo, callback ){
        // {{API_HOST}}/users/registration/certHeadCellNo?cmplxId=132&dong=101&ho=101&headNm=김영헌&headCell=01050447244&userCertId=37&headCertNum=652038
        axios.post(`${Store.api}/users/registration/certHeadCellNo?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${encodeURIComponent(name)}
                                                                   &headCell=${phone}&userCetId=${certNo}&headCertNum=${hhCertNo}`)
            .then( response => {
                callback( response.data );
            });
    }

};
