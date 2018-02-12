import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';


class ReservationHistory extends Component{

	constructor( props ) {
		super( props );
	}

	render(){

		return (
			<div>
				예약내역
			</div>
		)
	}
}


export default observer(withRouter(ReservationHistory));