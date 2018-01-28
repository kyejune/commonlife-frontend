import React, {Component} from 'react';
import { Button } from 'react-md';
import { Link } from 'react-router-dom';
import qaSrc from 'images/contact-bt-gray@3x.png';
import calSrc from 'images/calender-bt-gray@3x.png';


export default class LikeShareAndSome extends Component{



    render(){

        let Share, Some;
        if( this.props.share )
            Share = <Button flat inkDisabled className="cl-card-item__button"><span>Share</span></Button>;


        if( this.props.join )
            Some = <Button flat inkDisabled className="cl-icon cl-card-item__rsvp"/>;

        else if( this.props.qa )
            Some = <Button inkDisabled className="cl-mrn--32" iconEl={<img src={qaSrc} alt="문의하기" height="36"/>}/>;

        else if( this.props.schedule )
            Some = <Button inkDisabled className="cl-mrn--32" iconEl={<img src={calSrc} alt="달력에추가" height="36"/>}/>;


        return <div className="cl-flex-between">

            <div>

                <Button flat inkDisabled className="cl-card-item__button"><span>Like</span>
                    <Link to={this.props.like.to}>
                        <i>{this.props.like.count||0}</i>
                    </Link>
                </Button>

                {Share}

            </div>

            {Some}

        </div>

    }


}