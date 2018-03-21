import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Link} from "react-router-dom";
import IconLoader from "../../ui/IconLoader";
import IotBtnMode from "../../ui/IotBtnMode";
import LiOfCtrl from "./LiOfCtrl";
import LiOfToggle from "./LiOfToggle";
import Iot from "../../../scripts/iot";
import checkSrc from 'images/ic-check@3x.png';
import classNames from 'classnames';
import moment from 'moment';
import Store from 'scripts/store';

class IotModeSetting extends Component {

    constructor(props) {
        super(props);

        const {action, option1} = props.match.params;
        this.props.updateTitle(action === 'mode' ? 'Mode 설정 변경' : '시나리오 생성');

        this.state = {
            isMode: action === 'mode',

            // mode 상태
            modeName: option1,
            modeData: null,

            // add 시나리오 상태
            name:'',

        }

    }


    // Mode 편집 상태일경우 데이터 불러오기
    componentDidMount(){
        if( !this.state.isMode ) return;

        Iot.getModeDetail( this.state.modeName, data=>{
            console.log( '모드 상세정보:', data );
            this.setState( { modeData:  data });
        });
    }

    onChangeName = (event) => {
        console.log(event.target.value);
        this.setState({name: event.target.value});
    }

    render() {

        const { isMode, modeData, name } = this.state;

        if( isMode && modeData === null ) return <div/>

        const {pathname} = this.props.location;
        let scna;
        let Sensors = [];
        let Devices = [];

        let sensorMore, deviceMore;

        // 모드 편집 모드
        if ( isMode ) {
            scna = modeData.scna[0];

            sensorMore = <span className="ml-auto">설정불가</span>;
            deviceMore = <span className="ml-auto">설정가능</span>;


            // 일반 센서추가
            Sensors = modeData.scnaIfThings.map( (item, index)=>{
                if( item.deviceType && item.deviceType === 'button')
                    return <LiOfToggle key={index} icon={item.imgSrc} name={item.stsNm} to={`${pathname}/sensor/0`}/>
                else
                    return <LiOfCtrl key={index} icon={item.imgSrc} name={item.stsNm} to={`${pathname}/sensor/0`}/>
            });

            // 임시로 시간데이터 더미 추가
            modeData.scnaIfSpc = [{
                "spcTime": "12:00",
                "monYn": "Y",
                "tueYn": "Y",
                "wedYn": "Y",
                "thuYn": "Y",
                "friYn": "Y",
                "satYn": "N",
                "sunYn": "N"
            }];

            // 특정 시간 있으면 추가
            Sensors = Sensors.concat( modeData.scnaIfSpc.map( item => {
                return <LiOfCtrl key="time" icon={undefined} name="특정 시간(더미)" desc={ item.spcTime }/>
            }));

            // 시간 구간 조건 추가
            Sensors = Sensors.concat( modeData.scnaIfAply.map( item =>{
                return <LiOfCtrl key="time-range" icon={undefined} name="구간 시간" desc={ `${item.aplyStartTime} ~ ${item.aplyEndTime}` }/>
            }));


            //// 디바이스 추가
            Devices = modeData.scnaThings.map( (item, index)=>{
                if( item.deviceType && item.deviceType === 'button')
                    return <LiOfToggle key={index} src={undefined} name={item.deviceNm} />
                else
                    return <LiOfCtrl key={index} src={undefined} name={item.deviceNm} />
            })


        // 시나리오 생성모드
        } else {
            sensorMore = <Link className="ml-auto" to={`${pathname}/edit-sensor`}>센서편집</Link>;
            deviceMore = <Link className="ml-auto" to={`${pathname}/edit-device`}>기기편집</Link>;

            // Sensors = <div></div>
        }

        return (
            <div className="cl-bg--darkgray">

                <header className="cl-mode-setting__header">
                    <div className={classNames("cl-iot-mode__button", {"cl-iot-mode__button--expand": !isMode } )}>
                        <IconLoader src={undefined}/>
                        { isMode ?
                            <span className="cl-name">{scna.scnaNm}</span> :
                            <input className="cl-name pr-04em" type="text" placeholder="시나리오" value={name}
                                       onChange={this.onChangeName}/>
                        }
                    </div>

                    { this.state.isMode ?
                        <span className="desc">{ scna.msg }</span> :

                        <div>
                            <h5>사용자 Automation</h5>
                            <span className="desc">{ moment().format('YYYY년 MM월 DD일 | ') + Store.auth.name }</span>
                        </div>
                    }
                </header>

                <div className="pb-5em cl-bg--darkgray">
                    <div className="pt-05em bb--gray">
                        <div className="cl-flex fs-14 ml-1em mr-1em mt-1em mb-1em">
                            <h5 className="fs-14 cl-bold">사용된 센서 <span className="color-primary">{ Sensors.length }</span></h5>
                            {sensorMore}
                        </div>

                        <ul className="cl-iot-vertical-list">
                            {Sensors}
                            {/*<LiOfCtrl icon={undefined} name="뭐뭐센서" desc="센서 설명" to={`${pathname}/sensor/0`}/>*/}
                            {/*<LiOfCtrl icon={undefined} name="뭐뭐센서" desc="센서 설명" to={`${pathname}/sensor/0`}/>*/}
                        </ul>
                    </div>

                    <div>
                        <div className="cl-flex fs-14 ml-1em mr-1em mt-1em mb-1em">
                            <h5 className="fs-14 cl-bold">사용된 기기 <span className="color-primary">{ Devices.length }</span></h5>
                            {deviceMore}
                        </div>

                        <ul className="cl-iot-vertical-list">
                            {Devices}

                            {/*<LiOfToggle src={undefined} name="무슨무슨기기" desc="기기설명"/>*/}
                            {/*<LiOfCtrl icon={undefined} name="뭐뭐기기" desc="기기설명"*/}
                                      {/*to={`${pathname}/device/0`}*/}
                            {/*/>*/}
                        </ul>
                    </div>
                </div>


                <footer className={ classNames("cl-flex", "cl-opts__footer", { "cl-opts__footer--hide": ( Sensors.length + Devices.length === 0 ) } ) }>
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