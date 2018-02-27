import React, {Component} from "react";
import moment from "moment/moment";


class DateOne extends Component {

    constructor(props){
        super(props);

        let date = props.value?new Date(props.value):new Date();
        let yyyymmdd = moment(date).format("YYYY-MM-DD");

        this.min = this.props.min?moment( Math.max( moment(this.props.min), moment()) ).format('YYYY-MM-DD'):this.state.yyyymmdd;
        this.max = moment(this.props.max).format('YYYY-MM-DD');


        this.state = {
            yyyymmdd: yyyymmdd,
            label: this.getLabel( yyyymmdd )
        }
    }

    onDateChange( event ){
        let val = event.target.value;

        this.setState( {
            yyyymmdd: val,
            label: this.getLabel( val )
        } );

        if( this.props.onUpdate )
            this.props.onUpdate( val );
    }

    getLabel( yyyymmdd ){
        let days = Math.ceil(moment(yyyymmdd).diff( moment() )/86400000);
        return [ "오늘", "내일", "모레" ][days]||(days + '일 후');
    }

    render() {

        return <div className="cl-opt-sec cl-flex">
            <div className="cl-label">{ this.state.label }</div>
            <div>
                <input type="date"
                       value={ this.state.yyyymmdd }
                       onChange={ e => this.onDateChange( e ) }
                       min={this.min} max={this.max}
                />
            </div>
        </div>
    }

}


export default DateOne;