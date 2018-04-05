import React, {Component} from 'react';
import Iot from "../../../scripts/iot";
import LiOfCheck from "./LiOfCheck";
import classNames from "classnames";
import checkSrc from 'images/ic-check@3x.png';

/* 시나리오에서 추가할 센서 리스트 */
class IotAddingSensorList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            data: null,
            addingList:[],
        }
    }

    componentWillMount() {
        Iot.getAddibleItemOfScenario('conditions', data => {
            this.setState({
                data: data
            });
        });
    }

    onChangeCheck( bool, itemKey ){
        let searchIdx = this.state.addingList.indexOf( itemKey );
        let list = this.state.addingList.concat();

        if( bool && searchIdx < 0 )
            list.push( itemKey );
        if( !bool && searchIdx >= 0 )
            list.splice( searchIdx, 1 );

        this.setState({ addingList: list });
    }

    addSensors=()=>{
        console.log( '센서 더하긴마ㅣ렁니ㅏ러;ㄴㅇ라ㅣㄴ', this.state.addingList );
    }


    render() {

        if (this.state.data === null) return <div/>;

        let Sensors = [];
        let data = this.state.data;

        // 일반 센서추가
        Sensors = data.scnaIfThings.map((item, index) => {
            return <LiOfCheck key={index} icon={item.imgSrc} name={item.stsNm} onChange={ bool => this.onChangeCheck( bool, `${item.stsId}-${item.thingsId}-${item.deviceId}` ) }/>
        });

        // 특정 시간
        Sensors = Sensors.concat(data.scnaIfSpc.map(item => {
            return <LiOfCheck key="time" icon={item.imgSrc} name="특정 시간" onChange={ bool => this.onChangeCheck( bool, 'time' ) }/>
        }));

        // 시간 구간
        Sensors = Sensors.concat(data.scnaIfAply.map(item => {
            return <LiOfCheck key="time-range" icon={item.imgSrc} name="구간 시간" onChange={ bool => this.onChangeCheck( bool, 'duration' ) }/>
        }));


        return <div>


            <ul className="cl-iot-vertical-list">
                {Sensors}
            </ul>

            <footer
                className={classNames("cl-flex cl-opts__footer", {"cl-opts__footer--hide": this.state.addingList.length === 0})}>
                <div>{this.state.addingList.length}개의 센서 선택</div>
                <button className="ml-auto cl-flex mr-1em" onClick={this.addSensors}>
                    <img src={checkSrc} alt="확인" width="28" height="28"/>
                    <span className="color-primary ml-03em">확인</span>
                </button>
            </footer>
        </div>
    }
}


export default IotAddingSensorList;
