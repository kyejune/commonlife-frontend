import React, {Component} from 'react';
import {observer} from 'mobx-react';
import {TabsContainer, Tabs, Tab} from 'react-md';
import {withRouter} from 'react-router';

import CommunityFeed from './CommunityFeed';
import CommunityNews from './CommunityNews';
import CommunityEvent from './CommunityEvent';
import CardItemDetail from "../drawers/CardItemDetail";
import People from "../drawers/People";
import Store from "scripts/store";
import DrawerWrapper from "components/drawers/DrawerWrapper";
import ProfileViewer from "../drawers/ProfileViewer";

class Community extends Component {

    constructor(props) {
        super(props);

        this.state = {
            name: 'community',
            tabs: ['feed', 'event', 'news'],
            tabIndex: 0,
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

        console.log('community:', this.props );

        let paths = this.props.location.pathname.match(/\w+/g)||['community','feed'];

        if( this.props.match.params.id )
            Store.pushDrawer( 'card-item-detail' );
        else
            Store.clearDrawer();

        if( this.props.match.params.drawer === 'like' )
            Store.pushDrawer( 'people' );

        // if( paths.indexOf('profile') >= 0 )
        //     Store.pushDrawer( 'profile' );

        const WHO = new URLSearchParams( this.props.location.search ).get('profile');
        if( WHO ){
            Store.pushDrawer( 'profile' );
        }

        this.setState({
            tabIndex: this.state.tabs.indexOf( paths[1] || 'feed' )
        });
    }

    render() {

        return (
            <div className="cl-tab--community">
                <TabsContainer panelClassName="md-grid" colored
                               activeTabIndex={this.state.tabIndex}
                               onTabChange={index => this.onTabChange(index)}>
                    <Tabs tabId="simple-tab"
                          mobile={true}
                    >
                        <Tab label="feed" inkDisabled>
                            <CommunityFeed/>
                        </Tab>
                        <Tab label="event" inkDisabled>
                            <CommunityEvent/>
                        </Tab>
                        <Tab label="notice" inkDisabled>
                            <CommunityNews/>
                        </Tab>
                    </Tabs>
                </TabsContainer>


                {/* 카드 상세보기 */}
                <DrawerWrapper drawer="card-item-detail" statusdark >
                    <CardItemDetail/>
                </DrawerWrapper>

                <DrawerWrapper drawer="people" title="LIKE" >
                    <People/>
                </DrawerWrapper>


                {/* 사용자 정보 */}
                <DrawerWrapper drawer="profile" title="사용자 정보" back>
                    <ProfileViewer/>
                </DrawerWrapper>
            </div>
        )
    }
}

export default withRouter( observer(Community) );
