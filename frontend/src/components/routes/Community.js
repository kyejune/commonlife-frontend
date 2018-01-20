import React, { Component } from 'react';
import { FontIcon, TabsContainer, Tabs, Tab } from 'react-md';
import { withRouter } from 'react-router';
import { stringify } from 'qs';
// import { getTab } from 'react-m';

import CommunityFeed from 'components/routes/CommunityFeed';
import CommunityNews from 'components/routes/CommunityNews';
import CommunityEvent from 'components/routes/CommunityEvent';

// 애니메이션 라운터 추가
// https://github.com/trungdq88/react-router-page-transition/blob/master/EXAMPLES.md


class Community extends Component{

    constructor(props){
        super(props);

        this.state = {
            name:'community',
            tabs:['feed', 'event', 'news']
        }
    }

    onTabChange = (activeTabIndex) => {
        let path = '/' + this.state.name + '/' + this.state.tabs[activeTabIndex];
        this.props.history.replace( path );
    };

    render(){

        let activeTabIndex = 0;
        let tabStr = this.props.location.pathname.split('/')[2];
        if( tabStr != null )
            activeTabIndex = this.state.tabs.indexOf( tabStr );

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
