import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Link} from "react-router-dom";
import IconLoader from "../../ui/IconLoader";
import IotBtnMode from "../../ui/IotBtnMode";
import LiOfCtrl from "./LiOfCtrl";
import LiOfToggle from "./LiOfToggle";

class IotModeSetting extends Component {

    constructor(props) {
        super(props);

        this.props.updateTitle(props.match.params.action === 'mode' ? 'Mode 설정 변경' : '시나리오 생성');

        console.log( 'props:', this.props );
    }

    render() {

        const {action, option1} = this.props.match.params;
        const {pathname} = this.props.location;

        let sensorMore, deviceMore;
        if( action === 'mode' ){
            sensorMore = <span className="ml-auto">설정불가</span>;
            deviceMore = <span className="ml-auto">설정가능</span>;
        }else{
            sensorMore = <Link className="ml-auto" to={pathname}>센서편집</Link>;
            deviceMore = <Link className="ml-auto" to={pathname}>기기편집</Link>;
        }

        return (
            <div className="cl-bg--darkgray">

                <div className="cl-bg--lightgray">
                    {option1}번 모드
                    <IotBtnMode modeData={{}}/>
                </div>

                <div>
                    <div className="cl-flex">
                        <h5>사용된 센서</h5>
                        {sensorMore}
                    </div>

                    <ul className="cl-iot-vertical-list">
                        <LiOfCtrl icon={undefined} name="뭐뭐센서" desc="센서 설명" to={`${pathname}/sensor/0` }/>
                        <LiOfCtrl icon={undefined} name="뭐뭐센서" desc="센서 설명" to={`${pathname}/sensor/0` }/>
                    </ul>

                </div>


                <div>
                    <div className="cl-flex">
                        <h5>사용된 기기</h5>
                        {deviceMore}
                    </div>

                    <ul className="cl-iot-vertical-list">

                        <LiOfToggle src={undefined} title="무슨무슨기기" desc="기기설명"/>
                        <LiOfCtrl icon={undefined} name="뭐뭐기기" desc="기기설명"
                                  to={`${pathname}/device/0` }
                        />

                    </ul>

                </div>


                <footer>
                    <button>취소</button>
                    <button>확인</button>
                </footer>
            </div>
        )
    }
}


export default withRouter(IotModeSetting);