import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { Avatar, Card, CardActions, CardText, CardTitle, Media, MediaOverlay } from 'react-md';
import { Link } from 'react-router-dom';
import LikeShareAndSome from "components/ui/LikeShareAndSome";
import Store from 'scripts/store.js';

class CardItem extends Component {

	constructor( props ){
		super( props );

		this.user = props.cardData.user;
		this.user = props.cardData.user;
		this.data = props.cardData;
	}


	// 카드 종류
	cardName = ()=> {
		if( this.data.postType === 'brand') {
			return 'cl-card-item cl-card-item--brand';
		}
		else {
			return 'cl-card-item';
		}
	};

	// 피드, 뉴스 작성자 정보
	cardAuthorInfo = ()=> {
		if( this.data.postType !== 'event' ) {
			return (
				<CardTitle
					// 작성자 이름
					title={this.user.userNm}
					// 작성자 프로필 이미지
					avatar={<Avatar src={this.user.avatar} role="presentation"/>}
					// 작성 시간
					subtitle={ this.data.regDttm }
				/>
			);
		}
		else {
			return '';
		}
	};

	// 이벤트 썸네일, 제목
	cardEventTitle = ()=> {
		if( this.data.postType === "event" ) {
			return (
				<div>
					<Media>
						<img src={this.data.thumbnail} alt="이벤트 썸네일"/>
						<MediaOverlay>
							<CardTitle
								// 이벤트 제목
								title={this.data.title}
							/>
						</MediaOverlay>
					</Media>
				</div>
			);
		}
		if( this.data.postType === "brand" ) {
			return (
				<div>
					<Media>
						<img src={ Store.api + this.data.postFiles[0].filePath } alt="이벤트 썸네일"/>
						<MediaOverlay>
							<CardTitle
								// 이벤트 제목
								title={this.data.title}
							/>
						</MediaOverlay>
					</Media>
				</div>
			);
		}
		else {
			return '';
		}
	};

	// 이벤트 장소, 일정
	cardEventContent = ()=> {
		if( this.data.postType === "event" ) {
			return (
				<div className="cl-card-item__event-content">
					{/*이벤트 장소*/}
					<h3>{this.data.event_schedule}</h3>
					{/*이벤트 일정*/}
					<p>{this.data.event_location}</p>
				</div>
			);
		}
		else {
			return '';
		}
	};

	// 피드, 뉴스 컨텐츠
	cardTextContent = ()=> {
		if( this.data.postType !== "event" ) {
			return (
				<p>
					{this.data.content}
				</p>
			);
		}
		else {
			return '';
		}
	};


	render () {

		if( this.data ) {
			return (
				<div className={this.cardName()}>
					<Card style={{ maxWidth: "600px" }} className="md-block-centered">
						{/* 작성자 정보 - 피드, 뉴스 */}
						{this.cardAuthorInfo()}

						<Link to={this.props.list + '/' + this.data.postIdx }>
							{/* 이벤트 썸네일, 제목 */}
							{this.cardEventTitle()}

							<CardText>
								{/* 이벤트 장소, 일정 */}
								{this.cardEventContent()}

								{/*내용*/}
								{this.cardTextContent()}
							</CardText>
						</Link>

						<hr/>

						<CardActions>
							{/* schedule, qa에 관한 데이터는 아직 기준이 명확하지 못해서 임시로 지정 */}
							<LikeShareAndSome
								like={ { to:this.props.list + '/' + this.data.postIdx + '/like', count:this.data.likesCount } }
								share={this.data.postType !== 'feed' } />
						</CardActions>
					</Card>
				</div>
			)
		}
		else {
			return '';
		}
	}
}

export default withRouter( CardItem );
