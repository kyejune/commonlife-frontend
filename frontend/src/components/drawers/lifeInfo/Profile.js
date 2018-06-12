import React, {Component} from 'react';
import SimpleReactValidator from 'simple-react-validator';
import Net from "../../../scripts/net";
import Store, {VALIDATOR_MSG} from "../../../scripts/store";
import {MakingUserData} from "../../../scripts/store";
import DeviceStorage from "react-device-storage";
import {withRouter} from "react-router-dom";


/* https://www.npmjs.com/package/simple-react-validator */
class Profile extends Component {

    constructor(props) {
        super(props);
        this.state = {
            cmplxAddr:'',
            cmplxNm:'',
            email:null,
            userId:'',
            userImgSrc:'',
            changedImgB64:null,
            userNm:'',
            password: '',
            passwordconfirm: '',
            currentPassword:'',
            newEmail:'',
            editable: false,
        };

        this.passwordValidator = new SimpleReactValidator( VALIDATOR_MSG );
        this.mailValidator = new SimpleReactValidator( VALIDATOR_MSG );
    }

    componentDidMount(){
        this.setState({ password:'', passwordConfirm:'' });
        this.loadData();
    }

    loadData=()=>{
        Net.getProfileInfo( res=>{
            this.setState( {...res, newEmail: res.email } );
        });
    }

    changePassword=()=>{
        this.setState( { editable: true });
    }

    changeEmail=()=>{

        if( this.state.email !== this.state.newEmail ) {
            if (this.mailValidator.allValid()) {


                Net.changeEmail(this.state.newEmail, res => {
                    Store.alert('이메일이 성공적으로 변경되었습니다.');
                    this.loadData();
                });
            } else {
                this.mailValidator.showMessages();
                this.forceUpdate();
            }

        }else{
            console.log( window.history.back() );
        }

    }

    requestChangePassword=()=>{
        if( this.passwordValidator.allValid() ){
            Net.changePassword( this.state.currentPassword, this.state.password, res=>{
               this.setState({ editable: false });
            });
        }else{
            this.passwordValidator.showMessages();
            this.forceUpdate();
        }
    }

    changeProfileImage=()=>{
        if( navigator.camera ) {

                navigator.camera.getPicture(
                    (base64) => this.gettedPicture(base64),
                    (msg) => this.failedPicture(msg),
                    {
                        quality: 100,
                        sourceType: 0,
                        destinationType: 0,
                        encodingType: 0,
                    });
        }
    }

    gettedPicture(base64) {
        const b64 = 'data:image/jpeg;base64,' + base64;
        this.uploadImg( b64 );
    }

    failedPicture(message) {
        console.log( 'failedPicture:', message );
    }

    uploadImg( b64img ){
        Net.changeProfileImage( b64img, res=>{

            // 재로딩 하는건 반영되는 시간이 있어서 안맞을 가능성이 있어서 직접 b64때려 밖음
            this.setState({
                changedImgB64: b64img,
            });
            // this.loadData();
        });
    }


    submit = (event) => {

        if( this.validator.allValid() ){
            alert('전송');
        }else{
            this.validator.showMessages();
            this.forceUpdate();
        }
    }

    logout=()=>{
        const S = new DeviceStorage().localStorage();
        S.delete('savedId');
        S.delete('isSave');
        S.delete('token');

        Store.auth = { ...Store.auth, token: undefined };
        Store.isAuthorized = false;


        Net.logout();

        setTimeout(()=>{
            this.props.history.push('/login');
        }, 0 );

    }

    render() {

        console.log( Store.auth );

        let Form;
        if( this.state.editable ){

            Form = <div className="cl-form">

                <input type="password" className="cl__input w-100" placeholder="현재 비밀번호"
                       value={this.state.currentPassword} onChange={ e=> this.setState({ currentPassword: e.target.value })}
                />

                <p className="color-lightblue fs-12 mt-3em">비밀번호는 6~12자 영문 대 소문자, 숫자, 특수문자를 사용하세요.</p>

                <input type="password" className="cl__input w-100" placeholder="비밀번호"
                       value={this.state.password} onChange={ (e)=> this.setState({ password: e.target.value }) }
                />
                {this.passwordValidator.message('비밀번호', this.state.password, 'required|min:6|max:12')}

                <input type="password" className="cl__input w-100 mb-2em" placeholder="비밀번호 확인"
                       value={this.state.passwordconfirm} onChange={ (e)=> this.setState({ passwordconfirm: e.target.value }) }
                />
                {this.passwordValidator.message('비밀번호 확인', this.state.passwordconfirm, `required|min:6|max:12|same:${this.state.password}`)}


                <button className="cl__button--dark w-100" onClick={this.requestChangePassword}>비밀번호 변경 완료</button>
            </div>

        }else{

            Form = [<div className="cl-form" key="form">

                <input type="text" className="cl__input w-100"
                       placeholder="이메일 주소"
                       value={this.state.newEmail} onChange={ e=> this.setState({ newEmail: e.target.value }) }
                />
                {this.mailValidator.message('이메일', this.state.newEmail, 'required|mail', 'text-danger')}
                <p className="color-white opacity-80 fs-12 mt-1em">※ 기타 사용자 정보변경시 관리자에게 문의해 주세요.</p>

                <div className="cl-button-group mt-1em">
                    <button className="cl__button--dark" onClick={this.changePassword}>비밀번호 변경</button>
                    <button className="cl__button--dark" onClick={this.logout}>다른 아이디로 로그인</button>
                </div>
            </div>,

            <footer className="cl-opts__footer cl-flex" key="footer">
                <button className="ml-auto mr-1em" onClick={ this.changeEmail }>
                    <span className="color-primary cl-bold">확인</span>
                </button>
            </footer>]
        }


        return <div className="cl-profile drawer-fitted-box">
            <div className="cl-profile-card">

                <div className="cl-avatar-border">
                    <div className="cl-avatar" style={{
                        backgroundImage:`url(${ ( this.state.changedImgB64||this.state.userImgSrc ) })`,
                        backgroundSize:'cover',
                    }}
                    />
                </div>
                <button className="cl-edit__button" onClick={ this.changeProfileImage }/>

                <h4>{ this.state.userNm }</h4>
                <p>{ this.state.userId }</p>
            </div>

            <div className="cl-where">
                <span className="cl-name fs-16">{ this.state.cmplxNm }</span>
                <span className="cl-desc fs-14 ml-07em color-white50">({ this.state.cmplxAddr })</span>
            </div>

            { Form }

        </div>
    }
}


export default withRouter(Profile);
