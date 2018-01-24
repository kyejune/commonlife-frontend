import React, {Component} from 'react';
import {observer} from 'mobx-react';
import {TabsContainer, Tabs, Tab, Drawer} from 'react-md';
import {withRouter} from 'react-router';

import CommunityFeed from './CommunityFeed';
import CommunityNews from './CommunityNews';
import CommunityEvent from './CommunityEvent';
import DrawerContentHolder from 'components/drawers/DrawerContentHolder';
import CardItemDetail from "../drawers/CardItemDetail";
import People from "../drawers/People";
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

            switch (paths[3]) {
                case 'view':
                    Store.drawer = 'card-item-detail';
                    break;

                case 'like':
                    Store.drawer = 'people';
                    break;
            }
        }

        const Temp = <h1>111111111111</h1>;

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

                {/* 카드 상세보기 */}
                <Drawer {...Store.customDrawerProps}
                        visible={Store.drawer === 'card-item-detail'}>
                    <DrawerContentHolder>
                        <CardItemDetail/>
                    </DrawerContentHolder>
                </Drawer>

                {/* Like 찍은 분들 */}
                <Drawer visible={Store.drawer === 'people'}
                        {...Store.customDrawerProps} >
                    <DrawerContentHolder>
                        <People/>
                    </DrawerContentHolder>
                </Drawer>
            </div>
        )
    }
}

export default observer(withRouter(Community));
