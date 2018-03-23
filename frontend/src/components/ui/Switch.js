import React, { Component } from 'react';
   
class Switch extends Component {


    constructor(props, context) {
        super(props, context);
        this.state = {
            checked: this.props.defaultValue || false,
            offOnly: this.props.offOnly || false,
        };

        console.log('S:', props );
    }

    componentWillReceiveProps(nextProps){
        this.setState({
            checked: this.props.defaultValue || false,
            offOnly: this.props.offOnly || false,
        });
    }

    onChange=()=>{
        if( this.state.offOnly && this.state.checked === false ) return;

        const newChecked = !this.state.checked;
        this.setState({
            checked: newChecked,
        });

        if( this.props.onChange )
            this.props.onChange( newChecked, this.props );
    }


    render() {
        return (
            <label className="cl-iot-switch">
                <input type="checkbox" checked={this.state.checked} onChange={ this.onChange }/>
                <span className="cl-iot-switch-slider"/>
            </label>
        );
    }
}

    
export default Switch;
