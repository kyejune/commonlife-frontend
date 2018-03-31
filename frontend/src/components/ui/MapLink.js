import React, { Component } from 'react';
import IconSrc from 'images/ic-plus-24-px-copy-2@3x.png';


/* 원하는 지도를 띄울 맵 태그 */
class MapLink extends Component {



    render() {
        return (
            <a to="#" className="cl-icon-map__link">
               <i className="cl-map-pin__icon"/>
               <span>MAP</span>
            </a>
        );
    }
}

    
export default MapLink;
