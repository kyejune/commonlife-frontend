import React, { Component } from 'react';
import classNames from 'classnames';


class Checkbox extends Component {


    constructor(props){
        super(props);

        this.state = {
            checked: false,
        }
    }

    toggle = ()=>{
        var isChecked = !this.state.checked;
        this.setState( { checked: isChecked });

        if( this.props.onChange )
            this.props.onChange( isChecked );
    }

    render() {

        return (
            <button className={ classNames( this.props.className, 'cl__checkbox', { 'cl--checked':this.state.checked, 'cl__checkbox--light':this.props.dark } ) }
                    onClick={ this.toggle }
            />
        );
    }
}


export default Checkbox;
