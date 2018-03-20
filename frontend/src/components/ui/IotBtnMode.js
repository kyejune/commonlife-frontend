import React, { Component } from 'react';
import Store from "../../scripts/store";
import {ModeChanges} from "../../scripts/iot";
import ImageLoader from 'react-imageloader';
import IconLoader from "./IconLoader";

class IotBtnLg extends Component {

	viewProgress( modeInfo ){
		Store.imc = {
			targetValue:( this.props.modeData.execYn !== "Y" ),
			targetName: (this.props.modeData.modeNm),
			targetIcon: (this.props.modeData.imgSrc),
			...modeInfo
		};
	}

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
