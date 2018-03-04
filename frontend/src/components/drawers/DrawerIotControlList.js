import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';

class DrawerIotControlList extends Component{

	constructor(props) {
		super(props);

		this.state = {

		}
	}

	render() {

		return (
			<div>
				DrawerIotControlList
			</div>
		)
	}
}


export default observer(withRouter(DrawerIotControlList));