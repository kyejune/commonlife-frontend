import React, { Component } from 'react';
import Store from "../../scripts/store";
import {ModeChanges} from "../../scripts/iot";
import ImageLoader from 'react-imageloader';
import IconLoader from "./IconLoader";


// import IotIcModeOut from 'images/io-t-icon-1@3x.png'; // 외출모드
// import IotIcModeSleep from 'images/io-t-icon-2@3x.png'; // 취침모드
// import IotIcModeHoliday from 'images/io-t-icon-3@3x.png'; // 휴가모드
// import IotIcModeSave from 'images/io-t-icon-14@3x.png'; // 절약모드

class IotBtnLg extends Component {

	constructor ( props ) {
		super( props );

        console.log( props.modeData );
	}

	viewProgress( modeInfo ){
		Store.imc = { targetValue:true, ...modeInfo };
	}

	// IotIcMode = () => {
	// 	const srcMap = { "CM01101":IotIcModeSleep, "CM01102":IotIcModeHoliday, "CM01103": IotIcModeOut, "CM01104": IotIcModeSave };
    //
	// 	return <img src={srcMap[this.props.modeData.type]} alt=""/>;
	// };

	render () {

		return (

			<button type="button"
					onClick={ ()=> this.viewProgress( ModeChanges.outMode ) }
					className={( this.props.modeData.execYn === "Y" ? 'cl-iot-mode__button--active' : 'cl-iot-mode__button' )}>
                <IconLoader src={`icons/${this.props.modeData.imgSrc}.png`} />

				<span>{this.props.modeData.modeNm}</span>
			</button>

		)
	}
}

export default IotBtnLg;
