import React, {Component} from 'react';
import IconLoader from 'components/ui/IconLoader';
import IotSlider from "../../ui/IotSlider";
import Iot from "../../../scripts/iot";
import {withRouter} from "react-router-dom";
import checkSrc from 'images/ic-check@3x.png';
import SimpleJsonFilter from 'simple-json-filter/index';
import Switch from "../../ui/Switch";
import classNames from "classnames";
import Store from "../../../scripts/store";


let sjf = new SimpleJsonFilter();

/*
* 모드 or 시나리오 생성/편집 에서 기기편집용 화면
* */
class IotDeviceOfScan extends Component {

    constructor(props) {
        super(props);

        this.state = {
            deviceId: 19,
            name: '기기명',
            desc: '기기설명',
            options: [],

            // 전송용 데이터
            updateData: {},
        }
    }


    componentWillMount() {
        this.loadDeviceInformation();
    }

    loadDeviceInformation() {
        // 시나리오 생성할땐 아직 scnaId가 없으므로 -1을 전달
        Iot.getDeviceOfScan( this.props.scnaId || -1, this.props.deviceId, data => {

            this.setState({
                icon: data[0].imgSrc,
                name: data[1].deviceNm,
                desc: data[0].currStsKor,
                options: data,
            });

        });
    }

    componentWillUnmount() {
        this.setState({
            updateData: {},
        });
    }



    /*

    반환해줘야하는 데이터 샘플 !!!

    "scnaThings": [
    {
      "thingsId": "1",
      "deviceId": "2",
      "stsId": "42",
      "stsValue": "on",
      "chk" : "N"
    }
    ,
    {
      "thingsId": "1",
      "deviceId": "4",
      "stsId": "42",
      "stsValue": "off",
      "chk" : "Y"
    }*/

    // 즉시실행 일반버튼
    onRunButton(data) {

    }

    // 토글버튼
    onUpdateSwitch = (bool, data ) => {
        let d = data.data;
        this.updateSendingData( { ...d, stsValue:bool?d.maxVlu:d.minVlu } );
    }


    // 완료 누르면 후 전송할 데이터 차곡차곡 모아두기...
    onChangeOthers = (value, data, index ) => {
        let d = data.data;
        console.log('디바이스 아이템 상세 변경:', data.data.moAttr, ": ", value, data.data);

        // let obj= { ...d };
        d.stsValue = value;

        // UI에 반영되게
        if( index !== undefined ){
            let opts  = this.state.options.concat();
            opts[index].stsValue = value;
            console.log( '값 갱신:', opts[index], value );
            this.setState({ options: opts });
        }

        this.updateSendingData( d );
    }

    // ADD버튼 토글 ( 시나리오 용 )
    onChangeEnable( item, index ){
        let opts  = this.state.options.concat();
        const PREV = (opts[index].chk === 'Y');
        const YN = opts[index].chk = PREV?'N':'Y';

        this.setState({ options: opts });
        this.updateSendingData( opts[index], YN );
    }


    // 변경된 데이터 취합
    updateSendingData( data, YN = 'Y' ){
        let obj = Object.assign({}, this.state.updateData );
        // 고유키 : deviceId-stsId-thingsId
        obj[`${data.deviceId}-${data.stsId}-${data.thingsId}`] =
            { thingsId:data.thingsId, deviceId: data.deviceId, stsId:data.stsId, stsValue: data.stsValue, chk:YN };
        this.setState({ updateData: obj});

        console.log( 'updateSendingData:', Object.values(obj) );
    }

    onConfirm = () => {
        console.log('확인:', Object.values(this.state.updateData) );
        this.props.callback( this.state.updateData );
        Store.popDrawer();
    }


