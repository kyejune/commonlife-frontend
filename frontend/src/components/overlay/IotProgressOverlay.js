import React, { Component } from 'react';
import Store from "../../scripts/store";
import IconLoader from 'components/ui/IconLoader';

import IotConnectionProgress from "../ui/IotConnectionProgress";


class IotProgressOverlay extends Component {

	render () {
		const { name, value, status } = Store.myModal;
		const Status = [
            <h3 className="cl-iot-progress-overlay__text">변경사항을<br/>즉시 적용합니다.</h3>,
            <h3 className="cl-iot-progress-overlay__text">변경사항이<br/>적용되었습니다.</h3>,
            <h3 className="cl-iot-progress-overlay__text">변경적용에<br/>실패하였습니다.</h3>
		]

		return <div className="cl-iot-progress-overlay">
			<div className="cl-iot-progress-overlay__content">
				<div className="cl-iot-progress-overlay__top">
					<div className="cl-iot-progress-overlay__icon">
						<IconLoader src={undefined}/>
					</div>
					<p className="cl-iot-progress-overlay__info-text">{ name } <span className="uppercase">{ value }</span></p>
				</div>
				{ Status[ status ] }
				<IotConnectionProgress className={ status===1?'cl--done':'' }/>
			</div>
		</div>
	}
}


export default IotProgressOverlay