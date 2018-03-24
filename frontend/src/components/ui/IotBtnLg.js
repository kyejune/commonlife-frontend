import React, {Component} from 'react';
import {Devices} from "../../scripts/iot";

import IotIcAlert from 'images/alert-icon-black@3x.png';
import IotIcSet from 'images/io-t-i-con-b-copy@3x.png';
import IotIcOn from 'images/io-t-i-con-b@3x.png';
import IotIcOff from 'images/io-t-i-con-b-off@3x.png';
import Store from "../../scripts/store";
import classNames from "classnames";
import IconLoader from "./IconLoader";
import {Link, withRouter} from "react-router-dom";

class IotBtnLg extends Component {

    constructor(props) {
        super(props);
    }

    viewProgress(deviceInfo) {
        Store.ipo = {targetValue: true, ...deviceInfo};
    }

    // device && button
    onClickSwitch=( bool, data )=>{
        console.log( 'switch:', bool, data );
    }

    // automation
    onClickAutomaion=( data )=>{
        console.log( 'direct:', data );
    }


    render() {

        const {
            btImgSrc, btLeft, deviceId,
            btRightIcon, btRightIconType, btRightText, btSubTitle,
            btTitle, btTitleUnit, btType,
        } = this.props;

        const off = (btType === 'device' && btRightText === 'off');

        let link;
        let clickFunc;
        let BottomLeft, BottomRight;
        switch (btType) {
            case "information":
                BottomLeft = <div className="cl-bold">{btLeft}</div>;
                BottomRight = <div>{btRightText}</div>;
                link = `${this.props.location.pathname}/info/0`;
                break;

            case "device":
                let Src;
                if( btRightIconType !== "button" ) {
                    Src = IotIcSet;
                    link = `${this.props.location.pathname}/ctrl/device/${deviceId}`;
                }else {
                    Src = off ? IotIcOff : IotIcOn;
                    clickFunc = this.onClickSwitch;
                }


                BottomLeft = <div className="cl-bold">{btLeft}</div>;
                BottomRight = <div className="cl-flex">
                        <img src={Src} alt="특성 이미지" width="21" height="20"/>
                    <span className="uppercase cl-my-iot-ic__label">{btRightText}</span>
                </div>
                break;

            case "automation":
                BottomLeft = <div className="cl-bold">{Store.auth.name}</div>;
                break;
        }



        return (

            <button className={classNames("cl-my-iot__button",
                {"cl-my-iot__button--default": btType === "information"},
                {"cl-my-iot__button--set": btRightIconType === "detail"},
                {"cl-my-iot__button--automation": btType === "automation"},
                {"cl-my-iot__button--off": off }
            )}
            >
                <i className="cl-my-iot__ic-alert"><img src={IotIcAlert} alt=""/></i>

                <div className="cl-my-iot__content">
                    <div>
                        <h3 className="cl-bold mt-01em uppercase w-85 cl-wrap">
                            <span className="cl-bold">{btTitle}</span>
                            {btTitleUnit}
                        </h3>
                        <p className="cl-my-iot__paragraph">{btSubTitle}</p>
                    </div>

                    <IconLoader className="cl-my-iot__icons" src={btImgSrc} width="45" height="45"/>

                </div>

                <div className="cl-my-iot__bottom cl-flex pt-04em">
                    {BottomLeft}
                    {BottomRight}
                </div>

                {link&&
                <Link to={link} className="cl--full--abs"/>
                }
            </button>

        )
    }
}

export default withRouter( IotBtnLg );
