import React from 'react';
import {Route, NavLink, Link, Switch} from 'react-router-dom';

import CommunityFeed from 'components/routes/CommunityFeed';
import CommunityNews from 'components/routes/CommunityNews';
import CommunityEvent from 'components/routes/CommunityEvent';


const Community = ({ match }) => {
    return (
        <div>
            <div className="md-tabs" role="tablist">
                <NavLink to="/community/feed">FEED</NavLink>
                <NavLink to="/community/event">EVENT</NavLink>
                <NavLink to="/community/news">NEWS</NavLink>
            </div>

            <Route exact path='/community' component={CommunityFeed}/>
            <Route path='/community/feed' component={CommunityFeed}/>
            <Route path='/community/event' component={CommunityEvent}/>
            <Route path='/community/news' component={CommunityNews}/>

        </div>
    );
};

export default Community
