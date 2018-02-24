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

class App extends Component {

    constructor(props) {
        super(props);

        this.state = {
            scrolled: false,
            isDevice: window.location.protocol === 'file:',
        }
    }


    componentWillMount() {

        document.addEventListener('scroll', () => {
            this.setState({
                scrolled: (document.querySelector('body').scrollTop > 56 || document.querySelector('.App').scrollTop > 56)
            });
        }, true);

    }

    render() {

        return (
            <HashRouter>

                <div className={classNames({
                    'App': true,
                    'cl-app--expand': this.state.scrolled,
                    'cl-is-device': this.state.isDevice
                })}>

                    <Header/>


                    <div className="app-content">

                        <Switch>
                            <Route path="/community/:tab/:id/:drawer" component={Community}/>


                            <Route path="/iot" component={HomeIoT}/>


                            <Route path="/life" component={LifeInfo}/>


                            <Route path="/reservation/:id?/:add?" component={Reservation}/>


                            <Route path="/playground" component={Playground}/>


                            <Route component={Community}/>
                        </Switch>
                    </div>

                    <Footer/>

                </div>

            </HashRouter>
        );

    }
}

export default App;
