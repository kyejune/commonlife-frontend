import React, { Component } from 'react';
import {observer} from 'mobx-react';
import CardItem from 'components/ui/CardItem';
import Store from "scripts/store";
import Net from "../../scripts/net";

class CommunityEvent extends Component {

    constructor(props){
        super( props );

        this.isLoading = false;
        this.page = 0;
        this.maxPage = 1;
    }


    componentDidMount(){
        this.loadPage( 0 );
    }

    loadPage=( targetPage )=>{
        console.log( targetPage, this.maxPage );
        if( targetPage >= this.maxPage ) return;

        this.isLoading = true;

        Net.getFeed( 'event', targetPage, res=>{
            this.page = res.currentPage - 1;
            this.maxPage = res.totalPages - 1;
            this.isLoading = false;
        });
    }

    onScroll=(evt)=>{
        if( this.isLoading ) return;

        const SCROLL_VALUE = this.scrollBox.scrollTop;
        // const IS_TOP = (SCROLL_VALUE <= 0);
        const IS_BOTTOM = (SCROLL_VALUE >= this.scrollBox.scrollHeight - this.scrollBox.clientHeight );

        // if( IS_TOP ) this.loadPage( 0 );
        // else if( IS_BOTTOM ) this.loadPage( this.page + 1 );
        if( IS_BOTTOM ) this.loadPage( this.page + 1 );
    }

	render () {
		return (
			<div ref={ r => this.scrollBox = r } className="cl-fitted-box">

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