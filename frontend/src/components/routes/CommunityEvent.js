import React, { Component } from 'react';
import {observer} from 'mobx-react';
import CardItem from 'components/ui/CardItem';
import Store from "scripts/store";
import Net from "../../scripts/net";

class CommunityEvent extends Component {

	componentWillMount(){
        Net.getFeed( 'event', 0 );
	}

	render () {
		return (
			<div className="cl-fitted-box">

				<div className="cl-card-items">
					{ Store.event.map( ( card, index ) => {
						return (
							<CardItem key={index} list="/community/event" cardData={card}/>
						)
					} ) }
				</div>

			</div>
		)
	}
}

export default observer(CommunityEvent);