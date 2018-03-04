import React, {Component} from 'react';
import { Button } from 'react-md';
import { Link } from 'react-router-dom';
import qaSrc from 'images/contact-bt-gray@3x.png';
import calSrc from 'images/calender-bt-gray@3x.png';
import Net from 'scripts/net.js';


export default class LikeShareAndSome extends Component{

    constructor( props ){
        super( props );

        this.state = {
            count: props.like.count || 0
        }
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
        Net.setLikey( id, response =>{
            this.setState({
                count: response.likeIdx
            })
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

                <button className="cl-card-item__button cl-like__button" onClick={ ()=> this.likeItem( this.props.like.to.match(/\d+/)[0]) }>LIKE</button>
                <Link className="cl-counter__button" to={this.props.like.to}>
                    {this.state.count}
                </Link>

                {Share}

            </div>

            {Some}

        </div>

    }


}