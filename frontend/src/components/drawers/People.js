import React, {Component} from 'react';
import {withRouter} from 'react-router';
import Net from "scripts/net";
import LikeList from 'components/drawers/LikeList';

class People extends Component{

	constructor( props ) {
		super( props );

		this.state = {
			users: [],
		};
	}

	componentWillMount(){

		Net.getLikesOfPost( this.props.match.params.id, data => {

            // ContentHolder에 전달
            this.props.updateTitle(
                <h2 className="md-title md-title--toolbar cl-ellipsis">
                    <span>LIKE</span>
                    <span className="cl-secondary ml-03em">{data.length}</span>
                </h2>
			);

            let users = data.map( data =>{
            	return data.user;
            });

            this.setState( { users: users } );
		});
	}

    render(){

        return (
        	<div>
				<div className="cl-like__header"/>
				{ this.state.users.map( ( user, index ) => {
					return (
						<LikeList key={index} likeData={user}/>
					)
				} ) }

				<div className="cl-like__dim"/>
            </div>
		)
    }
}


export default withRouter(People);