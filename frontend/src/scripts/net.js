import Store from 'scripts/store.js';
import axios from 'axios';
import DeviceStorage from "react-device-storage";




axios.defaults.baseURL = Store.api;
// axios.defaults.headers.common['api_key'] = 'acfc218023f1d7d16ae9a38c31ddd89998f32a9ee15e7424e2c6016a8dbcda70';
axios.defaults.headers.common['token'] = undefined;
axios.defaults.headers.common['Content-Type'] = 'application/json; charset=UTF-8';

// Add a request interceptor
axios.interceptors.request.use(function (config) {
    // Do something before request is sent
    document.querySelector('#spinner').classList.add('cl-status--ajax');

    // console.log( 'config:', config );
    config.headers.token = Store.auth.token;

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

    // iot내에 변경쪽에서는 시스템 알럿 사용 안함
    const USE_SYSTEM_ALERT = ( Store.modeModal === null && Store.myModal === null );

    console.log( error );

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

    if( USE_SYSTEM_ALERT ){
        const ERROR_MSG = (error.response ? error.response.data.msg : error) || '잠시 후 다시 이용해 주시기 바랍니다.';
        //navigator.notification.alert(message, alertCallback, [title], [buttonName])
        if( navigator.notification ){
            navigator.notification.alert( ERROR_MSG, ()=>{}, 'CommonLife', '확인' );
        }else {
            alert( ERROR_MSG );
        }
    }

    // Do something with response error
    return Promise.reject(error);
});



