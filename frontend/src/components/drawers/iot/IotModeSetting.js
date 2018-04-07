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
import Store, {Scenario} from 'scripts/store';
import {observer} from 'mobx-react';



/*
*
*  센서는 thingsAttrIfCond 값으로,
*  기기는 stsValue로 읽기/쓰기
*
* */
class IotModeSetting extends Component {

    constructor(props) {
        super(props);

        const {action, option1} = props.match.params;

        this.props.updateTitle(action === 'mode' ? 'Mode 설정 변경' : (option1==='add'?'시나리오 생성':'시나리오 편집'));

        this.state = {
            action: action,
            isCreateMode: option1 === 'add',

            // mode 상태
            editTarget: option1,
            modeData: null,
            sendingData:null,

            // add 시나리오 상태
            // name:'',


            checkedMap:{},// 체크박스 선택된건 기억용
            updatedScnaThings:{} // 디바이스는 변경된 부분만 저장
        }

        if( action === 'scenario' ) // 필요한 경우 백버튼 path를 강제 지정
            this.props.setBackPath('/iot/scenario');

        console.log( `action:${ action }, editTarget:${ option1 }, isCreateMode: ${ option1 === 'add' }`);

    }

    componentDidUpdate(prevProps) {
        if( this.state.isCreateMode && (this.props.location !== prevProps.location))
            this.settingInitData(Scenario);
    }

    componentDidMount(){

        // 생성 및 편집 분기처리
        if( this.state.isCreateMode ){
            if( this.nameInput )
                this.nameInput.focus();

            // Scenario or Store의 값을 가져와서 modeData와 유사하게 object만들어주기
            this.settingInitData(Scenario);

        }else{ // Mode 편집 상태일경우 데이터 불러오기
            this.getSavedData();
        }

    }

    // 모드 값 로드
    getSavedData=()=>{
        Iot.getScenarioDetail( this.state.action === 'mode'?'modes':'automation', this.state.editTarget, data=>{
            console.log( '모드 상세정보:', data );
            this.settingInitData( data );
        });
    }

    // 들어온값 초기 세팅
    settingInitData=( data )=>{
        let checkedMap = [];
        data.scnaIfThings.forEach( item => {
            checkedMap[`${item.deviceId}-${item.stsId}-${item.thingsId}`] = (item.thingsAttrIfCond === item.maxVlu);
        });

        data.scnaThings.forEach( item => {
            checkedMap[`${item.deviceId}-${item.stsId}-${item.thingsId}`] = (item.stsValue === item.maxVlu);
        });

        this.setState( { checkedMap:checkedMap, modeData:data, sendingData:Object.assign({}, data ) });
    }



    /* Create Scenario */
    // 시나리오 생성 상태에서 이름 변경
    onChangeName = (event) => {
        Scenario.scna[0].name = event.target.value;
    }


    /*  Mode Callback */

    /*사용된 센서 ----------------------------------------------------------*/
    // 센서 토글
    updateSensorCheck( bool, index ){
        const item = this.state.modeData.scnaIfThings[index]
        console.log( item );
        this.updateCheck( bool, `${item.deviceId}-${item.stsId}-${item.thingsId}` );

        let d = this.state.sendingData.scnaIfThings[index];
            d.thingsAttrIfCond = bool?d.maxVlu:d.minVlu;
        this.state.sendingData.scnaIfThings[index] = d;
    }

    // 조건문 콜백
    updateCondition( {basic, condition}, index ){
        let d = this.state.sendingData.scnaIfThings[index];
            d.thingsAttrIfCond = basic;
            d.condi = condition;
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
        const item = this.state.modeData.scnaThings[index];
        this.updateCheck( bool, `${item.deviceId}-${item.stsId}-${item.thingsId}` ); // 토글 변경 적용 용

        let d = this.state.sendingData.scnaThings[index];
        let updateObj = Object.assign({}, this.state.updatedScnaThings );

        updateObj[d.deviceId] =
            { thingsId:d.thingsId, deviceId: d.deviceId, stsId:d.stsId, stsValue: bool?d.maxVlu:d.minVlu, chk:"Y" };

        this.setState({ updatedScnaThings: updateObj });
    }

    // 디바이스 내부 속성들 변경되면 [stsId:값들... 형태로 넘어옴
    updateDeviceInfo( changedMap, index ){
        console.log( '기기 내부 속성 변경됨:', changedMap );
        this.setState({ updatedScnaThings:{ ...changedMap, ...this.state.updatedScnaThings }}); // 변경된 데이터와 취합
    }

