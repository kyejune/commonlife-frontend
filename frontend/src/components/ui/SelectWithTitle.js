import React, { Component } from 'react';
import Store from "../../scripts/store";
import {observer} from "mobx-react";

/* 예약하기 메인 페이지 및 상세 페이지에서 좌측 타이틀과, 우측 셀렉박스 구성 */
class SelectWithTitle extends Component {


	render () {

		return <div className="cl-flex-between cl-select-with-title">
			<h5>그룹4</h5>
			<div className="cl-select-with-title__select">
				<select name="" id="">
					<option value="">선택지역보기</option>
					{
						Store.complexes.map( ( item, key ) => {
							return <option key={ key } value={ item.cmplxId }>{ item.cmplxNm }</option>
						} )
					}
				</select>
			</div>
		</div>

	}

}

SelectWithTitle = observer( SelectWithTitle );

export default SelectWithTitle;