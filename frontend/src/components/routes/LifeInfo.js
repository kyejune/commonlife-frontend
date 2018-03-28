/* LifeInfo.jsx */
import React, { Component } from 'react';
import Store from 'scripts/store';
import Iot, { Modes, MyIots } from 'scripts/iot';
import { Link } from 'react-router-dom';
import IotModeEditor from "components/drawers/iot/IotModeEditor";
import WithTitle from 'components/ui/WithTitle';
import IotBtnMode from 'components/ui/IotBtnMode';
import IotBtnLg from 'components/ui/IotBtnLg';

import IotIcAddMode from 'images/combined-shape@3x.png';

import IotIcAdd from 'images/combined-shape-plus@3x.png';
import {observer} from "mobx-react";
import DrawerWrapper from "../drawers/DrawerWrapper";
import IotDevice from "../drawers/iot/IotDevice";
import IotModeSetting from "../drawers/iot/IotModeSetting";
import IotAddingSensorList from "../drawers/iot/IotAddingSensorList";
import IotDeviceCategory from "../drawers/iot/IotDeviceCategory";
import IotDeviceList from "../drawers/iot/IotDeviceList";
import IotSensorEditor from "../drawers/iot/IotSensorEditor";
import IotDeviceEditor from "../drawers/iot/IotDeviceEditor";
import MyEditor from "../drawers/MyEditor";
import ExposableEditor from "../drawers/iot/ExposableEditor";

class LifeInfo extends Component {

    componentDidUpdate ( prevProps ) {
        if( this.props.location !== prevProps.location )
            this.updateRoute();
    }

    updateRoute ( data ) {

        const params = this.props.match.params;
        const compMap = {

            // Mode 추가 버튼 클릭
            mode:{
                mode:'iot-mode',
                sensor:'iot-sensor',
                device:'iot-device',
                time:'iot-time',
                duration:'iot-duration',
            },

        };


        const action = this.props.match.params.action;
        const map = compMap[action];

        Store.clearDrawer();
        if( action === undefined ) return;

        let prevSeg = null;
        for( var p in params ){
            let d = map[params[p]];
            if( RegExp(/\d+/).test(params[p])  ) d = map[prevSeg] + '-detail';
            if( d ) Store.pushDrawer( d );
            prevSeg = params[p];
        }
    }

    render () {

        return <div className="cl-info-container">
            <div className="cl-fitted-box cl-bg--black">

                <div className="cl-second-header cl-profile-card">
                    <div className="cl-flex">
                        <div className="cl-avatar"/>
                        <div>
                            <h4>김정신</h4>
                            <p>역삼하우스 101동 202호</p>
                        </div>
                    </div>

                    <Link class="cl-edit color-primary" to="#">ProfileEdit</Link>

                    <footer className="cl-profile-card__footer cl-flex pt-1em mt-1em">
                        <p>입주일: 2017년 4월 1일</p>
                        <p className="ml-auto">포인트: 25</p>
                    </footer>
                </div>


                <ul className="cl-info-dashboard__list">

                    <li>
                        <Link to="/community/event">
                            <img className="cl__thumb--rounded" src={"icons/cl_life-10.svg"}/>
                            <p>Event Feed</p>
                        </Link>
                    </li>

                    <li>
                        <Link to="/info/support">
                            <img className="cl__thumb--rounded" src={"icons/cl_life-11.svg"}/>
                            <p>Living Support</p>
                        </Link>
                    </li>

                    <li>
                        <Link to="/info/guide">
                            <img className="cl__thumb--rounded" src={"icons/cl_life-12.svg"}/>
                            <p>Living Guide</p>
                        </Link>
                    </li>

                    <li>
                        <Link to="/info/benefits">
                            <img className="cl__thumb--rounded" src={"icons/cl_life-9.svg"}/>
                            <p>Benefits</p>
                        </Link>
                    </li>

                    <li>
                        <Link to="/info/status">
                            <img className="cl__thumb--rounded" src={"icons/cl_life-7.svg"}/>
                            <p>My Status</p>
                        </Link>
                    </li>

                    <li>
                        <Link to="/info/profile">
                            <img className="cl__thumb--rounded" src={"icons/cl_life-8.svg"}/>
                            <p>Profile</p>
                        </Link>
                    </li>

                </ul>

            </div>
        </div>
    }
}

export default observer( LifeInfo );