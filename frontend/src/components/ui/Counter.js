import React, {Component} from 'react';
import minusSrc from 'images/ic-minus-24-px@3x.png';
import plusSrc from 'images/ic-plus-24-px@3x.png';


class Counter extends Component {

    constructor(props) {
        super(props);

        this.state = {
            amount: 1,
            max: props.max || 100,
            min: props.min || 1,
        }
    }

    offset( offsetValue ){
        let targetValue = this.state.amount + offsetValue;
        if( targetValue < this.state.min ) targetValue = this.state.min;
        else if( targetValue > this.state.max ) targetValue = this.state.max;

        this.setState({ amount: targetValue });
    }

    render() {

        return <div className="cl-counter" data-value={ this.state.amount }>

                <button onClick={ ()=> this.offset( -1 ) }>
                    <img src={minusSrc} alt="시간 감축 버튼" width="24"/>
                </button>

                <p>{ this.state.amount }개</p>

                <button onClick={ ()=> this.offset( 1 ) }>
                    <img src={plusSrc} alt="시간 증감 버튼" width="24"/>
                </button>
            </div>

    }
}


export default Counter