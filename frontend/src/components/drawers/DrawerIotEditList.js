import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';

class DrawerIotControlMode extends Component{

	constructor(props) {
		super(props);

		this.state = {

		}
	}

	render() {

		return (
			<div>
				editList
			</div>
		)
	}
}


export default observer(withRouter(DrawerIotControlMode));