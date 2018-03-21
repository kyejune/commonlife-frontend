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
			controlType: false
		};

		Iot.getDeviceCategories( ( byPlace, byType )=> {

			this.setState( {
				tabIndex: 0,
				tabData: [ byPlace, byType ]
			} );

			// console.log( '공간별 카테고리 목록:', byPlace );
			// console.log( '기기별 카테고리 목록:', byType );
		} );
	}

	toggleTab = ( index )=> {
		this.setState( { tabIndex: index } );
	};

	render () {

		// console.log( this.state );

		if( this.state.tabIndex === undefined ) return <div>00</div>;

		// console.log( 'list:', this.state.tabData[ this.state.tabIndex ] );

		const List = this.state.tabData[ this.state.tabIndex ].map( ( data, index )=> {
			return <li key={index}>
				<img className="cl__thumb" src={SampleSrc}/>
				<h4 className="cl__title">{data.cateNm || data.roomNm}</h4>
				<div className="cl-next__button ml-auto"/>
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

				<ul className="cl-iot-vertical-list cl-bg--light">
					{List}
				</ul>

				{/* Link to에 공간별은 roomId를, 기기별은 cateCd를 넣어주세요.*/}
				<ul>
					<li>
						<Link to={{ pathname: `${this.props.location.pathname}/2` }}>카테고리 목록 0</Link>
					</li>
					<li>
						<Link to={{ pathname: `${this.props.location.pathname}/HW00201` }}>카테고리 목록 1</Link>
					</li>
					<li>
						<Link to={{ pathname: `${this.props.location.pathname}/HW00202` }}>카테고리 목록 2</Link>
					</li>
				</ul>
			</div>
		)
	}
}


export default withRouter( IotDeviceTabList );