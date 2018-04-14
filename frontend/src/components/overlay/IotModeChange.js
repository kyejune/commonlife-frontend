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
			status: Store.modeModal.targetValue,
		};
	}

	setProgress () {
		this.setState( { step: 'progress' } );
		let { targetValue } = this.props;

		Iot.changeIotMode( Store.modeModal.mode, targetValue, ()=> {
			this.setState( { step: 'done' } );
		} );
	}

	doneProgress () {
		Store.modeModal = null;

		Iot.getMode();
	}

	closeProgress () {
		Store.modeModal = null;
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
							<IconLoader src={`icons/${Store.modeModal.targetIcon}.png`} />

							<span>{Store.modeModal.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.modeModal.targetName}로 바로 전환합니다.
						</p>
					</div>

					<div className="cl-iot-mode-progress__bottom">
						<button onClick={()=>this.closeProgress()}
								className="cl-iot-mode-progress__btn-link">
							취소
						</button>
						<button onClick={()=>this.setProgress()}
								className="cl-flex cl-iot-mode-progress__btn-primary">
							<span>확인</span>
						</button>
					</div>
				</div>;
			}

			else if( this.state.step === 'progress' ) {
				return <div className="cl-iot-mode-progress__progress">
					<div className="cl-iot-mode-progress__middle">
						<div className="cl-iot-mode-progress__button cl-iot-mode__button">
							<IconLoader src={`icons/${Store.modeModal.targetIcon}.png`} />

							<span>{Store.modeModal.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.modeModal.targetName}로 바로 전환합니다.
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
							<IconLoader src={`icons/${Store.modeModal.targetIcon}.png`} />

							<span>{Store.modeModal.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.modeModal.targetName}로 전환되었습니다.
						</p>

						<div className="cl-iot-mode-progress__on-bullet">
							<IotConnectionProgress className="cl--done"/>
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
							<IconLoader src={`icons/${Store.modeModal.targetIcon}.png`} />

							<span>{Store.modeModal.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.modeModal.targetName}를 해제합니다.
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
							<IconLoader src={`icons/${Store.modeModal.targetIcon}.png`} />

							<span>{Store.modeModal.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.modeModal.targetName}를<br/>해제 합니다.
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
							<IconLoader src={`icons/${Store.modeModal.targetIcon}.png`} />

							<span>{Store.modeModal.targetName}</span>
						</div>
						<p className="cl-iot-mode-progress__paragraph">
							{Store.modeModal.targetName}가 해제되었습니다.
						</p>

						<div className="cl-iot-mode-progress__off-bullet">
                            <IotConnectionProgress className="cl--done"/>
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