import React, {Component} from 'react';
import { Button } from 'react-md';
import { Link } from 'react-router-dom';
import qaSrc from 'images/contact-bt-gray@3x.png';
import calSrc from 'images/calender-bt-gray@3x.png';
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
		let options = {
			message: 'share this', // not supported on Facebook, Instagram
			subject: 'the subject', // fi. for email
			url: 'https://www.website.com/foo/#bar?a=b',
			chooserTitle: 'Pick an app' // Android only
		};

		window.plugins.socialsharing.shareWithOptions(options);
	}

	likeItem( id ){
        Net.setLikey( id, !this.state.liked, response =>{

            if( this.props.onChangeLike )
                this.props.onChangeLike( response.likeCount, response.myLikeFlag );

        });
    }

    render(){

        let Share, Some;
        if( this.props.share )
            Share = <button onClick={() => this.shareItem()}>SHARE</button>;


        if( this.props.join )
            Some = <Button flat inkDisabled className="cl-icon cl-card-item__rsvp"/>;

        else if( this.props.qa )
            Some = <button>
                <img className="ml-1em" src={qaSrc} alt="문의하기" width="118" height="36"/>
            </button>;

        else if( this.props.schedule )
            Some = <button>
                <img src={calSrc} alt="달력에추가" height="36"/>
            </button>;


        return <div className="cl-flex-between cl-like-share">

            <div className="cl-flex">

                <button className={ classNames("cl-card-item__button", "cl-like__button", { "cl-like--liked":this.state.liked } )}
                        onClick={ ()=> this.likeItem( this.props.like.to.match(/\d+/)[0]) }>LIKE</button>

                {this.state.count > 0 &&
                <Link className="cl-counter__button cl-flex" to={this.props.like.to}>
                    <span className="w-100">
                    {this.state.count}
                    </span>
                </Link>
                }

                {Share}

            </div>

            {Some}

        </div>

    }


}