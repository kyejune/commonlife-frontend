import React, { Component } from 'react';
// import Tooltip from 'rc-tooltip';
import Tooltip from 'rc-tooltip';
import Slider, { Handle, createSliderWithTooltip } from 'rc-slider';

// const SliderWithTooltip = createSliderWithTooltip(Slider);

// const Handle = Slider.Handle;

// const handle = (props) => {
// 	const { value, dragging, index, ...restProps } = props;
// 	return (
// 		<div>A</div>
// 	);
// };

class IotSlider extends Component {

	constructor(props){
		super(props);
		this.state = {
			value: 3,
			max: 20,
			min: 0,
		}
	}

	onBeforeChange = ()=>{
		console.log( 'drag start');
	}

	onChange = ( value )=>{
		this.setState( { value: value });
	}

    onAfterChange = ()=>{
        console.log( 'drag end');
    }


    render() {

		const { min, max, value } = this.state;
		const tl = value/( max - min )*100 + '%';

		return <div className="cl-iot-slider">
			<div className="cl-iot-slider__min">{min}°C</div>
			<Slider dots step={1} min={ this.state.min } max={ this.state.max } defaultValue={ this.state.value }
					className="cl-slider"
                    onBeforeChange={ this.onBeforeChange }
					onChange={ this.onChange }
                	onAfterChange={ this.onAfterChange }>
				<span style={{ position:'absolute', left: tl }}>TIP{this.state.value}</span>
			</Slider>
			<div className="cl-iot-slider__max">{max}°C</div>
		</div>

	}
}

export default IotSlider;