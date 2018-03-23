import React, {Component} from 'react';
import {Devices} from "../../scripts/iot";

import IotIcAlert from 'images/alert-icon-black@3x.png';
import IotIcSet from 'images/io-t-i-con-b-copy@3x.png';
import IotIcOn from 'images/io-t-i-con-b@3x.png';
import IotIcOff from 'images/io-t-i-con-b-off@3x.png';
import Store from "../../scripts/store";
import classNames from "classnames";
import IconLoader from "./IconLoader";

class IotBtnLg extends Component {

    constructor(props) {
        super(props);
    }

    viewProgress(deviceInfo) {
        Store.ipo = {targetValue: true, ...deviceInfo};
    }

    render() {

        const {
            btImgSrc, btLeft,
            btRightIcon, btRightIconType, btRightText, btSubTitle,
            btTitle, btTitleUnit, btType,
        } = this.props;

        const off = (btType === 'device' && btRightText === 'off');

        let BottomLeft, BottomRight;
        switch (btType) {
            case "information":
                BottomLeft = <div className="cl-bold">{btLeft}</div>;
                BottomRight = <div>{btRightText}</div>
                break;

            case "device":
                let Src;
                if( btRightIconType !== "button" )
                    Src = IotIcSet;
                else
                    Src = off?IotIcOff:IotIcOn;


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

            <button type="button" className={classNames("cl-my-iot__button",
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
            </button>

        )
    }
}

export default IotBtnLg;
