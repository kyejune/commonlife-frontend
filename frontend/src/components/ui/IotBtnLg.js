import React, { Component } from 'react';
import {Devices} from "../../scripts/iot";

import IotIcAlert from 'images/alert-icon-black@3x.png';
import IotIcModeSave from 'images/io-t-icon-14@3x.png'; // 절약모드
import IotIcSet from 'images/io-t-i-con-b-copy@3x.png';
import Store from "../../scripts/store";

class IotBtnLg extends Component {

	constructor ( props ) {
		super( props );
	}

	viewProgress( deviceInfo ){
		Store.ipo = { targetValue:true, ...deviceInfo };
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
				<div className="cl-my-iot__status">
					<span className="cl-my-iot__text">금월예상 {this.props.IotBtnText}Kw</span>
				</div>
			)
		}
		else if( this.props.IotBtnType === 'typeToggle' ) {
			return (
				<div className="cl-my-iot__status cl-my-iot__bullet-toggle-wrap" onClick={ ()=> this.viewProgress( Devices.living1 ) }>
					<i className="cl-my-iot__bullet-toggle"/>
				</div>
			)
		}
		else if( this.props.IotBtnType === 'typeCustom' ) {
			return (
				<div className="cl-my-iot__status cl-my-iot__bullet-toggle-wrap">
					<i className="cl-my-iot__bullet-toggle"/>
				</div>
			)
		}
		else {
			return (
				<div className="cl-my-iot__status" onClick={()=> this.viewProgress( Devices.boiler1 )}>
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
					<img src={IotIcModeSave} alt=""/>
				</div>
				<div className="cl-my-iot__bottom">
					<h4 className="cl-my-iot__label">
						{this.IotBtnLabel()}
					</h4>
					{this.IotBtnText()}
				</div>
			</button>

		)
	}
}

export default IotBtnLg;
