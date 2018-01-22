import React, { Component } from 'react';
import {observer} from 'mobx-react';
import CardItem from 'components/ui/CardItem';
import DB from "scripts/db";
import Store from "scripts/store";

class CommunityEvent extends Component {

	constructor ( props ) {
		super( props );
	}

    componentDidMount(){
        DB.getEvent();
    }

	render () {
		return (
			<div className="cl-fitted-box">

				<div>
					{ Store.event.map( ( card, index ) => {
						return (
							<CardItem key={index} cardData={card}/>
						)
					} ) }
				</div>

			</div>
		)
	}
}

export default observer(CommunityEvent);