import React, { Component } from 'react';
import Store from "../../scripts/store";
import ReservationDetail from "components/drawers/ReservationDetail";
import ReservationHistory from "components/drawers/ReservationHistory";
import ReservationList from "components/drawers/ReservationList";
import DrawerSwiperViewer from "components/drawers/DrawerSwiperViewer";

import { Link } from 'react-router-dom';
import HeaderOfReservation from 'components/ui/HeaderOfReservation';
import SelectWithTitle from 'components/ui/SelectWithTitle';
import ReserveGroupArrow from 'images/ic-favorite-24-px-blue@3x.png';
import ReserveGroupHome from 'images/r-icon-1@3x.png';
import ReserveGroupTool from 'images/r-icon-2@3x.png';
import ReserveGroupStore from 'images/r-icon-3@3x.png';
import ReserveGroupEtc from 'images/r-icon-4@3x.png';
import ReserveServiceLaundry from 'images/rs-icon-1@3x.png';
import ReserveServiceCleaning from 'images/rs-icon-2@3x.png';
import ReserveServiceFood from 'images/rs-icon-3@3x.png';
import ReserveServiceCarwash from 'images/rs-icon-4@3x.png';
import ReserveIcPlus from 'images/page-1@3x.png';
import ReserveIcNext from 'images/shape-time-next@3x.png';
import ReserveIcTime from 'images/shape-time-plus@3x.png';
import { observer } from "mobx-react";
import DrawerWrapper from "../drawers/DrawerWrapper";
import net from '../../scripts/net';
import TitleWithoutSelect from "../ui/TitleWithoutSelect";
import ReservationListItem from "../drawers/ReservationListItem";

class Reservation extends Component {

	constructor( props ) {
		super( props );

		this.state = {
			cmplxId: null,
			groups: [],
			schemes: [],
		}
	}

	load = cmplxId => {
		net.getReservationGroups( cmplxId, data => {
			this.setState( { cmplxId, groups: data.groups, schemes: data.schemes } );
		} );
	};

	componentDidMount () {
		this.updateRoute();

		// TODO: 어차피 Store 에서 관리할거면 로드하는 위치가 여기가 아니어도 될 것 같은데..
		net.getComplexes( data=> {
			Store.complexes = data;
		} );

		this.load( Store.cmplxId );
	}

	componentDidUpdate ( prevProps ) {
		if( this.props.location !== prevProps.location )
			this.updateRoute();
	}

	updateRoute () {

		// id위치에 특정 단어가 들어올때 처리
		if( this.props.match.params.id === 'history' )
			Store.pushDrawer( 'reservation-history' );
		else if( this.props.match.params.id === 'group' )
			Store.pushDrawer( 'reservation-list' );
		// id위치에 일반적으로 숫자가 들어오면 상세보기
		else if( this.props.match.params.id )
			Store.pushDrawer( 'reservation-detail' );
		else {
			Store.clearDrawer();
		}

		// 썸네일 뷰 추가
		if( this.props.match.params.add === 'thumbnails' )
			Store.pushDrawer( 'reservation-thumbnails' );


		setTimeout( ()=>this.render(), 1000 );
		// this.render();
	}

	render () {

		const cmplxId = this.state.schemes

		return <div className="cl-tab--reservation">
			{/*크레딧 에러 팝업 */}
			{/*<NoCreditPopup/>*/}

			<HeaderOfReservation/>

			<div className="cl-fitted-box pb-5em">

				<SelectWithTitle displayLength={ this.state.groups.length } selectedValue={ this.state.cmplxId } onChange={ event => this.load( event.currentTarget.value ) }/>

				<ul className="cl-reservation__list--group">
					{
						this.state.groups.map( ( group, key ) => {
							return <li className="cl-reservation__list-item" key={ key }>
                                <Link to={'/reservation/group/' + group.idx }>
                                    <div className="cl-flex-between">
                                        {group.icon === 'HOME' &&
                                        <img src={ReserveGroupHome} alt=""
                                             className="cl-reservation__list-item-type-img"/>
                                        }
                                        {group.icon === 'TOOL' &&
                                        <img src={ReserveGroupTool} alt=""
                                             className="cl-reservation__list-item-type-img"/>
                                        }
                                        {group.icon === 'STORE' &&
                                        <img src={ReserveGroupStore} alt=""
                                             className="cl-reservation__list-item-type-img"/>
                                        }
                                        {group.icon === 'ETC' &&
                                        <img src={ReserveGroupEtc} alt=""
                                             className="cl-reservation__list-item-type-img"/>
                                        }
                                        <div className="cl-reservation__list-item-text">
                                            <h5>{ group.title }</h5>
                                            <p className="cl-ellipsis">{ group.summary }</p>
                                        </div>
                                        <img src={ReserveGroupArrow} alt="" className="cl-reservation__list-item-bullet"/>
                                    </div>
                                </Link>
                            </li>
						} )
                    }
				</ul>

				<TitleWithoutSelect label={ '서비스' } displayLength={ this.state.schemes.length }/>

				<ul className="cl-reservation__list--service">
					{
						this.state.schemes.map( ( scheme, key ) => {
							return <ReservationListItem scheme={ scheme } key={ key }/>
						} )
                    }
				</ul>

			</div>

			{/* 예약 상세 화면 */}
			<DrawerWrapper drawer="reservation-detail">
				<ReservationDetail/>
			</DrawerWrapper>

			{/* 히스토리 */}
			<DrawerWrapper drawer="reservation-history" title="예약내역">
				<ReservationHistory/>
			</DrawerWrapper>

			{/* 이미지상세보기 */}
			<DrawerWrapper drawer="reservation-thumbnails">
				<DrawerSwiperViewer {...this.props}/>
			</DrawerWrapper>

			{/* 예약 목록 */}
			<DrawerWrapper drawer="reservation-list" light back >
				<ReservationList/>
			</DrawerWrapper>

		</div>
	}

}

export default observer( Reservation );