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


class App extends Component {

    constructor(props) {
        super(props);

        this.state = {
            scrolled: false,
        }
    }


    componentDidMount() {

        document.addEventListener('scroll', () => {
            let bodyTop = 0;
            let boxes = document.querySelectorAll('.cl-fitted-box');

            // 일반적인 상황에서..
            if( boxes.length === 1 ) bodyTop = boxes[0].scrollTop;
            else{
                // 탭구조라서 복수개가 존재할때 보이는 영역에만 제한
                document.querySelectorAll('.cl-fitted-box').forEach( item => {
                    if( item.parentElement.getAttribute('aria-hidden') === 'false' )
                        bodyTop = item.scrollTop;
                });
            }

            this.setState({
                scrolled: ( bodyTop > 56 )
            });

        }, true);

    }

    render() {

        return (
            <HashRouter>

                <div className={classNames({
                    'App': true,
                    'cl-app--expand': this.state.scrolled
                })}>

                    <Header/>


                    <div className="app-content">

                        <Route component={DrawerInjector}/>

                        <Switch>
                            <Route path="/community/:tab?/:id?/:drawer?" component={Community}/>


                            <Route path="/iot/:action?/:option1?/:option2?/:option3?/:option4?/:option5?" component={HomeIoT}/>


                            <Route path="/info/:cate?/:option1?/:option2?" component={LifeInfo}/>


                            <Route path="/reservation/:id?/:add?" component={Reservation}/>


                            <Route path="/playground" component={Playground}/>

                            <Route component={Community}/>
                        </Switch>

                    </div>

                    <Footer/>


                    {/* 회원가입, 로그인 */}
                    <Route path="/join/:step?" component={Join}/>
                    <Route path="/login" component={Login}/>

                </div>

            </HashRouter>
        );

    }
}

export default App;
