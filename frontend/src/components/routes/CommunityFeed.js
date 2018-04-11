import React, {Component} from 'react';
import {observer} from 'mobx-react';
import CardItem from 'components/ui/CardItem';
import Store from 'scripts/store';
import Net from 'scripts/net';

class CommunityFeed extends Component {

	constructor(props){
		super( props );

		this.isLoading = false;
		this.page = 0;
		this.maxPage = 1;

		this.state = {
			isEmpty: false,
		}
	}


    componentDidMount(){
		this.loadPage( 0 );
	}

	loadPage=( targetPage )=>{
		if( targetPage >= this.maxPage ) return;

		this.isLoading = true;

        Net.getFeed( 'feed', targetPage, res=>{
        	this.page = res.currentPage - 1;
        	this.maxPage = res.totalPages - 1;
        	this.isLoading = false;

        	this.setState({ isEmpty: ( Store.feed.length === 0 ) });
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

		let Content;

		if( this.state.isEmpty ){
			Content = <div className="cl-content--empty"/>;
		}else{
            Content = Store.feed.map( ( card, index ) => {
                return (
                    <CardItem key={index} list="/community/feed" cardData={card}/>
                )
            } )
		}

		return (
			<div ref={ r => this.scrollBox = r } className="cl-fitted-box" onScroll={ this.onScroll } >

				<div className="cl-card-items">
					{ Content }
				</div>
			</div>
		)
	}
}

export default observer(CommunityFeed);