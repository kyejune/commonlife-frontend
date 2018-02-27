import React, { Component } from 'react';
import {observer} from 'mobx-react';
import CardItem from 'components/ui/CardItem';
import Store from "scripts/store";

class CommunityNews extends Component {


	render () {
		return (
            <div className="cl-fitted-box">

                <div>

					{ Store.news.map( ( card, index ) => {
						return (
                            <CardItem key={index} list="/community/news" cardData={card}/>
						)
					} ) }

                </div>
            </div>
		)
	}
}

export default observer(CommunityNews);