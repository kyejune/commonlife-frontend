/* HomeIoT.jsx */
import React, { Component } from 'react';
import Store from 'scripts/store';
import Iot, { Modes, MyIots } from 'scripts/iot';
import { Link } from 'react-router-dom';
import IotModeEditor from "components/drawers/iot/IotModeEditor";
import WithTitle from 'components/ui/WithTitle';
import IotBtnMode from 'components/ui/IotBtnMode';
import IotBtnLg from 'components/ui/IotBtnLg';

import IotIcAdd from 'images/service-manage@3x.png';
import ModeManageSrc from 'images/mode-manage@3x.png';
import {observer} from "mobx-react";
import DrawerWrapper from "../drawers/DrawerWrapper";
import IotDevice from "../drawers/iot/IotDevice";
import IotModeSetting from "../drawers/iot/IotModeSetting";
import IotAddingSensorList from "../drawers/iot/IotAddingSensorList";
import IotDeviceCategory from "../drawers/iot/IotDeviceCategory";
import IotDeviceList from "../drawers/iot/IotDeviceList";
import IotSensorEditor from "../drawers/iot/IotSensorEditor";
import IotDeviceEditor from "../drawers/iot/IotDeviceEditor";
import MyEditor from "../drawers/MyEditor";
import ExposableEditor from "../drawers/iot/ExposableEditor";
import TimesSelector from "../ui/TimesSelector";
import TimeSelector from "../ui/TimeSelector";
import Automations from "../drawers/iot/Automations";
import RightArrowSrc from 'images/ic-favorite-24-px-blue@3x.png';
import Information from "../drawers/iot/Information";

