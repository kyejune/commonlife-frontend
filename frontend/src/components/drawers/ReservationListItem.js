import React, { Component } from 'react';

import ReserveGroupHome from 'images/r-icon-1@3x.png';
import ReserveServiceLaundry from 'images/rs-icon-1@3x.png';
import ReserveServiceCleaning from 'images/rs-icon-2@3x.png';
import ReserveServiceFood from 'images/rs-icon-3@3x.png';
import ReserveServiceCarwash from 'images/rs-icon-4@3x.png';
import ReserveIcPlus from 'images/page-1@3x.png';
import ReserveIcNext from 'images/shape-time-next@3x.png';
import ReserveIcTime from 'images/shape-time-plus@3x.png';
import ReserveIcAlert from 'images/alert-icon-red@3x.png';
import { Link } from "react-router-dom";

class ReservationListItem extends Component {
	render() {
		return <li className="cl-reservation__list-item cl-reservation__notice">
			<div>
				<div className="cl-flex-between">
					{ this.props.scheme.icon === 'CLEANING' &&
					<img src={ReserveServiceCleaning} alt=""
						 className="cl-reservation__list-item-type-img"/>
					}
					{ this.props.scheme.icon === 'LAUNDRY' &&
					<img src={ReserveServiceLaundry} alt=""
						 className="cl-reservation__list-item-type-img"/>
					}
					{ this.props.scheme.icon === 'FOOD' &&
					<img src={ReserveServiceFood} alt=""
						 className="cl-reservation__list-item-type-img"/>
					}
					{ this.props.scheme.icon === 'CARWASH' &&
					<img src={ReserveServiceCarwash} alt=""
						 className="cl-reservation__list-item-type-img"/>
					}
					<div className="cl-reservation__list-item-text">
						<h5>{ this.props.scheme.title }</h5>
						<p className="cl-ellipsis">{ this.props.scheme.summary }</p>
					</div>
					{this.props.scheme.isExpress !== 'Y' &&
					<Link to={'/reservation/' + this.props.scheme.idx} className="cl-reservation__list-item-bullet">
						<img src={ReserveIcPlus} alt=""/>
						<span>예약하기</span>
					</Link>
					}
				</div>
				{ this.props.scheme.isExpress === 'Y' &&
				<div className="cl-flex-between cl-reservation-list__link">
					<Link to={'/reservation/' + this.props.scheme.idx} className="cl-reservation__list-item-bullet">
						<img src={ReserveIcPlus} alt=""/>
						<span>예약하기</span>
					</Link>
					<Link to={'/reservation/' + this.props.scheme.idx} className="cl-reservation__list-item-bullet">
						<img src={ReserveIcNext} alt=""/>
						<span>내일예약</span>
					</Link>
					<Link to={'/reservation/' + this.props.scheme.idx} className="cl-reservation__list-item-bullet">
						<img src={ReserveIcTime} alt=""/>
						<span>날짜지정</span>
					</Link>
				</div>
				}
			</div>
		</li>;
	}
}

export default ReservationListItem;