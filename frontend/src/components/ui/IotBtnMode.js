import React, { Component } from 'react';
import Store from "../../scripts/store";
import {ModeChanges} from "../../scripts/iot";
import ImageLoader from 'react-imageloader';
import IconLoader from "./IconLoader";

class IotBtnMode extends Component {

	viewProgress( modeInfo ){
		Store.modeModal = {
			targetValue:( this.props.modeData.execYn !== "Y" ),
			targetName: (this.props.modeData.modeNm),
			targetIcon: (this.props.modeData.imgSrc),
			...modeInfo,
			...this.props.modeData
		};
	}

	render () {

		return (

			<button type="button"
					onClick={ ()=> this.viewProgress( ModeChanges.outMode ) }
					className={( this.props.modeData.execYn === "Y" ? 'cl-iot-mode__button--active' : 'cl-iot-mode__button' )}>
                <IconLoader src={this.props.modeData.imgSrc} />

				<span>{this.props.modeData.modeNm}</span>
			</button>

		)
	}
}

export default IotBtnMode;
