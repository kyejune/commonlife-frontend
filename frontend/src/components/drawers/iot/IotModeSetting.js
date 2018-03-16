import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Link} from "react-router-dom";

class IotModeSetting extends Component {

    constructor(props) {
        super(props);

        this.state = {}


    }

    render() {

        return (
            <div>
                무슨무슨 모드.... 어쩌고 <br/>


                <div>
                    센서 목록

                    <ul>
                        <li>
                            <Link to={{ pathname: `${this.props.location.pathname}/sensor/0` }}>센서0</Link>
                            <Link to={{ pathname: `${this.props.location.pathname}/sensor/1` }}>센서1</Link>
                        </li>
                    </ul>

                </div>



                <div>
                    기기 목록

                    <ul>
                        <li>기기1 토글타입</li>
                        <li>
                            <Link to={{ pathname: `${this.props.location.pathname}/device/0` }}>
                                기기2 ctrl타입
                            </Link>
                        </li>
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