    render() {

        const Options = this.state.options.map((item, index) => {

            let BeforeBtn;
            const hasBtn = (item.addBtnYn === 'Y' );
            const btnOn = (item.chk === 'Y');

            if( hasBtn ){
                BeforeBtn = <button onClick={ ()=> this.onChangeEnable( item, index ) }
                    className={ classNames("cl-device-of-scna-toggle__button", {"cl--selected":btnOn, "cl--editable":this.props.isScenario}) } />;
            }

            // 시나리오 생성의 경우 기본값이 없으므로 무조건 min값으로
            if( this.props.isScenario ){
                item.stsValue = item.stsValue || item.minVlu;
                console.log('시나리오 생성중인가? 기본값 없어서 min값 세팅');
            }


            switch (item.moAttr) {
                case 'image':
                    return null;

                case 'text':
                    return <li key={index} className={classNames({ 'cl--editable':btnOn })}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary">{item.currStsKor}</span>
                        </h4>
                    </li>;

                case 'binary':
                    // 밸브는 끄기만 가능
                    return <li key={index} className={classNames({ 'cl--editable':btnOn })}>
                        <div className="cl-flex">
                            {BeforeBtn}
                            <h4 className="cl__title ml-03em">
                                {item.stsNm} <span className="color-primary uppercase">{item.stsValue}</span>
                            </h4>
                        </div>

                        <div className="ml-auto">
                            <Switch offOnly={item.protcKey === 'valve'}
                                    defaultChecked={item.maxVlu === item.stsValue}
                                    onChange={ this.onUpdateSwitch }
                                    data={item}
                            />
                        </div>
                    </li>;

                case 'level':
                    return <li key={index} className={classNames({ 'cl--editable':btnOn })}>
                        <div className="cl-flex">
                            {BeforeBtn}
                            <h4 className="cl__title ml-03em">
                                {item.stsNm} <span className="color-primary">{item.stsValue + item.unit}</span>
                            </h4>
                        </div>

                        <IotSlider className="w-100 mt-3em ml-03em mr-03em"
                                   min={item.minVlu} max={item.maxVlu} value={item.stsValue } unit={item.unit}
                                   onChange={ (value)=> this.onChangeOthers( value, {data:item}, index ) } data={item}
                        />
                    </li>;


                case 'option':

                    const oText = sjf.filter({'VAL': item.stsValue}).data(item.option).wantArray().exec()[0].NM;
                    const Opts = item.option.map((optionItem, optionIndex) => {
                        console.log( 'opt추가!', optionItem );
                        return <option value={optionItem.VAL} key={optionIndex}>{optionItem.NM}</option>
                    });

                    return <li key={index} className={classNames({ 'cl--editable':btnOn })}>
                        <div className="cl-flex">
                            {BeforeBtn}
                            <h4 className="cl__title ml-03em">
                                {item.stsNm} <span className="color-primary">{oText}</span>
                            </h4>
                        </div>

                        <select name="cl-options" defaultValue={item.stsValue} onChange={evt => this.onChangeOthers(evt.target.value, {data: item} )}>
                            {Opts}
                        </select>
                    </li>

                case 'optiontext':

                    const otText = sjf.filter({'VAL': item.stsValue}).data(item.optiontext).wantArray().exec()[0].NM;

                    return <li key={index} className={classNames({ 'cl--editable':btnOn })}>
                        <div className="cl-flex">
                            {BeforeBtn}
                            <h4 className="cl__title ml-03em">
                                {item.stsNm} <span className="color-primary">{otText}</span>
                            </h4>
                        </div>
                    </li>

                case 'button':
                    return <li key={index} className={classNames({ 'cl--editable':btnOn })}>
                        {BeforeBtn}
                        <button onClick={() => this.onRunButton(item)}>{item.stsNm}</button>
                    </li>

                case 'noti':
                    return <li key={index} className={classNames({ 'cl--editable':btnOn })}>
                        <h4>{item.attr1Rmk}</h4>
                    </li>


                case 'inputtext':
                    return <li key={index} className={classNames({ 'cl--editable':btnOn })}>
                        <div className="cl-flex">
                            <h4 className="cl__title ml-03em">
                                {item.stsNm} <span className="color-primary">VALUE</span>
                            </h4>
                            {BeforeBtn}
                        </div>

                        <input type="text"
                               className="mt-2em ml-03em mr-03em" placeholder="TEXT를 입력해주세요."
                               defaultValue={item.stsValue}
                               onChange={event => this.onChangeOthers(event.target.value, {data: item})}
                        />
                    </li>

                default:
                    return <li key={index}>{item.moAttr}</li>;
            }
        });

        return <div className="cl-bg--lightgray">

            <div className="cl-iot__li--expand">
                <IconLoader src={this.state.icon} className="mr-1em"/>
                <h4 className="cl__title">{this.state.name}</h4>
                <p className="cl__desc ml-auto uppercase">{this.state.desc}</p>
            </div>

            {/* 적용되는 옵션들을 밑에 주르륵....*/}
            <ul className="cl-iot-vertical-list cl--full cl-iot-options cl-bg--iot cl-device-props-of-scna__list">
                {Options}
                {/*<li style={{height:'160px'}}/>*/}
            </ul>


            <footer
                className={classNames("cl-opts__footer", "cl-flex")}>
                <div className="ml-auto">
                    <img src={checkSrc} width="28" height="28" className="mr-03em" alt="완료"/>
                    <button className="color-primary" onClick={this.onConfirm}>확인</button>
                </div>
            </footer>

        </div>
    }
}


export default withRouter(IotDeviceOfScan);
