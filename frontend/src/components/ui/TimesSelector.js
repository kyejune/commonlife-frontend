import React, {Component} from 'react';
import DateSelector from "./DateSelector";

class TimesSelector extends Component {

    constructor(props) {
        super(props);

        let shhmm = this.props.shhmm || '0825';
        let ehhmm = this.props.ehhmm || '2005';

        this.state = {
            shh: parseInt(shhmm.substr(0,2)),
            smm: parseInt(shhmm.substr(2,2)),
            ehh: parseInt(ehhmm.substr(0,2)),
            emm: parseInt(ehhmm.substr(2,2)),
        }

        console.log( "times selector:", this.state );
    }

    componentWillReceiveProps(nextProps) {

        if( nextProps.shhmm && nextProps.ehhmm ) {
            let shhmm = nextProps.shhmm;
            let ehhmm = nextProps.ehhmm;

            this.state = {
                shh: parseInt(shhmm.substr(0,2)),
                smm: parseInt(shhmm.substr(2,2)),
                ehh: parseInt(ehhmm.substr(0,2)),
                emm: parseInt(ehhmm.substr(2,2)),
            }
        }
    }

    onChangeHour( isStart, value ){
        if( isStart ) this.setState({ shh: value });
        else          this.setState({ ehh: value });
    }

    onChangeMinute( isStart, value ){
        if( isStart ) this.setState({ smm: value });
        else          this.setState({ emm: value });
    }


    render() {
        return <div>
            <div className="cl-time-selector">
                <label>시작시간</label>

                <select value={this.state.shh} onChange={ e => this.onChangeHour( true, e.target.value ) }>
                    {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24].map(hour => {
                        return <option key={hour} value={hour}>{('0'+hour).substr(-2, 2)}</option>
                    })}
                </select>

                <select value={this.state.smm} onChange={ e => this.onChangeMinute( true, e.target.value ) }>
                    {[0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55].map(minute => {
                        return <option key={minute} value={minute}>{('0'+minute).substr(-2, 2)}</option>
                    })}
                </select>
            </div>

            <div className="cl-time-selector">
                <label>종료시간</label>

                <select value={this.state.ehh} onChange={ e => this.onChangeHour( false, e.target.value ) }>
                    {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24].map(hour => {
                        return <option key={hour} value={hour}>{('0'+hour).substr(-2, 2)}</option>
                    })}
                </select>

                <select value={this.state.emm} onChange={ e => this.onChangeMinute( false, e.target.value ) }>
                    {[0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55].map(minute => {
                        return <option key={minute} value={minute}>{('0'+minute).substr(-2, 2)}</option>
                    })}
                </select>
            </div>



            {/* 테스트 */}
            <DateSelector selected={1} disabled={[5]}/>

        </div>
    }
}


export default TimesSelector;
