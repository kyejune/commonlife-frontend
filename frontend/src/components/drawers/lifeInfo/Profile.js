import React, {Component} from 'react';
import SimpleReactValidator from 'simple-react-validator';


/* https://www.npmjs.com/package/simple-react-validator */
class Profile extends Component {

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            nickname: '',
            mail: '',
            password: '',
            passwordconfirm: ''
        }

        this.validator = new SimpleReactValidator({
            same:{
                message:'password mismatch',
                rule: (val, options)=>{
                    return (val.length > 0) && (val === options[0]);
                }
            }
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

    render() {

        const validations = {
            name:['required'],
            email:['required', 'email'],
            password:['required', 'min:6', 'max:12']
        };


        return <div className="cl-profile drawer-fitted-box--b">
            <div className="cl-profile-card">

                <div className="cl-avatar-border">
                    <div className="cl-avatar"/>
                </div>
                <button className="cl-edit__button"/>

                <h4>조성우</h4>
                <p>sungwoo@gmail.com</p>
            </div>


            <div className="cl-profile-family">
                <div className="cl-status-vertical-list cl-card-item--dark">
                    <h4 className="cl-name">
                        역삼동 하우징
                        <span className="cl-desc">(서울시 서초구 강남대로 373)</span>
                    </h4>
                    <p className="cl-desc">
                        101동 1002호 <span>조성우 세대</span>
                    </p>
                </div>

                <ul className="cl-status-vertical-list--nano">
                    <li className="cl-card-item--dark">
                        <div className="cl-avatar"/>
                        <div>
                            <h4 className="cl-name">이름</h4>
                            <p className="cl-desc">id@domain.com</p>
                        </div>
                        <button className="ml-auto cl--active">활성</button>
                    </li>

                    <li className="cl-card-item--dark">
                        <div className="cl-avatar"/>
                        <div>
                            <h4 className="cl-name">이름</h4>
                            <p className="cl-desc">id@domain.com</p>
                        </div>
                        <button className="ml-auto cl--active">활성</button>
                    </li>

                    <li className="cl-card-item--dark">
                        <div className="cl-avatar"/>
                        <div>
                            <h4 className="cl-name">이름</h4>
                            <p className="cl-desc">id@domain.com</p>
                        </div>
                        <button className="ml-auto">활성</button>
                    </li>
                </ul>
            </div>

            <div className="cl-form">

                <input type="text" className="cl__input w-100"
                       placeholder="이름"
                       value={this.state.name} onChange={ (e)=> this.setState({ name: e.target.value }) }
                />
                {this.validator.message('name', this.state.name, 'required')}

                <div className="cl-flex--bottom">
                    <input type="text" className="cl__input w-70"
                           placeholder="닉네임"
                           value={this.state.nickname} onChange={ (e)=> this.setState({ nickname: e.target.value }) }
                    />

                    <button className="cl__button ml-auto">중복확인</button>
                </div>
                {this.validator.message('nickname', this.state.nickname, 'required')}

                <input type="text" className="cl__input w-100"
                       placeholder="이메일 주소"
                       value={this.state.mail} onChange={ (e)=> this.setState({ mail: e.target.value }) }
                />
                {this.validator.message('name', this.state.mail, 'required|email', 'text-danger')}

                <p className="color-lightblue fs-12 mt-3em">비밀번호는 6~12자 영문 대 소문자, 숫자, 특수문자를 사용하세요.</p>

                <input type="password" className="cl__input w-100"
                       placeholder="비밀번호"
                       value={this.state.password} onChange={ (e)=> this.setState({ password: e.target.value }) }
                />
                {this.validator.message('password', this.state.password, 'required|min:6|max:12')}

                <input type="password" className="cl__input w-100"
                       placeholder="비밀번호 확인"
                       value={this.state.passwordconfirm} onChange={ (e)=> this.setState({ passwordconfirm: e.target.value }) }
                />
                {this.validator.message('passwordConfirm', this.state.passwordconfirm, `required|min:6|max:12|same:${this.state.password}`)}
            </div>


            <footer className="cl-opts__footer cl-flex">
                <button className="ml-auto pr-1em" onClick={ this.submit }>
                    <span className="color-primary cl-bold">확인</span>
                </button>
            </footer>
        </div>
    }
}


export default Profile;
