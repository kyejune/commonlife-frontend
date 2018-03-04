import React, {Component} from 'react';
import {HashRouter, Route, Switch} from 'react-router-dom';
import {Drawer} from 'react-md';
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

            let bodyTop = document.querySelector('body').scrollTop || document.querySelector('html').scrollTop;

            this.setState({
                scrolled: ( bodyTop > 56 || document.querySelector('.App').scrollTop > 56)
            });

        }, true);

        if( this.state.isDevice )
            document.querySelector('body').classList.add('cl-is-device');

    }

    render() {

        return (
            <HashRouter onChange={()=> console.log('change') }>

                <div className={classNames({
                    'App': true,
                    'cl-app--expand': this.state.scrolled
                })}>

                    <Header/>


                    <div className="app-content">

                        <Route component={DrawerInjector}/>

                        <Switch>
                            <Route path="/community/:tab/:id/:drawer" component={Community}/>


                            <Route path="/iot/:action?/:drawer?/:id?" component={HomeIoT}/>


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
