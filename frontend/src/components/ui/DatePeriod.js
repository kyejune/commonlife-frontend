import React, {Component} from "react";
import moment from "moment/moment";
import DayPicker, { DateUtils } from 'react-day-picker';
import 'react-day-picker/lib/style.css';


class DatePeriod extends Component {

    constructor(props){
        super(props);

        let now = moment().format('YYYY-MM-DD');
        let start = props.min > now?props.min:now;

        this.state = {
            start: start,
            end: props.max,
            days: props.days||2,
            from: props.value[0]?new Date(props.value[0]):new Date(start),
            to: props.value[1]?new Date(props.value[1]):moment(start).add( props.minDays, 'd').toDate(),
            onCalendar: false,
        }
    }

    // 클릭하면 range를 설정, {from, to}만 만들어주면 된다.
    // 기본기능 이외에 제한된 날짜 수 만큼만 선택가능하게 하는 기능 추가함
    handleDayClick(day) {

        let date = new Date(day);
        let range = DateUtils.addDayToRange(day, this.state);

        const MAX = this.props.maxDays;
        const MIN = this.props.minDays;


        // 시작 끝 둘다 지정되고
        if( range.to && range.from ){
            const MT = new Date( this.state.to );
            const MF = new Date( this.state.from );

            const DIFF = moment(range.to).diff( moment(range.from), 'd' );

            // 최대 지정가능 날짜보다 크면 자르기
            if( DIFF >= MAX ){

                if( this.state.from && date.getTime() < MF.getTime() )
                    range = { from:date, to:moment(date).add( MAX-1, 'd').toDate() };
                else if( this.state.to && date.getTime() > MT.getTime() )
                    range = { from:moment(date).add(-(MAX - 1), 'd').toDate(), to:date };
                else
                    range = DateUtils.addDayToRange( moment(range.from).add( MAX-1, 'd' ).toDate(), this.state );

            // 작으면 붙여주기
            }else if( DIFF < MIN-1 ){
                range.to = moment(range.from).add( MIN-1, 'd').toDate();
            }
        }
        else
        {
            range.to = moment(range.from).add( MIN-1, 'd').toDate();
        }

        this.setState(range);
    }

    confirm(){
        this.setState({ onCalendar:false });
    }

    render() {

        const { from, to } = this.state;
        const modifiers = { start: from, end: to };


        return <div className="cl-opt-sec cl-flex cl-opt-sec--daterange">
            <div className="cl-label">기간</div>
            <div className="cl-daterange-output" onClick={ ()=> this.setState({ onCalendar: true })}>
                <div>{moment(this.state.from).format('YYYY-MM-DD')}</div>
                <span className="cl-divider">-</span>
                <div>{moment(this.state.to).format('YYYY-MM-DD')}</div>
            </div>

            {this.state.onCalendar&&
                <div className="cl-date-range">
                    <DayPicker
                        selectedDays={[from, { from, to }]}
                        modifiers={modifiers}
                        disabledDays={[{
                            before: new Date( this.state.start ),
                            after: new Date( this.state.end )
                        }]}
                        onDayClick={ day => this.handleDayClick( day ) }
                    />

                    <button className="cl-ok" onClick={()=>this.confirm()}>OK</button>
                </div>
            }

        </div>
    }

}



export default DatePeriod;