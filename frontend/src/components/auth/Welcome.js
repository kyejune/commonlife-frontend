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
                    destinationType: 0
                });
        }else{
            let sample = `data:image/jpeg;base64,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`;
            this.setState({ thumb: sample });
            this.uploadImg( sample );
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
