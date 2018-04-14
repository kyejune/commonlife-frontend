import React, {Component} from 'react';
import {withRouter} from 'react-router';
import IconLoader from "../../ui/IconLoader";
import LiOfCtrl from "./LiOfCtrl";
import LiOfToggle from "./LiOfToggle";
import Iot from "../../../scripts/iot";
import checkSrc from 'images/ic-check@3x.png';
import classNames from 'classnames';
import moment from 'moment';
import Store, { Scenario } from 'scripts/store';
import { observer } from 'mobx-react';
import addSrc from 'images/combined-shape-plus@3x.png';

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

        this.props.updateTitle(action === 'mode' ? 'Mode 설정 변경' : (option1 === 'add' ? '자동화(Automation) 생성' : '자동화(Automation) 편집'));


        this.state = {
            action: action,
            isCreateMode: option1 === 'add',

            // mode 상태
            editTarget: option1,
            editingData: null,
            //  editingData:null,
            isEditingSensor: false,
            isEditingDevice: false,

            // add 시나리오 상태
            // name:'',


            checkedMap: {},// 체크박스 선택된건 기억용
            updatedScnaThings: {} // 디바이스는 변경된 부분만 저장
        };

        if (action === 'scenario') { // 필요한 경우 백버튼 path를 강제 지정
            this.props.setBackPath('/iot/scenario');

            // 생성모드 시작시엔 바로 편집모드로 바꿔두기
            if (option1 === 'add') {
                this.state.isEditingSensor = true;
                this.state.isEditingDevice = true;
            }
        }
        ;


        console.log(`action:${ action }, editTarget:${ option1 }, isCreateMode: ${ option1 === 'add' }`);

    }

    componentDidUpdate(prevProps) {
        if (this.state.isCreateMode && (this.props.location !== prevProps.location))
            this.settingInitData(Scenario);
    }

    componentDidMount() {

        // 생성 및 편집 분기처리
        if (this.state.isCreateMode) {
            if (this.nameInput)
                this.nameInput.focus();

            // Scenario or Store의 값을 가져와서 editingData와 유사하게 object만들어주기
            Scenario.scna[0].scnaNm = '';
            this.settingInitData(Scenario);

        } else { // 편집 상태일경우 데이터 불러오기
            this.getSavedData();
        }

    }

    // 모드 값 로드
    getSavedData = () => {
        Iot.getScenarioDetail(this.state.action === 'mode' ? 'modes' : 'automation', this.state.editTarget, data => {
            console.log('모드 상세정보:', data);
            this.settingInitData(data);
        });
    }

    // 들어온값 초기 세팅
    settingInitData = (data) => {
        let checkedMap = [];
        //data.scna[0].scnaNm = data'';
        data.scnaIfThings.forEach(item => {
            checkedMap[`${item.deviceId}-${item.stsId}-${item.thingsId}`] = (item.thingsAttrIfCond === item.maxVlu);
        });

        data.scnaThings.forEach(item => {
            checkedMap[`${item.deviceId}-${item.stsId}-${item.thingsId}`] = (item.stsValue === item.maxVlu);
        });

        this.setState({checkedMap: checkedMap, editingData: data,});
    }


    // 편집모드 전환 및 추가 화면 이동용 핸들러
    onClickEdit = (type) => {
        const DRAWER = {device: 'iot-device-category-detail', sensor: 'iot-sensor-list'};
        const CALLBACK = {device: this.gettedAddingDevices, sensor: this.gettedAddingSensors};

        if (type === 'device') this.setState({isEditingDevice: !this.state.isEditingDevice});
        if (type === 'sensor') this.setState({isEditingSensor: !this.state.isEditingSensor});


        // Store.pushDrawer(DRAWER[type], { action: this.state.action, callback: CALLBACK[type] } );
    }


    /* Create Scenario */
    // 시나리오 생성 상태에서 이름 변경
    onChangeName = (event) => {
        const newEditingData = Object.assign({}, this.state.editingData);
        newEditingData.scna[0].scnaNm = event.target.value;

        this.setState({editingData: newEditingData});
    }


    /*  Mode Callback */

    /*사용된 센서 ----------------------------------------------------------*/

    // 센서 토글
    updateSensorCheck(bool, index) {
        const item = this.state.editingData.scnaIfThings[index]
        console.log(item);
        this.updateCheck(bool, `${item.deviceId}-${item.stsId}-${item.thingsId}`);

        let d = this.state.editingData.scnaIfThings[index];
        d.thingsAttrIfCond = bool ? d.maxVlu : d.minVlu;
        this.state.editingData.scnaIfThings[index] = d;
    }

    // 조건문 콜백
    updateCondition({basic, condition}, index) {
        let d = this.state.editingData.scnaIfThings[index];
        d.thingsAttrIfCond = basic;
        d.condi = condition;
        this.state.editingData.scnaIfThings[index] = d;
    }

    // 시간선택 콜백
    updateTime = (hhmm, date) => {
        const d = {...this.state.editingData.scnaIfSpc[0], spcTime: hhmm, ...date};
        this.state.editingData.scnaIfSpc[0] = d;
    }

    // 구간시간 선택 콜백
    updateDuration = (start, end, date) => {
        const d = {...this.state.editingData.scnaIfAply[0], aplyStartTime: start, aplyEndTime: end, ...date};
        this.state.editingData.scnaIfAply[0] = d;
    }


    /* 사용된 기기 ----------------------------------------- */

    // 디바이스 토글
    updateDeviceCheck(bool, index) {
        const item = this.state.editingData.scnaThings[index];
        this.updateCheck(bool, `${item.deviceId}-${item.stsId}-${item.thingsId}`); // 토글 변경 적용 용

        let d = this.state.editingData.scnaThings[index];
        let updateObj = Object.assign({}, this.state.updatedScnaThings);

        updateObj[d.deviceId] =
            {
                thingsId: d.thingsId,
                deviceId: d.deviceId,
                stsId: d.stsId,
                stsValue: bool ? d.maxVlu : d.minVlu,
                chk: "Y"
            };

        this.setState({updatedScnaThings: updateObj});
    }

    // 디바이스 내부 속성들 변경되면 [stsId:값들... 형태로 넘어옴
    updateDeviceInfo(changedMap, index) {
        console.log('기기 내부 속성 변경됨:', changedMap);
        this.setState({updatedScnaThings: {...changedMap, ...this.state.updatedScnaThings}}); // 변경된 데이터와 취합
    }


    /* 편집모드에서 기기나 시나리오를 추가 할때 콜백 ( 생성할때는 그냥 바로 Scenario객체를 사용하므로 호출 안됨 ) */
    gettedAddingSensors = (items) => {
        let newEditingData = Object.assign({}, this.state.editingData);
        if (items.scnaIfSpc.length > 0) newEditingData.scnaIfSpc = items.scnaIfSpc;
        if (items.scnaIfAply.length > 0) newEditingData.scnaIfAply = items.scnaIfAply;

        // 중복 검사, 고유키가 같은 센서가 있는지 검사해서 없는 놈들만 추가
        newEditingData.scnaIfThings = newEditingData.scnaIfThings.concat(items.scnaIfThings.filter(item => {
            return !this.state.editingData.scnaIfThings.some(t => {
                console.log(`${item.deviceId}-${item.stsId}-${item.thingsId}`, `${t.deviceId}-${t.stsId}-${t.thingsId}`);
                return `${item.deviceId}-${item.stsId}-${item.thingsId}` === `${t.deviceId}-${t.stsId}-${t.thingsId}`;
            });
        }));

        console.log('더해서 취합된 센서리스트:', newEditingData.scnaIfThings);

        this.setState({
            editingData: newEditingData,
        });

    }

    gettedAddingDevices = (items) => {

        // deviceId로 중복 아이템이 안생기게 검사
        const newItems = items.filter(item => {
            return !this.state.editingData.scnaThings.some(t => {
                return parseInt(t.deviceId, 10) === parseInt(item.deviceId, 10);
            });
        });

        let newEditingData = Object.assign({}, this.state.editingData);
        newEditingData.scnaThings = newEditingData.scnaThings.concat(newItems);

        this.setState({
            editingData: newEditingData,
        });

    }

    /* 기기나 디바이스 삭제 요청
    * @params type: sensor, time, duration, device
    * */
    onRemoveItem = (item, type) => {
        if (!window.confirm('삭제 하시겠습니까?')) return;

        let newEditingData = Object.assign({}, this.state.editingData);
        let searchIdx;

        switch (type) {
            case 'time':
                if (newEditingData.scnaIfSpc.length > 0){
                    newEditingData.scnaIfSpc[0].chk = 'N';
                    newEditingData.scnaIfSpc.length = 0;
                }
                break;

            case 'duration':
                if (newEditingData.scnaIfAply.length > 0){
                    newEditingData.scnaIfAply[0].chk = 'N';
                    newEditingData.scnaIfAply.length = 0;
                }
                break;

            case 'sensor':
                // searchIdx = newEditingData.scnaIfThings.indexOf( item );
                item.chk = 'N';

                // console.log( 'U:', newEditingData.scnaIfThings[searchIdx], item );
                //if( searchIdx >= 0 ) newEditingData.scnaIfThings.splice( searchIdx, 1 );
                break;

            case 'device':
                // searchIdx = newEditingData.scnaThings.indexOf( item );
                item.chk = 'N';
                break;
			default :
        }

        console.log('newEditingData:', newEditingData);

        this.setState({
            editingData: newEditingData,
        });

    }


    // 변경값 적용
    onApply = () => {

        // Automation 생성
        if (this.state.isCreateMode) {

            let data = Object.assign({}, Scenario);

            const NAME = Scenario.scna[0].scnaNm;
            if (NAME === undefined || NAME.trim().length === 0) {
                alert('이름을 지어주세요.');
                this.nameInput.focus();
                return;
            }

            // scnaThings에는 추가된 목록이..
            // updatedScnaThings에는 추가후 변경된 놈들만 들어있음...
            // console.log( '이전데이터:', data.scnaThings );
            // deviceType=='button'은 기본으로 넣어주자..
            const Buttons = data.scnaThings.filter(item => {
                return ((item.deviceType === 'button') && this.state.updatedScnaThings[item.deviceId] === undefined);
            });

            data.scnaThings = Object.values(this.state.updatedScnaThings).concat(Buttons);

            if (data.scnaIfAply.length === 0) delete data.scnaIfAply;
            if (data.scnaIfSpc.length === 0) delete data.scnaIfSpc;

            console.log('취합된 데이터', data);

            Iot.createAutomation(data, res => {
                console.log('시나리오 생성:', res);
                alert(res.msg);
                this.props.history.goBack();
            });

        } else {
            // Mode 및 Automation 업데이트
            let {updatedScnaThings, editingData} = this.state;

            let data = Object.assign({}, editingData);
            if (data.scnaIfAply.length === 0) delete data.scnaIfAply;
            if (data.scnaIfSpc.length === 0) delete data.scnaIfSpc;
            delete data.scnaIfOption;
            delete data.msg;

            data.scna = [{msg: editingData.scna[0].msg, scnaNm: editingData.scna[0].scnaNm}];

            // console.log( '기기목록:', editingData.scnaThings ); // 모든데이터가 다 들어있고,
            // console.log( '기기추가후 업데이트내용:', updatedScnaThings ); // 업데이트에 필요한 데이터만 들어있음

            let ids = [];

            data.scnaThings = data.scnaThings.map(item => {

                // 수정본 데이터가 있으면 수정본 반환
                if (updatedScnaThings[`${item.deviceId}-${item.stsId}-${item.thingsId}`]) {
                    return updatedScnaThings[`${item.deviceId}-${item.stsId}-${item.thingsId}`];
                }

                ids.push(`${item.deviceId}-${item.stsId}-${item.thingsId}`); // 추가된 아이템의 유니크 키 수집

                // 데이터 필요한것만 전달
                return {
                    thingsId: item.thingsId,
                    deviceId: item.deviceId,
                    stsId: item.stsId,
                    stsValue: item.stsValue,
                    chk: item.chk
                }
            });

            // 업데이트된 디바이스중 윗줄에서 추가되지 않은 녀석이 있으면 추가해줌
            for (let key in updatedScnaThings) {
                if (ids.indexOf(key) <= 0) {
                    data.scnaThings.push(updatedScnaThings[key]);
                }
            }

            // console.log('최종 보낼 데이터:', data.scnaThings );

            Iot.updateAutomation(this.state.action === 'mode' ? 'modes' : 'automation', this.state.editTarget, data, res => {
                alert(res.msg);
                this.props.history.goBack();
            });
        }
    }

    onCancel = () => {
        this.props.history.goBack();
    }


    // 디바이스 토글되면 맵에 저장, 화면 유지용
    updateCheck(bool, id) {
        let nMap = Object.assign({}, this.state.checkedMap);
        nMap[id] = bool;
        this.setState({
            checkedMap: nMap,
        });
    }


    render() {

        const {action, isCreateMode, editingData, isEditingDevice, isEditingSensor} = this.state;

        if (!editingData) return <div/>;


        const ACTION_SCENARIO = (action === 'scenario');

        const {pathname} = this.props.location;
        let scna;
        let Sensors = [];
        let Devices = [];

        let sensorMore, deviceMore;
        if (ACTION_SCENARIO) { // Automation 생성 및 편집에서만 편집 가능하게 노출
            sensorMore = <button className="ml-auto cl--underline" onClick={() => this.onClickEdit('sensor')}>
                {isEditingSensor ? '센서 편집 종료' : '센서 편집'}
            </button>;
            deviceMore = <button className="ml-auto cl--underline" onClick={() => this.onClickEdit('device')}>
                {isEditingDevice ? '기기 편집 종료' : '기기 편집'}
            </button>;
        }

        let icon;

        scna = editingData.scna[0];
        icon = scna.icon;

        // 일반 센서추가
        Sensors = editingData.scnaIfThings.map((item, index) => {

            if (item.chk === 'N') {
                return null;
            }
            else if (item.deviceType && item.deviceType === 'button')
                return <LiOfToggle key={index} icon={item.imgSrc} name={item.stsNm}
                                   checked={this.state.checkedMap[`${item.deviceId}-${item.stsId}-${item.thingsId}`]}
                                   removable={this.state.isEditingSensor}
                                   onRemove={() => this.onRemoveItem(item, 'sensor')}
                                   onSwitch={bool => this.updateSensorCheck(bool, index)}
                />
            else
                return <LiOfCtrl key={index} icon={item.imgSrc} name={item.stsNm}
                                 removable={this.state.isEditingSensor}
                                 onRemove={() => this.onRemoveItem(item, 'sensor')}
                                 onClick={this.state.isEditingSensor ? undefined : () => Store.pushDrawer('edit-sensor-if',
                                     {
                                         ...item,
                                         options: editingData.scnaIfOption,
                                         callback: value => this.updateCondition(value, index)
                                     })}
                />
        });

        // 특정 시간 있으면 추가
        Sensors = Sensors.concat(editingData.scnaIfSpc.map(item => {
            if (item.chk === 'N') {
                return null;
            } else {
                return <LiOfCtrl key="time" icon={undefined}
                                 name="특정 시간"
                                 desc={item.spcTime}
                                 onClick={this.state.isEditingSensor ? undefined :
                                     () => Store.pushDrawer('edit-sensor-time', {...item, callback: this.updateTime})}
                                 removable={this.state.isEditingSensor}
                                 onRemove={() => this.onRemoveItem(item, 'time')}
                />
            }
        }));

        // 시간 구간 조건 추가
        Sensors = Sensors.concat(editingData.scnaIfAply.map(item => {
            if (item.chk === 'N') {
                return null;
            } else {
                return <LiOfCtrl key="time-range" icon={undefined} name="구간 시간"
                                 desc={`${item.aplyStartTime} ~ ${item.aplyEndTime}`}
                                 onClick={this.state.isEditingSensor ? undefined :
                                     () => Store.pushDrawer('edit-sensor-duration', {
                                         ...item,
                                         callback: this.updateDuration
                                     })}
                                 removable={this.state.isEditingSensor}
                                 onRemove={() => this.onRemoveItem(item, 'duration')}
                />
            }
        }));


        // 디바이스 추가
        Devices = editingData.scnaThings.map((item, index) => {

            if (item.chk === 'N') {
                return null;
            }
            else if (item.deviceType && item.deviceType === 'button')
                return <LiOfToggle key={index} icon={item.imgSrc} name={item.deviceNm}
                                   checked={this.state.checkedMap[`${item.deviceId}-${item.stsId}-${item.thingsId}`]}
                                   removable={this.state.isEditingDevice}
                                   onRemove={() => this.onRemoveItem(item, 'device')}
                                   onSwitch={bool => this.updateDeviceCheck(bool, index)}
                />
            else
                return <LiOfCtrl key={index} icon={item.imgSrc}
                                 name={item.deviceNm}
                                 removable={this.state.isEditingDevice}
                                 onRemove={() => this.onRemoveItem(item, 'device')}
                                 onClick={this.state.isEditingDevice ? undefined :
                                     () => Store.pushDrawer('edit-scan-device', {
                                         ...item,
                                         isScenario: ACTION_SCENARIO,
                                         callback: val => this.updateDeviceInfo(val, index)
                                     })}/>
        });


        return (
            <div className="cl-bg--darkgray">

                <header className="cl-mode-setting__header">
                    <div
                        className={classNames("cl-iot-mode__button", {"cl-iot-mode__button--expand": ACTION_SCENARIO})}>
                        <IconLoader src={icon}/>
                        {ACTION_SCENARIO ?
                            <input ref={r => this.nameInput = r} className="cl-name pr-04em" type="text"
                                   placeholder="제목을 입력하세요."
                                   value={scna.scnaNm}
                                   onChange={this.onChangeName}/>
                            :
                            <span className="cl-name">{scna.scnaNm}</span>
                        }
                    </div>

                    {ACTION_SCENARIO ?
                        <div>
                            <h5>{scna.msg || '사용자 Automation'}</h5>
                            <span className="desc">{
                                (this.state.isCreateMode ? '다양한 센서와 IoT기기를 통해 자동화된 제어가 가능합니다.'
                                    : moment(scna.regDt).format('YYYY년 MM월 DD일'))}
                            </span>
                        </div> :

                        <span className="desc">{scna.msg}</span>
                    }
                </header>

                <div className="pb-5em cl-bg--darkgray">
                    <div className="pt-05em bb--gray">
                        <div className="cl-flex fs-14 ml-1em mr-1em mt-1em mb-1em">
                            <h5 className="fs-14 cl-bold">사용된 센서 <span className="color-primary">{Sensors.length}</span>
                            </h5>
                            {sensorMore}
                        </div>

                        <ul className="cl-iot-vertical-list">
                            {Sensors}
                        </ul>

                        {ACTION_SCENARIO && this.state.isEditingSensor &&
                        <button className="cl-iot-add__button--light pb-3em"
                                onClick={() => Store.pushDrawer('iot-sensor-list', {
                                    isCreate: isCreateMode,
                                    target: this.state.editTarget,
                                    callback: this.gettedAddingSensors
                                })}>
                            <img src={addSrc} alt="센서추가" width="40" height="40"/>
                        </button>
                        }
                    </div>

                    <div>
                        <div className="cl-flex fs-14 ml-1em mr-1em mt-1em mb-1em">
                            <h5 className="fs-14 cl-bold">사용된 기기 <span className="color-primary">{Devices.length}</span>
                            </h5>
                            {deviceMore}
                        </div>

                        <ul className="cl-iot-vertical-list">
                            {Devices}
                        </ul>

                        {ACTION_SCENARIO && this.state.isEditingDevice &&
                        <button className="cl-iot-add__button--light pb-3em"
                                onClick={() => Store.pushDrawer('iot-device-category-detail', {
                                    isCreate: isCreateMode,
                                    target: this.state.editTarget,
                                    callback: this.gettedAddingDevices
                                })}>
                            <img src={addSrc} alt="기기추가" width="40" height="40"/>
                        </button>
                        }
                    </div>
                </div>


                <footer
                    className={classNames("cl-flex", "cl-opts__footer", {"cl-opts__footer--hide": (Sensors.length + Devices.length === 0)})}>
                    <button onClick={this.onCancel}>취소</button>
                    <button className="ml-auto cl-flex mr-1em" onClick={this.onApply}>
                        <img src={checkSrc} alt="확인" width="28" height="28"/>
                        <span className="color-primary ml-03em">확인</span>
                    </button>
                </footer>
            </div>
        )
    }
}


export default withRouter(observer(IotModeSetting));