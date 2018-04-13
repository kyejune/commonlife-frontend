import React, { Component } from 'react';
import IconLoader from "../../ui/IconLoader";
import classNames from 'classnames';

class LiOfCheck extends Component {

    constructor(props){
        super(props);

        this.state = {
            checked: props.defaultChecked || props.checked || false,
        }
    }

    componentWillReceiveProps(nextProps){
        // this.setState( {
        //     checked: nextProps.checked || false,
        // });
        this.setState( nextProps );
    }

    onChangeCheck=(bool)=>{
        this.setState({ checked:bool });

        if( this.props.onChange )
            this.props.onChange( bool, this.props );
    }

    render() {

        return (
            <li className={ classNames( "cl-iot-device__li--checkbox", { "cl--checked":this.state.checked } ) }>
                <IconLoader className="cl__thumb--rounded" src={this.props.icon}/>
                <div>
                    <h4 className="cl__title">{ this.props.name }</h4>
                    {this.props.desc&&
                    <p className="cl__desc mt-05em">{ this.props.desc }</p>
                    }
                </div>

                <span className={ classNames(
                    this.props.className,
                    'ml-auto',
                    'cl__checkbox',
                    { 'cl--checked':this.state.checked,
                      'cl__checkbox--light':this.props.dark
                    } ) }
                        onClick={ ()=> this.onChangeCheck( !this.state.checked ) }
                />
            </li>
        );
    }
}

    
export default LiOfCheck;
