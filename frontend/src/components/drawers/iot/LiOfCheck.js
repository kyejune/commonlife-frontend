import React, { Component } from 'react';
import IconLoader from "../../ui/IconLoader";
import Checkbox from "../../ui/Checkbox";
import classNames from 'classnames';
   
class LiOfCheck extends Component {

    constructor(props){
        super(props);

        this.state = {
            checked: this.props.checked || false,
        }
    }

    componentWillReceiveProps(nextProps){
        this.setState( {
            checked: nextProps.checked || false,
        });
    }

    onChangeCheck=(bool)=>{
        this.setState({ checked:bool });

        if( this.props.onChange )
            this.props.onChange( bool, this.props );
    }

    render() {

        console.log( 'LiOfCheck render:', this.props.name, this.state.checked );

        return (
            <li className={ classNames( "cl-iot-device__li--checkbox", { "cl--checked":this.state.checked } ) }>
                <IconLoader className="cl__thumb--rounded" src={this.props.icon}/>
                <div>
                    <h4 className="cl__title">{ this.props.name }</h4>
                    {this.props.desc&&
                    <p className="cl__desc mt-05em">{ this.props.desc }</p>
                    }
                </div>

                <Checkbox className="ml-auto" checked={this.state.checked} onChange={this.onChangeCheck}/>
            </li>
        );
    }
}

    
export default LiOfCheck;
