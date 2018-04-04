import React, {Component} from 'react';
import Store from "../../scripts/store";

class ConditionalExpression extends Component {


    onChangeCondition = (event) => {
        let val = event.target.value;

        console.log(val);
    }

    onApply=()=>{
        const val = this.selector.options[this.selector.selectedIndex].value;

        if( this.props.callback )
            this.props.callback( val, this.props.deviceId );

        Store.popDrawer();
    }


    render() {

        const { stsNm, condi, unit, thingsAttrIfCond, options = [] } = this.props;

        return (
            <div>

                <div className="cl-condition-expression pl-1em pr-1em pt-2em">
                    <h4 className="cl-bold">{stsNm}조건</h4>

                    <div className="cl-flex mt-2em">
                        <span className="cl-bold mr-1em">{stsNm}</span>
                        <span className="cl-value cl-bold mr-04em">{thingsAttrIfCond}</span>
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
