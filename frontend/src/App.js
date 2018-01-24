import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import './App.css';
import 'font-awesome/fonts/fontawesome-webfont.eot';
import 'react-md/dist/react-md.min.js';

import classNames from 'classnames';
import createBrowserHistory from 'history/createBrowserHistory';


import Header from 'components/layouts/Header';
import Footer from 'components/layouts/Footer';
import Community from 'components/routes/Community';
import HomeIoT from 'components/routes/HomeIoT';
import LifeInfo from 'components/routes/LifeInfo';
import Reservation from 'components/routes/Reservation';
import Store from 'scripts/store';

class App extends Component {

    constructor(props) {
        super(props);

        this.state = {
            scrolled: false,
            history: createBrowserHistory(),
            isDevice: !!window.cordova,
        }
    }


    componentDidMount() {

        document.addEventListener('scroll', () => {
            let doc = document.querySelector('.react-swipeable-view-container>div[aria-hidden=false]');
            if (doc) {
                this.setState({
                    scrolled: (doc.scrollTop > 56)
                });
            }
        }, true);

        Store.startAt = this.state.history.location.pathname || '/community';
        this.state.history.replace('/community');
    }

    render() {

        return (
            <Router>

                <div className={classNames({
                    'App': true,
                    'cl-app--expand': this.state.scrolled,
                    'cl-is-device': this.state.isDevice
                })}>

                    <Header/>



                    <div className="app-content">

                        <Switch>
                            <Route path="/community/:tab" component={Community}/>
                            <Route path="/iot" component={HomeIoT}/>
                            <Route path="/life" component={LifeInfo}/>
                            <Route path="/reservation" component={Reservation}/>

                            <Route component={Community}/>
                        </Switch>
                    </div>

                    <Footer/>


                </div>

            </Router>
        );

    }
}

export default App;
