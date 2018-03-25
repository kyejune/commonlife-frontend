import React, { Component } from 'react';
import { withRouter } from 'react-router';
import Iot from 'scripts/iot';
import LiOfCheck from "./LiOfCheck";
import LiOfToggle from "./LiOfToggle";
import LiOfCtrl from "./LiOfCtrl";
import checkSrc from 'images/ic-check@3x.png';
import classNames from 'classnames';

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
            modeAdd:this.props.match.params.action === 'add',
			addingList:[],
		};

		props.updateTitle( isRoom ? '공간별 기기목록' : '기기별 기기목록' );

		this.loadData();

	}

	loadData(){
        // 목록 가져오기
        Iot.getDevicesByCategory( this.state.modeAdd, this.state.isRoom, this.state.cateId, data=> {

            this.setState( {
				deviceData: data
            } );

        } );
	}


	// 기기 추가
    onChangeAddCheck=( bool, data )=>{
		data = data.data;
        let list = this.state.addingList.concat();
        const sIdx = list.indexOf( data.myIotId );

        if( bool ) list.push( data.myIotId );
        else if( sIdx >= 0 ) list.splice( sIdx, 1 );

        this.setState( { addingList:list });
	}

	// 추가된 기기 전송
    addDevices=()=>{
		Iot.addDevices( this.state.addingList, success=>{
			if( success )
				this.loadData();
			else
				alert( '기기 추가에 실패하였습니다. 잠시 후 다시 시도해주시기 바랍니다.' );
		});
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
		const DeviceList = this.state.deviceData.map( ( data, index )=> {

			switch( this.action ){
				case 'ctrl':
					if( data.deviceType === 'button' )
						return <LiOfToggle key={index} name={data.thingsNm} desc={data.cateNm}
										   checked={data.currSts === data.maxVlu}
										   onSwitch={ this.toggleDevice }
										   icon={data.imgSrc}
										   data={data}
						/>;
					else
						return <LiOfCtrl key={index} name={data.thingsNm} desc={data.cateNm}
                                         icon={data.imgSrc}
										 to={{ pathname: `${this.props.location.pathname}/device/${data.deviceId}`}}

						/>;
					break;

				case 'add':
                    return <LiOfCheck key={index} name={data.mNm} icon={data.btImgSrc} desc={data.cateNm} data={data} onChange={this.onChangeAddCheck }/>;
					break;
			}

		} );

		return (
			<div>
				<ul className="cl-iot-vertical-list cl-bg--light cl-iot-control-list">
					{DeviceList}
				</ul>

                {this.state.modeAdd&&
                    <footer className={ classNames("cl-flex cl-opts__footer", { "cl-opts__footer--hide":this.state.addingList.length === 0 } )}>
                        <div>{this.state.addingList.length}개의 기기 선택</div>
                        <button className="ml-auto cl-flex mr-1em" onClick={ this.addDevices }>
                            <img src={checkSrc} alt="확인" width="28" height="28"/>
                            <span className="color-primary ml-03em">확인</span>
                        </button>
                    </footer>
                }
			</div>
		)
	}
}


export default withRouter( IotDeviceCategory );