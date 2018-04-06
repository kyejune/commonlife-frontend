import React, {Component} from 'react';
import IconLoader from 'components/ui/IconLoader';
import IotSlider from "../../ui/IotSlider";
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
* -- 진입경로가 대시보드 -> 기기제어, 파란버튼 -> 기기제어 의 기기 제어쪽만 남을것 같은데 확인 요망
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
            action: action,

            // 전송용 데이터
            // updateData:{},
        }

        if( action === 'ctrl' ) this.props.updateTitle('상세 제어');
        else if( action === 'my' ) this.props.updateTitle('My IoT 기기 편집');
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

    // componentWillUnmount(){
    //     this.setState({
    //         updateData:{},
    //     });
    // }


    // 즉시실행 일반버튼
    onRunButton( data ){

    }

    // 인풋 텍스트 변경
    onChangeInputText( value, optIndex ){
        let opts = this.state.options.concat();
            opts[optIndex].currStsKor = value;
        this.setState( { options:opts });
    }

    // 즉시실행 토글버튼
    onUpdateSwitch=( bool, data )=>{
        console.log( this.state.action, data.data.moAttr, ": ", bool, data.data );

        // 기기제어
        if( this.state.action === 'ctrl') {
            Iot.setIotToggleDevice(data.data, bool, success => {
                if (!success) this.loadDeviceInformation();
            });

        // My Iot 편집
        }else{
            console.log( '편집용으로 저장???' );
        }
    }


    // 토글버튼 제외한 녀석들 값 변할때 수신
    onChangeOthers=( value, data )=>{
        console.log( data.data.moAttr, ": ", value, data.data );

        if( this.state.action === 'ctrl' ){

            // value change적용 용
            let idx = data.index;
            if( idx !== undefined ){
                let opts = this.state.options.concat();
                opts[idx].currSts = value;
                this.setState({ options: opts });
            }

            // 호출
            Iot.setIotDeviceValue(data.data, value, success => {
                if (!success) this.loadDeviceInformation();
            });
        }else{

            alert('어디서 접근한거냐? 아직 예외처리 안된 페이지인듯');
        }

        // 기기제어
        // if( this.state.action === 'ctrl') {
        //     let updateData = Object.assign({}, this.state.updateData);
        //     updateData['key'] = data.data;
        //
        //     this.setState({
        //         updateData: updateData,
        //     });
        //
        // // My Iot 편집
        // }else{
        //     console.log( '편집용으로 저장' );
        // }


    }


    // onConfirm=()=>{
    //     console.log('확인:', this.state.updateData );
    // }


    render() {

        const Options = this.state.options.map( (item, index)=>{
            //cl-bg--inner

            switch( item.moAttr ){
                case 'image': return null;

                case 'text':
                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary">{item.currStsKor}</span>
                        </h4>
                    </li>;

                case 'binary':
                    // 밸브는 끄기만 가능
                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary uppercase">{item.currSts}</span>
                        </h4>

                        <div className="ml-auto">
                            <Switch offOnly={ item.protcKey === 'valve' }
                                    checked={ item.maxVlu === item.currSts }
                                    onChange={ this.onUpdateSwitch }
                                    data={item}
                            />
                        </div>
                    </li>;

                case 'level':
                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary">{ item.currStsKor + item.unit }</span>
                        </h4>

                        <IotSlider className="w-100 mt-3em ml-03em mr-03em"
                                   min={item.minVlu} max={item.maxVlu} value={item.currSts} unit={item.unit}
                                   onChange={ value => this.onChangeOthers( value, { data:item, index:index }) } data={item}
                        />
                    </li>;


                case 'option':

                    const oText = sjf.filter({ 'VAL':item.currSts }).data( item.option ).wantArray().exec()[0].NM;
                    const Opts = item.option.map( (optionItem, optionIndex )=>{
                        return <option value={optionItem.VAL} key={optionIndex}>{optionItem.NM}</option>
                    });

                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary">{ oText }</span>
                        </h4>

                        <select name="cl-options" value={item.currSts} onChange={ evt =>this.onChangeOthers( evt.target.value, { data:item, index:index } )}>
                            {Opts}
                        </select>
                    </li>

                case 'optiontext':

                    const otText = sjf.filter({ 'VAL':item.currSts }).data( item.optiontext ).wantArray().exec()[0].NM;

                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary">{ otText }</span>
                        </h4>
                    </li>

                case 'button':
                    return <li key={index}>
                        <button onClick={ ()=>this.onRunButton( item ) }>{item.stsNm}</button>
                    </li>

                case 'noti':
                    return <li key={index}>
                        <h4>{item.attr1Rmk}</h4>
                    </li>


                case 'inputtext':

                    return <li key={index}>
                        <h4 className="cl__title ml-03em">
                            {item.stsNm} <span className="color-primary">VALUE</span>
                        </h4>

                        <div className="cl-flex w-100 mt-2em ml-03em mr-03em">
                            <div className="w-85">
                                <input type="text"
                                       placeholder="TEXT를 입력해주세요."
                                       defaultValue={ item.currStsKor }
                                       onChange={ event => this.onChangeInputText( event.target.value, index ) }
                                />
                            </div>
                            <button className="ml-auto"
                                    onClick={ ()=> this.onChangeOthers( item.currStsKor, { data:item, index:index } )}>확인</button>
                        </div>
                    </li>

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


            {/*<footer className={ classNames( "cl-opts__footer", "cl-flex", { "cl-opts__footer--hide": Object.keys(this.state.updateData).length === 0 } )}>*/}
                {/*<div className="ml-auto">*/}
                    {/*<img src={checkSrc} width="28" height="28" className="mr-03em" alt="완료"/>*/}
                    {/*<button className="color-primary" onClick={this.onConfirm }>확인</button>*/}
                {/*</div>*/}
            {/*</footer>*/}

        </div>
    }
}


export default withRouter( IotDevice );
