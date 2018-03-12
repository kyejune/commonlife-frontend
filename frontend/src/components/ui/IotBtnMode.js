import React, { Component } from 'react';

import IotIcModeOut from 'images/io-t-icon-1@3x.png'; // 외출모드
import IotIcModeSleep from 'images/io-t-icon-2@3x.png'; // 취침모드
import IotIcModeHoliday from 'images/io-t-icon-3@3x.png'; // 휴가모드
import IotIcModeSave from 'images/io-t-icon-14@3x.png'; // 절약모드

class IotBtnLg extends Component {

	constructor ( props ) {
		super( props );
	}

	IotIcMode = () => {
		const srcMap = { out:IotIcModeOut, sleep: IotIcModeSleep, holiday: IotIcModeHoliday, save: IotIcModeSave };

		return <img src={srcMap[this.props.modeData.type]} alt=""/>;
	};

	render () {

		return (

			<button type="button" onClick={ this.modeOutSelect }
					className={( this.props.modeData.status === "on" ? 'cl-iot-mode__button--active' : 'cl-iot-mode__button' )}>
				{this.IotIcMode()}
				<span>{this.props.modeData.name}</span>
			</button>

		)
	}
}

export default IotBtnLg;
