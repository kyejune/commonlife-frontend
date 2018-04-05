import React, {Component} from 'react';
import Store from "../../scripts/store";

class ConditionalExpression extends Component {

    constructor(props) {
        super(props);
        this.state = {
            value: props.thingsAttrIfCond,
        }

    }


    // onChangeCondition = (event) => {
    //     let val = event.target.value;
    //
    //     console.log(val);
    // }

    onChangeValue = ( event ) => {

        // let val = Math.min( Math.max( event.target.value, this.props.minVlu ), this.props.maxVlu );
        // if( isNaN(val) ) val = parseInt((this.props.maxVlu - this.props.minVlu)/2);

        this.setState({
            value: event.target.value,
        });


    }

    onApply=()=>{
        const optionValue = this.selector.options[this.selector.selectedIndex].value;
        const basicValue = Math.min( Math.max( this.state.value, this.props.minVlu ), this.props.maxVlu );

        if( this.props.callback )
            this.props.callback( { basic:basicValue, condition:optionValue }, this.props.deviceId );

        Store.popDrawer();
    }


    render() {

        const { stsNm, unit, condi, options = [] } = this.props;

        console.log('조건문:', this.props );

        return (
            <div>

                <div className="cl-condition-expression pl-1em pr-1em pt-2em">
                    <h4 className="cl-bold">{stsNm}조건</h4>

                    <div className="cl-flex mt-2em">
                        <span className="cl-bold mr-1em">{stsNm}</span>
                        <input type="number" value={this.state.value} onChange={ this.onChangeValue }
                               className="cl-value cl-bold mr-04em w-20"/>
                        <span>{unit}</span>

                        <select ref={ r => this.selector = r } className="cl-value ml-1em" defaultValue={condi}>
                            {options.map( item =>{
                              return <option key={item.condi} value={item.condi}>{item.comnCdnm}</option>
                            })}
                        </select>
                    </div>
                </div>


                <footer className="cl-flex cl-opts__footer">
                    <button className="ml-auto cl-flex mr-1em" onClick={ this.onApply }>
                        <span className="color-primary ml-03em">적용</span>
                    </button>
                </footer>
            </div>
        );
    }
}


export default ConditionalExpression;
