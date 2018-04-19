import React, {Component} from 'react';
import {withRouter} from 'react-router';
import { Link } from 'react-router-dom';
import nl2br from 'react-nl2br';

import ReserveGroupHome from 'images/r-icon-1@3x.png';
import ReserveServiceLaundry from 'images/rs-icon-1@3x.png';
import ReserveServiceCleaning from 'images/rs-icon-2@3x.png';
import ReserveServiceFood from 'images/rs-icon-3@3x.png';
import ReserveServiceCarwash from 'images/rs-icon-4@3x.png';
import ReserveIcPlus from 'images/page-1@3x.png';
import ReserveIcNext from 'images/shape-time-next@3x.png';
import ReserveIcTime from 'images/shape-time-plus@3x.png';
import ReserveIcAlert from 'images/alert-icon-red@3x.png';
import net from "../../scripts/net";
import TitleWithoutSelect from "../ui/TitleWithoutSelect";
import ReservationListItem from "./ReservationListItem";


class ReservationList extends Component{

	componentDidMount(){
		// ContentHolder에 전달
		this.props.updateTitle(
			<h2 className="md-title md-title--toolbar cl-ellipsis">
				<span>Reservation</span>
			</h2>
		);

		net.getReservationGroup( this.props.match.params.add, data => {
			this.setState( { group: data } );
		} );
	}

	render(){

		if( !this.state || !this.state.group ) {
			return '';
		}

		return (
			<div className="cl-reservation-list">
				<div className="cl-reservation-list__info">
					<div className="cl-reservation-list__info-credit">
						{/*예약가능 크레딧: <span>75.0</span>*/}
					</div>
					<div className="cl-flex-between">
						<img src={ReserveGroupHome} alt=""
							 className="cl-reservation-list__info-type-img"/>
						<div className="cl-reservation-list__info-text">
							<h4>{ this.state.group.title }</h4>
							<p className="cl-ellipsis">{ this.state.group.summary }</p>
						</div>
					</div>
					<p className="cl-reservation-list__info-paragraph">{ nl2br( this.state.group.description ) }</p>
				</div>

				{ ( this.state && this.state.group ) &&
                <TitleWithoutSelect label={ '서비스' } displayLength={ this.state.group.schemes.length } />
				}

				<ul className="cl-reservation__list--service">
					{
						this.state.group.schemes.map( ( scheme, key ) => {
							return <ReservationListItem scheme={ scheme } key={ key } />
						} )
					}
					{ 1 === 0 && <div>
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
                    </div> }
				</ul>
			</div>
		)
	}
}


export default withRouter(ReservationList);