import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';
import clRightCaretSrc from 'images/ic-favorite-24-px@3x.png';
import ReserveServiceLaundry from 'images/rs-icon-1@3x.png';
import ReserveServiceCleaning from 'images/rs-icon-2@3x.png';
import ReserveServiceFood from 'images/rs-icon-3@3x.png';
import ReserveServiceCarwash from 'images/rs-icon-4@3x.png';


class ReservationHistory extends Component{

	componentWillMount(){
		// ContentHolder에 전달
		this.props.updateTitle(
			<h2 className="md-title md-title--toolbar cl-ellipsis">
				<span>예약내역</span>
			</h2>
		);
	}

	render(){

		return (
			<div className="cl-reservation-history__list-wrap">
				<div className="cl-flex-between cl-reservation-history__item">
					<div className="cl-reservation-history__icon">
						<img src={ReserveServiceLaundry} alt=""/>
					</div>
					<div className="cl-reservation-history__content">
						<h3>세탁 서비스 <span>- 완료</span></h3>
						<p>
							역삼 하우징 / 조성우 / -2.0<br/>
							2017. 12. 24 (11:00오전 ~ 02:00오후)
						</p>
					</div>
					<div className="cl-reservation-history__arrow">
						<img src={clRightCaretSrc} height="24" alt="프로필 가기 버튼"/>
					</div>
				</div>
				<div className="cl-flex-between cl-reservation-history__item">
					<div className="cl-reservation-history__icon">
						<img src={ReserveServiceCleaning} alt=""/>
					</div>
					<div className="cl-reservation-history__content">
						<h3>세탁 서비스</h3>
						<p>
							역삼 하우징 / 조성우 / -2.0<br/>
							2017. 12. 24 (11:00오전 ~ 02:00오후)
						</p>
					</div>
					<div className="cl-reservation-history__arrow">
						<img src={clRightCaretSrc} height="24" alt="프로필 가기 버튼"/>
					</div>
				</div>
				<div className="cl-flex-between cl-reservation-history__item">
					<div className="cl-reservation-history__icon">
						<img src={ReserveServiceFood} alt=""/>
					</div>
					<div className="cl-reservation-history__content">
						<h3>계절창고</h3>
						<p>
							역삼 하우징 / 조성우 / -2.0<br/>
							2017. 12. 24 (11:00오전 ~ 02:00오후)
						</p>
					</div>
					<div className="cl-reservation-history__arrow">
						<p>대기요청 <span>3</span></p>
						<img src={clRightCaretSrc} height="24" alt="프로필 가기 버튼"/>
					</div>
				</div>
				<div className="cl-flex-between cl-reservation-history__item">
					<div className="cl-reservation-history__icon">
						<img src={ReserveServiceCarwash} alt=""/>
					</div>
					<div className="cl-reservation-history__content">
						<h3>세탁 서비스</h3>
						<p>
							역삼 하우징 / 조성우 / -2.0<br/>
							2017. 12. 24 (11:00오전 ~ 02:00오후)
						</p>
					</div>
					<div className="cl-reservation-history__arrow">
						<img src={clRightCaretSrc} height="24" alt="프로필 가기 버튼"/>
					</div>
				</div>
			</div>
		)
	}
}


export default observer(withRouter(ReservationHistory));