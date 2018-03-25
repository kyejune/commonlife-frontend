import React, { Component } from 'react';
import Iot from "../../../scripts/iot";
import LiOfCheck from "./LiOfCheck";


/* 시나리오에서 추가할 센서 리스트 */
class IotSensorList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            data: null,
        }
    }

    componentWillMount(){
        Iot.getAddibleItemOfScenario( 'conditions', data=>{
            this.setState({
                data: data
            });
        });
    }


    render() {

        if( this.state.data === null ) return <div/>;

        let Sensors = [];
        let data = this.state.data;

        // 일반 센서추가
        Sensors = data.scnaIfThings.map( (item, index)=>{
            return <LiOfCheck key={index} icon={item.imgSrc} name={item.stsNm}/>
        });

        // 임시로 시간데이터 더미 추가
        data.scnaIfSpc = [{
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
        Sensors = Sensors.concat( data.scnaIfSpc.map( item => {
            return <LiOfCheck key="time" icon={item.imgSrc} name="특정 시간(더미)" />
        }));

        // 시간 구간 조건 추가
        Sensors = Sensors.concat( data.scnaIfAply.map( item =>{
            return <LiOfCheck key="time-range" icon={item.imgSrc} name="구간 시간" />
        }));


        return (
            <ul className="cl-iot-vertical-list">
                {Sensors}
            </ul>
        );
    }
}


export default IotSensorList;
