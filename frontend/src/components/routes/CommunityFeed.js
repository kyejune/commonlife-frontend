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
		Net.getFeed( 'feed', 0 );
	}


	render () {

    	console.log('Community feed render' );

		return (
			<div className="cl-fitted-box">

				<div>
					{ Store.feed.map( ( card, index ) => {
						return (
							<CardItem key={index} list="/community/feed" cardData={card}/>
						)
					} ) }
				</div>
			</div>
		)
	}
}

export default observer(CommunityFeed);