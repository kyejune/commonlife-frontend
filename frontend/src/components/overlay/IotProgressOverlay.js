import React,{Component} from 'react';
import Iot from 'scripts/iot';
import Store from "../../scripts/store";





class IotProgressOverlay extends Component{

    componentDidMount(){
        let { cmd, name, type, targetValue } = this.props;

        Iot.setIotDevice( name, targetValue, ()=>{
            Store.ipo = null;
        });

    }


    render(){
        return <div>
            켜지거나 꺼지거나...
        </div>
    }
}


export default IotProgressOverlay