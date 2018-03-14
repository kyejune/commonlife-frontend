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
import IotBtnMode from 'components/ui/IotBtnMode';
import IotBtnLg from 'components/ui/IotBtnLg';
import Modal from 'components/overlay/Modal';

import IotIcAddMode from 'images/combined-shape@3x.png';

import IotIcAdd from 'images/combined-shape-plus@3x.png';
import IotProgressOverlay from "../overlay/IotProgressOverlay";
import {observer} from "mobx-react";
import DrawerWrapper from "../drawers/DrawerWrapper";
import IotDeviceDetail from "../drawers/iot/IotDeviceDetail";

class HomeIoT extends Component {

	constructor ( props ) {
		super( props );

		this.state = {
			// drawer: Store.drawer,
			mode: [],
		}
	}

	componentDidMount () {
		Iot.getIot( data =>{
			this.setState( { mode: data.mode } );
		} );


		this.updateRoute();
	}

	componentDidUpdate ( prevProps ) {
		if( this.props.location !== prevProps.location )
			this.updateRoute();
	}

	updateRoute ( data ) {

        // "/iot/:action?/:drawer?/:id?"
		const { action, drawer, id } = this.props.match.params;

		console.log('params:', this.props.match );

		for( var p in this.props.match.params ){
			console.log( p );
		}

		// id위치에 특정 단어가 들어올때 처리
		if( action === 'control' )
		{
            Store.pushDrawer('iot-control-list');

        }
		// IoT 기기제어 목록
		else if( action === 'control' )
		{
            Store.pushDrawer('iot-control-list');
        }
		// IoT Mode 편집
		else if( action === 'edit-mode' )
		{
            Store.pushDrawer('iot-edit-mode');
        }
        else{
            Store.clearDrawer();
		}
	}

	render () {

		return <div>
			<div className="cl-home-iot">
				<div className="cl-fitted-box">
					<WithTitle titleName="MODE"/>

					<div className="cl-iot-mode">
						<ul className="cl-iot-mode__list">
							{ this.state.mode.map( ( mode, index ) => {
								return (
									<li className="cl-iot-mode__list-item" key={index}>
										<IotBtnMode modeData={mode}/>
									</li>
								)
							} ) }
							<li className="cl-iot-mode__list-item">
								<Link to={'/iot/edit-mode'} className="cl-iot-mode__button cl-iot-mode__add-mode-button">
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
            <DrawerWrapper drawer="iot-edit-mode" title="Mode 편집">
                <DrawerIotEditList/>
			</DrawerWrapper>


			{/* IoT 기기제어, 기기추가 목록 */}
            <DrawerWrapper drawer="iot-control-list" title="IoT 제어">
                <DrawerIotControlList/>
            </DrawerWrapper>


			{/* 기기 상세 */}
            <DrawerWrapper drawer="iot-device-detail" title="상세 제어">
                <IotDeviceDetail/>
            </DrawerWrapper>

		</div>
	}
}

export default observer( HomeIoT );