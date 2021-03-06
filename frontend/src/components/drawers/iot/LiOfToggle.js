import React, { Component } from 'react';
import IconLoader from "../../ui/IconLoader";
import Switch from "../../ui/Switch";
   
class LiOfToggle extends Component {

    constructor(props){
        super( props );

        let defaultCheck;
        if( props.data && props.data.currSts && props.data.maxVlu )
            defaultCheck = (props.data.currSts === props.data.maxVlu);

        if( this.props.checked )
            defaultCheck = props.checked;

        this.state = {
            checked: defaultCheck,
            // removable: props.removable || false,
        }
    }

    componentWillReceiveProps(nextProps){
        this.setState({
            checked: nextProps.checked,
            // removable: nextProps.removable || this.state.removable
        });
    }

    onSwitch=( bool )=>{
        this.setState({
            checked: bool,
        });

        if( this.props.onSwitch )
            this.props.onSwitch( bool, this.props.data );
    }

    render() {

        return (
            <li>
                <IconLoader className="cl__thumb--rounded" src={this.props.icon}/>
                <div>
                    <h4 className="cl__title">{this.props.name}</h4>
                    {this.props.desc&&
                    <p className="cl__desc mt-05em">{this.props.desc}</p>
                    }
                </div>

                {this.props.removable ?
                    <button className="cl-delete__button ml-auto" onClick={ this.props.onRemove }/> :
                    <Switch className="ml-auto" checked={ this.state.checked } onChange={ this.onSwitch }/>
                }
            </li>
        );
    }
}

    
export default LiOfToggle;
