import React, { Component } from 'react';
import {observer} from 'mobx-react';
import CardItem from 'components/ui/CardItem';
import Store from "scripts/store";
import Net from "../../scripts/net";
import {intercept} from "mobx/lib/mobx";
import classNames from 'classnames';

class CommunityNews extends Component {


    constructor(props){
        super( props );

        this.isLoading = false;
        this.page = 0;
        this.maxPage = 1;

        this.state = {
            isEmpty: false,
            pulledTop: false,
            isRefresh: false,
        }

        intercept( Store, 'communityCmplxId', change=>{
            if( change.newValue != Store.communityCmplxId ) {
                Store.news = [];
                setTimeout(this.loadPage, 0);
            }

            return change;
        } );
    }


    componentDidMount(){
        this.loadPage( 0 );
    }

    loadPage=( targetPage=0 )=>{
        if( targetPage > 0 && targetPage >= this.maxPage ) return;

        this.isLoading = true;

        Net.getFeed( 'news', targetPage, res=>{
            this.page = res.currentPage - 1;
            this.maxPage = res.totalPages - 1;
            this.isLoading = false;

            this.setState({ isEmpty: ( Store.news.length === 0 ), pulledTop: false });
        });
    }

    onScroll=(evt)=>{
        if( this.isLoading ) return;

        const SCROLL_VALUE = this.scrollBox.scrollTop;
        const IS_TOP = (SCROLL_VALUE <= -10);
        const IS_BOTTOM = (SCROLL_VALUE >= this.scrollBox.scrollHeight - this.scrollBox.clientHeight );

        if( IS_BOTTOM ) this.loadPage( this.page + 1 );

        this.setState({ isRefresh: IS_TOP, pulledTop:(IS_TOP||true) });

        // 0으로 돌아왔고 && 밑으로 땡겨졌었다면...
        if( SCROLL_VALUE ===  0 && this.state.pulledTop ){
            this.loadPage( 0 );
        }
    }

	render () {

        let Content;

        if( this.state.isEmpty ){
            Content = <div className="cl-content--empty"/>;
        }else{
            Content = Store.news.map( ( card, index ) => {
                return (
                    <CardItem key={index} list="/community/news" cardData={card}/>
                )
            } )
        }


        return (
            <div className="cl-fitted-box cl-tab--news">

                <div className={ classNames( "spinner--more", { "cl--none":this.state.isEmpty||!this.state.isRefresh }) } />


                <div className="cl-card-items" ref={ r => this.scrollBox = r } onScroll={ this.onScroll }>
					{ Content }
                </div>
            </div>
		)
	}
}

export default observer(CommunityNews);