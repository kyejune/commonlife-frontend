/* LifeInfo.jsx */
import React, { Component } from 'react';
import Store from 'scripts/store';
import { Link, withRouter } from 'react-router-dom';
import { observer } from "mobx-react";
import DrawerWrapper from "../drawers/DrawerWrapper";
import NoticeDetail from "../drawers/lifeInfo/NoticeDetail";
import LivingSupportCategory from "../drawers/lifeInfo/LivingSupportCategory";
import SubjectList from "../drawers/lifeInfo/SubjectList";
import Status from "../drawers/lifeInfo/Status";
import Profile from "../drawers/lifeInfo/Profile";
import ContentPage from "../drawers/lifeInfo/ContentPage";
import Net from "../../scripts/net";
import IconLoader from "../ui/IconLoader";
import StatusDetail from "../drawers/lifeInfo/StatusDetail";
import classNames from 'classnames';

class LifeInfo extends Component {

	componentDidMount () {
		this.updateRoute();

		Net.getInfoPage( data=> {

			this.setState( data );
			// console.log( 'info dashboard:', data );
		} );

	}

	componentDidUpdate ( prevProps ) {
		if( this.props.location !== prevProps.location )
			this.updateRoute();
	}

	updateRoute ( data ) {

		const params = this.props.match.params;
		const compMap = {
			notice: {
				notice: 'info-notice',
			},

			support: {
				support: 'info-support'
			},

			guide: {
				guide: 'info-subject',
			},

			benefits: {
				benefits: 'info-subject',
			},

			profile: {
				profile: 'info-profile',
			},

			status: {
				status: 'info-status',
			}


		};

		const cate = this.props.match.params.cate;
		const map = compMap[ cate ];

		Store.clearDrawer();
		if( cate === undefined ) return;

		let prevSeg = null;
		for( var p in params ) {
			let d = map[ params[ p ] ];
			if( RegExp( /\d+/ ).test( params[ p ] ) ) d = map[ prevSeg ] + '-detail';
			if( d ) Store.pushDrawer( d );
			prevSeg = params[ p ];
		}
	}

	render () {
		if( !this.state ) return <div/>;

		const { cmplxNm, dong, ho, infoList, notice, point, startDt, userImgSrc, userNm } = this.state;

		return <div className="cl-info-container cl-tab--info">
			<div className="cl-bg--black">

				<div className="cl-fitted-box">

					<div className="cl-profile-card cl-second-header">
						<div className="cl-flex cl-summary">
							<div className="cl-avatar" style={{ backgroundImage: `url(${userImgSrc})` }}/>
							<div>
								<h4>{userNm}</h4>
								<p>{`${cmplxNm} ${dong}동 ${ho}호`}</p>
							</div>
						</div>

						<Link className="cl-edit color-primary" to="/info/profile">ProfileEdit</Link>

						<footer className="cl-profile-card__footer">
							<div className="cl-flex">
								<p>입주일: {startDt}</p>
								<p className="ml-auto">크레딧: {point}</p>
							</div>
						</footer>
					</div>


					<div className="cl-info-notice">
						<h4>Notice</h4>
						<Link to="/info/notice/0"
							  className={classNames( "cl-ellipsis--3", { 'cl--disabled': notice === null } )}
							  style={{ 'WebkitBoxOrient': 'vertical' }}>
							{notice || '등록된 공지사항이 없습니다.'}
						</Link>
					</div>


					<ul className="cl-info-dashboard__list">

						{infoList.map( ( item, key )=> {

							const TO = ( item.infoKey === 'event' ) ? '/community/event' : `/info/${item.infoKey}`;

							return <li key={key}>
								<Link to={TO}>
									<IconLoader className="cl__thumb--rounded" src={item.imgSrc}/>
									<p>{item.infoNm}</p>
								</Link>
							</li>
						} )
						}

					</ul>
				</div>

			</div>


			{/* 공지 상세보기 */}
			<DrawerWrapper drawer="info-notice-detail" title="" deepdark back>
				<NoticeDetail/>
			</DrawerWrapper>

			{/* 리빙서포트 카테고리 */}
			<DrawerWrapper drawer="info-support" title="Living Support" light back>
				<LivingSupportCategory/>
			</DrawerWrapper>

			{/* Benefit 목록, 리빙가이드 목록 */}
			<DrawerWrapper drawer="info-subject" back>
				<SubjectList/>
			</DrawerWrapper>

			{/* Benefit 목록, 리빙가이드 상세 */}
			<DrawerWrapper drawer="info-subject-detail" back>
				<ContentPage/>
			</DrawerWrapper>

			{/* My Status */}
			<DrawerWrapper drawer="info-status" title="My Status" darkgray back>
				<Status/>
			</DrawerWrapper>

			{/* My Status */}
			<DrawerWrapper drawer="info-status-detail" statusdark back>
				<StatusDetail/>
			</DrawerWrapper>

			{/* Profile */}
			<DrawerWrapper drawer="info-profile" title="사용자 정보 관리" back>
				<Profile/>
			</DrawerWrapper>

		</div>
	}
}

export default withRouter( observer( LifeInfo ) );