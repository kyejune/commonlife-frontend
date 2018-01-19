import React, { Component } from 'react';
import { FontIcon, TabsContainer, Tabs, Tab } from 'react-md';
import { withRouter } from 'react-router';
import { stringify } from 'qs';
// import { getTab } from 'react-m';

import CommunityFeed from 'components/routes/CommunityFeed';
import CommunityNews from 'components/routes/CommunityNews';
import CommunityEvent from 'components/routes/CommunityEvent';


class Community extends Component{

    constructor(props){
        super(props);
    }

    onTabChange = (activeTabIndex) => {
        const { history, location: { pathname } } = this.props;

        let search;
        if (activeTabIndex > 0) {
            search = stringify({ tab: activeTabIndex });
        }

        history.replace({ pathname, search });
    };

    render(){

        let activeTabIndex = 0;
        let search = this.props.location.search.match(/\d/);
        if( search != null )
            activeTabIndex = parseInt(search[0]);


        return(
            <TabsContainer panelClassName="md-grid" colored
                           activeTabIndex={ activeTabIndex }
                           onTabChange={ index => this.onTabChange( index ) }>
                <Tabs tabId="simple-tab"
                      mobile={true}
                      className="cl-second-header"
                >
                    <Tab label="feed">
                        <CommunityFeed/>
                    </Tab>
                    <Tab label="event">
                        <CommunityEvent/>
                    </Tab>
                    <Tab label="news">
                        <CommunityNews/>
                    </Tab>
                </Tabs>
            </TabsContainer>
        )
    }
}

export default withRouter(Community);
