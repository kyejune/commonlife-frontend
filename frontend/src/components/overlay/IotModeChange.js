import React, { Component } from 'react';
import Iot from 'scripts/iot';
import Store from "../../scripts/store";
import IconLoader from "../ui/IconLoader";
import IotConnectionProgress from "../ui/IotConnectionProgress";

class IotModeChange extends Component {

	constructor ( props ) {
		super( props );

		this.state = {
			step: 'ready',
			status: Store.imc.targetValue,
		};
	}

	setProgress () {
		this.setState( { step: 'progress' } );

		console.log( this.state.step );

		let { cmd, name, type, targetValue } = this.props;

		console.log( Store.imc.targetValue );

		Iot.changeIotMode( name, targetValue, ()=> {
			this.setState( { step: 'done' } );
		} );
	}

	doneProgress () {
		Store.imc = null;

		Iot.getIot();
	}

	closeProgress () {
		Store.imc = null;
	}

	iotModeChangeTitle () {
		// IoT 모드 실행
		if( this.state.status === true ) {
			return <div className="cl-iot-mode-progress__top-on">
				<h3>실행</h3>
			</div>
		}

		// IoT 모드 종료
		else {
			return <div className="cl-iot-mode-progress__top-off">
				<h3>종료</h3>
			</div>
		}
	}

	status () {
		// IoT 모드 실행
		if( this.state.status === true ) {
			if( this.state.step === 'ready' ) {
				return <div className="cl-iot-mode-progress__ready">
					<div className="cl-iot-mode-progress__middle">
						<div className="cl-iot-mode-progress__button cl-iot-mode__button">
							<IconLoader src={`icons/${Store.imc.targetIcon}.png`} />

							<span>{Store.imc.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.imc.targetName} 설정시 보안설정이 강화되며,<br/>귀가시 자동해제 됩니다.
						</p>
					</div>

					<div className="cl-iot-mode-progress__bottom">
						<button onClick={()=>this.closeProgress()}
								className="cl-iot-mode-progress__btn-link">
							취소
						</button>
						<button onClick={()=>this.setProgress()}
								className="cl-iot-mode-progress__btn-primary">
							확인
						</button>
					</div>
				</div>;
			}

			else if( this.state.step === 'progress' ) {
				return <div className="cl-iot-mode-progress__progress">
					<div className="cl-iot-mode-progress__middle">
						<div className="cl-iot-mode-progress__button cl-iot-mode__button">
							<IconLoader src={`icons/${Store.imc.targetIcon}.png`} />

							<span>{Store.imc.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.imc.targetName}를<br/>즉시 실행합니다.
						</p>

						<div className="cl-iot-mode-progress__on-bullet">
							<IotConnectionProgress/>
						</div>
					</div>

					<div className="cl-iot-mode-progress__bottom">
						<button onClick={()=>this.closeProgress()}
								className="cl-iot-mode-progress__btn-link">
							취소
						</button>
						<button className="cl-iot-mode-progress__btn-primary--disabled">
							확인
						</button>
					</div>
				</div>;
			}

			else if( this.state.step === 'done' ) {
				return <div className="cl-iot-mode-progress__done">
					<div className="cl-iot-mode-progress__middle">
						<div className="cl-iot-mode-progress__button cl-iot-mode__button--active cl-iot-mode__button">
							<IconLoader src={`icons/${Store.imc.targetIcon}.png`} />

							<span>{Store.imc.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.imc.targetName}가<br/>적용되었습니다.
						</p>

						<div className="cl-iot-mode-progress__on-bullet">
							<IotConnectionProgress/>
						</div>
					</div>
					<div className="cl-iot-mode-progress__bottom">
						<button onClick={()=>this.doneProgress()}
								className="cl-iot-mode-progress__btn-primary">
							확인
						</button>
					</div>
				</div>;
			}

			else {
				return '';
			}
		}

		// IoT 모드 종료
		else {
			if( this.state.step === 'ready' ) {
				return <div className="cl-iot-mode-progress__ready">
					<div className="cl-iot-mode-progress__middle">
						<div className="cl-iot-mode-progress__button cl-iot-mode__button">
							<IconLoader src={`icons/${Store.imc.targetIcon}.png`} />

							<span>{Store.imc.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.imc.targetName} 종료시 보안설정이 해제되며,<br/>일상 모드로 자동 전환됩니다.
						</p>
					</div>

					<div className="cl-iot-mode-progress__bottom">
						<button onClick={()=>this.closeProgress()}
								className="cl-iot-mode-progress__btn-link">
							취소
						</button>
						<button onClick={()=>this.setProgress()}
								className="cl-iot-mode-progress__btn-dark">
							확인
						</button>
					</div>
				</div>;
			}

			else if( this.state.step === 'progress' ) {
				return <div className="cl-iot-mode-progress__progress">
					<div className="cl-iot-mode-progress__middle">
						<div className="cl-iot-mode-progress__button cl-iot-mode__button">
							<IconLoader src={`icons/${Store.imc.targetIcon}.png`} />

							<span>{Store.imc.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.imc.targetName}를<br/>즉시 종료합니다.
						</p>

						<div className="cl-iot-mode-progress__off-bullet">
							<IotConnectionProgress/>
						</div>
					</div>
					<div className="cl-iot-mode-progress__bottom">
						<button onClick={()=>this.closeProgress()}
								className="cl-iot-mode-progress__btn-link">
							취소
						</button>
						<button className="cl-iot-mode-progress__btn-dark--disabled">
							확인
						</button>
					</div>
				</div>;
			}

			else if( this.state.step === 'done' ) {
				return <div className="cl-iot-mode-progress__done">
					<div className="cl-iot-mode-progress__middle">
						<div className="cl-iot-mode-progress__button cl-iot-mode__button--inactive cl-iot-mode__button">
							<IconLoader src={`icons/${Store.imc.targetIcon}.png`} />

							<span>{Store.imc.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.imc.targetName}<br/>종료되었습니다.
						</p>

						<div className="cl-iot-mode-progress__off-bullet">
							<IotConnectionProgress/>
						</div>
					</div>
					<div className="cl-iot-mode-progress__bottom">
						<button onClick={()=>this.doneProgress()}
								className="cl-iot-mode-progress__btn-dark">
							확인
						</button>
					</div>
				</div>;
			}

			else {
				return '';
			}
		}
	}

	render () {

		return <div className="cl-iot-mode-progress">
			{this.iotModeChangeTitle()}

			<div className="cl-iot-mode-progress__content">
				{this.status()}
			</div>
		</div>
	}
}


export default IotModeChange