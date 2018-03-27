import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { Avatar, Card, CardActions, CardText, CardTitle, Media, MediaOverlay } from 'react-md';
import { Link } from 'react-router-dom';
import LikeShareAndSome from "components/ui/LikeShareAndSome";
import Store from 'scripts/store.js';
import reactStringReplace from 'react-string-replace';

class CardItem extends Component {

	constructor( props ){
		super( props );

		this.state = props.cardData;
	}

    componentWillReceiveProps(nextProps){
		if( this.state.postIdx !== nextProps.cardData.postIdx )
        	this.setState( nextProps.cardData );
    }

    onChangeLike=( likeCount, hasLiked )=>{
		let obj = Object.assign({}, this.state );
		obj.likesCount = likeCount;
		obj.myLikeFlag = hasLiked;

		this.setState( obj );
	}

	// 카드 종류
	cardName = ()=> {
		if( this.state.postType === 'brand') {
			return 'cl-card-item cl-card-item--brand';
		}
		else {
			return 'cl-card-item';
		}
	};

	// 피드, 뉴스 작성자 정보
	cardAuthorInfo = ()=> {
		if( this.state.postType !== 'event' ) {
			return (
				<CardTitle
					// 작성자 이름
					title={this.state.user.userNm}
					// 작성자 프로필 이미지
					avatar={<Avatar src={this.state.user.avatar} role="presentation"/>}
					// 작성 시간
					subtitle={ this.state.regDttm }
				/>
			);
		}
		else {
			return '';
		}
	};

	// 이벤트 썸네일, 제목
	cardEventTitle = ()=> {
		if( this.state.postType === "event" ) {
			return (
				<div>
					<Media>
						<img src={this.state.thumbnail} alt="이벤트 썸네일"/>
						<MediaOverlay>
							<CardTitle
								title={this.state.title || '이벤트 제목' }
							/>
						</MediaOverlay>
					</Media>
				</div>
			);
		}
		if( this.state.postType === "brand" ) {
			return (
				<div>
					<Media>
						<img src={ Store.api + this.state.postFiles[0].filePath } alt="이벤트 썸네일"/>
						<MediaOverlay>
							<CardTitle
								// 이벤트 제목
								title={this.state.title}
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
		if( this.state.postType === "event" ) {
			return (
				<div className="cl-card-item__event-content">
					{/*이벤트 장소*/}
					<h3>{this.state.event_schedule}</h3>
					{/*이벤트 일정*/}
					<p>{this.state.event_location}</p>
				</div>
			);
		}
		else {
			return '';
		}
	};

	// 피드, 뉴스 컨텐츠
	cardTextContent = ()=> {
		if( this.state.postType !== "event" ) {

			return (
				<p>
					{reactStringReplace( this.state.content, /(\n)/g, ( match, index, offset )=>{
						return <br key={index} />;
					})}
				</p>
			);
		}
		else {
			return '';
		}
	};


	render () {

		if( this.state ) {
			return (
				<div className={this.cardName()}>
					<Card style={{ maxWidth: "600px" }} className="md-block-centered">
						{/* 작성자 정보 - 피드, 뉴스 */}
						{this.cardAuthorInfo()}

						<Link to={this.props.list + '/' + this.state.postIdx }>
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
								like={ { to:this.props.list + '/' + this.state.postIdx + '/like', count:this.state.likesCount, liked:this.state.myLikeFlag } }
								share={ this.state.postType !== 'feed' }
								onChangeLike={ this.onChangeLike }
							/>
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
