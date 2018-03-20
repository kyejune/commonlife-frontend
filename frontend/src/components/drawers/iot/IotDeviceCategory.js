import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Link} from "react-router-dom";
import Iot from "../../../scripts/iot";

class IotDeviceTabList extends Component {

    constructor(props) {
        super(props);

        this.state = {}

        Iot.getDeviceCategories( ( byPlace, byType )=>{
            console.log( '공간별 카테고리 목록:', byPlace );
            console.log( '기기별 카테고리 목록:', byType );
        });
    }

    render() {

        return (
            <div>
                탭으로 구분된 기기 카테고리 정보들

                {/* Link to에 공간별은 roomId를, 기기별은 cateCd를 넣어주세요.*/}
                <ul>
                    <li>
                        <Link to={{ pathname: `${this.props.location.pathname}/2` }}>카테고리 목록 0</Link>
                    </li>
                    <li>
                        <Link to={{ pathname: `${this.props.location.pathname}/HW00201` }}>카테고리 목록 1</Link>
                    </li>
                    <li>
                        <Link to={{ pathname: `${this.props.location.pathname}/HW00202` }}>카테고리 목록 2</Link>
                    </li>
                </ul>
            </div>
        )
    }
}


export default withRouter(IotDeviceTabList);