import React,{Component} from 'react';






class IotProgressOverlay extends Component{

    componentDidMount(){
        console.log( 'IotProgressOverlay', this.props );
    }


    render(){
        return <div>
            켜지거나 꺼지거나...
        </div>
    }
}


export default IotProgressOverlay