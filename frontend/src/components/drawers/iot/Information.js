import React, { Component } from 'react';

import IotIcGraph from 'images/iot-info-status-1@3x.png';
import IotIcWater from 'images/iot-info-status-6@3x.png';
import IotIcHot from 'images/iot-info-status-5@3x.png';
import IotIcValve from 'images/iot-info-device-6@3x.png';
import IotIcHeater from 'images/iot-info-io-t-heater-3@3x.png';

class Information extends Component {
	constructor ( props ) {
		super( props );

		this.state = {
			tabIndex: 0,
			tabData: 0,
		};
	}

	toggleTab = ( index )=> {
		this.setState( { tabIndex: index, tabData: index, } );
	};

	InfoContent () {
		switch ( this.state.tabIndex ) {
			case 0:
				return <div>content1</div>;
				break;
			case 1:
				return <div>content2</div>;
				break;
			case 2:
				return <div>content3</div>;
				break;
			case 3:
				return <div>content4</div>;
				break;
			case 4:
				return <div>content5</div>;
				break;
			default:
				return <div>내용이 없습니다</div>;
				break;
		}
	};

	render () {
		return (
			<div>
				<div className="cl-iot-info-tab">
					<button onClick={()=>this.toggleTab( 0 )}
							className={( this.state.tabIndex === 0 ? 'cl-iot-info-tab__item--active' : 'cl-iot-info-tab__item' )}>
						<img src={IotIcGraph} alt="img"/>
						<span>전기</span>
					</button>
					<button onClick={()=>this.toggleTab( 1 )}
							className={( this.state.tabIndex === 1 ? 'cl-iot-info-tab__item--active' : 'cl-iot-info-tab__item' )}>
						<img src={IotIcWater} alt="img"/>
						<span>수도</span>
					</button>
					<button onClick={()=>this.toggleTab( 2 )}
							className={( this.state.tabIndex === 2 ? 'cl-iot-info-tab__item--active' : 'cl-iot-info-tab__item' )}>
						<img src={IotIcHot} alt="img"/>
						<span>온수</span>
					</button>
					<button onClick={()=>this.toggleTab( 3 )}
							className={( this.state.tabIndex === 3 ? 'cl-iot-info-tab__item--active' : 'cl-iot-info-tab__item' )}>
						<img src={IotIcValve} alt="img"/>
						<span>가스</span>
					</button>
					<button onClick={()=>this.toggleTab( 4 )}
							className={( this.state.tabIndex === 4 ? 'cl-iot-info-tab__item--active' : 'cl-iot-info-tab__item' )}>
						<img src={IotIcHeater} alt="img"/>
						<span>난방</span>
					</button>
				</div>

				<div className="cl-iot-info-content">
					{this.InfoContent()}
				</div>
			</div>
		);
	}
}


export default Information;
