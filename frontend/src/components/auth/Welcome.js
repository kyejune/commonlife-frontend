import React, { Component } from 'react';
import { MakingUserData } from "../../scripts/store";
import { observer } from "mobx-react";
import Net from "../../scripts/net";
import DefautSrc from 'images/profile-thumb-img-big-default@3x.png';

class Welcome extends Component {

    constructor(props) {
        super(props);

        this.state = {
            thumb: DefautSrc,
        }
    }


    selectPicture=()=>{

        if( navigator.camera ) {

            navigator.camera.getPicture(
                (base64) => this.gettedPicture(base64),
                (msg) => this.failedPicture(msg),
                {
                    quality: 100,
                    sourceType: 0,
                    destinationType: 0,
                    encodingType: 0,
                });
        }

    }

    gettedPicture(base64) {

        const b64 = 'data:image/jpeg;base64,' + base64;

        this.setState({
            thumb: b64
        });

        this.uploadImg( b64 );
    }

    failedPicture(message) {
        console.log( 'failedPicture:', message );
    }

    uploadImg( b64img ){
        Net.uploadUserProfileImage( MakingUserData.user.id, MakingUserData.user.password, b64img, res=>{
           console.log( res );
        });
    }


    render() {

        let {branch, houseHolder, user } = MakingUserData;


        return <div className="cl-join-welcome cl-bg--black30 h-100">

            <div className="cl-input-container">
                <div className="color-white mb-1em">
                    <span className="fs-16 cl-bold">{branch.cmplxNm}</span>
                    <p className="color-white50">({branch.addr})</p>
                </div>
                <p className="fs-16">
                    <span className="color-white50 mr-05em">{`${houseHolder.dong}동 ${houseHolder.ho}호`}</span>
                    <span className="color-lightblue">{`${houseHolder.name}세대`}</span>
                </p>


                <div className="cl-thumb-container text-center">
                    <img src={ this.state.thumb } alt="" onClick={ this.selectPicture }/>
                    <p className="fs-20 color-white cl-bold mt-1em">{user.id || '사용자 아이디'}</p>
                    <p className="color-white50">{ `${user.name} ${user.mail}` }</p>
                </div>

            </div>
        </div>
    }
}


export default observer(Welcome);
