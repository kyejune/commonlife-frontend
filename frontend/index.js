// import 'react-md/dist/react-md.blue-amber.min.css';
import 'styles/app.scss';

import 'react-md/dist/react-md.min.js';
import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import ReactDOM from 'react-dom';
import classNames from 'classnames';

import {Header, Footer} from './src/components/layouts/*';
import {Community, CommunityFeed, HomeIoT, LifeInfo, Reservation} from './src/components/routes/*';

class App extends Component {

    constructor( prop ){
        super( prop );

        this.state = {
            scrolled: false,
        }
    }

    componentDidMount() {
        document.addEventListener('scroll', ()=>{
            this.setState({
                scrolled: ( document.documentElement.scrollTop > 56 )
            })
        });
    }

    render() {

        return (
            <Router>

                <div className={ classNames( { 'App':true, 'cl-app--expand':this.state.scrolled } ) }>
                    <Header/>

                    <div className="app-content">

                        <Route exact path="/" component={Community}/>

                        <Route path="/community" component={Community}/>
                        <Route path="/iot" component={HomeIoT}/>
                        <Route path="/life" component={LifeInfo}/>
                        <Route path="/reservation" component={Reservation}/>

                    </div>

                    <Footer/>
                </div>

            </Router>
        );

    }
}

ReactDOM.render(<App/>, document.getElementById("app"));