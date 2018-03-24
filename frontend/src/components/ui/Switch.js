import React, { Component } from 'react';
   
class Switch extends Component {


    constructor(props, context) {
        super(props, context);
        this.state = {
            checked: this.props.defaultValue || false,
            offOnly: this.props.offOnly || false,
        };
    }

    componentWillReceiveProps(nextProps){
        this.setState({
            checked: nextProps.defaultValue,
            offOnly: nextProps.offOnly,
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
            <label className={"cl-iot-switch " + this.props.className }>
                <input type="checkbox" checked={this.state.checked} onChange={ this.onChange }/>
                <span className="cl-iot-switch-slider"/>
            </label>
        );
    }
}

    
export default Switch;
