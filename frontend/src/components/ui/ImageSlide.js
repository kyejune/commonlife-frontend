import React, { Component } from 'react';

class ThumbnailSwiper extends Component {

	render () {

		return (

			<div className="swiper-slide" style={{ backgroundImage: `url('${this.props.slideData.thumbnail}')` }}/>

		)
	}
}

export default ThumbnailSwiper;
