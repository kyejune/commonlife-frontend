import React, {Component} from 'react';
import DateSelector from "./DateSelector";
import classNames from "classnames";
import Store from "../../scripts/store";

class TimeSelector extends Component {

    constructor(props) {
        super(props);

        const {spcTime, monYn, tueYn, wedYn, thuYn, friYn, satYn, sunYn} = props;
        let hhmm = (spcTime || '08:00').split(':');


        this.state = {
            am: hhmm[0] <= 12,
            hour: ( hhmm[0] > 12 ? hhmm[0] - 12 : hhmm[0] ),
            minute: hhmm[1],
            dates: DateSelector.str2IndexValues(monYn, tueYn, wedYn, thuYn, friYn, satYn, sunYn),
        }
    }

    // componentWillReceiveProps(nextProps) {
    //     // this.setState({
    //     //     am: nextProps.am,
    //     //     hour: nextProps.hour,
    //     //     minute: nextProps.minute,
    //     // })
    // }

    onApply=()=> {
        let {am, hour, minute} = this.state;

        if (am.toString() === 'false') {
            hour = parseInt(hour) + 12;
        }

        // 전달할 데이터
        let spcTime = [hour, minute].join(':');
        let dates = DateSelector.indexes2ObjectValue(this.state.dates);

        if( this.props.callback )
            this.props.callback( spcTime, dates );

        Store.popDrawer();
    }


    onChangeAm = (event) => {
        this.setState({am: event.target.value});
    }

    onChangeHour = (event) => {
        this.setState({hour: event.target.value});
    }

    onChangeMinute = (event) => {
        this.setState({minute: event.target.value});
    }

    onChangeDate = (idxs) => {
        this.setState({dates: idxs});
    }


    render() {
        return <div>

            <div className="pl-1em pr-1em pt-2em">

                <h4 className="cl-bold">특정 시간 선택</h4>
                <div className="cl-time-selector">

                    <select value={this.state.am} onChange={this.onChangeAm}>
                        <option value={true}>오전</option>
                        <option value={false}>오후</option>
                    </select>

                    <select value={this.state.hour} onChange={this.onChangeHour}>
                        {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12].map(hour => {
                            let val = ('0' + hour).substr(-2, 2);
                            return <option key={hour} value={val}>
                                {val}
                            </option>
                        })}
                    </select>

                    <select value={this.state.minute} onChange={this.onChangeMinute}>
                        {[0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55].map(minute => {
                            let val = ('0' + minute).substr(-2, 2);
                            return <option key={minute} value={val}>
                                {val}
                            </option>
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


export default TimeSelector;
