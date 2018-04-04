import React, { Component } from 'react';
   
class Switch extends Component {


    constructor(props, context) {
        super(props, context);
        this.state = {
            checked: this.props.defaultChecked || this.props.checked || false,
            offOnly: this.props.offOnly || false,
        };
    }

    componentWillReceiveProps(nextProps){
        this.setState( nextProps );
    }

    onChange=( event )=>{
        if( this.state.offOnly && this.state.checked === false ) return;

        const newChecked = this.state.checked?false:true;
        this.setState({
            checked: newChecked,
        });

        if( this.props.onChange )
            this.props.onChange( newChecked, this.props );
    }


    render() {
        return (
            <label className={"cl-iot-switch " + this.props.className||'' }>
                <input type="checkbox" checked={this.state.checked} onChange={ this.onChange }/>
                <span className="cl-iot-switch-slider"/>
            </label>
        );
    }
}

    
export default Switch;
