import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { Avatar, Card, CardActions, CardText, CardTitle, Media, MediaOverlay } from 'react-md';
import { Link } from 'react-router-dom';
import LikeShareAndSome from "components/ui/LikeShareAndSome";

class CardItem extends Component {

	// 카드 종류
	cardName = ()=> {
		if( this.props.cardData.type === 'brand') {
			return 'cl-card-item cl-card-item--brand';
		}
		else {
			return 'cl-card-item';
		}
	};

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
		if( this.props.cardData.type === "brand" ) {
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


	render () {

		if( this.props.cardData ) {
			return (
				<div className={this.cardName()}>
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
							{/* schedule, qa에 관한 데이터는 아직 기준이 명확하지 못해서 임시로 지정 */}
							<LikeShareAndSome like={ { to:this.props.list + '/like/' + this.props.cardData.index, count:this.props.cardData.like_count } } share={this.props.cardData.type !== 'feed'} />
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
