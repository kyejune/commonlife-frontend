import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';
import {Link} from "react-router-dom";


/* 모드 목록 (편집용) */
class IotModeEditor extends Component {

    constructor(props) {
        super(props);

        this.state = {}
    }

    render() {

        return (
            <div>
                <ul>
                    <li>
                        <Link to="/iot/mode/0">
                            체크박스, 썸네일, --모드
                        </Link>
                    </li>
                </ul>

                <p>드래그로 순서 바꾸기 기능 포함</p>

                <footer>
                    <button>상세</button>
                    :는 위에 리스트중 하나라도 체크되면 노출
                </footer>
            </div>
        )
    }
}


export default withRouter(IotModeEditor);