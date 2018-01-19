import React, { Component } from 'react';
import CardItem from 'components/ui/CardItem';

class CommunityEvent extends Component {
	constructor ( props ) {
		super( props );

		this.state = {
			style: {
				height: 3000,
				width: '100%',
				backgroundColor: 'green',
			},
			cardData: [
				{
					index: '1',
					type: 'news',
					author: {
						name: '김딸기',
						avatar: 'https://placeimg.com/100/100/people',
						place: '역삼하우스',
					},
					content: '오늘의 뉴스/안녕하세요. 반갑습니다.',
					like_count: '10',
					created_at: '2018-01-09 00:00:00',
				},
				{
					index: '2',
					type: 'news',
					author: {
						name: '민자몽',
						avatar: 'https://placeimg.com/100/100/people',
						place: '역삼하우스',
					},
					content: '오늘의 뉴스/오늘은 중요한날입니다.. 반갑습니다.1',
					like_count: '11',
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
                        커뮤니티 뉴스
                    </h2>

					컨텐츠 내용
					{ this.state.cardData.map( ( card, index ) => {
						return (
                            <CardItem key={index} cardData={card}/>
						)
					} ) };
                </div>
            </div>
		)
	}
}

export default CommunityEvent;