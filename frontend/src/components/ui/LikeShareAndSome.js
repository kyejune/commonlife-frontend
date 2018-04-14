import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import qaSrc from 'images/contact-bt-gray@3x.png';
import calSrc from 'images/calender-bt-gray.svg';
import joinSrc from 'images/rsvp-normal@3x.png';
import joinedSrc from 'images/rsvp-activity@3x.png';
import Net from 'scripts/net.js';
import classNames from 'classnames';

export default class LikeShareAndSome extends Component{

    constructor( props ){
        super( props );

        this.state = {
            count: props.like.count || 0,
            liked: props.like.liked,
        }
    }

    componentWillReceiveProps( nextProps ){

        this.setState({
            count: nextProps.like.count || 0,
            liked: nextProps.like.liked,

        });
    }

	shareItem() {

        const { title, content } = this.props.data;

		let options = {
			message: content, // not supported on Facebook, Instagram
			subject: title, // fi. for email
			url: null,//'https://www.website.com/foo/#bar?a=b',
			chooserTitle: 'CommonLife' // Android only
		};

		if( window.plugins )
		    window.plugins.socialsharing.shareWithOptions(options);
		else
		    console.log('공유', this.props.data );
	}

	likeItem( id ){
        Net.setLikey( id, !this.state.liked, response =>{

            if( this.props.onChangeLike )
                this.props.onChangeLike( response.likeCount, response.myLikeFlag );

        });
    }

    toggleJoin=( bool )=>{
        // join은 부모 컨덴츠에서 조절
        if( this.props.onChangeJoin )
            this.props.onChangeJoin( bool, this.props.data );
    }

    addCalendar=()=>{
        console.log( 'addCalendar', this.props.calendar );
        const { title, eventPlaceNm, eventCmplxNm, content } = this.props.data;

        if( window.plugins ) {
            window.plugins.calendar.createEventInteractively(
                title, (eventCmplxNm||'') + (eventPlaceNm||''), content,
                new Date(this.props.calendar[0]), new Date(this.props.calendar[1]),

                ()=>{
                    // alert('달력에 추가되었습니다.');
                },

                ()=>{
                    // alert('달력에 추가되지 않았습니다.');
                }
            );
        }
    }

    render(){

        let Share, Cal, Join, Joined, Qa;
        if( this.props.share )
            Share = <button className="cl-card-item__button" onClick={() => this.shareItem()}><span>SHARE</span></button>;

        if( this.props.join )
            Join = <button  className={classNames(
                "cl-card-item__button",
                {"cl--fulled": this.props.joinFulled })}
                            onClick={ ()=> this.toggleJoin( true ) }>
                <img src={joinSrc} alt="참석" height="30"/>
            </button>;

        if( this.props.joined )
            Joined = <button className="cl-card-item__button">
                <img src={joinedSrc} alt="참석취소" height="30" onClick={ ()=> this.toggleJoin( false )}/>
            </button>;

        if( this.props.qa )
            Qa = <a className="cl-card-item__button cl-flex" href={this.props.qa}>
                <img className="ml-1em" src={qaSrc} alt="문의하기" height="18"/>
            </a>;

        if( this.props.calendar )
            Cal = <button className="cl-card-item__button cl-card-item__button--cal">
                <img src={calSrc} alt="달력에추가" height="36" onClick={ this.addCalendar }/>
            </button>;


        return <div className="cl-flex-between cl-like-share">

            <div className="cl-flex">

                {/* Like 노출 */}
                <button className={ classNames("cl-card-item__button", "cl-like__button", { "cl-like--liked":this.state.liked } )}
                        onClick={ ()=> this.likeItem( this.props.like.to.match(/\d+/)[0]) }><span>LIKE</span></button>

                {this.state.count > 0 &&
                <Link className="cl-counter__button cl-flex" to={this.props.like.to}>
                    <span className="w-100">
                    {this.state.count}
                    </span>
                </Link>
                }


                {/* 공유기능 */}
                {Share}

            </div>

            {/* 문의하기, 달력에추가, 참석 */}
            <div className="cl-flex">
                {Cal}
                {Join || Joined}
                {Qa}
            </div>

        </div>

    }


}