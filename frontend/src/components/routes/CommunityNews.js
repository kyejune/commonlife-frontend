import React, { Component } from 'react';
import {observer} from 'mobx-react';
import CardItem from 'components/drawers/CardItem';
import Store from "scripts/store";
import DB from "scripts/db";

class CommunityNews extends Component {

    componentDidMount(){
        DB.getNews();
    }

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