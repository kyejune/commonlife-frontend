import React, { Component } from 'react';
import { Avatar, Button, Card, CardActions, CardText, CardTitle, Media, MediaOverlay } from 'react-md';

class CardItem extends Component{

	// 피드, 뉴스 작성자 정보
	cardAuthorInfo = () => {
		if( this.props.cardData.type !== 'event' ) {
			return(
				<CardTitle
					// 작성자 이름
					title={this.props.cardData.author.name}
					// 작성자 프로필 이미지
					avatar={<Avatar src={this.props.cardData.author.avatar} role="presentation" />}
					// 작성 시간
					subtitle={this.props.cardData.created_at}
				/>
			);
		}
		else {
			return '';
		}
	};

	// 이벤트 썸네일, 제목
	cardEventTitle = () => {
		if( this.props.cardData.type === "event" ) {
			return (
				<div>
					<Media>
						<img src={this.props.cardData.thumbnail}/>
						<MediaOverlay>
							<CardTitle
								// 이벤트 제목
								title={this.props.cardData.event_title}
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
	cardEventContent = () => {
		if( this.props.cardData.type === "event" ) {
			return(
				<div>
					{/*이벤트 장소*/}
					<div>{this.props.cardData.event_schedule}</div>
					{/*이벤트 일정*/}
					<div>{this.props.cardData.event_location}</div>
				</div>
			);
		}
		else {
			return '';
		}
	};

	// 피드, 뉴스 컨텐츠
	cardTextContent = () => {
		if( this.props.cardData.type !== "event" ) {
			return(
				<p>
					{this.props.cardData.content}
				</p>
			);
		}
		else {
			return '';
		}
	};

	// Share
	cardShare = () => {
		if( this.props.cardData.type !== "feed" ) {
			return(
				<Button flat>Share</Button>
			);
		}
		else {
			return '';
		}
	};

	// RSVP
	cardRsvp = () => {
		if( this.props.cardData.type === 'event' ) {
			return(
				<Button flat>+rsvp</Button>
			);
		}
		else {
			return '';
		}
	};

	render(){

		if( this.props.cardData ) {
			return(
				<Card style={{ maxWidth: "600px" }} className="md-block-centered">
					{/* 작성자 정보 - 피드, 뉴스 */}
					{ this.cardAuthorInfo() }

					{/* 이벤트 썸네일, 제목 */}
					{ this.cardEventTitle() }

					<CardText>
						{/* 이벤트 장소, 일정 */}
						{ this.cardEventContent() }

						{/*내용*/}
						{ this.cardTextContent() }
					</CardText>
					<CardActions>
						{/* Like */}
						<Button flat>Like <i>{this.props.cardData.like_count}</i></Button>

						{/* Share */}
						{ this.cardShare() }

						{/* RSVP */}
						{ this.cardRsvp() }
					</CardActions>
				</Card>
			)
		}
		else {
			return '';
		}
	}
}

export default CardItem;
