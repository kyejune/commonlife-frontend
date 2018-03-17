import React, { Component } from 'react';

class IotConnectionProgress extends Component {

	render () {

		return (

			<div className="cl-iot-connect-progress">
				<div className="cl-iot-connect-progress__icon-start"/>
				<p className="cl-iot-connect-progress__text-start">Connecting…</p>
				<span className="cl-iot-connect-progress__box"/>
				<span className="cl-iot-connect-progress__circle-end"/>
				<div className="cl-iot-connect-progress__icon-done"/>
				<p className="cl-iot-connect-progress__text-done">Complete!</p>
			</div>

		)
	}
}

export default IotConnectionProgress;
