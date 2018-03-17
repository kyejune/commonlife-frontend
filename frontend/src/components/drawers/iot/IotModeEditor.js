import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';
import {Link} from "react-router-dom";
import EditableList from "../../ui/EditableList";


/* 모드 목록 (편집용) */
class IotModeEditor extends Component {

    constructor(props) {
        super(props);

        this.state = {}
    }

    render() {

        return (
            <div>
                <EditableList
                    items={[
                        {img:'', name:'외출모드', to:"/iot/mode/0" },
                        {img:'', name:'취침모드', to:"/iot/mode/1"},
                        {img:'', name:'휴가모드', to:"/iot/mode/2"},
                        {img:'', name:'절약모드', to:"/iot/mode/3"}]}
                />

                <footer>
                    <button>상세</button>
                    :는 위에 리스트중 하나라도 체크되면 노출
                </footer>
            </div>
        )
    }
}


export default withRouter(IotModeEditor);