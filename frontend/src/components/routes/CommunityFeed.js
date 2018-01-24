import React, { Component } from 'react';
import {observer} from 'mobx-react';
import { Button } from 'react-md';
import WriteDrawer from "components/WriteDrawer";
import CardItem from 'components/ui/CardItem';
import Store from 'scripts/store';
import DB from 'scripts/db';

class CommunityFeed extends Component {

    componentDidMount(){
		DB.getFeed();
	}

	// onWriteChange ( isWrite ) {
    //
	// 	this.setState( {
	// 		isWrite: isWrite,
	// 	} )
	// }


	render () {
		return (
			<div className="cl-fitted-box">

				<div>
					{ Store.feed.map( ( card, index ) => {
						return (
							<CardItem key={index} list="/community/feed" cardData={card}/>
						)
					} ) }
				</div>


				<Button floating primary
						iconClassName="fa fa-pencil fa-2x"
						className="cl-write__button--fixed"
						onClick={()=> Store.drawer = 'write' }
				/>

				<WriteDrawer
					visible={ Store.drawer === 'write' }/>
			</div>
		)
	}
}

export default observer(CommunityFeed);