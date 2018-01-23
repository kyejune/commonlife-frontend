import React, {Component} from 'react';
import {observer} from 'mobx-react';
import {FontIcon, TabsContainer, Tabs, Tab, Drawer} from 'react-md';
import {withRouter} from 'react-router';
import {stringify} from 'qs';

import CommunityFeed from 'components/routes/CommunityFeed';
import CommunityNews from 'components/routes/CommunityNews';
import CommunityEvent from 'components/routes/CommunityEvent';
import CardItemDetailDrawer from "./CardItemDetailDrawer";
import Store from "../../scripts/store";

// 애니메이션 라운터 추가
// https://github.com/trungdq88/react-router-page-transition/blob/master/EXAMPLES.md


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

        if (paths.length == 3) {
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
