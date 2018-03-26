import React, { Component } from 'react';
import { withRouter } from 'react-router';
import Swiper from 'react-id-swiper';
import ImageSlide from 'components/ui/ImageSlide';


class SwiperViewer extends Component {

	render () {
		const params = {
			pagination: {
				el: '.swiper-pagination',
				clickable: true
			}
		};

		return (
			<div className={this.props.viewType === 'ractangle' ? 'cl-swiper-viewer' : ''}>
				<Swiper {...params}>
					{ this.props.thumbnails.map( ( thumbnail, index ) => {
						return (
							<ImageSlide key={index} slideData={thumbnail}/>
						)
					} ) }
				</Swiper>
			</div>
		)
	}
}


export default withRouter( SwiperViewer );