export default {
///posts/?postType=feed&page=2
    getFeed( type, page, callback ){

        // console.log( 'A of getFeed:', axios.defaults );
        //?cmplxId=127
        axios.get( `/posts/?postType=${type}&page=${(page+1)||1}&cmplxId=${Store.communityCmplxId}`)
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

                // 정렬
                Store[type] = Store[type].sort((a,b)=>{
                    return new Date(b.updDttm).getTime() - new Date(a.updDttm).getTime();
                });

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
            axios.get( `/posts/${postIdx}` )
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
            axios.post( `/posts/${id}/rsv/` )
                .then( response => {
                   callback( response.data );
                });
        }else{
            axios.delete( `/posts/${id}/rsv/` )
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

    // key value형태로 complex목록 가져오기, login하고 호출됨
    getComplexesKeyValue(){
        axios.get('/complexes/kvm/')
            .then( response => {
                Store.complexMap = response.data;
            });
    },

    getReservationGroups( id, callback ){
        axios.get( Store.api + '/reservation-groups/?cmplxIdx=' + id )
            .then( response => {
                callback( response.data );
            } );
    },

    getReservationGroup( id, callback ){
        axios.get( Store.api + '/reservation-groups/' + id )
            .then( response => {
                callback( response.data );
            } );
    },

    getReservationScheme( id, callback ){
        axios.get( Store.api + '/reservation-schemes/' + id )
            .then( response => {
                callback( response.data );
            } );
    },

    getReservationOnDate( id, date, callback ) {
		axios.get( Store.api + '/reservation-schemes/' + id + '/reservations?startDt=' + date )
			.then( response => {
				callback( response.data );
			} );
    },

    getHomeHead( callback ) {
        axios.get( Store.api + '/home-head/' )
            .then( response => {
                callback( response.data );
            } );
    },

    createReservation( data, callback ) {
        axios.post( Store.api + '/reservations/', data )
            .then( response => {
                callback( response.data );
            } );
    },

    getReservationHistory( callback ) {
        axios.get( Store.api + '/reservations/my' )
            .then( response => {
				callback( response.data );
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
    getPolicy( callback ){
      axios.get('/users/registration/agreement')
          .then( response=>{
             callback( response.data );
          });
    },

    // 등록 지점 가져오기
    getBranch( isAll, callback ){
        // {{API_HOST}}/users/myComplexGroup // 내가 속한 지점만
        // {{API_HOST}}/users/registration/complexes/ // 전체 목록
        const PATH = isAll?'/users/registration/complexes/':'/users/myComplexGroup';
        axios.get( PATH )
            .then( response => {
                callback( response.data.data );
            });
    },

    // 동 목록 가져오기
    getDongsInBranch( branchId, callback ){
        axios.get( `/users/registration/complexes/${branchId}` )
            .then( response => {
               callback( response.data.data );
            });
    },

    // 호 목록 가져오기
    getNumbersInBranch( branchId, dongId, callback ){
        axios.get( `/users/registration/complexes/${branchId}/${dongId}` )
            .then( response => {
                callback( response.data.data );
            });
    },

    // 세대주 휴대폰 인증번호 요청
    requestHouseHolderPhoneAuthNumber( branchId, dongId, hoId, name, phone, callback ){
        axios.get(`/users/registration/certHeadCellNo?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${encodeURIComponent(name)}&headCell=${phone}`)
            .then( response => {
                callback( response.data );
            });
    },

    // 사용자 휴대폰 인증번호 요청
    requestUserPhoneAuthNumber( branchId, dongId, hoId, hhname, hhphone, name, phone, certNo, callback ){
        // console.log( 'rpa:', branchId, dongId, hoId, hhname, hhphone, name, phone, certNo );
        axios.get(`/users/registration/certUserCellNo?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${encodeURIComponent(hhname)}&headCell=${hhphone}&userCertId=${certNo}&userNm=${name}&userCell=${phone}`)
            .then( response => {
                callback( response.data );
            });
    },

    // 세대주 휴대폰 인증번호 확인
    confirmHouseHolderPhoneAuthNumber( branchId, dongId, hoId, name, phone, certNo, hhCertNo, callback ){
        // {{API_HOST}}/users/registration/certHeadCellNo?cmplxId=132&dong=101&ho=101&headNm=김영헌&headCell=01050447244&userCertId=37&headCertNum=652038
        axios.post(`/users/registration/certHeadCellNo?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${encodeURIComponent(name)}&headCell=${phone}&userCertId=${certNo}&headCertNum=${hhCertNo}`)
            .then( response => {
                callback( response.data );
            });
    },

    // 사용자 휴대폰 인증번호 확인
    confirmUserPhoneAuthNumber( branchId, dongId, hoId, hhname, hhphone, certReq, certId, phone, callback ){
        // {{API_HOST}}/users/registration/certUserCellNo?cmplxId=132&dong=101&ho=101&headNm=김영헌&headCell=01050447244&userCertId=34&userCertNum=361016&userCell=01050447244
        axios.post(`/users/registration/certUserCellNo?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${hhname}&headCell=${hhphone}&userCertId=${certReq}&userCertNum=${certId}&userCell=${phone}`)
            .then( response => {
                callback( response.data );
            });
    },

    checkIdDuplicate( newId, callback ){
        //{{API_HOST}}/users/registration/existedUser/NEWUSER
        axios.get(`/users/registration/existedUser/${newId}`)
            .then( response => {
               callback( response.data );
            });
    },

    registNewUser( branchId, dongId, hoId, hhname, hhphone, name, phone, certId, certDate, id, password, callback ){
        // {{API_HOST}}/users/registration/newUser?cmplxId=132&dong=101&ho=101&headNm=김영헌&headCell=01050447244&userNm=사용자KIM2&userCell=01050447244&userCertId=34&smsChkYn=Y&smsChkDt=2018-02-10 14:42:22&userId=newuser201&userPw=fumT4DmfVP/X+RgvSg1CBNA6QAberSGDf0Iu49s0cMSlundj0QVHqTM+hS6BcVyY
        axios.post(`/users/registration/newUser?cmplxId=${branchId}&dong=${dongId}&ho=${hoId}&headNm=${hhname}&headCell=${hhphone}&userNm=${name}&userCell=${phone}&userCertId=${certId}&smsChkYn=Y&smsChkDt=${certDate}&userId=${id}&userPw=${password}`)
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
        axios.post( `/users/registration/newUser/photo`, { userId:id, userPw:pw, file:base64img })
            .then( response =>{
               callback( response.data );
            });
    },


    login( id, password, callback ){

        axios.get(`/users/login?userId=${id}&userPw=${password}&gcmRegId=${Store.gcm}&deviceId=${Store.deviceId}`)
            .then( response =>{

                console.log( '로그인 성공:', response );

                //{"msg":"로그인에 성공하였습니다.","userNm":"june","usrId":716,"expireDate":"2018-04-27 01:03:34","cmplxId":127,"issueDate":"2018-04-10 09:03:04","homeId":1,"userId":"aaaa","token":"ePptHwjAPOetJkJqX9SCu+/XamBKNUXs5eLRV9qxH5zKNXXtisnQWvSShRWChj95"}

                const DATA = response.data;
                Store.cmplxId = DATA.cmplxId;
                Store.communityCmplxId = DATA.cmplxId;
                Store.homeId = DATA.homeId;
                Store.auth = { name:DATA.userNm, id:DATA.userId, token:DATA.token, key:DATA.usrId };
                Store.isAuthorized = true;

                const S = new DeviceStorage().localStorage();
                S.save( 'token', DATA.token );

                this.getComplexesKeyValue();

                callback( response.data );
            })
    },

    findId( hhname, hhphone, name, phone, callback ){
        // {{API_HOST}}/users/registration/findUserId?headNm=김영헌&headCell=01050447244&userNm=김연아&userCell=01050447244
        axios.get(`/users/registration/findUserId?headNm=${hhname}&headCell=${hhphone}&userNm=${name}&userCell=${phone}`)
            .then( response => {
                callback( response.data );
            });
    },

    resetPassword( hhname, hhphone, id, phone, callback ){
        // {{API_HOST}}/users/registration/resetPwd?headNm=김영헌&headCell=01050447244&userId=yunakim&userCell=01050447244
        axios.get(`/users/registration/resetPwd?headNm=${hhname}&headCell=${hhphone}&userId=${id}&userCell=${phone}`)
            .then( response => {
                callback( response.data );
            });
    },


    // 토큰 검사
    checkAuth( callback ){
      //{{API_HOST}}/users/status?userId=baek
      // axios.get( `/users/status?userId=${id}`)
      //     .then( response => {
      //           const BOOL = (response.data.status === '0001');
      //           Store.isAuthorized = true;
      //           callback( BOOL );
      //     });
        let bool = false;

        const S = new DeviceStorage().localStorage();

        const TOKEN = S.read( 'token' ) || Store.auth.token;
        console.log('검사할 토큰:', TOKEN );

        bool = (TOKEN !== undefined);

        if( bool ){
            Store.auth = { ...Store.auth, token: TOKEN };
        }

        // 토큰 체크하는 Api가 아직 없어서 임시로 있으면 무조건 통과
        setTimeout( ()=>{

            Store.isAuthorized = bool;
            callback( bool );


            // 지점정보 가져오기
            this.getComplexesKeyValue();

        }, 0 );

    },


    tokenUpdate(){
    //{{API_HOST}}/users/pushToken?gcmRegId=dvCS7UlJeXA:APA91bEBwTT8oS8uHwFS1yzZrzUPt2p3IhsYlHW_N1onsJCqNoSX4jkNwR_KsH1kmJzmLIXjivF7l8O99JfvCjt8siZkNpIFHQFHQfFlLAi0CrF7TUmAwVKOEYmswggq6yTo4EFmxgeb&deviceId=0b5a32e31439a5ce3d0b6511900a03b7
        axios.get( `/users/pushToken?gcmRegId=${Store.auth.token}`)
            .then( response => {
                console.log( 'token update:', response );
            });

    },




    // My Info
    getInfoPage( callback ){
        axios.get('/info/')
            .then( response=>{
                callback( response.data );
            });
    },

    // 공지상세
    getNoticeDetail( callback ){
        axios.get('/info/notice' )
            .then( response=>{
                callback( response.data );
            });
    },

    // 인포페이지 클릭하면 나오는 페이지들...
    getInfoSubjectListOf( type, callback ){
        axios.get('/info/' + type )
            .then( response=>{
                callback( response.data );
            });
    },


    // 리빙가이드, 베네핏 목록
    getInfoDetailOf( type, idx, callback ){
        axios.get( `/info/${type}/${idx}` )
            .then( response=>{
                callback( response.data );
            });
    },


    /* Ticket 작성 및 Ticket용 파일올리기 */
    uploadTicketImg( base64, callback ){
        axios.post( '/info/support/ticketFiles', { file:  base64 } )
            .then( response => {
                callback( response.data );
            });
    },

    makeTicket( data, callback ) {
        axios.post('/info/support/ticket/', data )
            .then(response => {
                callback(true, response);
            });
    },


    // 프로필 페이지
    getProfileInfo( callback ){
        axios.get('/info/profile' )
            .then( response=>{
                callback( response.data );
            });
    },

    changeProfileImage( base64img, callback ){
        axios.post( '/imageStore/profile/b64', { file:base64img })
            .then( response =>{
                callback( response.data );
            });
    },

    changeEmail( address, callback ){
        axios.put( '/info/profile', { newEmail: address } )
            .then( response=>{
               callback( response.data );
            });
    },

    changePassword( oldPw, newPw, callback ){
        axios.put( '/info/profile', { oldUserPw:oldPw, newUserPw:newPw })
            .then( response=>{
               callback( response.data );
            });
    },



};
