import React, { Component } from 'react';
import { FontIcon, TabsContainer, Tabs, Tab } from 'react-md';

import CommunityFeed from 'components/routes/CommunityFeed';
import CommunityNews from 'components/routes/CommunityNews';
import CommunityEvent from 'components/routes/CommunityEvent';

class Community extends Component{

    render(){

        return(
            <TabsContainer panelClassName="md-grid" colored
                           defaultTabIndex={0}>
                <Tabs tabId="simple-tab" mobile={true} className="cl-second-header">
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

export default Community;
