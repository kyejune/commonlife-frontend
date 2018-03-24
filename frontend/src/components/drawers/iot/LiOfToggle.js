import React, { Component } from 'react';
import IconLoader from "../../ui/IconLoader";
   
class LiOfToggle extends Component {


    onSwitch=( event )=>{
        if( this.props.onSwitch )
            this.props.onSwitch( event.target.checked, this.props.data );
    }

    render() {

        let defaultChecked = false;
        if( this.props.data )
            defaultChecked = (this.props.data.currSts === this.props.data.maxVlu);

        return (
            <li>
                <IconLoader className="cl__thumb--rounded" src={this.props.icon}/>
                <div>
                    <h4 className="cl__title">{this.props.name}</h4>
                    {this.props.desc&&
                    <p className="cl__desc mt-05em">{this.props.desc}</p>
                    }
                </div>

                <div className="ml-auto">
                    {/* IoT 스위치 토글버튼 */}
                    <label className="cl-iot-switch">
                        <input type="checkbox" onChange={ this.onSwitch } checked={defaultChecked}/>
                        <span className="cl-iot-switch-slider"/>
                    </label>
                </div>
            </li>
        );
    }
}

    
export default LiOfToggle;
