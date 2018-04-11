import React, { Component } from 'react';
import Net from "../scripts/net";
import DeviceStorage from "react-device-storage";
import {withRouter} from "react-router-dom";
import Store from "../scripts/store"

class LandingPage extends Component {



    componentDidMount(){

        const S = this.storage = new DeviceStorage().localStorage();
        const ID = S.read('savedId') || Store.auth.id;

        if(ID){

            Net.checkAuth( ID, logined=>{
                if( logined ) this.props.history.push('/community/feed');
                else          this.props.history.push('/login');
            });

        }else{

            this.props.history.push('/login');

        }
    }

    render() {
        return (
            <div className="cl-join cl-full-page cl-bg--white">

                <p className="color-white mt-8em text-center">
                    사용자 인증 중입니다.
                </p>
            </div>
        );
    }
}


export default withRouter(LandingPage);