    // 변경값 적용
    onApply=()=>{

        // Automation 생성
        if( this.state.isCreateMode ) {

            let data = Object.assign({}, Scenario );

            const NAME = Scenario.scna[0].name;
            if( NAME === undefined || NAME.trim().length === 0 ){
                alert('이름을 지어주세요.');
                this.nameInput.focus();
                return;
            }

            // scnaThings에는 추가된 목록이..
            // updatedScnaThings에는 추가후 변경된 놈들만 들어있음...
            // console.log( '이전데이터:', data.scnaThings );
            // deviceType=='button'은 기본으로 넣어주자..
            const Buttons = data.scnaThings.filter( item=>{
                // console.log( item.deviceId, '수정된이력:', this.state.updatedScnaThings[item.deviceId] );
                return ((item.deviceType === 'button') && this.state.updatedScnaThings[item.deviceId] === undefined );
            });

            data.scnaThings = Object.values(this.state.updatedScnaThings).concat( Buttons );

            if( data.scnaIfAply.length === 0 ) delete data.scnaIfAply;
            if( data.scnaIfSpc.length === 0 ) delete data.scnaIfSpc;

            console.log('취합된 데이터', data );

            Iot.createAutomation( data, res =>{
                console.log( '시나리오 생성:', res );
                alert(res.msg);
            });

        }else{
        // Mode 및 Automation 업데이트

            const prevData = this.state.sendingData;
            const scna = prevData.scna[0];
            const data = { ...prevData, scna:[{msg:scna.msg, scnaNm:scna.scnaNm}], scnaThings:Object.values(this.state.updatedScnaThings) };


            Iot.updateAutomation( this.state.action === 'mode'?'modes':'automation', scna.mode, data, res => {
                alert(res.msg);
            });


        }
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

        const { action, isCreateMode, modeData } = this.state;

        if( !modeData ) return <div/>


        const ACTION_SCENARIO = (action === 'scenario');

        const {pathname} = this.props.location;
        let scna;
        let Sensors = [];
        let Devices = [];

        let sensorMore, deviceMore;
        if( ACTION_SCENARIO ){ // Automation 생성 및 편집에서만 편집 가능하게 노출
            sensorMore = <Link className="ml-auto" to={`${pathname}/edit-sensor`}>센서편집</Link>;
            deviceMore = <Link className="ml-auto" to={`${pathname}/edit-device`}>기기편집</Link>;
        }

        let icon;

        scna = modeData.scna[0];
        icon = scna.icon;

        // 일반 센서추가
        Sensors = modeData.scnaIfThings.map( (item, index)=>{

            if( item.deviceType && item.deviceType === 'button')
                return <LiOfToggle key={index} icon={item.imgSrc} name={item.stsNm}
                                   checked={ this.state.checkedMap[`${item.deviceId}-${item.stsId}-${item.thingsId}`] }
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
                                   checked={ this.state.checkedMap[`${item.deviceId}-${item.stsId}-${item.thingsId}`] }
                                   onSwitch={ bool => this.updateDeviceCheck( bool, index ) }
                />
            else
                return <LiOfCtrl key={index} icon={item.imgSrc}
                                 name={item.deviceNm}
                                 onClick={ ()=> Store.pushDrawer('edit-scan-device', { ...item, isScenario: ACTION_SCENARIO, callback: val => this.updateDeviceInfo( val, index ) } )}/>
        });


        return (
            <div className="cl-bg--darkgray">

                <header className="cl-mode-setting__header">
                    <div className={classNames("cl-iot-mode__button", {"cl-iot-mode__button--expand": ACTION_SCENARIO } )}>
                        <IconLoader src={icon}/>
                        { ACTION_SCENARIO ?
                            <input ref={ r => this.nameInput = r } className="cl-name pr-04em" type="text" placeholder="시나리오"
                                   value={Scenario.scnaNm}
                                   onChange={this.onChangeName}/>
                            :
                            <span className="cl-name">{scna.scnaNm}</span>
                        }
                    </div>

                    { ACTION_SCENARIO ?
                        <div>
                            <h5>사용자 Automation</h5>
                            <span className="desc">{ moment().format('YYYY년 MM월 DD일') }</span>
                        </div> :

                        <span className="desc">{ scna.msg }</span>
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
                    <button onClick={ this.getSavedData }>취소</button>
                    <button className="ml-auto cl-flex mr-1em" onClick={ this.onApply }>
                        <img src={checkSrc} alt="확인" width="28" height="28"/>
                        <span className="color-primary ml-03em">확인</span>
                    </button>
                </footer>
            </div>
        )
    }
}


export default withRouter(observer(IotModeSetting));