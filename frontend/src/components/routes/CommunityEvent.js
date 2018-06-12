import React, { Component } from 'react';
import {observer} from 'mobx-react';
import CardItem from 'components/ui/CardItem';
import Store from "scripts/store";
import Net from "../../scripts/net";
import {intercept} from "mobx/lib/mobx";
import classNames from 'classnames';
import {withRouter} from "react-router-dom";

class CommunityEvent extends Component {

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
                Store.event = [];
                setTimeout(this.loadPage, 0);
            }

            return change;
        } );
    }


    componentDidMount(){
        this.loadPage( 0 );
    }

    componentDidUpdate(prevProps) {

        // 상세보기 왔다갔다할때 쓸데없는 목록 갱신을 막음으로 스크롤 위치 초기화되는걸 방어
        let pathname = this.props.location.pathname;
        let prevPathname = prevProps.location.pathname;
        if( pathname.indexOf( prevPathname ) >= 0 || prevPathname.indexOf( pathname ) >= 0 ) return;

        if (this.props.location !== prevProps.location && ( this.props.location.pathname === '/community/event' ) ){
            Store.event.length = 0;
            this.loadPage( 0 );
        }
    }

    loadPage=( targetPage=0 )=>{
        if( targetPage > 0 && targetPage >= this.maxPage ) return;

        this.isLoading = true;

        Net.getFeed( 'event', targetPage, res=>{
            this.page = res.currentPage - 1;
            this.maxPage = res.totalPages - 1;
            this.isLoading = false;

            this.setState({ isEmpty: ( Store.event.length === 0 ), pulledTop:false });
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
            Content = Store.event.concat(Store.event).map( ( card, index ) => {
                return (
                    <CardItem key={index} list="/community/event" cardData={card}/>
                )
            } );
        }


		return (
			<div className="cl-fitted-box">

                <div className={ classNames( "spinner--more", { "cl--none":this.state.isEmpty||!this.state.isRefresh }) } />

				<div className="cl-card-items" ref={ r => this.scrollBox = r } onScroll={ this.onScroll }>
					{ Content }
				</div>

			</div>
		)
	}
}

export default withRouter( observer(CommunityEvent) );