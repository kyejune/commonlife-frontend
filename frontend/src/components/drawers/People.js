import React, {Component} from 'react';
import {withRouter} from 'react-router';
import Net from "scripts/net";
import LikeList from 'components/drawers/LikeList';
import {observer} from 'mobx-react';


class People extends Component{

	constructor( props ) {
		super( props );

		this.state = {
			likey: [],
		}
	}

	componentWillMount(){
		Net.getLikey( 1, data => {
			this.setState( { likey: data } );

            // ContentHolder에 전달
            this.props.updateTitle(
                <h2 className="md-title md-title--toolbar cl-ellipsis">
					<span>{this.props.title}</span>
					<span className="cl-secondary ml-03em">{data.length}</span>
				</h2>
			);
		});
	}

    render(){

        return (
        	<div>
                {/*{this.props.match.params.id }번 아이디 게시물의 사람,사람,사람,,, 을 출력해주세여.*/}
				<div className="cl-like__header"/>
				{ this.state.likey.map( ( like, index ) => {
					return (
						<LikeList key={index} likeData={like}/>
					)
				} ) }

				<div className="cl-like__dim"/>
            </div>
		)
    }
}


export default observer(withRouter(People));