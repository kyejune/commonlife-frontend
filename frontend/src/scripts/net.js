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
                let prevStack = Store[type];
                let newStack = response.data.data.filter( item => {
                    let sames = prevStack.filter( prevItem => {
                            return (prevItem.postIdx === item.postIdx);
                        }
                    );

                    return ( sames.length === 0 );
                });

                Store[type] = Store[type].concat( newStack );



                // = Store[type].concat(( response.data.data )); //  추후 페이지별로 삽입 시켜주기
                // console.log( 'get data ', type, page, response.data.data );

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
        // console.log( 'rpa:', branchId, dongId, hoId, hhname, hhphone, name, phone, certNo );
        axios.get(`${Store.api}/users/registration/certUserCellNo?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${encodeURIComponent(hhname)}&headCell=${hhphone}&userCertId=${certNo}&userNm=${name}&userCell=${phone}`)
            .then( response => {
                callback( response.data );
            });
    },

    // 세대주 휴대폰 인증번호 확인
    confirmHouseHolderPhoneAuthNumber( branchId, dongId, hoId, name, phone, certNo, hhCertNo, callback ){
        // {{API_HOST}}/users/registration/certHeadCellNo?cmplxId=132&dong=101&ho=101&headNm=김영헌&headCell=01050447244&userCertId=37&headCertNum=652038
        axios.post(`${Store.api}/users/registration/certHeadCellNo?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${encodeURIComponent(name)}&headCell=${phone}&userCertId=${certNo}&headCertNum=${hhCertNo}`)
            .then( response => {
                callback( response.data );
            });
    },

    // 사용자 휴대폰 인증번호 확인
    confirmUserPhoneAuthNumber( branchId, dongId, hoId, hhname, hhphone, certReq, certId, phone, callback ){
        // {{API_HOST}}/users/registration/certUserCellNo?cmplxId=132&dong=101&ho=101&headNm=김영헌&headCell=01050447244&userCertId=34&userCertNum=361016&userCell=01050447244
        axios.post(`${Store.api}/users/registration/certUserCellNo?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${hhname}&headCell=${hhphone}&userCertId=${certReq}&userCertNum=${certId}&userCell=${phone}`)
            .then( response => {
                callback( response.data );
            });
    },

    checkIdDuplicate( newId, callback ){
        //{{API_HOST}}/users/registration/existedUser/NEWUSER
        axios.get(`${Store.api}/users/registration/existedUser/${newId}`)
            .then( response => {
               callback( response.data );
            });
    },

    registNewUser( branchId, dongId, hoId, hhname, hhphone, name, phone, certId, certDate, id, password, callback ){
        // {{API_HOST}}/users/registration/newUser?cmplxId=132&dong=101&ho=101&headNm=김영헌&headCell=01050447244&userNm=사용자KIM2&userCell=01050447244&userCertId=34&smsChkYn=Y&smsChkDt=2018-02-10 14:42:22&userId=newuser201&userPw=fumT4DmfVP/X+RgvSg1CBNA6QAberSGDf0Iu49s0cMSlundj0QVHqTM+hS6BcVyY
        axios.post(`${Store.api}/users/registration/newUser?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${hhname}&headCell=${hhphone}&userNm=${name}&userCell=${phone}&userCertId=${certId}&smsChkYn=Y&smsChkDt=${certDate}&userId=${id}&userPw=${password}`)
            .then( response => {
                callback( response.data );
            });
    },


    //{
    // "userId": "yunakim",
    // "userPw": "fumT4DmfVP/X+RgvSg1CBNA6QAberSGDf0Iu49s0cMSlundj0QVHqTM+hS6BcVyY",
    // "file": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAA6lBMVEUAAAAXQl8XQmAXQV4YPloWRmUZNlAXP1wYPloXRGIWSWcYP1wVTm4YQV4YPVcZOlQXQV0ZOVQZOlUWR2YXRWMYPVkVTm4YQl8ZOFEYQV0aM0wUU3YYQV0XQl8XQ2AYQFwXPloXPVgXQF0SPVoBIkLp7vEmS2YWRGMMN1QHNFMNNVIEME8BKknz9vfu8PKpuMKfr7uInq10jZ5cdolVcogrUmwbRWEWO1fS2+DL1NrO0detu8aar72XpbKOobCDlqV2kqN4kaFqhJZjgZRRZHo6WnIWSmkvT2guTGciSGMTOlYNOFUGLksACS7w+BDIAAAAHHRSTlMA7Z7SZS4H5jsJ/O3l3tTOy769npaVfnV1aGgoe5kp8wAAAMZJREFUGNNFj9VyAlEQBSfZZSHuPjPXYBUIThR3+f/fYbkU0G+nq85Dw46Mc5uBI2dPxAE/evv9SlqglFK92Jm7JMQoSZI26mwOwM1KItls1MdFpcU1wEOAFM7Lv/+Fz6JWFwCnAoXxO/l1vRQZdZIKZjL4V2mMCuFCWoEYNr9q3+lFia1Alsvpz6zXH8RtKwhV7JeGrUp5ZSgVz+dSGKxVq10/ZroC8NwJIuZTopZSri25E4IYKaB779DKmvjmA468Oc47WDZI7RlDwZXI7gAAAABJRU5ErkJggg=="
    uploadUserProfileImage( id, pw, base64img, callback ){
        //{{API_HOST}}/users/registration/newUser/photo
        axios.post( `${Store.api}/users/registration/newUser/photo`, { userId:id, userPw:pw, file:base64img })
            .then( response =>{
               callback( response.data );
            });
    },


    login( id, password, callback ){

        // password =

        const GCM = 'dvCS7UlJeXA:APA91bEBwTT8oS8uHwFS1yzZrzUPt2p3IhsYlHW_N1onsJCqNoSX4jkNwR_KsH1kmJzmLIXjivF7l8O99JfvCjt8siZkNpIFHQFHQfFlLAi0CrF7TUmAwVKOEYmswggq6yTo4EFmxgeb';
        const DEVICE_ID = '0b5a32e31439a5ce3d0b6511900a03b7';

        axios.get(`${Store.api}/users/login?userId=${id}&userPw=${password}&gcmRegId=${GCM}&deviceId=${DEVICE_ID}`)
            .then( response =>{

                console.log( '로그인 성공:', response );

                //{"msg":"로그인에 성공하였습니다.","userNm":"june","usrId":716,"expireDate":"2018-04-27 01:03:34","cmplxId":127,"issueDate":"2018-04-10 09:03:04","homeId":1,"userId":"aaaa","token":"ePptHwjAPOetJkJqX9SCu+/XamBKNUXs5eLRV9qxH5zKNXXtisnQWvSShRWChj95"}

                const DATA = response.data;
                Store.cmplxId = DATA.cmplxId;
                Store.homeId = DATA.homeId;
                Store.auth = { name:DATA.userNm, id:DATA.userId, token:DATA.token, key:DATA.usrId };


                callback( response.data );
            })
    }

};
