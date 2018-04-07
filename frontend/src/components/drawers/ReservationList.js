import React, {Component} from 'react';
import {withRouter} from 'react-router';
import SelectWithTitle from 'components/ui/SelectWithTitle';
import { Link } from 'react-router-dom';

import ReserveGroupHome from 'images/r-icon-1@3x.png';
import ReserveServiceLaundry from 'images/rs-icon-1@3x.png';
import ReserveServiceCleaning from 'images/rs-icon-2@3x.png';
import ReserveServiceFood from 'images/rs-icon-3@3x.png';
import ReserveServiceCarwash from 'images/rs-icon-4@3x.png';
import ReserveIcPlus from 'images/page-1@3x.png';
import ReserveIcNext from 'images/shape-time-next@3x.png';
import ReserveIcTime from 'images/shape-time-plus@3x.png';
import ReserveIcAlert from 'images/alert-icon-red@3x.png';


class ReservationList extends Component{

	componentWillMount(){
		// ContentHolder에 전달
		this.props.updateTitle(
			<h2 className="md-title md-title--toolbar cl-ellipsis">
				<span>Reservation</span>
			</h2>
		);
	}

	render(){

		return (
			<div className="cl-reservation-list">
				<div className="cl-reservation-list__info">
					<div className="cl-reservation-list__info-credit">
						예약가능 크레딧: <span>75.0</span>
					</div>
					<div className="cl-flex-between">
						<img src={ReserveGroupHome} alt=""
							 className="cl-reservation-list__info-type-img"/>
						<div className="cl-reservation-list__info-text">
							<h4>생활 서비스</h4>
							<p className="cl-ellipsis">세탁배달, 청소서비스, 음식배달외 3</p>
						</div>
					</div>
					<p className="cl-reservation-list__info-paragraph">COMMON Life에서 제공하는 생활편의 서비스입니다.</p>
				</div>

				<SelectWithTitle/>

				<ul className="cl-reservation__list--service">
					<li className="cl-reservation__list-item cl-reservation__alert">
						<div>
							<div className="cl-flex-between">
								<img src={ReserveServiceLaundry} alt="" className="cl-reservation__list-item-type-img"/>
								<div className="cl-reservation__list-item-text">
									<h5 className="cl-flex">세탁 서비스 <span className="cl-flex cl-reservation__list-item-alert"><img src={ReserveIcAlert} alt=""/> 예약가능</span></h5>
									<p className="cl-ellipsis">배달 1일전 예약가능</p>
								</div>
							</div>
							<div className="cl-flex-between cl-reservation-list__link">
								<Link to="" className="cl-reservation__list-item-bullet">
									<img src={ReserveIcPlus} alt=""/>
									<span>예약하기</span>
								</Link>
								<Link to="" className="cl-reservation__list-item-bullet">
									<img src={ReserveIcNext} alt=""/>
									<span>내일예약</span>
								</Link>
								<Link to="" className="cl-reservation__list-item-bullet">
									<img src={ReserveIcTime} alt=""/>
									<span>날짜지정</span>
								</Link>
							</div>
						</div>
					</li>
					<li className="cl-reservation__list-item cl-reservation__notice">
						<div>
							<div className="cl-flex-between">
								<img src={ReserveServiceCleaning} alt=""
									 className="cl-reservation__list-item-type-img"/>
								<div className="cl-reservation__list-item-text">
									<h5>청소 서비스</h5>
									<p className="cl-ellipsis">외부 서비스업체 사정에 따라 변경 또는 취소의 여지가 있을 수도 있습니다.</p>
								</div>
								<Link to={'/reservation/0'} className="cl-reservation__list-item-bullet">
									<img src={ReserveIcPlus} alt=""/>
									<span>예약하기</span>
								</Link>
							</div>
						</div>
					</li>
					<li className="cl-reservation__list-item">
						<div>
							<div className="cl-flex-between">
								<img src={ReserveServiceCleaning} alt=""
									 className="cl-reservation__list-item-type-img"/>
								<div className="cl-reservation__list-item-text">
									<h5>청소 서비스</h5>
									<p className="cl-ellipsis">외부 서비스업체 사정에 따라 변경 또는 취소의 여지가 있을 수도 있습니다.</p>
								</div>
								<Link to={'/reservation/0'} className="cl-reservation__list-item-bullet">
									<img src={ReserveIcPlus} alt=""/>
									<span>예약하기</span>
								</Link>
							</div>
						</div>
					</li>
					<li className="cl-reservation__list-item">
						<div>
							<div className="cl-flex-between">
								<img src={ReserveServiceFood} alt="" className="cl-reservation__list-item-type-img"/>
								<div className="cl-reservation__list-item-text">
									<h5>음식배달</h5>
									<p className="cl-ellipsis">배달 1일전 예약가능</p>
								</div>
								<Link to={'/reservation/0'} className="cl-reservation__list-item-bullet">
									<img src={ReserveIcPlus} alt=""/>
									<span>예약하기</span>
								</Link>
							</div>
						</div>
					</li>
					<li className="cl-reservation__list-item">
						<div>
							<div className="cl-flex-between">
								<img src={ReserveServiceCarwash} alt="" className="cl-reservation__list-item-type-img"/>
								<div className="cl-reservation__list-item-text">
									<h5>세차서비스</h5>
									<p className="cl-ellipsis">배달 1일전 예약가능</p>
								</div>
								<Link to={'/reservation/0'} className="cl-reservation__list-item-bullet">
									<img src={ReserveIcPlus} alt=""/>
									<span>예약하기</span>
								</Link>
							</div>
						</div>
					</li>
				</ul>
			</div>
		)
	}
}


export default withRouter(ReservationList);