import React, { Component } from 'react';
import classNames from "classnames";
import LogoSrc from 'images/logo@3x.png';
import TagComponent from "../ui/TagComponent";
import {Link, withRouter} from "react-router-dom";

class FindAuth extends Component {


    constructor(props) {
        super(props);

        this.state = {
            isComplete: false,
            hhname:'',
            hhphone:'',
            id:'',
            phone:'',
            reqCertId:'',
            certId:''
        }
    }


    reqCertId=()=>{

    }

    render() {

        const MODE =  this.props.match.params.mode;

        const TEXT_MAP = {
            id:{
                title:'아이디 찾기',
                desc:'세대주 정보와 사용자 아이디를 입력합니다.\n사용자 휴대폰으로 인증번호 및 아이디가 전송됩니다.',
                completeMessage:'입력한 사용자의 휴대폰번호로 아이디가\n전송되었습니다.'
            },

            password:{
                title:'비밀번호 찾기',
                desc:'세대주 정보와 사용자 아이디를 입력합니다.\n사용자 휴대폰으로 인증번호 및 비밀번호가 전송됩니다.',
                completeMessage:'재설된 비밀번호가 전송 되었습니다.\n로그인 후에 바로 비밀번호를 변경하시기 바랍니다.'
            }
        };

        const T = TEXT_MAP[MODE];


        let Content;
        let Footer;
        if( !this.state.isComplete ){
            Content =  <div className="pb-3em cl-input-container">

                <input type="text" className="cl__input--dark" placeholder="세대주 이름"
                       value={this.state.hhname}
                       onChange={ evt=>this.setState({ hhname: evt.target.value })}
                />
                <input type="number" className="cl__input--dark" placeholder="세대주 휴대폰 번호('-'제외 숫자만 입력)"
                       value={this.state.hhphone}
                       onChange={ evt=>this.setState({ hhphone: evt.target.value })}
                />


                {MODE==='password' &&
                <input type="text" className="cl__input--dark mt-2em" placeholder="사용자 아이디"
                       value={this.state.id}
                       onChange={ evt=>this.setState({ id: evt.target.value })}
                />
                }

                <input type="number" className="cl__input--dark" placeholder="사용자 휴대폰 번호('-'제외 숫자만 입력)"
                       value={this.state.phone}
                       onChange={ evt=>this.setState({ phone: evt.target.value })}
                />

                <button className="cl__button--dark" onClick={ this.reqCertId }>인증번호 요청</button>

                <input type="number" className="cl__input--dark" placeholder="휴대폰 인증번호 입력"
                       value={this.state.certId}
                       onChange={ evt=>this.setState({ certId: evt.target.value })}
                />

            </div>;


            Footer = <footer className="cl-opts__footer cl-flex cl-bg--darkgray">
                <button className="color-primary cl-bold ml-auto">{T.title}</button>
            </footer>

        }else{

            Content = <div className="pb-3em cl-input-container cl-find-auth-complete">
                <p className="color-white50 fs-16">
                    <TagComponent content={T.completeMessage}></TagComponent>
                </p>
            </div>;

            Footer = <Link className="cl-opts__footer cl-flex cl-bg--darkgray" to="/login">

                {MODE === 'password' &&
                <div className="cl-bg--black cl-password-reset-notification">
                    <i className="ic-exclamation mb-04em"/>
                    <p className="color-white fs-12">
                        최초로그인시 반드시 비밀번호를 변경해 주세요.<br/>
                        비밀번호 변경은 '<span className="color-secondary">Info > Profile</span>'메뉴를 통해 가능합니다.
                    </p>
                </div>
                }

                <span className="color-primary cl-bold ml-auto mr-auto">로그인</span>
            </Link>
        }



        return <div className="cl-find-auth cl-full-page cl-bg--dark">

            <header className="cl-join__header">
                <div className="cl-flex">
                    <img className="cl-logo__img" src={LogoSrc}/>
                    <div className="cl-logo__text">
                        <span className="fs-36 cl-bold">COMMON Life</span> <br/>
                        <span className="fs-10">Membership</span>
                    </div>

                    <button className="cl-close__button--black ml-auto" onClick={this.props.history.goBack}/>
                </div>

                <h1>{T.title}</h1>
                <p> <TagComponent content={T.desc}></TagComponent> </p>
            </header>

            { Content }

            { Footer }

        </div>
    }
}


export default withRouter(FindAuth);
