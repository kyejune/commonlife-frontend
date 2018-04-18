import React, { Component } from 'react';
import {observer} from 'mobx-react';
import CardItem from 'components/ui/CardItem';
import Store from "scripts/store";
import Net from "../../scripts/net";
import {intercept} from "mobx/lib/mobx";
import classNames from 'classnames';

class CommunityEvent extends Component {

    constructor(props){
        super( props );

        this.isLoading = false;
        this.page = 0;
        this.maxPage = 1;

        this.state = {
            isEmpty: false,
        }

        intercept( Store, 'communityCmplxId', change=>{
            if( change.newValue != Store.communityCmplxId ) {
                Store.event = [];
                setTimeout(this.loadPage, 0);
            }

            return change;
        } );
    }


    componentDidMount(){
        this.loadPage( 0 );
    }

    loadPage=( targetPage=0 )=>{
        console.log( targetPage, this.maxPage );
        if( targetPage >= this.maxPage ) return;

        this.isLoading = true;

        Net.getFeed( 'event', targetPage, res=>{
            this.page = res.currentPage - 1;
            this.maxPage = res.totalPages - 1;
            this.isLoading = false;

            this.setState({ isEmpty: ( Store.event.length === 0 ) });
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

        if( this.state.isEmpty ){
            Content = <div className="cl-content--empty"/>;
        }else{
            Content = Store.event.concat(Store.event).map( ( card, index ) => {
                return (
                    <CardItem key={index} list="/community/event" cardData={card}/>
                )
            } );
        }


		return (
			<div className="cl-fitted-box">

                <div className={ classNames( "spinner--more", { "cl--none":this.state.isEmpty||!this.state.isMore }) } />

				<div className="cl-card-items" ref={ r => this.scrollBox = r } onScroll={ this.onScroll }>
					{ Content }
				</div>

			</div>
		)
	}
}

export default observer(CommunityEvent);