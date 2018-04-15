import React, { Component } from 'react';
import Net from "../../scripts/net";
import SimpleReactValidator from "simple-react-validator";
import axios from 'axios';
import Store, { MakingUserData } from "../../scripts/store";
import { observer } from "mobx-react";

class UserInputs extends Component {


    constructor(props) {
        super(props);

        this.validator = new SimpleReactValidator({
            same:{
                message:'password mismatch',
                rule: (val, options)=>{
                    return val === options[0];
                }
            },

            mail:{
                message:'The :attribute must be a valid email address.',
                rule: val =>{
                    const R = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                    return R.test( val );
                }
            }
        });

        // 아이디 중복 체크 여부
        this.state = {
            checkedId: false,
        }
    }


    /* Method */
    // 검증성 테스트
    validate = () => {
        const BOOL = this.validator.allValid();

        if (!BOOL) {
            this.validator.showMessages();
            this.forceUpdate();
        }


        return BOOL;
    }

    /* Private Function */
    // 아이디 입력시
    onChangeId= evt =>{
        MakingUserData.user = { ...MakingUserData.user, id: evt.target.value };
        this.setState({ checkedId: false });
    }

    //  아이디 중복 확인
    onCheckDuplicate = () => {
        // console.log(MakingUserData.user.id, '아이디 중복확인');
        Net.checkIdDuplicate( MakingUserData.user.id, res=>{
            Store.alert( res.msg );
            this.setState({ checkedId: !res.isExisted });
        });
    }

    // 인증 번호 요청
    onRequestCertNo = () => {
        let { branch, houseHolder, user } = MakingUserData;

        console.log('유저 인증정보 요청:', houseHolder, user );

        //console.log( 'rpa:', branchId, dongId, hoId, hhname, hhphone, name, phone, certNo );
        Net.requestUserPhoneAuthNumber( branch.cmplxId,
            houseHolder.dong, houseHolder.ho, houseHolder.name, houseHolder.phone,
            user.name, user.phone, houseHolder.certReqId, res=>{

                MakingUserData.user = { ...MakingUserData.user, certReqId: res.userCertId };
                console.log( 'res:', res );

                // 임시로 인증번호 자동으로 가져오기
                //{{API_HOST}}/users/debug/userCertNum?userCertId=106&userCell=01046147636
                axios.get(`${Store.api}/users/debug/userCertNum?userCertId=${res.userCertId}&userCell=${user.phone}`)
                    .then( response => {
                        MakingUserData.user = { ...MakingUserData.user, certId: response.data.userCertNo };
                    });

            });
    }

    render() {
        const {branch, houseHolder, user} = MakingUserData;

        return <div className="cl-join-householder pb-3em">

            <div className="cl-input-container cl-bg--black30">
                <div className="color-white mb-1em">
                    <span className="fs-16 cl-bold">{branch.cmplxNm}</span>
                    <p className="color-white50">({branch.addr})</p>
                </div>
                <p className="fs-16">
                    <span className="color-white50 mr-05em">{`${houseHolder.dong}동 ${houseHolder.ho}호`}</span>
                    <span className="color-lightblue">{`${houseHolder.name}세대`}</span>
                </p>
            </div>

            <div className="cl-input-container">

                {/* 사용자 이름 */}
                <input className="cl__input--dark" placeholder="사용자 이름" type="text"
                       value={user.name}
                       onChange={evt => MakingUserData.user = {...user, name: evt.target.value }}
                />
                {this.validator.message('사용자 이름', user.name, `required|min:2`)}

                {/* 사용자 아이디 */}
                <div className="cl-input-with-button">
                    <input className="cl__input--dark w-70" placeholder="사용자 아이디" type="text"
                           value={user.id}
                           onChange={ this.onChangeId }
                    />
                    <button className="cl__button--dark ml-auto" onClick={this.onCheckDuplicate}>중복확인</button>
                </div>
                {this.validator.message('사용자 아이디', user.id, `required|min:3|max:20|alpha_num_dash`)}
                {this.validator.message('중복확인', this.state.checkedId, `accepted`)}


                {/* 사용자 이메일 */}
                <input className="cl__input--dark w-70" placeholder="사용자 이메일" type="text"
                       value={ user.mail }
                       onChange={ evt=> MakingUserData.user = { ...user, mail: evt.target.value }}
                />
                {this.validator.message('사용자 이메일', user.mail, `required|mail`)}


                {/* 사용자 휴대폰 번호 */}
                <input className="cl__input--dark" type="number" placeholder="사용자 휴대폰 번호('-'제외 숫자만 입력)"
                       value={user.phone}
                       onChange={evt => MakingUserData.user = {...user, phone: evt.target.value }}
                />
                {this.validator.message('휴대폰 번호', user.phone, `required|phone`)}


                {/* 인증번호 요청 */}
                {this.validator.fieldValid('사용자 이름') && this.validator.fieldValid('휴대폰 번호') &&
                <button className="cl__button--dark"
                        onClick={this.onRequestCertNo}>
                    인증번호 요청
                </button>
                }


                {/* 인증번호 입력 */}
                {this.validator.fieldValid('사용자 이름') && this.validator.fieldValid('휴대폰 번호') &&
                <input className="cl__input--dark" type="number" placeholder="인증번호 입력"
                       value={user.certId}
                       onChange={evt => MakingUserData.user = { ...user, certId: evt.target.value }}
                />
                }
                {this.validator.message('인증번호', user.certId, `required`)}


                {/* 비밀번호 */}
                <p className="color-lightblue fs-12 mt-3em">비밀번호는 6~12자 영문 대 소문자, 숫자, 특수문자를 사용하세요.</p>

                <input className="cl__input--dark w-70" placeholder="비밀번호" type="password"
                       value={ user.password }
                       onChange={ evt=> MakingUserData.user = { ...user, password: evt.target.value }}
                />
                {this.validator.message('비밀번호', user.password, `required|min:6|max:12`)}

                <input className="cl__input--dark w-70" placeholder="비밀번호 확인" type="password"
                       value={ user.passwordConfirm }
                       onChange={ evt=> MakingUserData.user = { ...user, passwordConfirm: evt.target.value }}
                />
                {this.validator.message('비밀번호 확인', user.passwordConfirm, `required|min:6|max:12|same:${user.password}`)}

            </div>

        </div>
    }
}


export default observer(UserInputs);
