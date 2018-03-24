import React, {Component} from 'react';
import IconLoader from 'components/ui/IconLoader';
import LiOfToggle from "./LiOfToggle";
import IotSlider from "../../ui/IotSlider";
import CheckBoxes from "../../ui/CheckBoxes";
import Iot from "../../../scripts/iot";
import {withRouter} from "react-router-dom";
import checkSrc from 'images/ic-check@3x.png';
import SimpleJsonFilter from 'simple-json-filter/index';
import Switch from "../../ui/Switch";
import classNames from "classnames";


let sjf = new SimpleJsonFilter();

/*
* 상세제어,
* 진입경로: MyIot, MyIot추가,
* */
class IotDevice extends Component {

    constructor(props){
        super( props );

        const { action, option2, option3 } = this.props.match.params;

        this.state = {
            deviceId: option3||option2,
            name:'기기명',
            desc:'기기설명',
            options:[],

            // 전송용 데이터
            updateData:{},
            rnd:Math.random(), // 강제 업데이트용 키
        }

        if( action === 'ctrl' ) this.props.updateTitle('상세 제어');
    }


    componentWillMount(){
        this.loadDeviceInformation();
    }

    loadDeviceInformation(){

        Iot.getDeviceInfo( this.state.deviceId, data=>{

            this.setState({
                icon: data[0].imgSrc,
                name: data[1].deviceNm,
                desc: data[0].currStsKor,
                options: data,
            });
        });
    }

    componentWillUnmount(){
        this.setState({
            updateData:{},
        });
    }


    // 즉시실행 일반버튼
    onRunButton( data ){

    }

    // 즉시실행 토글버튼
    onUpdateSwitch=( bool, data )=>{
        console.log( data.data.moAttr, ": ", bool, data.data );
        Iot.setIotToggleDevice( data.data, bool, success=>{
            if( !success ) this.loadDeviceInformation();
        });
    }


    // 완료 누르면 후 전송할 데이터 차곡차곡 모아두기...
    onChangeOthers=( value, data )=>{
        console.log( data.data.moAttr, ": ", value, data.data );

        let updateData = Object.assign({}, this.state.updateData);
        updateData['key'] = data.data;

        this.setState({
            updateData: updateData,
        });
    }


    onConfirm=()=>{
        console.log('확인:', this.state.updateData );
    }


    render() {

        const Options = this.state.options.map( (item, index)=>{
            //cl-bg--inner

            switch( item.moAttr ){
                case 'image': return null;
                break;

                case 'text':
                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary">{item.currStsKor}</span>
                        </h4>
                    </li>;
                break;

                case 'binary':
                    // 밸브는 끄기만 가능
                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary uppercase">{item.currSts}</span>
                        </h4>

                        <div className="ml-auto">
                            <Switch offOnly={ item.protcKey === 'valve' }
                                    defaultValue={ item.maxVlu === item.currSts }
                                    onChange={ this.onUpdateSwitch }
                                    data={item}
                            />
                        </div>
                    </li>;
                break;

                case 'level':
                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary">{ item.currStsKor + item.unit }</span>
                        </h4>

                        <IotSlider className="w-100 mt-3em ml-03em mr-03em"
                                   min={item.minVlu} max={item.maxVlu} value={item.currSts} unit={item.unit}
                                   onChange={ this.onChangeOthers } data={item}
                        />
                    </li>;
                break;


                case 'option':

                    const oText = sjf.filter({ 'VAL':item.currSts }).data( item.option ).wantArray().exec()[0].NM;
                    const Opts = item.option.map( (optionItem, optionIndex )=>{
                        return <option value={optionIndex} key={optionIndex}>{optionItem.NM}</option>
                    });

                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary">{ oText }</span>
                        </h4>

                        <select name="cl-options" onChange={ evt =>this.onChangeOthers( evt.target.value, { data:item } )}>
                            {Opts}
                        </select>
                    </li>
                break;

                case 'optiontext':

                    const otText = sjf.filter({ 'VAL':item.currSts }).data( item.optiontext ).wantArray().exec()[0].NM;

                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary">{ otText }</span>
                        </h4>
                    </li>
                break;

                case 'button':
                    return <li key={index}>
                        <button onClick={ ()=>this.onRunButton( item ) }>{item.stsNm}</button>
                    </li>
                break;

                case 'noti':
                    return <li key={index}>
                        <h4>{item.attr1Rmk}</h4>
                    </li>
                break;


                case 'inputtext':
                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            인풋텍스트 <span className="color-primary">VALUE</span>
                        </h4>

                        <input type="text"
                               className="mt-2em ml-03em mr-03em" placeholder="TEXT를 입력해주세요."
                               onChange={ event => this.onChangeOthers( event.target.value, {data: item} )}
                        />
                    </li>
                break;

                default: return <li key={index}>{item.moAttr}</li>;
            }
        });

        return <div className="cl-bg--lightgray">

            <div className="cl-iot__li--expand">
                <IconLoader src={this.state.icon} className="mr-1em"/>
                <h4 className="cl__title">{ this.state.name }</h4>
                <p className="cl__desc ml-auto uppercase">{ this.state.desc }</p>
            </div>

            {/* 적용되는 옵션들을 밑에 주르륵....*/}
            <ul className="cl-iot-vertical-list cl--full cl-iot-options cl-bg--iot">
                {Options}
                {/*<li style={{height:'160px'}}/>*/}
            </ul>


            <footer className={ classNames( "cl-opts__footer", "cl-flex", { "cl-opts__footer--hide": Object.keys(this.state.updateData).length === 0 } )}>
                <div className="ml-auto">
                    <img src={checkSrc} width="28" height="28" className="mr-03em" alt="완료"/>
                    <button className="color-primary" onClick={this.onConfirm }>확인</button>
                </div>
            </footer>

        </div>
    }
}


export default withRouter( IotDevice );
