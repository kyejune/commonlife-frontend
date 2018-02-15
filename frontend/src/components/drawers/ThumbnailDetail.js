import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';
import ThumbnailSwiper from 'components/ui/ThumbnailSwiper';


class ThumbnailDetail extends Component{

	componentWillMount(){
		// ContentHolder에 전달
		this.props.updateTitle(
			<h2 className="md-title md-title--toolbar cl-ellipsis">
				<span>이미지상세보기 (0/0)</span>
			</h2>
		);
	}

	render(){

		return (
			<div className="cl-thumbnail-detail">
				<ThumbnailSwiper/>
			</div>
		)
	}
}


export default observer(withRouter(ThumbnailDetail));