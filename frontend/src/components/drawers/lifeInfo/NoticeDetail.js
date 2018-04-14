import React, { Component } from 'react';
import TagComponent from "../../ui/TagComponent";
import Net from "../../../scripts/net";
import moment from "moment";
import Store from "../../../scripts/store";
   
class NoticeDetail extends Component {

    constructor(props) {
        super(props);

        this.state = {
            content:'',
            user:{},
            regDttm: null,
        }
    }


    componentDidMount(){

        Net.getNoticeDetail( data => {
            this.setState( data );
        });

    }


    render() {

        let userImgStyle;
        if( this.state.user.imgSrc )
            userImgStyle = {backgroundImage:`url(${this.state.user.imgSrc})`};

        return (
            <div className="cl-notice-detail">
                <p className="cl-notice-detail__content">
                    <TagComponent content={ this.state.content }/>
                </p>

                <div className="cl-card-item--dark cl-flex mb-3em">
                    <div className="cl-avatar" style={userImgStyle}/>
                    <div>
                        <h6 className="cl-name">{ this.state.user.userNm }</h6>
                        <p className="cl-desc">{ `${this.state.user.cmplxNm}, ${moment(this.state.regDttm).fromNow()}` }</p>
                    </div>
                </div>
            </div>
        );
    }
}

    
export default NoticeDetail;
