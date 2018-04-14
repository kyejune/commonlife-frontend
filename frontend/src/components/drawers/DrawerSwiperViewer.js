import React, {Component} from 'react';
import DB from "scripts/net";
import SwiperViewer from 'components/ui/SwiperViewer';
import Store from "../../scripts/store";


class DrawerSwiperViewer extends Component{

	constructor( props ) {
		super( props );

		this.state = {
			pictures: [],
		}
	}

	componentDidMount(){
		DB.getReservationScheme( this.props.match.params.id, data => {
			const pictures = data.images.split( ',' ).map( image => {
				return { thumbnail: Store.api + "/imageStore/" + image }
			} );
			this.setState( { pictures } );

			// ContentHolder에 전달
			this.props.updateTitle(
				<h2 className="md-title md-title--toolbar cl-ellipsis">
					<span>이미지상세보기</span>
					<span className="cl-secondary ml-03em">{pictures.length}</span>
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


export default DrawerSwiperViewer;