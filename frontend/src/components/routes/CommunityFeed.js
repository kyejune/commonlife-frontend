import React, {Component} from 'react';
import {observer} from 'mobx-react';
import {intercept} from 'mobx';
import CardItem from 'components/ui/CardItem';
import Store from 'scripts/store';
import Net from 'scripts/net';
import classNames from 'classnames';

class CommunityFeed extends Component {

	constructor(props){
		super( props );

		this.isLoading = false;
		this.page = 0;
		this.maxPage = 1;

		this.state = {
			isEmpty: false,
			isMore: false,
		}

        intercept( Store, 'communityCmplxId', change=>{

        	if( change.newValue != Store.communityCmplxId ) {
                Store.feed = [];
                setTimeout(this.loadPage, 0);
            }

            return change;
		} );
	}

    componentDidMount(){
		this.loadPage( 0 );
	}

	loadPage=( targetPage=0 )=>{
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
		const IS_TOP = (SCROLL_VALUE <= -10);
		const IS_BOTTOM = (SCROLL_VALUE >= this.scrollBox.scrollHeight - this.scrollBox.clientHeight );

		if( IS_TOP ) this.loadPage( 0 );
		else if( IS_BOTTOM ) this.loadPage( this.page + 1 );

		this.setState({ isMore: IS_TOP });
	}


	render () {

		let Content;
		let Spinner;

		if( this.state.isEmpty ){
			Content = <div className="cl-content--empty"/>;
		}else{
			// 임시로 event랑 통합
			let complexFeed = Store.event.concat(Store.feed);

            complexFeed.sort( (a, b )=>{
                return new Date(b.updDttm).getTime() - new Date(a.updDttm).getTime();
			});


            Content = complexFeed.map( ( card, index ) => {
                return (
                    <CardItem key={index} list="/community/feed" cardData={card}/>
                )
            } )
		}

		return (
			<div className="cl-fitted-box" >

				<div className={ classNames( "spinner--more", { "cl--none":this.state.isEmpty||!this.state.isMore }) } />

				<div ref={ r => this.scrollBox = r } onScroll={ this.onScroll } className="cl-card-items">
					{ Content }
				</div>
			</div>
		)
	}
}

export default observer(CommunityFeed);