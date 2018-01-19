import 'styles/app.scss';
import 'font-awesome/fonts/fontawesome-webfont.eot';
import 'font-awesome/scss/font-awesome.scss';

import 'react-md/dist/react-md.min.js';
import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import ReactDOM from 'react-dom';
import classNames from 'classnames';

import {Header, Footer} from './src/components/layouts/*';
import {Community, CommunityFeed, HomeIoT, LifeInfo, Reservation} from './src/components/routes/*';

try {
	document.write( '<script src="cordova.js"></script>' );
}
catch( error ) {
    console.warn( 'cannot load cordova.js' );
}

class App extends Component {

    constructor( prop ){
        super( prop );

        this.state = {
            scrolled: false,
        }
    }


    componentDidMount() {

        document.addEventListener('scroll', ()=>{
            let doc = document.querySelector('.react-swipeable-view-container>div[aria-hidden=false]');
            if( doc ){
                this.setState({
                    scrolled: ( doc.scrollTop > 56 )
                });
            }
        }, true );

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

document.addEventListener( 'deviceready', () => {
    console.log( 'device ready' );
} );