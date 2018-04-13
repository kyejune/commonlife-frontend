import React, { Component } from 'react';
import Iot from "../../../scripts/iot";
import LiOfCheck from "./LiOfCheck";
import classNames from "classnames";
import checkSrc from 'images/ic-check@3x.png';
import Store, { Scenario } from "../../../scripts/store";
import { withRouter } from "react-router-dom";
import _ from 'underscore';

/* 시나리오에서 추가할 센서 리스트, 생성 편집 */
class IotAddingSensorList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            data: null,
            addingList:[],
            time: false,
            duration: false,

            addedData: { scnaIfSpc:[], scnaIfAply:[], scnaIfThings:[] },
        }
    }

    componentWillMount() {
        Iot.getAddibleItemOfScenario('conditions', data => {
            this.setState({
                data: data
            });

            const clonedState = _.clone( this.state );
			clonedState.addedData.scnaIfOption = data.scnaIfOption;
			this.setState( clonedState );
        });
    }

    onChangeCheck( bool, itemKey ){
        if( itemKey === 'time' || itemKey === 'duration' )
        {
            let state = {};

            // time, duration은 중복 선택 안됨, 한쪽 선택시 다른쪽 끄기
            if( bool && itemKey === 'time' && this.state.duration )
                state.duration = false;

            if( bool && itemKey === 'duration' && this.state.time )
                state.time = false;

            state[itemKey]  = bool;


            console.log( state );
            this.setState( state )
        }
        else
        {
            let searchIdx = this.state.addingList.indexOf( itemKey );
            let list = this.state.addingList.concat();

            if( bool && searchIdx < 0 )
                list.push( itemKey );
            if( !bool && searchIdx >= 0 )
                list.splice( searchIdx, 1 );

            this.setState({ addingList: list });
        }
    }

    /*
    * "scnaIfThings": [{
      "thingsId": "43",
      "deviceId": "44",
      "stsId": "55",
      "thingsAttrIfCond": "undetected",
      "condi": "CM00801",
        "chk" : "Y"
    },
    */

    addSensors=()=>{

        let { addedData } = this.state;



        addedData.scnaIfSpc = this.state.time?[{ spcTime:'12:00', monYn:'Y', tueYn:'Y', wedYn:'Y', thuYn:'Y', friYn:'Y', satYn:'Y', sunYn:'Y' }]:[];
        addedData.scnaIfAply = this.state.duration?[{ aplyStartTime:'01:00', aplyEndTime:'23:00', monYn:'Y', tueYn:'Y', wedYn:'Y', thuYn:'Y', friYn:'Y', satYn:'Y', sunYn:'Y' }]:[]

        const Things = this.state.data.scnaIfThings;
        // 일반센서 데이터 생성
        addedData.scnaIfThings = this.state.addingList.map( ids => {

            const Id3 = ids.split('-');

            return { ...Things.filter( thing => {
                    return ( thing.stsId.toString() === Id3[0] && thing.thingsId.toString() === Id3[1] && thing.deviceId.toString() === Id3[2] );
                })[0], chk:'Y' }; // 일치하는 값만 가져와서, chk만 'Y'로 수정
        });


        // 생성하기위한 데이터 모으기
        if( this.props.isCreate ){

            Scenario.scnaIfSpc = addedData.scnaIfSpc;
            Scenario.scnaIfAply = addedData.scnaIfAply;
            Scenario.scnaIfThings = Scenario.scnaIfThings.concat( addedData.scnaIfThings );

            // 기본값으로 데이터 만들어서 저장
            // Scenario.scnaIfSpc = this.state.time?[{ spcTime:'12:00', monYn:'Y', tueYn:'Y', wedYn:'Y', thuYn:'Y', friYn:'Y', satYn:'Y', sunYn:'Y' }]:[];
            // Scenario.scnaIfAply = this.state.duration?[{ aplyStartTime:'01:00', aplyEndTime:'23:00', monYn:'Y', tueYn:'Y', wedYn:'Y', thuYn:'Y', friYn:'Y', satYn:'Y', sunYn:'Y' }]:[]
            //
            // const Things = this.state.data.scnaIfThings;
            // // 일반센서 데이터 생성
            // Scenario.scnaIfThings = Scenario.scnaIfThings.concat(this.state.addingList.map( ids => {
            //
            //     const Id3 = ids.split('-');
            //
            //     return { ...Things.filter( thing => {
            //             return ( thing.stsId.toString() === Id3[0] && thing.thingsId.toString() === Id3[1] && thing.deviceId.toString() === Id3[2] );
            //         })[0], chk:'Y' }; // 일치하는 값만 가져와서, chk만 'Y'로 수정
            // }));

        // 편집용은 콜백해주기
        } else {

            this.props.callback( addedData );

        }


        // 시나리오 편집화면으로 이동
        Store.popDrawer();
    }


    render() {

        if (this.state.data === null) return <div/>;

        let Sensors = [];

        // 일반 센서추가
        Sensors = this.state.data.scnaIfThings.map((item, index) => {
            return <LiOfCheck key={index} icon={item.imgSrc} name={item.stsNm} onChange={ bool => this.onChangeCheck( bool, `${item.stsId}-${item.thingsId}-${item.deviceId}` ) }/>
        });

        // 특정 시간
        Sensors = Sensors.concat(this.state.data.scnaIfSpc.map(item => {
            return <LiOfCheck key="time" icon={item.imgSrc} name="특정 시간"
                              checked={this.state.time}
                              onChange={ bool => this.onChangeCheck( bool, 'time' ) }/>
        }));

        // 시간 구간
        Sensors = Sensors.concat(this.state.data.scnaIfAply.map(item => {
            return <LiOfCheck key="time-range" icon={item.imgSrc} name="구간 시간"
                              checked={this.state.duration}
                              onChange={ bool => this.onChangeCheck( bool, 'duration' ) }/>
        }));

        let checkedLen = this.state.addingList.length;
        if( this.state.time ) checkedLen++;
        if( this.state.duration ) checkedLen++;


        return <div>


            <ul className="cl-iot-vertical-list">
                {Sensors}
            </ul>

            <footer
                className={classNames("cl-flex cl-opts__footer", {"cl-opts__footer--hide": checkedLen === 0 })}>
                <div>{ checkedLen }개의 센서 선택</div>
                <button className="ml-auto cl-flex mr-1em" onClick={this.addSensors}>
                    <img src={checkSrc} alt="확인" width="28" height="28"/>
                    <span className="color-primary ml-03em">확인</span>
                </button>
            </footer>
        </div>
    }
}


export default withRouter(IotAddingSensorList);
