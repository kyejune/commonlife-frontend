/* HomeIoT.jsx */
import React, { Component } from 'react';
import Store from 'scripts/store';
import Iot from 'scripts/iot';
import DrawerContentHolder from "components/drawers/DrawerContentHolder";
import { Drawer } from 'react-md';
import { Link } from 'react-router-dom';
import DrawerIotControlList from "components/drawers/DrawerIotControlList";
import DrawerIotEditList from "components/drawers/DrawerIotEditList";
import WithTitle from 'components/ui/WithTitle';
import IotBtnLg from 'components/ui/IotBtnLg';
import Modal from 'components/overlay/Modal';

import IotIcAddMode from 'images/combined-shape@3x.png';
import IotIcModeOut from 'images/io-t-icon-1@3x.png'; // 외출모드
import IotIcModeSleep from 'images/io-t-icon-2@3x.png'; // 취침모드
import IotIcModeHoliday from 'images/io-t-icon-3@3x.png'; // 휴가모드
import IotIcModeSave from 'images/io-t-icon-14@3x.png'; // 절약모드

import IotIcAdd from 'images/combined-shape-plus@3x.png';
import IotProgressOverlay from "../overlay/IotProgressOverlay";

class HomeIoT extends Component {

	constructor ( props ) {
		super( props );

		this.state = {
			drawer: Store.drawer,
			isModeOut: false,
			isModeSleep: false,
			isModeHoliday: false,
			isModeSave: false,
		}
	}

	componentDidMount () {
		Iot.getIot( data =>{} );
		this.updateRoute();
	}

	componentDidUpdate ( prevProps ) {
		if( this.props.location !== prevProps.location )
			this.updateRoute();
	}

	updateRoute () {

		// id위치에 특정 단어가 들어올때 처리
		if( this.props.match.params.id === 'control' )
			Store.drawer.push( 'iot-control-list' );

		// id위치에 일반적으로 숫자가 들어오면 상세보기
		// else if( this.props.match.params.id  )
		// 	Store.drawer.push( 'reservation-detail' );

		else {
			Store.drawer.length = 0;
		}

		// IoT 기기제어 목록
		if( this.props.match.params.action === 'control' )
			Store.drawer.push( 'iot-control-list' );

		// IoT Mode 편집
		if( this.props.match.params.action === 'control-mode' )
			Store.drawer.push( 'iot-control-mode' );

		this.setState( {
			drawer: Store.drawer || [],
		} );
	}

	modeOutSelect = ()=> {
		this.setState( {
			isModeOut: !this.state.isModeOut,
			isModeSleep: false,
			isModeHoliday: false,
			isModeSave: false,
		} );
	};

	modeSleepSelect = ()=> {
		this.setState( {
			isModeOut: false,
			isModeSleep: !this.state.isModeSleep,
			isModeHoliday: false,
			isModeSave: false
		} );
	};

	modeHolidaySelect = ()=> {
		this.setState( {
			isModeOut: false,
			isModeSleep: false,
			isModeHoliday: !this.state.isModeHoliday,
			isModeSave: false
		} );
	};

	modeSaveSelect = ()=> {
		this.setState( {
			isModeOut: false,
			isModeSleep: false,
			isModeHoliday: false,
			isModeSave: !this.state.isModeSave
		} );
	};


	render () {
		return <div>
			<div className="cl-home-iot">
				<div className="cl-fitted-box">
					<WithTitle titleName="MODE"/>

					<div className="cl-iot-mode">
						<ul className="cl-iot-mode__list">
							<li className="cl-iot-mode__list-item">
								<button type="button" onClick={ this.modeOutSelect }
										className={( this.state.isModeOut ? 'cl-iot-mode__button--active' : 'cl-iot-mode__button' )}>
									<img src={IotIcModeOut} alt=""/>
									<span>외출모드</span>
								</button>
							</li>
							<li className="cl-iot-mode__list-item">
								<button type="button" onClick={ this.modeSleepSelect }
										className={( this.state.isModeSleep ? 'cl-iot-mode__button--active' : 'cl-iot-mode__button' )}>
									<img src={IotIcModeSleep} alt=""/>
									<span>취침모드</span>
								</button>
							</li>
							<li className="cl-iot-mode__list-item">
								<button type="button" onClick={ this.modeHolidaySelect }
										className={( this.state.isModeHoliday ? 'cl-iot-mode__button--active' : 'cl-iot-mode__button' )}>
									<img src={IotIcModeHoliday} alt=""/>
									<span>휴가모드</span>
								</button>
							</li>
							<li className="cl-iot-mode__list-item">
								<button type="button" onClick={ this.modeSaveSelect }
										className={( this.state.isModeSave ? 'cl-iot-mode__button--active' : 'cl-iot-mode__button' )}>
									<img src={IotIcModeSave} alt=""/>
									<span>절약모드</span>
								</button>
							</li>
							<li className="cl-iot-mode__list-item">
								<Link to={'/iot/control-mode'} className="cl-iot-mode__button cl-iot-mode__add-mode-button">
									<img src={IotIcAddMode} alt=""/>
								</Link>
							</li>
						</ul>
					</div>

					<WithTitle titleName="My IoT"/>

					<div className="cl-my-iot">
						<ul className="h-group cl-my-iot__list">
							<li className="col cl-my-iot__list-item">
								<IotBtnLg IotBtnType="typeDefault"
								          IotBtnTitle="12"
								          IotBtnParagraph="에너지 사용량"
								          IotBtnLabel="표준"
								          IotBtnText="340"/>
							</li>
							<li className="col cl-my-iot__list-item">
								<IotBtnLg IotBtnType="typeToggle"
								          IotBtnTitle="거실조명"
										  IotBtnParagraph="조명이 켜져있습니다"
										  IotBtnLabel="스위치"
										  IotBtnText="Active"/>
							</li>
							<li className="col cl-my-iot__list-item">
								<IotBtnLg IotBtnType="typeCustom"
								          IotBtnTitle="둘만의시간"
										  IotBtnLabel="스위치"
										  IotBtnText="Active"/>
							</li>
							<li className="col cl-my-iot__list-item">
								<IotBtnLg IotBtnType="typeSet"
								          IotBtnTitle="안방보일러"
										  IotBtnParagraph="설정온도 30℃"
										  IotBtnLabel="높음"
										  IotBtnText="34"/>
							</li>
							<li className="col cl-my-iot__list-item">
								<Link to={'/iot/control'}>
									<div className="cl-my-iot__button cl-my-iot__button--add">
										<img src={IotIcAdd} alt=""/>
									</div>
								</Link>
							</li>
						</ul>
					</div>

					<button type="button" className="cl-iot__help-button">기기 제어에 어려움이 있으신가요?</button>
				</div>
			</div>

			{/* IoT Mode 편집 */}
			<Drawer {...Store.customDrawerProps} renderNode={document.querySelector( '.App' )}
					visible={this.state.drawer.indexOf( 'iot-control-mode' ) >= 0}>
				<DrawerContentHolder back title="Mode 편집">
					<DrawerIotEditList/>
				</DrawerContentHolder>
			</Drawer>

			{/* IoT 기기제어 목록 */}
			<Drawer {...Store.customDrawerProps} renderNode={document.querySelector( '.App' )}
					visible={this.state.drawer.indexOf( 'iot-control-list' ) >= 0}>
				<DrawerContentHolder back title="IoT 제어">
					<DrawerIotControlList/>
				</DrawerContentHolder>
			</Drawer>

		</div>
	}
}

export default HomeIoT;