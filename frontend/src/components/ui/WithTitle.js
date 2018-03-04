import React, { Component } from 'react';

/* 홈 iot 페이지 좌측 타이틀 구성 */
class WithTitle extends Component {


	render () {

		return <div className="cl-flex-between cl-select-with-title">
			<h5>{ this.props.titleName }</h5>
		</div>

	}

}

export default WithTitle