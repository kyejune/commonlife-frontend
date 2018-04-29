import React, {Component} from 'react';
import {withRouter} from "react-router-dom";
import Net from "../../scripts/net";


class ProfileViewer extends Component {

    constructor(props) {
        super(props);

        this.state = {
            userImgSrc: null,
            userId: "",
            userNm: "",
            email: "",
            cmplxNm: "",
            cmplxAddr: ""
        }
    }

    componentDidMount(){

        const ID = new URLSearchParams( this.props.location.search ).get('profile');
        console.log( 'ID:', ID );


        Net.getUserInfo( ID, res=>{
            this.setState( res );
        });

    }


    render() {

        return <div className="cl-profile drawer-fitted-box">
            <div className="cl-profile-card">

                <div className="cl-avatar-border">
                    <div className="cl-avatar" style={{ backgroundImage:`url(${ this.state.userImgSrc})` }}/>
                </div>
                <p className="pt-1em">{ this.state.userId }</p>
            </div>

            <div className="cl-where">
                <span className="cl-name fs-16">{ this.state.userNm }</span>
                <span className="cl-desc fs-14 ml-07em color-white50">({ this.state.cmplxNm }지점)</span>
            </div>

            <div className="cl-form" key="form">
                <p className="color-white opacity-80 fs-12 mt-1em">이메일 정보</p>
                <a href={`mailto:${this.state.email}`} className="cl__input w-100">{this.state.email}</a>
            </div>

        </div>
    }
}


export default withRouter(ProfileViewer);
