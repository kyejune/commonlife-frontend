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
import DrawerWrapper from "components/drawers/DrawerWrapper";

class Community extends Component {

    constructor(props) {
        super(props);

        this.state = {
            name: 'community',
            tabs: ['feed', 'event', 'news'],
            tabIndex: 0,
            // drawer: Store.drawer,
        }
    }

    onTabChange = (activeTabIndex) => {
        let path = '/' + this.state.name + '/' + this.state.tabs[activeTabIndex];
        this.props.history.replace(path);
    }

    componentDidMount(){
        this.updateRoute();
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location)
            this.updateRoute();
    }

    updateRoute(){

        console.log('community:', this.props.match.params );

        let paths = this.props.location.pathname.match(/\w+/g)||['community','feed'];

        if( this.props.match.params.id )
            Store.pushDrawer( 'card-item-detail' );
        else
            Store.clearDrawer();

        if( this.props.match.params.drawer === 'like' )
            Store.pushDrawer( 'people' );
            // setTimeout( ()=> Store.pushDrawer( 'people' ), 0 );

        this.setState({
            tabIndex: this.state.tabs.indexOf( paths[1] || 'feed' )
        });
    }

    render() {

        return (
            <div>
                <TabsContainer panelClassName="md-grid" colored
                               activeTabIndex={this.state.tabIndex}
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
                <DrawerWrapper drawer="card-item-detail">
                    <CardItemDetail/>
                </DrawerWrapper>

                <DrawerWrapper drawer="people" title="LIKE">
                    <People/>
                </DrawerWrapper>
            </div>
        )
    }
}

export default withRouter( observer(Community) );
