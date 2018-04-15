import React, { Component } from 'react';
import LogoSrc from 'images/logo@3x.png';
import {Link, withRouter} from "react-router-dom";
import classNames from 'classnames';
import Net from "../../scripts/net";
import DeviceStorage from "react-device-storage";


class Login extends Component {

    constructor(props) {
        super(props);

        const S = this.storage = new DeviceStorage().localStorage();

        const isSaveId = S.read('isSaveId')||false;

        this.state = {
            id:isSaveId?(S.read('savedId')||''):'',
            pw:'',
            isSaveId: isSaveId //sevedId?true:false,
        }

    }

    onChangeSaveId= evt =>{
        this.setState({ isSaveId: evt.target.checked });
        this.storage.save( 'isSaveId', evt.target.checked );
    }

    onLogin=()=>{

        this.storage.save( 'savedId', this.state.id );

        Net.login( this.state.id, this.state.pw, res=>{
           this.props.history.push('/');
        });

    }

    render() {
        return <div className="cl-login cl-full-page cl-bg--white">

            {/*<button className="cl-close__button--black" onClick={this.props.history.goBack}/>*/}

            <div className="cl-flex cl-logo-pack">
                <img className="cl-logo__img" src={LogoSrc} alt=""/>
                <div className="cl-logo__text">
                    <span className="fs-36 cl-bold">COMMON Life</span>
                    <p className="fs-10">Membership</p>
                </div>
            </div>
            
            
            <div className="cl-input-container">
                <h2>로그인</h2>

                <input type="text" className="cl__input--white" placeholder="ID를 입력해주세요."
                       value={this.state.id}
                       onChange={evt=> this.setState({ id:evt.target.value.trim() })}
                />
                <input type="password" className="cl__input--white" placeholder="비밀번호를 입력해주세요."
                       value={this.state.pw}
                       onChange={evt=> this.setState({ pw:evt.target.value.trim() })}
                />

                <div className="cl-flex fs-13 mt-2em">
                    <Link to="/join" className="mr-auto color-black">사용자 등록</Link>
                    <Link to="/find/id" className="color-primary mr-1em">아이디 찾기</Link>
                    <Link to="/find/password" className="color-primary">비밀번호 찾기</Link>
                </div>
            </div>


            <footer className="cl-flex cl-login__footer">
                <label className="cl-checkboxes"><input className="fs-14 opacity-80" type="checkbox" defaultChecked={this.state.isSaveId} onChange={this.onChangeSaveId}/> 아이디를 저장합니다.</label>
                <button className={classNames( "cl-confirm__button ml-auto fs-22 color-black", {
                    "cl--ready": ( this.state.id.length > 2 && this.state.pw.length > 5 )
                })}
                        onClick={this.onLogin}>
                    확인
                </button>
            </footer>

            </div>
    }
}


export default withRouter(Login);
