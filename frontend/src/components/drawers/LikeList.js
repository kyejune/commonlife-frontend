import React, { Component } from 'react';

class LikeList extends Component{

	render() {
		return <div>
			<div className="cl-flex-between">
				<div>{this.props.likeData.avatar}</div>
				<div>{this.props.likeData.name}</div>
				<div>{this.props.likeData.place}</div>
			</div>
		</div>
	}

}

export default LikeList;