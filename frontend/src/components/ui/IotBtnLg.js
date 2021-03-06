import React, {Component} from 'react';

// import IotIcAlert from 'images/alert-icon-black@3x.png';
import IotIcSet from 'images/io-t-i-con-b-copy@3x.png';
import IotIcOn from 'images/io-t-i-con-b@3x.png';
import IotIcOff from 'images/io-t-i-con-b-off@3x.png';
import Store from "../../scripts/store";
import classNames from "classnames";
import IconLoader from "./IconLoader";
import {Link, withRouter} from "react-router-dom";
import Iot from "../../scripts/iot";

class IotBtnLg extends Component {

    // device && button
    onClickSwitch( bool, btId, btTitle ){
        Iot.setMyIot( 'switch', btId, btTitle, bool, success=>{
           console.log( success );
        });
    }

    // automation
    onClickAutomaion=( btId, btTitle )=>{
        Iot.setMyIot( 'automation', btId, btTitle, 'automation', success=>{
            console.log( success );
        });
    }


    render() {

        const { btId,
            btImgSrc, btLeft, deviceId,
            btRightIconType, btRightText, btSubTitle,
            btOnOff,
            btTitle, btTitleUnit, btType, myIotId,
        } = this.props;

        const off = (btType === 'device' && btOnOff === 'off');

        let rtIcon;
        let link;
        let clickFunc;
        let BottomLeft, BottomRight;
        switch (btType) {
            case "information":
                BottomLeft = <div className="cl-bold">{btLeft}</div>;
                BottomRight = <div>{btRightText}</div>;
                // rtIcon = <i className="cl-my-iot__ic-alert"><img src={IotIcAlert} alt=""/></i>;

                // 날씨는 연결안되게 막아야됨
                // link = `${this.props.location.pathname}/info/${myIotId}`;// 지금은 연결할 페이지 없음
                break;

            case "device":
                let Src;
                if( btRightIconType !== "button" ) {
                    Src = IotIcSet;
                    link = `${this.props.location.pathname}/ctrl/device/${deviceId}`;
                }else {
                    Src = off ? IotIcOff : IotIcOn;
                    clickFunc = () => this.onClickSwitch( off, btId, btTitle );
                }


                BottomLeft = <div className="cl-bold">{btLeft}</div>;
                BottomRight = <div className="cl-flex">
                        <img src={Src} alt="특성 이미지" width="21" height="20"/>
                    <span className="uppercase cl-my-iot-ic__label">{btRightText}</span>
                </div>
                break;

            case "automation":
                BottomLeft = <div className="cl-bold">{ btLeft }</div>;
                BottomRight = <div className="cl-flex">
                    <img src={IotIcOn} alt="특성 이미지" width="21" height="20"/>
                    <span className="uppercase cl-my-iot-ic__label">{btRightText}</span>
                </div>;

                clickFunc = () => this.onClickAutomaion( btId, btTitle );
                break;
            default :
        }



        return (

            <button className={classNames("cl-my-iot__button",
                {"cl-my-iot__button--default": btType === "information"},
                {"cl-my-iot__button--set": btRightIconType === "detail"},
                {"cl-my-iot__button--automation": btType === "automation"},
                {"cl-my-iot__button--off": off }
            )}
                    onClick={ clickFunc }
            >
                {rtIcon}

                <div className="cl-my-iot__content">
                    <div>
                        <h3 className="mt-01em uppercase cl-wrap">
                            <span>{btTitle}</span>
                            {btTitleUnit}
                        </h3>
                        <p className="cl-my-iot__paragraph">{btSubTitle}</p>
                    </div>

                    <IconLoader className="cl-my-iot__icons" src={btImgSrc} width="45" height="45"/>

                </div>

                <div className="cl-my-iot__bottom cl-flex">
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
