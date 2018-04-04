import React, { Component } from 'react';
import classNames from 'classnames';

class DateSelector extends Component {


    static str2IndexValues( ...dateYns ){
        let r = [];
        dateYns.forEach( (yn, index) =>{
            if( yn === 'Y' ) r.push( index );
        });

        return r;
    }

    static indexes2ObjectValue( selectedDates ){
        return {
            monYn: selectedDates.indexOf(0) >= 0?'Y':'N',
            tueYn: selectedDates.indexOf(1) >= 0?'Y':'N',
            wedYn: selectedDates.indexOf(2) >= 0?'Y':'N',
            thuYn: selectedDates.indexOf(3) >= 0?'Y':'N',
            friYn: selectedDates.indexOf(4) >= 0?'Y':'N',
            satYn: selectedDates.indexOf(5) >= 0?'Y':'N',
            sunYn: selectedDates.indexOf(6) >= 0?'Y':'N'}
    }



    constructor(props) {
        super(props);
        this.state = {
            selected: props.selected || [],
        }
    }

    componentWillReceiveProps( nextProps ){
        this.setState({
            selected: nextProps.selected,
        });
    }



    onClick( idx ){
        let selected = this.state.selected;
        let idxOf = selected.indexOf( idx );
        if( idxOf >= 0 )
            selected.splice( idxOf, 1 );
        else
            selected.push( idx );

        if( this.props.onChange ) this.props.onChange( selected );
    }


    render() {

        let { selected } = this.state;

        const labels = ['월', '화', '수', '목', '금', '토', '일'];
        const Dates = labels.map( (label, index)=>{
            return <button key={index} className={classNames(
                'cl-date-selector__button',
                {'cl--selected':selected.indexOf(index) >= 0 })}
                // {'cl--disabled':disabled.indexOf(index) >= 0 })}
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
