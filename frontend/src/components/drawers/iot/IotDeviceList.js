import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Link} from "react-router-dom";

class IotDeviceCategory extends Component {

    constructor(props) {
        super(props);

        this.state = {}
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