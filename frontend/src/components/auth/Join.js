import React, {Component} from 'react';
import LogoSrc from 'images/logo@3x.png';
import PrivatePolicy from "./PrivatePolicy";
import BranchList from "../ui/BranchList";
import {Link, withRouter} from "react-router-dom";
import UserInputs from "./UserInputs";
import HouseHolderInputs from "./HouseHolderInputs";
import Net from "../../scripts/net";
import {MakingUserData} from "../../scripts/store";
import Welcome from "./Welcome";
import classNames from 'classnames';
import moment from "moment/moment";

class Join extends Component {

    constructor(props) {
        super(props);

        this.state = {
            step: parseInt(new URLSearchParams(props.location.search).get('step'), 10) || 0,
        };

        this.STEP_INFO = [
            {name: '사용자 등록', msg: '약관 동의'},
            {name: '사용자 등록', msg: '1. 등록 지점을 선택하세요.'},
            {name: '사용자 등록', msg: '2. 세대주 정보 입력'},
            {name: '사용자 등록', msg: '2. 사용자 정보 입력'},
            {name: '환영합니다', msg: '등록완료'},
        ];
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location)
            this.setState({
                step: parseInt(new URLSearchParams(this.props.location.search).get('step'), 10) || 0
            });
    }

    onChangeBranch(item) {
        MakingUserData.branch = item;
    }


    /* 공통 UI */
    onPrev = () => {
        this.setState({step: this.state.step - 1});
    }

    onNext = () => {
        // 검증 후
        if (!this.validate()) return;

        if (this.state.step === 2) {

            const {branch, houseHolder} = MakingUserData;

            // 세대주 인증번호 통과하면 다음 스텝으로 진행
            Net.confirmHouseHolderPhoneAuthNumber(branch.cmplxId, houseHolder.dong, houseHolder.ho,
                houseHolder.name, houseHolder.phone, houseHolder.certReqId, houseHolder.certId, res => {
                    this.setState({step: this.state.step + 1});
                });

        } else if (this.state.step === 3) {

            const {branch, houseHolder, user} = MakingUserData;

            //confirmUserPhoneAuthNumber( branchId, dongId, hoId, hhname, hhphone, certReq, certId, phone, callback ){
            Net.confirmUserPhoneAuthNumber(branch.cmplxId, houseHolder.dong, houseHolder.ho,
                houseHolder.name, houseHolder.phone, user.certReqId, user.certId, user.phone, res => {
                    this.registerUser();
                });

        } else {
            this.setState({step: this.state.step + 1});
        }
    }

    // 새 회원으로 등록
    registerUser() {
        const {branch, houseHolder, user} = MakingUserData;
        //registNewUser( branchId, dongId, hoId, hhname, hhphone, name, phone, certId, certDate, id, password, callback ){//2018-02-10 14:42:22
        Net.registNewUser(branch.cmplxId, houseHolder.dong, houseHolder.ho, houseHolder.name, houseHolder.phone, user.name, user.phone, user.certReqId, moment().format('YYYY-MM-DD hh:mm:ss'), user.id, user.password, res => {
            this.setState({step: this.state.step + 1});
        });
    }


    /* Validation */
    validate = () => {
        let inCorrectMsg = null;
        const data = this.state.data;

        switch (this.state.step) {
            case 1:
                if (MakingUserData.branch.cmplxId === undefined)
                    inCorrectMsg = '등록 지점을 선택하세요.';

                break;
            case 2:
                if (!this.houseHolder.validate())
                    inCorrectMsg = '입력 양식을 맞춰 주시기 바랍니다.';
                break;

            case 3:
                if (!this.user.validate())
                    inCorrectMsg = '입력 양식을 맞춰 주시기 바랍니다.';
                break;
        }

        const IS_PASS = (inCorrectMsg === null);

        if (!IS_PASS)
            alert(inCorrectMsg);

        return IS_PASS;
    }


    render() {

        const {STEP_INFO} = this;
        const {step} = this.state;
        const IS_START = (step === 0);
        const IS_END = (step === 4);
        const INFO = STEP_INFO[step];

        let component;
        switch (step) {
            case 0:
                component = <PrivatePolicy/>;
                break;

            case 1:
                component = <BranchList className="pt-3em" defautValue={MakingUserData.branch.cmplxId}
                                        onChange={this.onChangeBranch}/>;
                break;

            case 2:
                component = <HouseHolderInputs ref={r => this.houseHolder = r}/>;
                break;

            case 3:
                component = <UserInputs ref={r => this.user = r}/>;
                break;

            case 4:
                component = <Welcome/>
                break;
        }


        return <div className="cl-join cl-bg--dark">

            <div className="cl-full-page">

                <header className="cl-join__header">
                    <div className="cl-flex">
                        <img className="cl-logo__img" src={LogoSrc}/>
                        <div className="cl-logo__text">
                            <span className="fs-36 cl-bold">COMMON Life</span> <br/>
                            <span className="fs-10">Membership</span>
                        </div>

                        <button className="cl-close__button--black ml-auto" onClick={this.props.history.goBack}/>
                    </div>

                    <h1>{INFO.name}</h1>
                    <p>{INFO.msg}</p>
                </header>


                <div className={classNames("pb-3em", {"cl-join-welcome-wrap": step == 4})}>
                    {component}
                </div>

            </div>

            <footer className="cl-opts__footer cl-flex">

                {IS_END && <Link to="/login" className="ml-auto mr-auto cl-bold color-primary">로그인</Link>}

                {IS_START && <span className="mr-auto">위 정책에 동의하고 시작합니다.</span>}

                {step > 0 && step < 4 && [
                    <button key="prev" className="mr-auto" onClick={this.onPrev}>이전</button>,
                    <span key="pager">{`${step}/3`}</span>]
                }

                {!IS_END &&
                <button className="color-primary cl-bold ml-auto" onClick={this.onNext}>
                    {IS_START ? "확인" : "다음"}
                </button>
                }

            </footer>

        </div>
    }
}


export default withRouter(Join);
