import React, { Component } from 'react';
import classNames from 'classnames';

class DateSelector extends Component {

    onClick( idx ){
        if( this.props.onChange ) this.props.onChange( idx );
    }


    render() {

        let { selected, disabled } = this.props;

        if( !disabled ) disabled = [];


        const labels = ['월', '화', '수', '목', '금', '토', '일'];
        const Dates = labels.map( (label, index)=>{
            return <button className={classNames(
                'cl-date-selector__button',
                {'cl--selected':index===selected},
                {'cl--disabled':disabled.indexOf(index) >= 0 })}
                onClick={ ()=> this.onClick(index) }
            >
                {label}
                </button>
        });


        return <div className="cl-date-selector">
            {Dates}
            </div>
    }
}

    
export default DateSelector;
