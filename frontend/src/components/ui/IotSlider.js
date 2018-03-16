import React, { Component } from 'react';
// import Tooltip from 'rc-tooltip';
import Tooltip from 'rc-tooltip';
import Slider, { createSliderWithTooltip } from 'rc-slider';

const SliderWithTooltip = createSliderWithTooltip(Slider);

const Handle = Slider.Handle;

const handle = (props) => {
	const { value, dragging, index, ...restProps } = props;
	return (
		<Tooltip
			prefixCls="rc-slider-tooltip"
			overlay={value}
			visible={dragging}
			placement="top"
			key={index}
		>
			<Handle value={value} {...restProps} />
		</Tooltip>
	);
};

class IotSlider extends Component {

	render() {

		return <div className="cl-iot-slider">
			<div className="cl-iot-slider__min">5°C</div>
			<SliderWithTooltip dots step={1} min={0} max={20} defaultValue={3} handle={handle} className="cl-slider"/>
			<div className="cl-iot-slider__max">45°C</div>
		</div>

	}
}

export default IotSlider;