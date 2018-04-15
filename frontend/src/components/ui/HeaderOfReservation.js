import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import recentReserve from 'images/recent-reservation-bt-white@3x.png';
import recentReserveReady from 'images/ic-favorite-24-px-copy-14@3x.png';
import net from '../../scripts/net';
import moment from 'moment';
import Store from '../../scripts/store';


class HeaderOfReservation extends Component {

	componentDidMount() {
		net.getHomeHead( data => {
			this.setState( { homeHead: data } );
		} )
	}

	render () {

		return <div className="cl-header--reservation cl-second-header">

			{( this.state && this.state.homeHead ) &&
			<div className="cl-flex-between cl-reservation__credit-wrap">
				<div>
					<label className="cl-reservation__credit-label">예약크레딧</label>
					<p className="cl-reservation__credit-text">{ moment().format( 'YY년 M월' ) } <span>{ Store.auth.name }</span> 세대의 크레딧은</p>
				</div>
				<p className="cl-reservation__credit-point">{ this.state.homeHead.points }</p>
			</div>
			}

			<Link to={ '/reservation/history' } className="cl-reservation__link-recent-reserve">
				<i><img src={recentReserve} alt="" height="20"/></i>
				<span>최근 예약내역</span>
			</Link>

			<Link to={ '/reservation/history' } className="cl-reservation__link-recent-reserve cl-reservation__link-recent-reserve--ready">
				<i><img src={recentReserveReady} alt="" height="20"/></i>
				{/*<span>예약대기</span>*/}
				{/*<span className="cl-reservation__link-recent-reserve-count">0</span>*/}
			</Link>

		</div>
	}


}


export default HeaderOfReservation