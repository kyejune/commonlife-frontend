import React, { Component } from 'react';
import Swiper from 'react-id-swiper';
import { withRouter } from 'react-router';
import {observer} from 'mobx-react';

class ThumbnailSwiper extends Component {

	render() {
		const params = {
			pagination: {
				el: '.swiper-pagination',
				clickable: true
			}
		};

		return (
			<Swiper {...params}>
				<div className="swiper-slide--1" style={{ backgroundImage: `url('https://placeimg.com/640/480/arch')` }}/>
				<div className="swiper-slide--2" style={{ backgroundImage: `url('https://placeimg.com/480/640/nature')` }}/>
				<div className="swiper-slide--3" style={{ backgroundImage: `url('https://placeimg.com/300/300/tech')` }}/>
				<div className="swiper-slide--4" style={{ backgroundImage: `url('https://placeimg.com/640/480/animals')` }}/>
				<div className="swiper-slide--5" style={{ backgroundImage: `url('https://placeimg.com/640/480/arch')` }}/>
			</Swiper>
		)
	}
}

export default observer(withRouter(ThumbnailSwiper));
