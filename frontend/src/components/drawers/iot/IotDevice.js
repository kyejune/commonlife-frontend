import React, {Component} from 'react';
import IconLoader from 'components/ui/IconLoader';
import LiOfToggle from "./LiOfToggle";
import IotSlider from "../../ui/IotSlider";
import CheckBoxes from "../../ui/CheckBoxes";
import Iot from "../../../scripts/iot";
import {withRouter} from "react-router-dom";


/*
* 상세제어,
* 진입경로: MyIot, MyIot추가,
* */
class IotDevice extends Component {


    componentWillMount(){
        const { action, option3 } = this.props.match.params;
        Iot.getDeviceInfo( option3, data=>{
            console.log( data );
        });
    }


    render() {
        return <div className="cl-bg--lightgray">

            <div className="cl-iot__li--expand">
                <IconLoader src={undefined} className="mr-1em"/>
                <h4 className="cl__title">기기이름</h4>
                <p className="cl__desc ml-auto">설명글..</p>
            </div>

            {/* 적용되는 옵션들을 밑에 주르륵....*/}
            <ul className="cl-iot-vertical-list cl--full cl-iot-options">

                {/* 전원이 있으면, */}
                <li className="cl-bg--inner">
                    <h4 className="cl__title ml-03em">
                        전원 <span className="color-primary">ON</span>
                    </h4>

                    <div className="ml-auto">
                        {/* IoT 스위치 토글버튼 */}
                        <label className="cl-iot-switch">
                            <input type="checkbox"/>
                            <span className="cl-iot-switch-slider"/>
                        </label>
                    </div>
                </li>

                <li>
                    <h4 className="cl__title ml-03em">
                        슬라이더이름 <span className="color-primary">단위</span>
                    </h4>

                    <IotSlider className="w-100 mt-3em ml-03em mr-03em" min={10} max={50} value={30} unit={"°C"}/>
                </li>

                <li>
                    <h4 className="cl__title ml-03em">
                        복수체크 가능 <span className="color-primary">VALUE</span>
                    </h4>
                    <CheckBoxes items={[ {label:"label1", value:0}, {label:"label2", value:1}, {label:"label3", value:2}, {label:"label4", value:3} ]}/>
                </li>

                <li>
                    <h4 className="cl__title ml-03em">
                        하나만 체크가능 <span className="color-primary">VALUE</span>
                    </h4>

                    <CheckBoxes radio items={[ {label:"label1", value:0}, {label:"label2", value:1}, {label:"label3", value:2}, {label:"label4", value:3} ]}/>
                </li>

                <li>
                    <h4 className="cl__title ml-03em">
                        인풋텍스트 <span className="color-primary">VALUE</span>
                    </h4>

                    <input type="text" className="mt-2em ml-03em mr-03em" placeholder="TEXT를 입력해주세요."/>
                </li>


                <li>
                    <h4 className="cl__title ml-03em">
                        콤보박스 <span className="color-primary">VALUE</span>
                    </h4>

                    <select name="cl-options">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                </li>


                <li style={{height:'160px'}}/>
            </ul>


            <footer className="cl-opts__footer">
                <button className="ml-auto">확인</button>
            </footer>

        </div>
    }
}


export default withRouter( IotDevice );
