import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Link} from "react-router-dom";

class IotSensor extends Component {

    constructor(props) {
        super(props);

        this.state = {}
    }

    render() {

        return (
            <div>
                센서 세부 화면
            </div>
        )
    }
}


export default withRouter(IotSensor);