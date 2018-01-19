import React, { Component } from 'react';
import { Button } from 'react-md';
import WriteDrawer from "components/WriteDrawer";
import CardItem from 'components/ui/CardItem';

class CommunityFeed extends Component {

	constructor ( props ) {
		super( props );

		this.state = {
			style: {
				height: 3000,
				width: '100%',
				backgroundColor: 'orange',
			},

			isWrite: false,

			cardData: [
				{
					index: '1',
					type: 'news',
					author: {
						name: '김딸기',
						avatar: 'https://placeimg.com/100/100/people',
						place: '역삼하우스',
					},
					content: '오늘의 뉴스 안녕하세요. 반갑습니다.',
					like_count: '10',
					created_at: '2018-01-09 00:00:00',
				},
				{
					index: '2',
					type: 'feed',
					author: {
						name: '김용',
						avatar: 'https://placeimg.com/100/100/people',
						place: '역삼하우스',
					},
					content: '안녕하세요. 반갑습니다.',
					thumbnail: 'https://placeimg.com/640/480/people',
					like_count: '10',
					event_title: '모임합니다',
					event_schedule: '2018-01-12 00:00:00',
					event_location: '모임공간',
					rsvp_count: '0',
					created_at: '2018-01-09 00:00:00',
				},
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
				},
				{
					index: '6',
					type: 'feed',
					author: {
						name: '신쪼리',
						avatar: 'https://placeimg.com/100/100/people',
						place: '역삼하우스1',
					},
					content: '안녕하세요. 반갑습니다.1',
					thumbnail: 'https://placeimg.com/640/480/people',
					like_count: '11',
					event_title: '모임합니다1',
					event_schedule: '2018-01-12 00:00:00',
					event_location: '모임공간',
					rsvp_count: '0',
					created_at: '2018-01-09 00:00:00',
				}
			]
		}
	}

	onWriteChange ( isWrite ) {

		console.log( 'FEED change:', isWrite )

		this.setState( {
			isWrite: isWrite,
		} )
	}


	render () {
		return (
			<div className="cl-fitted-box">

				<div style={this.state.style}>
					<h2>
						커뮤니티 피드
					</h2>

					컨텐츠 내용
					{ this.state.cardData.map( ( card, index ) => {
						return (
							<CardItem key={index} cardData={card}/>
						)
					} ) };
				</div>


				<Button floating primary
						iconClassName="fa fa-pencil fa-2x"
						className="cl-write__button--fixed"
						onClick={()=>this.onWriteChange( true )}
				/>

				<WriteDrawer
					visible={this.state.isWrite}
					onWriteChange={( value )=>this.onWriteChange( value )}/>
			</div>
		)
	}
}

export default CommunityFeed;