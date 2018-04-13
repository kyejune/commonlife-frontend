import React, { Component } from 'react';


/* 원하는 지도를 띄울 맵 태그 */
class MapLink extends Component {


    // Android 공용
    // geo:LATITUDE,LONGITUDE?q=PLACE_NAME

    // iOS
    // navermaps://?menu=location&pinType=place&lat=LATITUDE&lng=LONGITUDE&title=PLACE_NAME
    // lng=a24c4613a8320e2ddc4d7ba8ea37c467
    // lat=4c8280d31d80d8eb13ac4743c97789ea
    render() {

        //if( !this.props.href ) return null;

        return (
            <a href={this.props.href} target="_blank" className={ "cl-map__link--branch " + this.props.className }>
               <i className="cl-map-pin__icon"/>
               <span>MAP</span>
            </a>
        );
    }
}

    
export default MapLink;
