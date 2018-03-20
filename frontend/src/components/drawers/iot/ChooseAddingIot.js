import React, {Component} from 'react';
import Link from "react-router-dom/es/Link";
import Iot from 'scripts/iot';


class ChooseAddingIot extends Component {


    render() {

        return <div>
            <ul>
                <li>
                    <Link to="/iot/add/category">기기추가</Link>
                </li>
                <li>
                    <Link to="/iot/add/scenario">시나리오 추가</Link>
                </li>
            </ul>

        </div>
    }

}

export default ChooseAddingIot;

