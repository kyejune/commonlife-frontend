import React, { Component } from 'react';
import { withRouter } from 'react-router';
import Iot from 'scripts/iot';
import LiOfCheck from "./LiOfCheck";
import LiOfToggle from "./LiOfToggle";
import LiOfCtrl from "./LiOfCtrl";


class IotDeviceCategory extends Component {

	constructor ( props ) {
		super( props );

		this.action = props.match.params.action;
		const dId = props.match.params.option2 || props.match.params.option1;

		const isRoom = !isNaN( dId ); // 숫자형이면 룸별 목록, 문자 조합이면 공간별 목록으로 구분

		this.state = {
			isRoom: isRoom,
			cateId: dId,
			deviceData: [],
		};

		props.updateTitle( isRoom ? '공간별 기기목록' : '기기별 기기목록' );

		this.loadData();

	}

	loadData(){
        // 목록 가져오기
        Iot.getDevicesByCategory( this.state.isRoom, this.state.cateId, data=> {
            this.setState( {
				deviceData: data
            } );
        } );
	}

	// 기기 추가
	addDevice=()=>{

	}


	// 기기 제어: 토글 바로 적용
    toggleDevice=( bool, data )=>{
        Iot.setIotToggleDevice( data, bool, ( success )=>{
        	console.log( '전환성공?', success );
        	if( !success )
        		this.loadData();
		});
    }

	render () {

		console.log( 'DEVICE DATA 새로 그릑');

		const DeviceList = this.state.deviceData.map( ( data, index )=> {

			switch( this.action ){
				case 'ctrl':
					if( data.deviceType === 'button' )
						return <LiOfToggle key={index} name={data.thingsNm} desc={data.cateNm}
										   onSwitch={ this.toggleDevice }
										   data={data}
						/>;
					else
						return <LiOfCtrl key={index} name={data.thingsNm} desc={data.cateNm}
										 to={{ pathname: `${this.props.location.pathname}/device/${data.deviceId}`}}

						/>;
					break;

				case 'add':
                    return <LiOfCheck key={index} name={data.thingsNm} desc={data.cateNm}/>;
					break;
			}

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