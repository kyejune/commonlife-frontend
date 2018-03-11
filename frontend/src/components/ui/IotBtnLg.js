import React, { Component } from 'react';
import {Devices} from "../../scripts/iot";

import IotIcAlert from 'images/alert-icon-black@3x.png';
// import IotIcModeOut from 'images/io-t-icon-1@3x.png'; // 외출모드
// import IotIcModeSleep from 'images/io-t-icon-2@3x.png'; // 취침모드
// import IotIcModeHoliday from 'images/io-t-icon-3@3x.png'; // 휴가모드
import IotIcModeSave from 'images/io-t-icon-14@3x.png'; // 절약모드
import IotIcSet from 'images/io-t-i-con-b-copy@3x.png';
import Store from "../../scripts/store";

class IotBtnLg extends Component {

	constructor ( props ) {
		super( props );
	}

	viewProgress( deviceInfo ){
		console.log('모달로 진행률 보기');
		Store.ipo = deviceInfo;

		console.log( 'store.ipo:', Store.ipo, Store.set );
	}

	IotBtnType = ()=> {
		if( this.props.IotBtnType === 'typeDefault' ) {
			return 'cl-my-iot__button cl-my-iot__button--default'
		}
		else if( this.props.IotBtnType === 'typeToggle' ) {
			return 'cl-my-iot__button cl-my-iot__button--toggle'
		}
		else if( this.props.IotBtnType === 'typeCustom' ) {
			return 'cl-my-iot__button cl-my-iot__button--toggle cl-my-iot__button--custom'
		}
		else {
			return 'cl-my-iot__button cl-my-iot__button--set'
		}
	};

	IotBtnTitle = ()=> {
		if( this.props.IotBtnType === 'typeDefault' ) {
			return (
				<div>
					<span>{this.props.IotBtnTitle}</span>KW
				</div>
			)
		}
		else if( this.props.IotBtnType === 'typeToggle' ) {
			return (
				<div>
					<span>{this.props.IotBtnTitle}</span>
				</div>
			)
		}
		else if( this.props.IotBtnType === 'typeCustom' ) {
			return (
				<div>
					{this.props.IotBtnTitle}<br/>모드
				</div>
			)
		}
		else {
			return (
				<div>
					<span>{this.props.IotBtnTitle}</span>
				</div>
			)
		}
	};

	IotBtnParagraph = ()=> {
		if( this.props.IotBtnType === 'typeDefault' ) {
			return <p className="cl-my-iot__paragraph">{this.props.IotBtnParagraph}</p>
		}
		else if( this.props.IotBtnType === 'typeToggle' ) {
			return <p className="cl-my-iot__paragraph">{this.props.IotBtnParagraph}</p>
		}
		else if( this.props.IotBtnType === 'typeCustom' ) {
			return ''
		}
		else {
			return <p className="cl-my-iot__paragraph">{this.props.IotBtnParagraph}</p>
		}
	};

	IotBtnLabel = ()=> {
		if( this.props.IotBtnType === 'typeDefault' ) {
			return (
				<div>
					{this.props.IotBtnLabel}
				</div>
			)
		}
		else if( this.props.IotBtnType === 'typeToggle' ) {
			return (
				<div>
					{this.props.IotBtnLabel}
				</div>
			)
		}
		else if( this.props.IotBtnType === 'typeCustom' ) {
			return (
				<div>
					{this.props.IotBtnLabel}
				</div>
			)
		}
		else {
			return (
				<div>
					{this.props.IotBtnLabel}
				</div>
			)
		}
	};

	IotBtnText = ()=> {
		if( this.props.IotBtnType === 'typeDefault' ) {
			return (
				<div className="cell right vertical-middle text-right">
					<span className="cl-my-iot__text">금월예상 {this.props.IotBtnText}Kw</span>
				</div>
			)
		}
		else if( this.props.IotBtnType === 'typeToggle' ) {
			return (
				<div className="cell right vertical-middle text-right cl-my-iot__bullet-toggle-wrap" onClick={ ()=> this.viewProgress( Devices.living1 ) }>
					<i className="cl-my-iot__bullet-toggle"/>
				</div>
			)
		}
		else if( this.props.IotBtnType === 'typeCustom' ) {
			return (
				<div className="cell right vertical-middle text-right cl-my-iot__bullet-toggle-wrap">
					<i className="cl-my-iot__bullet-toggle"/>
				</div>
			)
		}
		else {
			return (
				<div className="cell right vertical-middle text-right" onClick={()=> this.viewProgress( Devices.boiler1 )}>
					<i className="cl-my-iot__bullet"><img src={IotIcSet} alt=""/></i>
					<span className="cl-my-iot__text">{this.props.IotBtnText}℃</span>
				</div>
			)
		}
	};

	render () {

		return (

			<button type="button" className={this.IotBtnType()}>
				<i className="cl-my-iot__ic-alert"><img src={IotIcAlert} alt=""/></i>
				<h3 className="cl-my-iot__title">{this.IotBtnTitle()}</h3>
				{this.IotBtnParagraph()}
				<div className="cl-my-iot__icons">
					<div className="tablize">
						<div className="table-row">
							<div className="cell vertical-bottom">
								<img src={IotIcModeSave} alt=""/>
							</div>
						</div>
					</div>
				</div>
				<div className="cl-my-iot__bottom">
					<div className="tablize">
						<div className="table-row">
							<div className="cell vertical-middle">
								<h4 className="cl-my-iot__label">
									{this.IotBtnLabel()}
								</h4>
							</div>
							{this.IotBtnText()}
						</div>
					</div>
				</div>
			</button>

		)
	}
}

export default IotBtnLg;
