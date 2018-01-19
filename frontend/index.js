import 'styles/app.scss';
import 'font-awesome/fonts/fontawesome-webfont.eot';
import 'font-awesome/scss/font-awesome.scss';

import 'react-md/dist/react-md.min.js';
import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import ReactDOM from 'react-dom';
import classNames from 'classnames';
import createBrowserHistory from 'history/createBrowserHistory'

import {Header, Footer} from './src/components/layouts/*';
import {Community, CommunityFeed, HomeIoT, LifeInfo, Reservation} from './src/components/routes/*';
import CardItemView from 'components/ui/CardItemView';
import Store from 'scripts/store';

try {
    document.write('<script src="cordova.js"></script>');
}
catch (error) {
    console.warn('cannot load cordova.js');
}

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
                            <Route path="/community/view" component={CardItemView}/>
                            <Route path="/community" component={Community}/>



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

ReactDOM.render(<App/>, document.getElementById("app"));

document.addEventListener('deviceready', () => {

});