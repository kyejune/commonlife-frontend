import React, { Component } from 'react';
import CreditIcRed from 'images/alert-icon-red@3x.png';
import CreditIcTimes from 'images/ic-history-24-px-white@3x.png';

class NoCreditPopup extends Component {

	render() {
		return <div>
			<div className="cl-credit-popup">
				<button type="button" className="cl-credit-popup__btn-close"><img src={CreditIcTimes} alt=""/></button>
				<div className="cl-credit-popup__content">
					<h3 className="cl-credit-popup__title">
						<i><img src={CreditIcRed} alt=""/></i><br/>
						<span>사용가능 크레딧이 부족합니다.</span>
					</h3>

					<p className="cl-credit-popup__need">필요 크레딧 : <span>2.0</span></p>
					<p className="cl-credit-popup__remaining">잔여 크레딧 : <span>1.0</span></p>
				</div>

				<div className="cl-credit-popup__btn-wrap">
					<div className="cl-credit-popup__btn--request">추가 크레딧 신청</div>
					<div className="cl-credit-popup__btn--submit">확인</div>
				</div>
			</div>
		</div>
	}

}

export default NoCreditPopup