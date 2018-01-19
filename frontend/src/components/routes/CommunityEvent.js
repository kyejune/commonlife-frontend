import React, { Component } from 'react';
import CardItem from 'components/ui/CardItem';

class CommunityEvent extends Component {
	constructor ( props ) {
		super( props );

		this.state = {
			cardData: [
				{
					index: '3',
					type: 'event',
					author: {
						name: '한초코',
						avatar: 'https://placeimg.com/100/100/people',
						place: '역삼하우스',
					},
					thumbnail: 'https://placeimg.com/640/480/people',
					like_count: '10',
					event_title: '모임합니다',
					event_schedule: '2018-01-12 00:00:00',
					event_location: '모임공간',
					rsvp_count: '0',
					created_at: '2018-01-09 00:00:00',
				},
				{
					index: '4',
					type: 'event',
					author: {
						name: '조은풀',
						avatar: 'https://placeimg.com/100/100/people',
						place: '역삼하우스1',
					},
					thumbnail: 'https://placeimg.com/640/480/people',
					like_count: '11',
					event_title: '파티합니다',
					event_schedule: '2018-01-12 00:00:00',
					event_location: '파티룸',
					rsvp_count: '0',
					created_at: '2018-01-09 00:00:00',
				}
			]
		}
	}

	render () {
		return (
			<div className="cl-fitted-box">

				<div style={this.state.style}>
					<h2>
						커뮤니티 이벤트
					</h2>

					컨텐츠 내용
					{ this.state.cardData.map( ( card, index ) => {
						return (
							<CardItem key={index} cardData={card}/>
						)
					} ) }
				</div>
			</div>
		)
	}
}

export default CommunityEvent;