import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Link} from "react-router-dom";
import IconLoader from "../../ui/IconLoader";
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
            sendingData:null,

            // add 시나리오 상태
            name:'',
            checkedMap:{},// 체크박스 선택된건 기억용
            updatedScnaThings:{}
        }

    }


    // Mode 편집 상태일경우 데이터 불러오기
    componentDidMount(){
        if( !this.state.isMode ) return;

        Iot.getModeDetail( this.state.modeName, data=>{
            console.log( '모드 상세정보:', data );


            let checkedMap = [];
            data.scnaIfThings.forEach( item => {
                checkedMap[item.deviceId] = (item.currSts === item.maxVlu);
            });

            this.setState( { checkedMap:checkedMap, modeData:data, sendingData:Object.assign({}, data ) });
        });
    }

    /* Create Scenario */
    // 시나리오 생성 상태에서 이름 변경
    onChangeName = (event) => {
        console.log(event.target.value);
        this.setState({name: event.target.value});
    }


    /*  Mode Callback */

    /*사용된 센서 ----------------------------------------------------------*/
    // 센서 토글
    updateSensorCheck( bool, index ){
        this.updateCheck( bool, this.state.modeData.scnaIfThings[index].deviceId );

        let d = this.state.sendingData.scnaIfThings[index];
            d.currSts = bool?d.maxVlu:d.minVlu;
        this.state.sendingData.scnaIfThings[index] = d;
    }

    // 조건문 콜백
    updateCondition( value, index ){
        let d = this.state.sendingData.scnaIfThings[index];
            d.currSts = value;
        this.state.sendingData.scnaIfThings[index] = d;
    }

    // 시간선택 콜백
    updateTime=( hhmm, date )=>{
        const d = { ...this.state.sendingData.scnaIfSpc[0], spcTime:hhmm, ...date };
        this.state.sendingData.scnaIfSpc[0] = d;
    }

    // 구간시간 선택 콜백
    updateDuration=( start, end, date )=>{
        const d = { ...this.state.sendingData.scnaIfAply[0], aplyStartTime:start, aplyEndTime:end, ...date };
        this.state.sendingData.scnaIfAply[0] = d;
    }


    /* 사용된 기기 ----------------------------------------- */
    // 디바이스 토글
    updateDeviceCheck( bool, index ){
        this.updateCheck( bool, this.state.modeData.scnaThings[index].deviceId ); // 토글 변경 적용 용

        let d = this.state.sendingData.scnaThings[index];
        let updateObj = Object.assign({}, this.state.updatedScnaThings );
        updateObj[`${d.deviceId}-${d.stsId}-${d.thingsId}`] = { thingsId:d.thingsId, deviceId: d.deviceId, stsId:d.stsId, stsValue: bool?d.maxVlu:d.minVlu };

        this.setState({ updatedScnaThings: updateObj });
    }

    // 디바이스 내부 속성들 변경되면 [stsId:값들... 형태로 넘어옴
    updateDeviceInfo( changedMap, index ){
        console.log( changedMap );
        this.setState({ updatedScnaThings:{ ...changedMap, ...this.state.updatedScnaThings }}); // 변경된 데이터와 취합
    }

    // 변경값 적용
    onApply=()=>{
        const data = { ...this.state.sendingData, scnaThings:Object.values(this.state.updatedScnaThings) };

        console.log( '적용:', data );
        Iot.updateAutomationOrScenario( this.state.sendingData.scna[0].scnaId, data, res=>{
           console.log( '적용결과:', res );
        });
        // console.log( 'scnaThigns:', Object.values(this.state.updatedScnaThings) );
    }





    // 디바이스 토글되면 맵에 저장, 화면 유지용
    updateCheck( bool, id ){
        let nMap = Object.assign({}, this.state.checkedMap );
        nMap[id] = bool;
        this.setState({
            checkedMap: nMap,
        });
    }


    render() {

        const { isMode, modeData, name } = this.state;

        if( isMode && modeData === null ) return <div/>

        const {pathname} = this.props.location;
        let scna;
        let Sensors = [];
        let Devices = [];

        let sensorMore, deviceMore;

        let icon;

        // 모드 편집 모드
        if ( isMode ) {
            scna = modeData.scna[0];
            icon = scna.icon;

            sensorMore = <span className="ml-auto"/>;
            deviceMore = <span className="ml-auto"/>;


            // 일반 센서추가
            Sensors = modeData.scnaIfThings.map( (item, index)=>{
                if( item.deviceType && item.deviceType === 'button')
                    return <LiOfToggle key={index} icon={item.imgSrc} name={item.stsNm}
                                       checked={ this.state.checkedMap[item.deviceId] }
                                       onSwitch={ bool => this.updateSensorCheck( bool, index ) }
                                       />
                else
                    return <LiOfCtrl key={index} icon={item.imgSrc} name={item.stsNm}
                                     onClick={ ()=> Store.pushDrawer('edit-sensor-if',
                                         { ...item,
                                             options: modeData.scnaIfOption,
                                             callback: value => this.updateCondition( value, index )
                                         }) }
                    />
            });

            // 특정 시간 있으면 추가
            Sensors = Sensors.concat( modeData.scnaIfSpc.map( item => {
                return <LiOfCtrl key="time" icon={undefined}
                                 name="특정 시간"
                                 desc={ item.spcTime }
                                 onClick={ ()=> Store.pushDrawer('edit-sensor-time', { ...item, callback: this.updateTime } ) }
                                 />
            }));

            // 시간 구간 조건 추가
            Sensors = Sensors.concat( modeData.scnaIfAply.map( item =>{
                return <LiOfCtrl key="time-range" icon={undefined} name="구간 시간"
                                 desc={ `${item.aplyStartTime} ~ ${item.aplyEndTime}`}
                                 onClick={ ()=> Store.pushDrawer('edit-sensor-duration', { ...item, callback: this.updateDuration } ) }
                                 />
            }));


            // 디바이스 추가
            Devices = modeData.scnaThings.map( (item, index)=>{
                if( item.deviceType && item.deviceType === 'button')
                    return <LiOfToggle key={index} icon={item.imgSrc} name={item.deviceNm}
                                       checked={ this.state.checkedMap[item.deviceId] || (item.currSts === item.maxVlu) }
                                       onSwitch={ bool => this.updateDeviceCheck( bool, index ) }
                    />
                else
                    return <LiOfCtrl key={index} icon={item.imgSrc}
                                     name={item.deviceNm}
                                     onClick={ ()=> Store.pushDrawer('edit-scan-device', { ...item, callback: val => this.updateDeviceInfo( val, index ) } )}/>
            })


        // 시나리오 생성모드
        } else {
            sensorMore = <Link className="ml-auto" to={`${pathname}/edit-sensor`}>센서편집</Link>;
            deviceMore = <Link className="ml-auto" to={`${pathname}/edit-device`}>기기편집</Link>;
        }

        return (
            <div className="cl-bg--darkgray">

                <header className="cl-mode-setting__header">
                    <div className={classNames("cl-iot-mode__button", {"cl-iot-mode__button--expand": !isMode } )}>
                        <IconLoader src={icon}/>
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
                        </ul>
                    </div>

                    <div>
                        <div className="cl-flex fs-14 ml-1em mr-1em mt-1em mb-1em">
                            <h5 className="fs-14 cl-bold">사용된 기기 <span className="color-primary">{ Devices.length }</span></h5>
                            {deviceMore}
                        </div>

                        <ul className="cl-iot-vertical-list">
                            {Devices}
                        </ul>
                    </div>
                </div>


                <footer className={ classNames("cl-flex", "cl-opts__footer", { "cl-opts__footer--hide": ( Sensors.length + Devices.length === 0 ) } ) }>
                    <button>취소</button>
                    <button className="ml-auto cl-flex mr-1em" onClick={ this.onApply }>
                        <img src={checkSrc} alt="확인" width="28" height="28"/>
                        <span className="color-primary ml-03em">확인</span>
                    </button>
                </footer>
            </div>
        )
    }
}


export default withRouter(IotModeSetting);