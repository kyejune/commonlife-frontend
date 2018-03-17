import React, { Component } from 'react';
import Iot from 'scripts/iot';
import SampleSrc from 'images/io-t-icon-1@3x.png';
import Store from "../../scripts/store";

import IotConnectionProgress from "../ui/IotConnectionProgress";


class IotProgressOverlay extends Component {

	componentDidMount () {
		let { cmd, name, type, targetValue } = this.props;

		Iot.setIotDevice( name, targetValue, ()=> {
			Store.ipo = null;
		} );

	}


	render () {
		return <div className="cl-iot-progress-overlay">
			<div className="cl-iot-progress-overlay__top">
				<div className="cl-iot-progress-overlay__icon">
					<img src={SampleSrc} alt=""/>
				</div>
				<p>중앙 보일러 - 설정온도 12°C</p>
			</div>
			<h3>변경사항을<br/>즉시 적용합니다.</h3>
			<IotConnectionProgress/>
		</div>
	}
}


export default IotProgressOverlay