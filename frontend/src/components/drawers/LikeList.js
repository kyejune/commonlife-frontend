import React, { Component } from 'react';
import { withRouter } from 'react-router';
import clRightCaretSrc from 'images/ic-favorite-24-px@3x.png';
import {Link} from 'react-router-dom';

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
		return <div>
			<div className="cl-flex-between cl-like__item">
				<div className="cl-like__avatar" style={{backgroundImage:`url(${this.props.avatar||'https://i.ytimg.com/vi/fUWrhetZh9M/maxresdefault.jpg'})`}}/>
				<div className="cl-like__name">{this.props.userNm || '아무개'}</div>
				<div className="cl-like__place">{this.props.branch || '?? 지점'}</div>
				<Link className="cl-like__arrow" to={`/profile/${this.props.usrId||1}`}><img src={clRightCaretSrc} height="24" alt="프로필 가기 버튼"/></Link>
			</div>
			<hr className="cl-like__hr"/>
		</div>
	}
}

export default withRouter(LikeList);