import React, {Component} from 'react';
import {HashRouter, Route, Switch} from 'react-router-dom';
import './App.css';
import 'font-awesome/fonts/fontawesome-webfont.eot';
import 'react-md/dist/react-md.min.js';

import classNames from 'classnames';

import Header from 'components/layouts/Header';
import Footer from 'components/layouts/Footer';
import Community from 'components/routes/Community';
import HomeIoT from 'components/routes/HomeIoT';
import LifeInfo from 'components/routes/LifeInfo';
import Reservation from 'components/routes/Reservation';
import Playground from 'components/Playground';
import DrawerInjector from "./components/drawers/DrawerInjector";
import Join from "./components/auth/Join";
import Login from "./components/auth/Login";
import FindAuth from "./components/auth/FindAuth";
import LandingPage from "./components/LandingPage";
import DeviceStorage from "react-device-storage";
import Net from "./scripts/net";
import Store from "./scripts/store";
import {observer} from "mobx-react";
import Walkt from "./components/Walkt";


class App extends Component {

    constructor(props) {
        super(props);

        this.state = {
            scrolled: false,
        }


        // 앱 복귀시 로그인 체크 다시
        document.addEventListener("resume", ()=>{
            Store.isAuthorized = false;
            setTimeout( this.checkAuth, 0 );

        }, false );
    }

    checkAuth=()=>{
        const S = this.storage = new DeviceStorage().localStorage();
        const ID = S.read('savedId') || Store.auth.id;

        if(ID){

            Net.checkAuth( bool=>{
                if( bool ) Store.isAuthorized = true;
                else       this.props.history.push('/login');
            });

        }else{
            this.props.history.push('/login');
        }
    }


    componentDidMount() {

        this.oldTop = 0;

        document.addEventListener('scroll', event => {
            let bodyTop = 0;
            let boxes = document.querySelectorAll('.cl-fitted-box');

            // 일반적인 상황에서..
            if( boxes.length === 1 ) bodyTop = boxes[0].scrollTop;
            else{
                // 탭구조라서 복수개가 존재할때 보이는 영역에만 제한
                document.querySelectorAll('.cl-fitted-box').forEach( item => {
                    if( item.parentElement.getAttribute('aria-hidden') === 'false' ){
                        bodyTop = item.scrollTop;
                    }
                });
            }

            const DELTA = (this.oldTop - bodyTop );

            this.oldTop = bodyTop;


            if( Math.abs( DELTA ) > 10 ){
                let openMode =  DELTA < 0;
                this.setState({
                    scrolled: openMode,//( bodyTop > 56 )
                });
            }

        }, true);

        if( this.router ) {
            // 마지막 라우터 주소 저장
            this.router.history.listen(location => {
                const S = new DeviceStorage().localStorage();
                const PATH = location.pathname;
                if (PATH.indexOf('/community') === 0 || PATH.indexOf('/reservation') === 0 || PATH.indexOf('/iot') === 0 || PATH.indexOf('/info') === 0)
                    S.save('location', location.pathname);
            });
        }
    }

    render() {

        return <HashRouter ref={ r => this.router = r }>
                <div className={classNames({
                    'App': true,
                    'cl-app--expand': this.state.scrolled
                })}>

                    {Store.isAuthorized ? [
                            <Header key="header"/>,

                            <div key="app-content" className="app-content">

                                <Route component={DrawerInjector}/>

                                <Switch>
                                    <Route path="/community/:tab?/:id?/:drawer?" component={Community}/>


                                    <Route path="/iot/:action?/:option1?/:option2?/:option3?/:option4?/:option5?" component={HomeIoT}/>


                                    <Route path="/info/:cate?/:option1?/:option2?" component={LifeInfo}/>


                                    <Route path="/reservation/:id?/:add?" component={Reservation}/>


                                    <Route path="/playground" component={Playground}/>

                                    <Route component={Community}/>
                                </Switch>

                            </div>,
                            <Footer key="footer"/>]
                        :

                        <div>
                            <Route component={LandingPage} exact />
                            {/* 회원가입, 로그인 */}
                            <Route path="/join/:step?" component={Join}/>
                            <Route path="/login" component={Login}/>
                            <Route path="/find/:mode" component={FindAuth}/>
                        </div>
                    }

                    <Route path="/welcome" component={Walkt}/>

                </div>


            </HashRouter>;

    }
}

export default observer(App);
