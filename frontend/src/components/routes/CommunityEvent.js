import React, { Component } from 'react';
import {observer} from 'mobx-react';
import CardItem from 'components/ui/CardItem';
import Store from "scripts/store";
import DB from "scripts/db";

class CommunityEvent extends Component {

    componentDidMount(){
        DB.getEvent();
    }

	render () {
		return (
			<div className="cl-fitted-box">

				<div>
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