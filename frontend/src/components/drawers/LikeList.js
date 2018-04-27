import React, {Component} from 'react';
import {withRouter} from 'react-router';
import Store from "../../scripts/store";
import clRightCaretSrc from 'images/ic-favorite-24-px@3x.png';
import {Link} from "react-router-dom";

class LikeList extends Component{

    // "user": {
    //     "usrId": 14,
    //     "userClass": null,
    //     "kind": "AD00501",
    //     "userId": "benit204",
    //     "userNm": "홍길동",
    //     "cell": "010-2584-3958",
    //     "email": "youknow@kolon.com",
    //     "cellYn": "Y",
    //     "emailYn": "a",
    //     "liveYn": "N",
    //     "inDt": null,
    //     "outDt": null,
    //     "reDt": null,
    //     "smsChkYn": "Y",
    //     "smsChkNo": null,
    //     "smsChkDt": null,

	render() {

        let userThumb = {}
        if ( this.props && this.props.imgSrc )
            userThumb.backgroundImage = `url(${this.props.imgSrc})`;

		return <div className="cl-like-item">
			<div className="cl-flex">
				<div className="cl-avatar" style={userThumb}/>
				<div className="cl-like__name">{this.props.userNm}</div>
				<div className="cl-like__place">{this.props.cmplxNm}</div>

				<Link className="cl-like__arrow ml-auto" to={`?profile=${this.props.usrId}`}>
					<img src={clRightCaretSrc} height="24" alt="프로필 가기 버튼"/>
				</Link>
			</div>
			<hr className="cl-like__hr"/>
		</div>
	}
}

export default withRouter(LikeList);