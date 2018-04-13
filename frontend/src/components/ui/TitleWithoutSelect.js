import React, { Component } from 'react';

/* 예약하기 메인 페이지 및 상세 페이지에서 좌측 타이틀과, 우측 셀렉박스 구성 */
class TitleWithoutSelect extends Component {


	render () {

		return <div className="cl-flex-between cl-select-with-title">
			<h5>{ this.props.label || '그룹' } { this.props.displayLength || 0 }</h5>
		</div>

	}

}

export default TitleWithoutSelect;