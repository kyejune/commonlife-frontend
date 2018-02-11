import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import recentReserve from 'images/recent-reservation-bt-white@3x.png';
import recentReserveReady from 'images/ic-favorite-24-px-copy-14@3x.png';


class HeaderOfReservation extends Component {


	render () {

		return <div className="cl-header--reservation cl-second-header">

			<div className="cl-flex-between cl-reservation__credit-wrap">
				<div>
					<label className="cl-reservation__credit-label">예약크레딧</label>
					<p className="cl-reservation__credit-text">17년 8월 <span>조아해</span> 세대의 크레딧은</p>
				</div>
				<p className="cl-reservation__credit-point">75.0</p>
			</div>

			<Link to={ '/reservation/history' } className="cl-reservation__link-recent-reserve">
				<i><img src={recentReserve} alt="" height="20"/></i>
				<span>최근 예약내역</span>
			</Link>

			<Link to={ '/reservation/history' } className="cl-reservation__link-recent-reserve">
				<i><img src={recentReserveReady} alt="" height="20"/></i>
				<span>예약대기</span>
				<span className="cl-reservation__link-recent-reserve-count">3</span>
			</Link>

		</div>
	}


}


export default HeaderOfReservation