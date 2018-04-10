import React, {Component} from 'react';
import Net from "../../scripts/net";
import classNames from "classnames";
import SimpleReactValidator from "simple-react-validator";
import axios from 'axios';
import Store, {MakingUserData} from "../../scripts/store";
import {observer} from "mobx-react";

class HouseHolderInputs extends Component {


    constructor(props) {
        super(props);

        this.state = {
            dongs: [],
            hos: [],
        }

        // this.certReqId = null; // 휴대포 인증 요청 후 받은 값
        this.validator = new SimpleReactValidator({
            'not-undefined': {
                message: '동',
                rule: vals => {
                    return (vals[0] != 'undefined' && vals[1] != 'undefined');
                }
            }
        });
    }


    componentDidMount() {
        Net.getDongsInBranch( MakingUserData.branch.cmplxId, data => {
            this.setState({dongs: data});

            if (data.length === 1) {
                MakingUserData.houseHolder.dong = data[0];
                this.loadHos(data[0]);
            }

        });
    }


    /* Method */
    // 검증성 테스트
    validate=()=>{
        const BOOL = this.validator.allValid();

        if (BOOL) {
            this.validator.showMessages();
            this.forceUpdate();
        }

        return BOOL;
    }

    /* Private Function */
    loadHos(dongId) {
        Net.getNumbersInBranch( MakingUserData.branch.cmplxId, dongId, data => {
            this.setState({hos: data});

            // 호수가 하나면 자동 선택
            if (data.length === 1) {
                MakingUserData.houseHolder.ho = data[0];
            }
        });
    }

    // 동 선택되면,
    onSelectedDong = evt => {
        MakingUserData.houseHolder.dong = evt.target.value;
        MakingUserData.houseHolder.ho = "undefined";
        this.loadHos(evt.target.value);
    }

    // 호 선택되면,
    onSelectedHo = evt => {
        MakingUserData.houseHolder.ho = evt.target.value;
    }

    // 인증 번호 요청
    onRequestCertNo = () => {
        let {branch, houseHolder} = MakingUserData;

        Net.requestHouseHolderPhoneAuthNumber(branch.cmplxId, houseHolder.dong, houseHolder.ho, houseHolder.name, houseHolder.phone, res => {
            alert(res.msg);

            MakingUserData.houseHolder = { ...houseHolder, certReqId:res.userCertId };
            console.log('인증 번호 요청:', res, MakingUserData.houseHolder );

            // 임시로 인증번호 자동으로 가져오기
            axios.get(`${Store.api}/users/debug/headCertNum?userCertId=${res.userCertId}&headNm=김영헌&headCell=01050447244`)
                .then( response => {
                    console.log( '자동 인증번호 갱신:', response.data.headCertNo );
                    MakingUserData.houseHolder = { ...MakingUserData.houseHolder, certId: response.data.headCertNo };

                    console.log( 'MakignUserData:', MakingUserData.houseHolder );
                });
        });
    }

    render() {

        let {branch, houseHolder} = MakingUserData;

        return <div className="cl-join-householder">

            <div className="cl-input-container cl-bg--black30">
                <div className="color-white mb-1em">
                    <span className="fs-16 cl-bold">{branch.cmplxNm}</span>
                    <p className="color-white50">({branch.addr})</p>
                </div>

                <select className="cl__select--dark" value={houseHolder.dong} onChange={this.onSelectedDong}>
                    <option value="undefined" disabled>동</option>
                    {this.state.dongs.map(item => {
                        return <option key={item} value={item}>{item}</option>
                    })}
                </select>

                <select className="cl__select--dark ml-2em" value={houseHolder.ho} onChange={this.onSelectedHo}>
                    <option value="undefined" disabled>호수</option>
                    {this.state.hos.map(item => {
                        return <option key={item} value={item}>{item}</option>
                    })}
                </select>
                {this.validator.message('', [houseHolder.dong, houseHolder.ho], `not-undefined`)}
            </div>

            <div className="cl-input-container">

                {/* 세대주 이름 */}
                <input className="cl__input--dark" placeholder="세대주 이름"
                       type="text" value={houseHolder.name}
                       onChange={evt => MakingUserData.houseHolder = { ...houseHolder, name: evt.target.value }}
                />
                {this.validator.message('세대주 이름', houseHolder.name, `required|min:2`)}

                {/* 세대주 휴대폰 번호 */}
                <input className="cl__input--dark" type="number" placeholder="세대주 휴대폰 번호('-'제외 숫자만 입력)"
                       value={houseHolder.phone}
                       onChange={evt => MakingUserData.houseHolder = { ...houseHolder, phone: evt.target.value }}
                />
                {this.validator.message('휴대폰 번호', houseHolder.phone, `required|phone`)}


                {/* 인증번호 요청 */}
                {this.validator.fieldValid('세대주 이름') && this.validator.fieldValid('휴대폰 번호') &&
                <button className="cl__button--dark"
                        onClick={ this.onRequestCertNo }>
                    인증번호 요청
                </button>
                }


                {/* 인증번호 입력 */}
                {this.validator.fieldValid('세대주 이름') && this.validator.fieldValid('휴대폰 번호') &&
                <input className="cl__input--dark" type="number" placeholder="인증번호 입력"
                       value={houseHolder.certId}
                       onChange={ evt => MakingUserData.houseHolder = evt.target.value }
                />
                }
                {this.validator.message('인증번호', houseHolder.certId, `required`)}

            </div>

        </div>
    }
}


export default observer( HouseHolderInputs );
