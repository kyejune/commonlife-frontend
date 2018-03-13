import React, { Component } from 'react';
import Iot from 'scripts/iot';
import Store from "../../scripts/store";
import { ModeChanges } from "../../scripts/iot";

class IotModeChange extends Component {

	componentDidMount () {
		let { cmd, name, type, targetValue } = this.props;

		Iot.setIotMode( name, targetValue, ()=> {
			Store.imc = null;
		} );
	}

	viewProgress ( modeInfo ) {
		let { cmd, name, type, targetValue } = this.props;
		const btn = document.querySelector( '.cl-iot-mode-progress__btn-primary' );

		btn.disabled = true;

		Store.imc = { targetValue: true, ...modeInfo };

		Iot.changeIotMode( name, targetValue, ()=> {
			Store.imc = null;
		} );
	}

	closeProgress () {
		Store.imc = null;
	}

	render () {
		return <div className="cl-iot-mode-progress">
			<div className="cl-iot-mode-progress__top"><h3>실행</h3></div>
			<div className="cl-iot-mode-progress__content">
				<div className="cl-iot-mode-progress__bottom">
					<button onClick={()=>this.closeProgress()}
						className="cl-iot-mode-progress__btn-link">
						취소
					</button>
					<button onClick={()=>this.viewProgress( ModeChanges.outMode )}
							className="cl-iot-mode-progress__btn-primary">
						확인
					</button>
				</div>
			</div>
		</div>
	}
}


export default IotModeChange