import React, {Component} from 'react';
import {withRouter} from "react-router-dom";


class ProfileViewer extends Component {

    constructor(props) {
        super(props);

        const ID = new URLSearchParams( this.props.location.search ).get('profile');
        console.log( 'ID:', ID );
    }


    render() {

        return <div className="cl-profile drawer-fitted-box">
            <div className="cl-profile-card">

                <div className="cl-avatar-border">
                    <div className="cl-avatar" style={{ backgroundImage:`url(sdf)` }}/>
                </div>
                <p className="pt-1em">{ '닉네임' }</p>
            </div>

            <div className="cl-where">
                <span className="cl-name fs-16">{ '아무개' }</span>
                <span className="cl-desc fs-14 ml-07em color-white50">({ '**지점' })</span>
            </div>

            <div className="cl-form" key="form">
                <p className="color-white opacity-80 fs-12 mt-1em">이메일 정보</p>
                <a href="mailto:abc@abc.com" className="cl__input w-100">abc@abc.com</a>
            </div>

        </div>
    }
}


export default withRouter(ProfileViewer);
