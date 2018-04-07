import React, { Component } from 'react';
import { withRouter } from 'react-router';
import Iot from 'scripts/iot';
import LiOfCheck from "./LiOfCheck";
import LiOfToggle from "./LiOfToggle";
import LiOfCtrl from "./LiOfCtrl";
import checkSrc from 'images/ic-check@3x.png';
import classNames from 'classnames';
import {Scenario} from "../../../scripts/store";
import Store from "../../../scripts/store";


/*
*  my, senario, ctrl 세군데서 접근하는 페이지
*  scenario, ctrl는 풀 리스트를 보여주고,
*  my는 추가 가능한 리스트만 보여주기
*
*  ctrl은 제어
*  scenario, my는 체크박스 형태
*
* */
class IotDeviceCategory extends Component {

	constructor ( props ) {
		super( props );

		const params = props.match.params;
		const action = params.action;
		let dId = params.option4 || params.option3 || params.option2 || params.option1;

		if( action === 'ctrl' ) dId = params.option1;

		const isRoom = !isNaN( dId ); // 숫자형이면 룸별 목록, 문자 조합이면 공간별 목록으로 구분

		this.state = {
			isRoom: isRoom,
			cateId: dId,
			deviceData: [],
            action: action,
			addingList:[],
		};

		if( action === 'scenario' ){
            props.updateTitle( '추가할 기기목록' );
            // props.setBackPath( undefined );
		}
		else{
            props.updateTitle( isRoom ? '공간별 기기목록' : '기기별 기기목록' );
		}

		this.loadData();
	}

	loadData(){
		
        // 목록 가져오기
		if( this.state.action === 'scenario' )
		{
			Iot.getAddibleItemOfScenario( 'actors', data =>{
                this.setState({
                    deviceData: data.scnaThings,
                    addingList: [],
                });
			});
		}
		else
		{

            Iot.getDevicesByCategory( this.state.action === 'my', this.state.isRoom, this.state.cateId, ( success, data )=> {
                if( success ) {
                    this.setState({
                        deviceData: data,
                        addingList: [],
                    });
                }else{
                    console.log( data );
                }
            } );

		}
	}

    // Scenario or My IoT ---------------------------------------------------------------------------------------
	// 기기 추가
    onChangeAddCheck=( bool, data, index )=>{
		data = data.data;
		const ID = index;//this.state.action==='my'?data.myIotId:data.clntId;

        let list = this.state.addingList.concat();
        const sIdx = list.indexOf( ID );

        if( bool ) list.push( ID );
        else if( sIdx >= 0 ) list.splice( sIdx, 1 );

        this.setState( { addingList:list });
	}

	// My IoT 추가된 기기 전송
    addDevices=()=>{

		if( this.state.action === 'my' ) {
			const Addings = this.state.addingList.map(itemIndex=>{
				return this.state.deviceData[itemIndex].myIotId;
			});

            Iot.setExposeItemOfDashboard( Addings, () => {
            	if( this.state.deviceData.length > Addings.length )
            		this.loadData();
            	else
            		this.props.history.goBack();
            });

        }else if( this.state.action === 'scenario' ){

            const Adding = this.state.addingList.map(itemIndex => {
                return {...this.state.deviceData[itemIndex], chk: 'Y'};
            });

			// 시나리오 생성 중이면 바로 저장
			if( this.props.isCreate ) {
                Scenario.scnaThings = Scenario.scnaThings.concat( Adding );

			// 편집중이면 콜백
            }else{
				this.props.callback( Adding );
			}

            // 시나리오 편집화면으로 이동
            Store.popDrawer();

		}else{

			alert('예외상황');
		}
	}


    // Ctrl  ---------------------------------------------------------------------------------------
	// 기기 제어: 토글 바로 적용, detail은 페이지 이동해서 제어
    toggleDevice=( bool, data )=>{
        Iot.setIotToggleDevice( data, bool, ( success )=>{
        	console.log( '전환성공?', success );
        	if( !success )
        		this.loadData();
		});
    }

	render () {

		const action = this.props.match.params.action;

		const DeviceList = this.state.deviceData.map( ( data, index )=> {

			switch( action ){
				case 'ctrl':
					if( data.deviceType === 'button' )
						return <LiOfToggle key={index} name={data.deviceNm} desc={data.cateNm}
										   checked={data.currSts === data.maxVlu}
										   onSwitch={ this.toggleDevice }
										   icon={data.imgSrc}
										   data={data}
						/>;
					else
						return <LiOfCtrl key={index} name={data.deviceNm} desc={data.cateNm}
                                         icon={data.imgSrc}
										 to={`${this.props.location.pathname}/device/${data.deviceId}`}

						/>;

				case 'scenario':
                case 'my':
                    return <LiOfCheck key={index} name={action==='my'?data.mNm:data.deviceNm} icon={data.btImgSrc} desc={data.cateNm}
									  data={data}
									  checked={this.state.addingList.indexOf( index ) >= 0 }
									  onChange={ (bool, data)=>this.onChangeAddCheck( bool, data, index ) }/>;
				default:
					return '';
			}

		} );

		return (
			<div>
				<ul className="cl-iot-vertical-list cl-bg--light cl-iot-control-list">
					{DeviceList}
				</ul>

                {this.state.mode !== 'ctrl' &&
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