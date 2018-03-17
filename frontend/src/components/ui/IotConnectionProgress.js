import React, { Component } from 'react';

class IotConnectionProgress extends Component {

	render () {

		return (

			<div className="cl-iot-connect-progress">
				{/* 시작 */}
				<div className="cl-iot-connect-progress__icon-start"/>
				<p className="cl-iot-connect-progress__text-start">Connecting…</p>

				{/* 프로그레스바 */}
				<span className="cl-iot-connect-progress__box"/>

				{/* 완료 */}
				<div className="cl-iot-connect-progress__icon-done"/>
				<p className="cl-iot-connect-progress__text-done">Complete!</p>
			</div>

		)
	}
}

export default IotConnectionProgress;
