import React, { Component } from 'react';
import clRightCaretSrc from 'images/ic-favorite-24-px@3x.png';

class LikeList extends Component{

	render() {
		return <div>
			<div className="cl-flex-between cl-like__item">
				<div className="cl-like__avatar"><img src={this.props.likeData.avatar} alt=""/></div>
				<div className="cl-like__name">{this.props.likeData.name}</div>
				<div className="cl-like__place">{this.props.likeData.place}</div>
				<div className="cl-like__arrow"><img src={clRightCaretSrc} height="24"/></div>
			</div>
			<hr className="cl-like__hr"/>
		</div>
	}

}

export default LikeList;