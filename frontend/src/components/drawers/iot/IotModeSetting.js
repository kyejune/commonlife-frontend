import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Link} from "react-router-dom";
import IconLoader from "../../ui/IconLoader";
import IotBtnMode from "../../ui/IotBtnMode";
import LiOfCtrl from "./LiOfCtrl";
import LiOfToggle from "./LiOfToggle";
import {ModeChanges} from "../../../scripts/iot";
import checkSrc from 'images/ic-check@3x.png';
import classNames from 'classnames';

class IotModeSetting extends Component {

    constructor(props) {
        super(props);

        const {action} = props.match.params;
        this.props.updateTitle(action === 'mode' ? 'Mode 설정 변경' : '시나리오 생성');

        // 시나리오 추가의 경우
        if (action === 'add') {
            this.state = {
                name: '',
            }
        }

        console.log('props:', this.props);
    }

    onChangeName = (event) => {
        console.log(event.target.value);
        this.setState({name: event.target.value});
    }

    render() {

        const {action, option1} = this.props.match.params;
        const {pathname} = this.props.location;

        let sensorMore, deviceMore;
        if (action === 'mode') {
            sensorMore = <span className="ml-auto">설정불가</span>;
            deviceMore = <span className="ml-auto">설정가능</span>;
        } else {
            sensorMore = <Link className="ml-auto" to={pathname}>센서편집</Link>;
            deviceMore = <Link className="ml-auto" to={pathname}>기기편집</Link>;
        }

        // let Header;
        // if( action === 'mode' ){
        //     Header = [
        //         <span className="desc">
        //             모드 설정시 실내온도를 26도로 유지하며, 모든 전등을 소등하여 전력소모를 최소화 합니다.
        //         </span>
        //     ]
        // }else{
        //     Header = <div className="cl-iot-mode__button">
        //         <IconLoader src={undefined} />
        //         <input type="text" placeholder="시나리오 쓰고 있네" value={this.state.name} onChange={this.onChangeName}/>
        //     </div>
        // }

        return (
            <div className="cl-bg--darkgray">

                <header className="cl-mode-setting__header">
                    <div className={classNames("cl-iot-mode__button", {"cl-iot-mode__button--expand": action==='add'} )}>
                        <IconLoader src={undefined}/>
                        {action === 'mode' ?
                            <span className="cl-name">무슨무슨모드</span> :
                            <input className="cl-name pr-04em" type="text" placeholder="시나리오" value={this.state.name}
                                       onChange={this.onChangeName}/>
                        }
                    </div>

                    {action === 'mode' ?
                        <span className="desc">모드 설정시 실내온도를 26도로 유지하며, 모든 전등을 소등하여 전력소모를 최소화 합니다.</span> :

                        <div>
                            <h5>사용자 Automation</h5>
                            <span className="desc">2017년 2월 28일 | 조성우 생성</span>
                        </div>
                    }
                </header>

                <div className="pb-5em cl-bg--darkgray">
                    <div className="pt-05em bb--gray">
                        <div className="cl-flex fs-14 ml-1em mr-1em mt-1em mb-1em">
                            <h5 className="fs-14 cl-bold">사용된 센서 <span className="color-primary">5</span></h5>
                            {sensorMore}
                        </div>

                        <ul className="cl-iot-vertical-list">
                            <LiOfCtrl icon={undefined} name="뭐뭐센서" desc="센서 설명" to={`${pathname}/sensor/0`}/>
                            <LiOfCtrl icon={undefined} name="뭐뭐센서" desc="센서 설명" to={`${pathname}/sensor/0`}/>
                        </ul>
                    </div>

                    <div>
                        <div className="cl-flex fs-14 ml-1em mr-1em mt-1em mb-1em">
                            <h5 className="fs-14 cl-bold">사용된 기기 <span className="color-primary">5</span></h5>
                            {deviceMore}
                        </div>

                        <ul className="cl-iot-vertical-list">

                            <LiOfToggle src={undefined} name="무슨무슨기기" desc="기기설명"/>
                            <LiOfCtrl icon={undefined} name="뭐뭐기기" desc="기기설명"
                                      to={`${pathname}/device/0`}
                            />
                        </ul>
                    </div>
                </div>


                <footer className="cl-flex cl-opts__footer">
                    <button>취소</button>
                    <button className="ml-auto cl-flex mr-1em">
                        <img src={checkSrc} alt="확인" width="28" height="28"/>
                        <span className="color-primary ml-03em">확인</span>
                    </button>
                </footer>
            </div>
        )
    }
}


export default withRouter(IotModeSetting);