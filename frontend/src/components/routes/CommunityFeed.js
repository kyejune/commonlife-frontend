import React, { Component } from 'react';
import {observer} from 'mobx-react';
import { Button } from 'react-md';
import WriteDrawer from "components/WriteDrawer";
import CardItem from 'components/ui/CardItem';
import Store from 'scripts/store';
import DB from 'scripts/db';

class CommunityFeed extends Component {

	constructor ( props ) {
		super( props );

		this.state = {
			isWrite: false,
		}
	}

    componentDidMount(){
		DB.getFeed();
	}

	onWriteChange ( isWrite ) {

		console.log( 'FEED change:', isWrite )

		this.setState( {
			isWrite: isWrite,
		} )
	}


	render () {
		return (
			<div className="cl-fitted-box">

				<div>
					{ Store.feed.map( ( card, index ) => {
						return (
							<CardItem key={index} cardData={card}/>
						)
					} ) }
				</div>


				<Button floating primary
						iconClassName="fa fa-pencil fa-2x"
						className="cl-write__button--fixed"
						onClick={()=>this.onWriteChange( true )}
				/>

				<WriteDrawer
					visible={this.state.isWrite}
					onWriteChange={( value )=>this.onWriteChange( value )}/>
			</div>
		)
	}
}

export default observer(CommunityFeed);