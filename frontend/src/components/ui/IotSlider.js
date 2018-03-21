import React, {Component} from 'react';
// import Tooltip from 'rc-tooltip';
import Tooltip from 'rc-tooltip';
import Slider, {Handle, createSliderWithTooltip} from 'rc-slider';
import classNames from 'classnames';


class IotSlider extends Component {

    constructor(props) {
        super(props);
        this.state = {
            value: this.props.value,
            isDragging: false,
        }
    }

    onBeforeChange = () => {
        this.setState({ isDragging: true });
    }

    onChange = (value) => {
        this.setState({value: value});
    }

    onAfterChange = () => {
        this.setState({ isDragging: false });
    }


    render() {

        const {min, max, unit} = this.props;
        const {value} = this.state;
        const tl = (value - min) / (max - min) * 100 + '%';
        console.log('tl:', min, max, value, tl);

        return <div className={"cl-iot-slider cl-flex " + this.props.className}>
            <div className="cl-iot-slider__min">{min}°C</div>

            <Slider dots step={1} min={min} max={max} defaultValue={value}
                    className="cl-slider"
                    onBeforeChange={this.onBeforeChange}
                    onChange={this.onChange}
                    onAfterChange={this.onAfterChange}>
				<span className="cl-handle" style={{left: tl }}>
					<span className={ classNames( 'cl-handle-mark', { 'cl--hide': !this.state.isDragging } ) } >
					    <span className="cl-handle-mark-bg"/>
					    <span className="cl-handle-mark-value">{this.state.value}</span>
					</span>
				</span>
            </Slider>

            <div className="cl-iot-slider__max">{max}°C</div>
        </div>

    }
}

export default IotSlider;