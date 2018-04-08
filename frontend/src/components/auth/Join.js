import React, {Component} from 'react';
import LogoSrc from 'images/logo@2x.png';
import PrivatePolicy from "./PrivatePolicy";
import BranchList from "../ui/BranchList";
import {withRouter} from "react-router-dom";
import UserInputs from "./UserInputs";
import HouseHolderInputs from "./HouseHolderInputs";


class Join extends Component {

    constructor(props) {
        super(props);

        this.state = {
            step: parseInt(new URLSearchParams(props.location.search).get('step')) || 0,
            data: {
                branch: { cmplxId: undefined }, // 등록 지점
            },
        }

        this.STEP_INFO = [
            {name: '사용자 등록', msg: '약관 동의'},
            {name: '사용자 등록', msg: '1. 등록 지점을 선택하세요.'},
            {name: '사용자 등록', msg: '2. 세대주 정보 입력'},
            {name: '환영합니다', msg: '등록완료'},
        ]
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location)
            this.setState({
                step: parseInt(new URLSearchParams(this.props.location.search).get('step')) || 0
            });
    }


    /* 공통 UI */
    onPrev = () => {
        this.setState({step: this.state.step - 1});
    }

    onNext = () => {
        // 검증 후
        if( this.validate() )
            this.setState({step: this.state.step + 1});
    }

    /* 각 스텝별 처리 */
    onChangeBranch = branchItem => {

        this.setState({
            data: {...this.state.data, branch: branchItem }
        });
    }




    /* Validation */
    validate=()=>{
        let inCorrectMsg = null;
        const data = this.state.data;

        switch( this.state.step ){
            case 1:
                if( data.branch.cmplxId === undefined )
                    inCorrectMsg = '등록 지점을 선택하세요.';

                break;
            case 2:

                break;

            case 3:

                break;
        }

        const IS_PASS = (inCorrectMsg === null);

        if( !IS_PASS )
            alert( inCorrectMsg );

        return IS_PASS;
    }


    render() {

        const {STEP_INFO} = this;
        const {step, data} = this.state;
        const IS_START = (step === 0);
        const INFO = STEP_INFO[step];

        let component;
        switch( step ){
            case 0:
                component = <PrivatePolicy/>;
            break;

            case 1:
                component = <BranchList value={data.branch.cmplxId} onChange={this.onChangeBranch}/>;
            break;

            case 2:
                component = <HouseHolderInputs branch={data.branch} />;
            break;

            case 3:
                component = <UserInputs/>;
            break;
        }


        return <div className="cl-join cl-full-page cl-bg--dark">

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


            <div className="pb-3em">
                {component}
            </div>

            <footer className="cl-opts__footer cl-flex cl-bg--darkgray">

                {IS_START ?
                    <span className="mr-auto">위 정책에 동의하고 시작합니다.</span> :
                    [<button key="prev" className="mr-auto" onClick={this.onPrev}>이전</button>, <span key="pager">{ `${step+1}/3` }</span>]
                }
                <button className="color-primary cl-bold ml-auto" onClick={this.onNext}>
                    {IS_START ? "확인" : "다음"}
                </button>

            </footer>

        </div>
    }
}


export default withRouter(Join);
