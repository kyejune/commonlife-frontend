import React, {Component} from 'react';
import {withRouter} from 'react-router';
import DB from "scripts/net";
import SwiperViewer from 'components/ui/SwiperViewer';
import {observer} from 'mobx-react';


class DrawerSwiperViewer extends Component{

	constructor( props ) {
		super( props );

		this.state = {
			pictures: [],
		}
	}

	componentWillMount(){
		DB.getReservation( 0, data => {
			this.setState( { pictures: data.pictures } );

			// ContentHolder에 전달
			this.props.updateTitle(
				<h2 className="md-title md-title--toolbar cl-ellipsis">
					<span>이미지상세보기</span>
					<span className="cl-secondary ml-03em">{data.pictures.length}</span>
				</h2>
			);
		});
	}

	render(){

		return (
			<div>
				<SwiperViewer thumbnails={this.state.pictures} viewType={'full-screen'}/>
			</div>
		)
	}
}


export default withRouter(DrawerSwiperViewer);