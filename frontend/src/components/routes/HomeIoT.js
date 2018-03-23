/* HomeIoT.jsx */
import React, { Component } from 'react';
import Store from 'scripts/store';
import Iot, { Modes, MyIots } from 'scripts/iot';
import { Drawer } from 'react-md';
import { Link } from 'react-router-dom';
import IotModeEditor from "components/drawers/iot/IotModeEditor";
import WithTitle from 'components/ui/WithTitle';
import IotBtnMode from 'components/ui/IotBtnMode';
import IotBtnLg from 'components/ui/IotBtnLg';

import IotIcAddMode from 'images/combined-shape@3x.png';

import IotIcAdd from 'images/combined-shape-plus@3x.png';
import {observer} from "mobx-react";
import DrawerWrapper from "../drawers/DrawerWrapper";
import IotDevice from "../drawers/iot/IotDevice";
import IotModeSetting from "../drawers/iot/IotModeSetting";
import IotSensor from "../drawers/iot/IotSensor";
import ChooseAddingIot from "../drawers/iot/ChooseAddingIot";
import IotDeviceCategory from "../drawers/iot/IotDeviceCategory";
import IotDeviceList from "../drawers/iot/IotDeviceList";
import IotSensorEditor from "../drawers/iot/IotSensorEditor";
import IotDeviceEditor from "../drawers/iot/IotDeviceEditor";
import IotSensorList from "../drawers/iot/IotSensorList";

class HomeIoT extends Component {

	constructor ( props ) {
		super( props );

		// this.state = {
		// 	// drawer: Store.drawer,
		// 	// mode: [],
		// }
	}

	componentDidMount () {
		Iot.getIotAll();
		this.updateRoute();
	}

	componentDidUpdate ( prevProps ) {
		if( this.props.location !== prevProps.location )
			this.updateRoute();
	}

	updateRoute ( data ) {

		const params = this.props.match.params;
		const compMap = {

			// Mode 추가 버튼 클릭
			mode:{
                mode:'iot-mode',
                sensor:'iot-sensor',
                device:'iot-device'
			},

			// MyIot중 상세 제어 페이지가 있는 녀석 클릭
			run:{
				run:'iot-device-detail',
			},

			// MyIot추가 버튼 클릭
			add:{
				add:'iot-add',
				scenario:'iot-mode-detail',
                sensor:'iot-sensor',
                device:'iot-device',
				category:'iot-device-category',
				'edit-sensor':'iot-sensor-editor',
				'edit-device':'iot-device-editor',
				'add-device':'iot-device-category',
				'add-sensor':'iot-sensor-list',
			},

			// Footer에 붙어있는 파란 ctrl버튼 클릭
			ctrl:{
				ctrl:'iot-device-category',
				device:'iot-device'
			}
		};


		const action = this.props.match.params.action;
		const map = compMap[action];

		Store.clearDrawer();
		if( action === undefined ) return;


		let prevSeg = params[p];
		for( var p in params ){
			let d = map[params[p]];
			if( RegExp(/\d+/).test(params[p])  ) d = map[prevSeg] + '-detail';
			if( d ) Store.pushDrawer( d );
            prevSeg = params[p];
		}
	}

	render () {

		return <div>
			<div className="cl-home-iot">
				<div className="cl-fitted-box">
					<WithTitle titleName="MODE"/>

					<div className="cl-iot-mode">
						<ul className="cl-iot-mode__list">
							{ Modes.map( ( m, index ) => {
								return (
									<li className="cl-iot-mode__list-item" key={index}>
										<IotBtnMode modeData={m}/>
									</li>
								)
							} ) }
							<li className="cl-iot-mode__list-item">
								<Link to={'/iot/mode'} className="cl-iot-mode__button cl-iot-mode__add-mode-button">
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
								<Link to="/iot/run/11">
								<IotBtnLg IotBtnType="typeSet"
								          IotBtnTitle="안방보일러"
										  IotBtnParagraph="설정온도 30℃"
										  IotBtnLabel="높음"
										  IotBtnText="34"/>
								</Link>
							</li>
							<li className="col cl-my-iot__list-item">
                                <Link to={ { pathname:'/iot/add' } } >
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
            <DrawerWrapper drawer="iot-mode" title="Mode 편집" back >
                <IotModeEditor/>
			</DrawerWrapper>

            {/* IoT 모드 설정 변경 || 모드 추가 > 시나리오 */}
            <DrawerWrapper drawer="iot-mode-detail" title="생성/설정 변경" back >
                <IotModeSetting/>
            </DrawerWrapper>

            {/* 기기 카테고리 목록 */}
            <DrawerWrapper drawer="iot-device-category" title="기기 카테고리" back className="cl-bg--light">
                <IotDeviceCategory/>
            </DrawerWrapper>

            {/* 기기 카테고리 리스트 */}
            <DrawerWrapper drawer="iot-device-category-detail" title="기기 리스트" back className="cl-bg--light">
                <IotDeviceList/>
            </DrawerWrapper>

            {/* Iot 센서 목록 */}
            <DrawerWrapper drawer="iot-sensor-list" title="IoT 센서 추가" back >
                <IotSensorList/>
            </DrawerWrapper>


			{/* 기기 상세 */}
            <DrawerWrapper drawer="iot-device-detail" title="기기 상세" back >
                <IotDevice/>
            </DrawerWrapper>

			{/* 센서 상세 */}
            <DrawerWrapper drawer="iot-sensor-detail" title="센서" back >
                <IotSensor/>
            </DrawerWrapper>

            {/* Iot 추가 */}
            <DrawerWrapper drawer="iot-add" title="추가" back >
                <ChooseAddingIot/>
            </DrawerWrapper>

			{/* 센서 편집 */}
			<DrawerWrapper drawer="iot-sensor-editor" title="센서 편집" >
				<IotSensorEditor/>
			</DrawerWrapper>

            {/* 기기 편집 */}
            <DrawerWrapper drawer="iot-device-editor" title="기기 편집" >
                <IotDeviceEditor/>
            </DrawerWrapper>

		</div>
	}
}

export default observer( HomeIoT );