import React, {Component} from 'react';

class TimeSelector extends Component {

    constructor(props) {
        super(props);
        this.state = {
            am: 0,
            hour: this.props.hour || 1,
            minute: this.props.minute || 0,
        }
    }

    componentWillReceiveProps( nextProps ){
        this.setState({
            am: nextProps.am,
            hour: nextProps.hour,
            minute: nextProps.minute,
        })
    }


    onChangeAm=( event )=>{
        this.setState({ am:event.target.value });
    }

    onChangeHour=( event )=>{
        this.setState({ hour:event.target.value });
    }

    onChangeMinute=( event )=>{
        this.setState({ minute:event.target.value });
    }



    render() {
        return <div><div className="cl-time-selector">

            <select value={this.state.am} onChange={ this.onChangeAm }>
                <option value="0">오전</option>
                <option value="1">오후</option>
            </select>

            <select value={this.state.hour} onChange={ this.onChangeHour }>
                {[1,2,3,4,5,6,7,8,9,10,11,12].map(hour=>{
                    return <option key={hour} value={hour}>
                        { ('0' + hour ).substr(-2, 2)}
                        </option>
                })}
            </select>

            <select value={this.state.minute} onChange={ this.onChangeMinute }>
                {[0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55].map( minute =>{
                    return <option key={minute} value={minute}>
                        { ('0' + minute).substr(-2,2)}
                        </option>
                })}
            </select>

        </div></div>
    }
}


export default TimeSelector;
