import React, { Component } from 'react';

/* 예약하기 메인 페이지 및 상세 페이지에서 좌측 타이틀과, 우측 셀렉박스 구성 */
class SelectWithTitle extends Component {


	render () {

		return <div className="cl-flex-between cl-select-with-title">
			<h5>그룹4</h5>
			<div className="cl-select-with-title__select">
				<select name="" id="">
					<option value="">선택지역보기</option>
					<option value="">1</option>
					<option value="">2</option>
					<option value="">3</option>
					<option value="">4</option>
				</select>
			</div>
		</div>

	}

}

export default SelectWithTitle