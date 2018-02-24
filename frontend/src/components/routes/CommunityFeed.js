import React, { Component } from 'react';
import {observer} from 'mobx-react';
import WriteDrawer from "components/drawers/WriteDrawer";
import CardItem from 'components/ui/CardItem';
import Store from 'scripts/store';
import Net from 'scripts/net';
import BottomDrawer from "components/drawers/BottomDrawer";
import DrawerContentHolder from "components/drawers/DrawerContentHolder";

class CommunityFeed extends Component {

    componentDidMount(){
		Net.getFeedAll();
	}


	render () {

    	console.log( 'feed:', Store.feed );

		return (
			<div className="cl-fitted-box">

				<div>
					{ Store.feed.map( ( card, index ) => {
						return (
							<CardItem key={index} list="/community/feed" cardData={card}/>
						)
					} ) }
				</div>


                <BottomDrawer visible={Store.drawer.indexOf('write')>=0}
							  onVisibilityChange={()=>{}}
							  portal={true}>
                    <DrawerContentHolder title="새글쓰기">
                        <WriteDrawer/>
                    </DrawerContentHolder>
                </BottomDrawer>
			</div>
		)
	}
}

export default observer(CommunityFeed);