import React, {Component} from 'react';
import {withRouter} from 'react-router';
import DB from "scripts/db";
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
		DB.getLikey( 1, data => {
			this.setState( { likey: data } );
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