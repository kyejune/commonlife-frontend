import React, { Component } from 'react';
import {Link} from "react-router-dom";

class Automations extends Component {
    render() {
        return (
            <div>
                자동화 리스트...
                를 뿌려야되는데.. 음음...


                <br/>



                <Link to={'/iot/scenario/add'}>
                    생성
                </Link>

            </div>
        );
    }
}


export default Automations;
