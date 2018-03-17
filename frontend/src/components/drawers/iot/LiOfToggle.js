import React, { Component } from 'react';
import IconLoader from "../../ui/IconLoader";
   
class LiOfToggle extends Component {
    render() {
        return (
            <li>
                <IconLoader className="cl__thumb--rounded" src={undefined}/>
                <div>
                    <h4 className="cl__title">{this.props.name}</h4>
                    {this.props.desc&&
                    <p className="cl__desc mt-05em">{this.props.desc}</p>
                    }
                </div>

                <div className="ml-auto">
                    {/* IoT 스위치 토글버튼 */}
                    <label className="cl-iot-switch">
                        <input type="checkbox"/>
                        <span className="cl-iot-switch-slider"/>
                    </label>
                </div>
            </li>
        );
    }
}

    
export default LiOfToggle;
