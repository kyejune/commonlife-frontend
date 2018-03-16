import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Link} from "react-router-dom";

class IotDeviceTabList extends Component {

    constructor(props) {
        super(props);

        this.state = {}
    }

    render() {

        return (
            <div>
                탭으로 구분된 기기 카테고리 정보들

                <ul>
                    <li>
                        <Link to={{ pathname: `${this.props.location.pathname}/0` }}>카테고리 목록 0</Link>
                    </li>
                    <li>
                        <Link to={{ pathname: `${this.props.location.pathname}/1` }}>카테고리 목록 1</Link>
                    </li>
                    <li>
                        <Link to={{ pathname: `${this.props.location.pathname}/2` }}>카테고리 목록 2</Link>
                    </li>
                </ul>
            </div>
        )
    }
}


export default withRouter(IotDeviceTabList);