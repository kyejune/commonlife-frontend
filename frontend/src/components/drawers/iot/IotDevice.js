import React, {Component} from 'react';
import IconLoader from 'components/ui/IconLoader';
import LiOfToggle from "./LiOfToggle";
import IotSlider from "../../ui/IotSlider";


/*
* 상세제어,
* 진입경로: MyIot, MyIot추가,
* */
class IotDevice extends Component {
    render() {
        return <div className="cl-bg--lightgray">

            <div className="cl-iot__li--expand">
                <IconLoader src={undefined} className="mr-1em"/>
                <h4 className="cl__title">기기이름</h4>
                <p className="cl__desc ml-auto">설명글..</p>
            </div>

            {/* 적용되는 옵션들을 밑에 주르륵....*/}
            <ul className="cl-iot-vertical-list cl--full">

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
                    등등
                </li>
            </ul>


            <footer>
                <button className="ml-auto">확인</button>
            </footer>

        </div>
    }
}


export default IotDevice;
