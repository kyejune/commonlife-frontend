import 'react-md/dist/react-md.blue-amber.min.css';
import 'styles/app.scss';

import 'react-md/dist/react-md.min.js';
import React from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import ReactDOM from 'react-dom';

import {Header, Footer} from './src/components/layouts/*';
import {Community, CommunityFeed, HomeIoT, LifeInfo, Reservation} from './src/components/routes/*';


const App = () => (
    <Router>

        <div className="App">
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

ReactDOM.render(<App/>, document.getElementById("app"));

// Hot Module Replacement
// if (module.hot) {
//     module.hot.accept();
// }