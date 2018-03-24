import React, { Component } from 'react';
import IconLoader from "../../ui/IconLoader";
import Link from "react-router-dom/es/Link";
import Checkbox from "../../ui/Checkbox";
   
class LiOfCtrl extends Component {
    render() {
        return (
            <li className="cl-iot-device__li--checkbox">
                <IconLoader className="cl__thumb--rounded" src={this.props.icon}/>
                <div>
                    <h4 className="cl__title">{ this.props.name }</h4>
                    {this.props.desc&&
                    <p className="cl__desc mt-05em">{ this.props.desc }</p>
                    }
                </div>

                <Checkbox className="ml-auto" dark/>
            </li>
        );
    }
}

    
export default LiOfCtrl;
