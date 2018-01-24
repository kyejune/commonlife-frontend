import React, { Component } from 'react';
import {observer} from 'mobx-react';
import { Button } from 'react-md';
import WriteDrawer from "components/drawers/WriteDrawer";
import CardItem from 'components/ui/CardItem';
import Store from 'scripts/store';
import DB from 'scripts/db';
import BottomDrawer from "components/drawers/BottomDrawer";
import DrawerContentHolder from "components/drawers/DrawerContentHolder";

class CommunityFeed extends Component {

    componentDidMount(){
		DB.getFeed();
	}


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

                <BottomDrawer visible={Store.drawer === 'write'} portal={true}>
                    <DrawerContentHolder title="새글쓰기">
                        <WriteDrawer/>
                    </DrawerContentHolder>
                </BottomDrawer>
			</div>
		)
	}
}

export default observer(CommunityFeed);