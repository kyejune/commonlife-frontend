import React, { Component } from 'react';
import Net from "../scripts/net";
import DeviceStorage from "react-device-storage";
import {withRouter} from "react-router-dom";
import Store from "../scripts/store"
import Push from "../scripts/push";

class LandingPage extends Component {



    componentDidMount(){

        const S = this.storage = new DeviceStorage().localStorage();
        const ID = S.read('savedId') || Store.auth.id;

        if(ID){

            Net.checkAuth( logined=>{
                if( logined ){

                    if( window.cordova )
                        this.props.history.push( '/community/feed' );
                    else
                        this.props.history.push( S.read('location') || '/community/feed');


                    setTimeout( Push.init, 1000 );
                }
                else{
                    this.props.history.push('/login');
                }
            });

        }else{

            this.props.history.push('/login');

        }
    }

    render() {
        return (
            <div className="cl-join cl-full-page cl-bg--white"/>
        );
    }
}


export default withRouter(LandingPage);
