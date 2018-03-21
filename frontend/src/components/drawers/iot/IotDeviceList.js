import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { Link } from "react-router-dom";
import Iot from 'scripts/iot';
import Checkbox from "../../ui/Checkbox";

import SampleSrc from 'images/io-t-icon-1@3x.png';


class IotDeviceCategory extends Component {

	constructor ( props ) {
		super( props );

		const dId = props.match.params.option2 || props.match.params.option1;

		const isRoom = !isNaN( dId ); // 숫자형이면 룸별 목록, 문자 조합이면 공간별 목록으로 구분

		this.state = {
			isRoom: isRoom,
			deviceData: []
		};

		props.updateTitle( isRoom ? '공간별 기기목록' : '기기별 기기목록' );

		// 목록 가져오기
		Iot.getDevicesByCategory( isRoom, dId, data=> {
			this.setState( { deviceData: data } );
		} );
	}

	render () {
		const DeviceList = this.state.deviceData.map( ( data, index )=> {
			return <li className="cl__list--middle" key={index}>
				<Link className="cl-flex" to={{ pathname: `${this.props.location.pathname}/device/0` }}>
					<img className="cl__thumb--rounded" src={SampleSrc}/>
					<div>
						<h4 className="cl__title">{data.thingsNm}</h4>
						<p className="cl__desc mt-05em">{data.cateNm}</p>
					</div>

					<Checkbox className="ml-auto" dark/>
				</Link>
			</li>
		} );

		return (
			<div>
				<ul className="cl-iot-vertical-list cl-bg--light cl-iot-control-list">
					{DeviceList}
				</ul>
			</div>
		)
	}
}


export default withRouter( IotDeviceCategory );