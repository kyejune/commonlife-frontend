import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { Link } from "react-router-dom";
import Iot from "../../../scripts/iot";

import SampleSrc from 'images/io-t-icon-1@3x.png';

class IotDeviceTabList extends Component {

	constructor ( props ) {
		super( props );

		this.state = {
			controlTab: true,
			controlType: false,
		};

		Iot.getDeviceCategories( ( byPlace, byType )=> {

			this.setState( {
				tabIndex: 0,
				tabData: [ byPlace, byType ]
			} );
		} );
	}

	toggleTab = ( index )=> {
		this.setState( { tabIndex: index } );
	};

	render () {

		if( this.state.tabIndex === undefined ) return <div/>;

		const List = this.state.tabData[ this.state.tabIndex ].map( ( data, index )=> {
			return <li key={index}>
				<Link className="cl-flex" to={( this.state.tabIndex === 0 ? { pathname: `${this.props.location.pathname}/${data.roomId}` } : { pathname: `${this.props.location.pathname}/${data.cateCd}` } )}>
					<img className="cl__thumb" src={SampleSrc}/>
					<h4 className="cl__title">{data.cateNm || data.roomNm}</h4>
					<div className="cl-next__button ml-auto"/>
				</Link>
			</li>
		} );

		return (
			<div>
				<div className="cl-iot-control-tab">
					<button onClick={()=>this.toggleTab( 0 )}
							className={( this.state.tabIndex === 0 ? 'cl-iot-control-tab__item--active' : 'cl-iot-control-tab__item' )}>
						공간별 보기
					</button>
					<button onClick={()=>this.toggleTab( 1 )}
							className={( this.state.tabIndex === 1 ? 'cl-iot-control-tab__item--active' : 'cl-iot-control-tab__item' )}>
						기기별 보기
					</button>
				</div>

				<ul className="cl-iot-vertical-list cl-bg--light cl-iot-control-list">
					{List}
				</ul>
			</div>
		)
	}
}


export default withRouter( IotDeviceTabList );