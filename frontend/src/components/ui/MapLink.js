import React, { Component } from 'react';
import IconSrc from 'images/ic-plus-24-px-copy-2@3x.png';


/* 원하는 지도를 띄울 맵 태그 */
class MapLink extends Component {


    // navermaps://?menu=location&pinType=place&lat=LATITUDE&lng=LONGITUDE&title=PLACE_NAME
    render() {
        return (
            <a to="#" className="cl-map__link--branch">
               <i className="cl-map-pin__icon"/>
               <span>MAP</span>
            </a>
        );
    }
}

    
export default MapLink;
