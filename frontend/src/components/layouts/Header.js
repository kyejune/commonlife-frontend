import React, {Component} from 'react';

import logo from 'images/logo.svg';
import alert from 'images/noti-24-px-copy.svg';
import Store from "../../scripts/store";
import {observer} from "mobx-react";
import {withRouter} from "react-router-dom";

class Header extends Component {

    constructor(props) {
        super(props);
        this.state = {
            branchType: 0, // 0: 셀렉트박스, 1: 내 지점 텍스트, 2: 안보이기
        }
    }


    openBranchList=()=>{
        Store.pushDrawer( 'branch' );
    }

    openNotificationCenter(){
        Store.pushDrawer( 'notifications' );
    }

    componentDidMount(){
        this.updateRoute();
    }


    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location)
            this.updateRoute();
    }

    updateRoute=()=>{

        let pathname = this.props.location.pathname;

        if( pathname.indexOf('/community') === 0 ) this.setState({ branchType: 0 });
        else if( pathname.indexOf('/info') === 0 ) this.setState({ branchType: 2 });
        else                                       this.setState({ branchType: 1 });
    }

    render() {

        let name = '';


        let Branch;
        switch( this.state.branchType ){
            case 0:
                if( Object.keys(Store.complexMap).length > 0 && Store.communityCmplxId ){
                    name = Store.complexMap[ Store.communityCmplxId ].cmplxNm;
                }


                Branch = <button className="md-title md-title--toolbar cl-header__branch-btn" onClick={ this.openBranchList }>
                    { name }
                </button>;
                break;

            case 1:

                if( Object.keys(Store.complexMap).length > 0 && Store.cmplxId ){
                    name = Store.complexMap[ Store.cmplxId ].cmplxNm;
                }

                Branch = <div className="cl-header__branch-label">{ name }</div>;
                break;
        }

        if( this.state.branchType === 2 )
            return null;


        return (
            <header
                className="md-paper md-toolbar md-background--primary md-toolbar--text-white cl-flex cl-header">

                <img className="cl-header__logo" src={logo} alt="로고 이미지" width="40" height="40"/>

                {Branch}

                <button className="ml-auto cl-flex cl-header__alert-btn"
                        onClick={ this.openNotificationCenter }
                        type="button"
                >
                    <img src={alert} alt="알림 이미지"/>
                </button>

            </header>
        );
    }
}

export default withRouter(observer(Header));