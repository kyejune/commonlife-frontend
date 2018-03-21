import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Link} from "react-router-dom";
import Iot from 'scripts/iot';


class IotDeviceCategory extends Component {

    constructor(props) {
        super(props);

        const dId = props.match.params.option2 || props.match.params.option1;

        const isRoom = !isNaN(dId); // 숫자형이면 룸별 목록, 문자 조합이면 공간별 목록으로 구분
        this.state = {
            isRoom: isRoom,
        }

        console.log( isRoom, dId );

        props.updateTitle( isRoom?'공간별 기기목록':'기기별 기기목록');

        // 목록 가져오기
        Iot.getDevicesByCategory( isRoom, dId, data => {
            console.log( '기기목록:', data );
        });
    }

    render() {

        return (
            <div>

                <ul>
                    <li>
                        기기 토글
                    </li>
                    <li>
                        <Link to={{ pathname: `${this.props.location.pathname}/device/0` }}>기기 ctrl</Link>
                    </li>
                </ul>
            </div>
        )
    }
}


export default withRouter(IotDeviceCategory);