import React, {Component} from 'react';
import DateSelector from "./DateSelector";
import Store from "../../scripts/store";

class TimesSelector extends Component {

    constructor(props) {
        super(props);

        const {aplyStartTime, aplyEndTime, monYn, tueYn, wedYn, thuYn, friYn, satYn, sunYn} = props;
        let shhmm = (aplyStartTime || '08:00').split(':');
        let ehhmm = (aplyEndTime || '09:00').split(':');

        this.state = {
            shh: shhmm[0],
            smm: shhmm[1],
            ehh: ehhmm[0],
            emm: ehhmm[1],
            dates: DateSelector.str2IndexValues(monYn, tueYn, wedYn, thuYn, friYn, satYn, sunYn),
        }

        console.log("times selector:", this.state);
    }

    // componentWillReceiveProps(nextProps) {
    //
    //     if( nextProps.shhmm && nextProps.ehhmm ) {
    //         let shhmm = nextProps.shhmm;
    //         let ehhmm = nextProps.ehhmm;
    //
    //         this.state = {
    //             shh: parseInt(shhmm.substr(0,2)),
    //             smm: parseInt(shhmm.substr(2,2)),
    //             ehh: parseInt(ehhmm.substr(0,2)),
    //             emm: parseInt(ehhmm.substr(2,2)),
    //         }
    //     }
    // }

    onApply=()=> {
        const {shh, smm, ehh, emm, dates} = this.state;
        let shhmm = [shh, smm].join(':');
        let ehhmm = [ehh, emm].join(':');
        let datesVal = DateSelector.indexes2ObjectValue(dates);

        if( this.props.callback )
            this.props.callback( shhmm, ehhmm, datesVal );

        Store.popDrawer();
    }

    onChangeHour(isStart, value) {
        if (isStart) this.setState({shh: value});
        else this.setState({ehh: value});
    }

    onChangeMinute(isStart, value) {
        if (isStart) this.setState({smm: value});
        else this.setState({emm: value});
    }

    onChangeDate = (idxs) => {
        this.setState({dates: idxs});
    }

    render() {
        return <div>
            <div className="pl-1em pr-1em pt-2em">

                <h4 className="cl-bold">특정 시간 선택</h4>
                <div className="cl-time-selector">
                    <label>시작시간</label>

                    <select value={this.state.shh} onChange={e => this.onChangeHour(true, e.target.value)}>
                        {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24].map(hour => {
                            let val = ('0' + hour).substr(-2, 2);
                            return <option key={hour} value={val}>{val}</option>
                        })}
                    </select>

                    <select value={this.state.smm} onChange={e => this.onChangeMinute(true, e.target.value)}>
                        {[0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55].map(minute => {
                            let val = ('0' + minute).substr(-2, 2);
                            return <option key={minute} value={val}>{val}</option>
                        })}
                    </select>
                </div>

                <div className="cl-time-selector">
                    <label>종료시간</label>

                    <select value={this.state.ehh} onChange={e => this.onChangeHour(false, e.target.value)}>
                        {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24].map(hour => {
                            let val = ('0' + hour).substr(-2, 2);
                            return <option key={hour} value={val}>{val}</option>
                        })}
                    </select>

                    <select value={this.state.emm} onChange={e => this.onChangeMinute(false, e.target.value)}>
                        {[0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55].map(minute => {
                            let val = ('0' + minute).substr(-2, 2);
                            return <option key={minute} value={val}>{val}</option>
                        })}
                    </select>
                </div>


                <h4 className="mt-2em cl-bold mb-1em">요일 선택</h4>
                <DateSelector selected={this.state.dates} onChange={this.onChangeDate}/>
            </div>

            <footer className="cl-flex cl-opts__footer">
                <button className="ml-auto cl-flex mr-1em" onClick={ this.onApply }>
                    <span className="color-primary ml-03em">적용</span>
                </button>
            </footer>

        </div>
    }
}


export default TimesSelector;
