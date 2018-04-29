import React, { Component } from 'react';
import Store from "../../scripts/store";
import {observer} from "mobx-react";

/* 예약하기 메인 페이지 및 상세 페이지에서 좌측 타이틀과, 우측 셀렉박스 구성 */
class SelectWithTitle extends Component {

	onChange = event => {
		if( this.props.onChange && typeof this.props.onChange === 'function' ) {
			this.props.onChange( event );
		}

		Store.alert('변경 완료되었습니다.');
	};

	render () {
		const selectedValue = this.props.selectedValue || -1;
		return <div className="cl-flex-between cl-select-with-title">
			<h5>그룹 { this.props.displayLength || 0 }</h5>
			<div className="cl-select-with-title__select">
				<select onChange={ this.onChange }>
					{
						Store.complexes.map( ( item, key ) => {
							if( selectedValue.toString() === item.cmplxId.toString() ) {
								return <option key={ key } value={ item.cmplxId } selected>{ item.cmplxNm }</option>
							}
							else {
								return <option key={ key } value={ item.cmplxId }>{ item.cmplxNm }</option>
							}
						} )
					}
				</select>
			</div>
		</div>

	}

}

SelectWithTitle = observer( SelectWithTitle );

export default SelectWithTitle;