import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { Avatar, Button, Card, CardActions, CardText, CardTitle, Media, MediaOverlay } from 'react-md';
import { Link } from 'react-router-dom';

class CardItem extends Component {

	// 피드, 뉴스 작성자 정보
	cardAuthorInfo = ()=> {
		if( this.props.cardData.type !== 'event' ) {
			return (
				<CardTitle
					// 작성자 이름
					title={this.props.cardData.author.name}
					// 작성자 프로필 이미지
					avatar={<Avatar src={this.props.cardData.author.avatar} role="presentation"/>}
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
	cardEventTitle = ()=> {
		if( this.props.cardData.type === "event" ) {
			return (
				<div>
					<Media>
						<img src={this.props.cardData.thumbnail} alt="이벤트 썸네일"/>
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
	cardEventContent = ()=> {
		if( this.props.cardData.type === "event" ) {
			return (
				<div className="cl-card-item__event-content">
					{/*이벤트 장소*/}
					<h3>{this.props.cardData.event_schedule}</h3>
					{/*이벤트 일정*/}
					<p>{this.props.cardData.event_location}</p>
				</div>
			);
		}
		else {
			return '';
		}
	};

	// 피드, 뉴스 컨텐츠
	cardTextContent = ()=> {
		if( this.props.cardData.type !== "event" ) {
			return (
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
	cardShare = ()=> {
		if( this.props.cardData.type !== "feed" ) {
			return (
				<Button flat className="cl-card-item__button"><span>Share</span></Button>
			);
		}
		else {
			return '';
		}
	};

	// RSVP
	cardRsvp = ()=> {
		if( this.props.cardData.type === 'event' ) {
			return (
				<Button flat className="cl-icon cl-card-item__rsvp"/>
			);
		}
		else {
			return '';
		}
	};

	render () {

		if( this.props.cardData ) {
			return (
				<div className="cl-card-item">
					<Card style={{ maxWidth: "600px" }} className="md-block-centered">
						{/* 작성자 정보 - 피드, 뉴스 */}
						{this.cardAuthorInfo()}

						<Link to={this.props.list + '/view/' + this.props.cardData.index }>
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
							<div className="h-group">
								<div className="col">
									{/* Like */}
									<Button flat className="cl-card-item__button"><span>Like</span>
                                        <Link to={this.props.list + '/like/' + 0 + '?count=' + this.props.cardData.like_count}>
                                            <i>{this.props.cardData.like_count}</i>
                                        </Link>
                                    </Button>

									{/* Share */}
									{this.cardShare()}
								</div>
								<div className="col right">
									{/* RSVP */}
									{this.cardRsvp()}
								</div>
							</div>
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
