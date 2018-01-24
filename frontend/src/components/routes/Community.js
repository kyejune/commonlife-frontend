import React, {Component} from 'react';
import {observer} from 'mobx-react';
import {TabsContainer, Tabs, Tab} from 'react-md';
import {withRouter} from 'react-router';

import CommunityFeed from './CommunityFeed';
import CommunityNews from './CommunityNews';
import CommunityEvent from './CommunityEvent';
import CardItemDetailDrawer from "./CardItemDetailDrawer";
import Store from "scripts/store";

class Community extends Component {

    constructor(props) {
        super(props);

        this.state = {
            name: 'community',
            tabs: ['feed', 'event', 'news'],
        }
    }

    onTabChange = (activeTabIndex) => {
        let path = '/' + this.state.name + '/' + this.state.tabs[activeTabIndex];
        this.props.history.replace(path);
    };

    render() {

        let activeTabIndex = 0;

        let paths = this.props.location.pathname.split('/');

        if (paths.length === 3) {
            let tabStr = paths[2];
            if (tabStr != null)
                activeTabIndex = this.state.tabs.indexOf(tabStr);

        } else if (paths.length >= 4) {
            let detailStr = paths[3];
            if (detailStr === 'view'){
                Store.drawer = 'card-item-detail';
            }
        }

        return (
            <div>

                <TabsContainer panelClassName="md-grid" colored
                               activeTabIndex={activeTabIndex}
                               onTabChange={index => this.onTabChange(index)}>
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

                <CardItemDetailDrawer
                    visibility={ Store.drawer === 'card-item-detail' }/>
            </div>
        )
    }
}

export default observer(withRouter(Community));