class HomeIoT extends Component {

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
                device:'iot-device',
				time:'iot-time',
				duration:'iot-duration',
			},

			// MyIot중 상세 제어 페이지가 있는 녀석 클릭
			run:{
				run:'iot-device-detail',
			},

			// 인포메이션
			info:{
				info:'iot-information-detail',
			},

			// MyIot추가 버튼 클릭, 이미 있는 목록 중 노출될 녀석들 관리
			my:{
				my:'iot-my-editor',
				'add-device':'iot-device-category',
				'add-auto':'iot-adding-automation',
				'add-info':'iot-adding-information',
                sensor:'iot-sensor',
                device:'iot-device',
				category:'iot-device-category'
			},

			// Footer에 붙어있는 파란 ctrl버튼 클릭
			ctrl:{
				ctrl:'iot-device-category',
				device:'iot-device'
			},

			// 시나리오 +
            scenario:{
                scenario:'iot-scenario',
				add:'iot-mode-detail',


                'edit-sensor':'iot-sensor-editor',
                'edit-device':'iot-device-editor',
                // 'add-device':'iot-device-category',
                // 'add-sensor':'iot-sensor-list',
			}
		};


		const action = this.props.match.params.action;
		const map = compMap[action];


		Store.clearDrawer();
		if( action === undefined ) return;


		// let prevSeg = params[p];
		let prevSeg = null;
		for( var p in params ){
			let d = map[params[p]];
			if( RegExp(/\d+/).test(params[p])  ) d = map[prevSeg] + '-detail';
			if( d ) Store.pushDrawer( d );
            prevSeg = params[p];
		}
	}

	render () {

		return <div className="cl-tab--iot">
			<div className="cl-home-iot">
				<div className="cl-fitted-box pb-3em">
					<WithTitle titleName="Smart Mode"/>

					<div className="cl-iot-mode">
						<ul className="cl-iot-mode__list">
							{ Modes.map( ( m, index ) => {
								return (
									<li className="cl-iot-mode__list-item" key={index}>
										<IotBtnMode modeData={m}/>
									</li>
								)
							} ) }
							<li className="cl-iot-mode__list-item cl-iot-mode__add-mode-button">
								<Link to={'/iot/mode'} className="cl-iot-mode__button">
									<img src={ModeManageSrc} alt="모드 관리"/>
								</Link>
							</li>
						</ul>
					</div>

					<WithTitle titleName="My Service"/>

					<div className="cl-my-iot pb-3em">
						<ul className="cl-my-iot__list">
							{ MyIots.map(( iot, index )=>{
                                	return <li key={index} className="col cl-my-iot__list-item">
										<IotBtnLg {...iot }/>
									</li>
                                })
							}


                            {/* 시나리오 추가 용 + */}
                            <li className="cl-my-iot__list-item">
                                <Link to={ { pathname:'/iot/scenario' } } >
                                    <div className="cl-my-iot__button cl-my-iot__button--set" style={{backgroundColor:'#E8EDF6'}}>
                                        <div className="cl-my-iot__content">
                                            <div>
                                                <h3 className="cl-bold mt-01em uppercase w-85 cl-wrap">
                                                    <span className="cl-bold">자동화 관리</span>
                                                </h3>
                                                <p className="cl-my-iot__paragraph">Automation</p>
                                            </div>
                                            <img src="icons/cl_device-12.svg" width="45" height="45" alt="아이콘이미지:cl_device-12" className="cl-my-iot__icons"/>
                                        </div>
                                        <div className="cl-my-iot__bottom cl-flex pt-04em">
                                            <div className="cl-bold color-black">목록보기</div>
                                            <img className="cl-flex" src={RightArrowSrc} width="24" height="24" alt=""/>
                                        </div>
                                    </div>
                                </Link>
                            </li>
						</ul>

                        {/* 서비스 관리 */}
						<Link className="cl-my-iot-service-manage" to={ { pathname:'/iot/my' } } >
							<img src={IotIcAdd} alt="서비스 관리" width="150" height="65"/>
						</Link>

					</div>

					{/*<button type="button" className="cl-iot__help-button">기기 제어에 어려움이 있으신가요?</button>*/}
				</div>
			</div>

			{/* IoT Mode 편집 */}
            <DrawerWrapper drawer="iot-mode" title="Mode 편집" back >
                <IotModeEditor/>
			</DrawerWrapper>

            <DrawerWrapper drawer="iot-information-detail" title="인포메이션" back lightgray>
                <Information/>
            </DrawerWrapper>

            {/* IoT 모드 설정 변경 || 모드 추가 > 시나리오 */}
            <DrawerWrapper drawer="iot-mode-detail" title="생성/설정 변경" back >
                <IotModeSetting/>
            </DrawerWrapper>


			{/* 자동화 리스트 목록 */}
            <DrawerWrapper drawer="iot-scenario" title="자동화(Automation)관리" back light >
                <Automations/>
            </DrawerWrapper>

            {/* 자동화 리스트 개별 편집 */}
            <DrawerWrapper drawer="iot-scenario-detail" title="자동화 리스트 편집" back >
                <IotModeSetting/>
            </DrawerWrapper>


            {/* 기기 카테고리 목록, 경로에 따라 풀 리스트나 추가 가능한 리스트로 나눠서 보여줌 */}
            <DrawerWrapper drawer="iot-device-category" title="기기 카테고리" back className="cl-bg--light">
                <IotDeviceCategory/>
            </DrawerWrapper>

            {/* 기기 카테고리 리스트, 시나리오 생성시 추가 가능한 기기목록을 바로 접근 */}
            <DrawerWrapper drawer="iot-device-category-detail" title="기기 리스트" close className="cl-bg--light">
                <IotDeviceList/>
            </DrawerWrapper>

            {/* Iot 센서 목록 */}
            <DrawerWrapper drawer="iot-sensor-list" title="IoT 센서 추가" close darkgray >
                <IotAddingSensorList/>
            </DrawerWrapper>


            {/* 밖에 끄집어낼 시나리오 목록 */}
            <DrawerWrapper drawer="iot-adding-automation" title="자동화 추가" back darkgray >
                <ExposableEditor/>
            </DrawerWrapper>

            {/* 밖에 끄집어낼 가치정보 목록 */}
            <DrawerWrapper drawer="iot-adding-information" title="가치정보 추가" back darkgray >
                <ExposableEditor/>
            </DrawerWrapper>

			{/* 기기 상세 */}
            <DrawerWrapper drawer="iot-device-detail" title="기기 상세" back >
                <IotDevice/>
            </DrawerWrapper>

			{/* 센서 상세 */}
            <DrawerWrapper drawer="iot-sensor-detail" title="센서" back >
                <IotDevice/>
            </DrawerWrapper>

            {/* 특정시간 상세 */}
            <DrawerWrapper drawer="iot-time-detail" title="특정시간" back >
                <TimeSelector/>
            </DrawerWrapper>

            {/* 구간시간 상세 */}
            <DrawerWrapper drawer="iot-duration-detail" title="구간시간" back >
                <TimesSelector/>
            </DrawerWrapper>

            {/* Iot 편집 */}
            <DrawerWrapper drawer="iot-my-editor" title="My Service 편집" back strongdark >
                <MyEditor/>
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