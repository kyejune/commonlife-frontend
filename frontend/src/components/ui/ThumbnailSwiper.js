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
				<div className="swiper-slide--1">Slide 1</div>
				<div className="swiper-slide--2">Slide 2</div>
				<div className="swiper-slide--3">Slide 3</div>
				<div className="swiper-slide--4">Slide 4</div>
				<div className="swiper-slide--5">Slide 5</div>
			</Swiper>
		)
	}
}

export default observer(withRouter(ThumbnailSwiper));
