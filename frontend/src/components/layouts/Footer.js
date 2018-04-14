/* Community.jsx */
import React, {Component} from 'react';
import {NavLink, Switch, Route} from 'react-router-dom';
import {Button} from 'react-md';
import Store from "scripts/store";

import Src0 from 'images/ic-menu-0.png';
import Src1 from 'images/ic-menu-1.png';
import Src2 from 'images/ic-menu-2.png';
import Src3 from 'images/ic-menu-3.png';
import IotSrc from 'images/iot-run@3x.png';
import Link from "react-router-dom/es/Link";
import Write from "images/ic-write.svg";


const WriteButton = () => {
    return <Button floating primary
                   // iconClassName="fa fa-pencil fa-2x"
                   className="cl-write__button"
                   onClick={() => Store.pushDrawer('write', { type:'feed' } )}
    >
        <img src={Write} alt=""/>
    </Button>
}


const IotButton = () => {
    return <Link className="cl-iot-run__button md-background--primary" to="/iot/ctrl"
    >
        <img src={IotSrc} alt="IoT제어" width="20" height="20"/>
    </Link>
}

class Footer extends Component {



    render() {

        return (

            <div id="cl-footer">

                <Switch>
                    <Route path="/" component={WriteButton} exact />
                    <Route path="/community" component={WriteButton} exact />
                    <Route path="/community/feed" component={WriteButton}/>

                    <Route path="/iot" component={IotButton}/>
                </Switch>


                <footer role="navigation"
                        className="md-paper md-paper--1 md-bottom-navigation md-bottom-navigation--dynamic">

                    <NavLink to="/community" activeClassName="footer__button--selected"
                             isActive={(match, location) => {
                                 return (location.pathname.indexOf('CoreSimulator') >= 0 || location.pathname === '' || location.pathname === '/' || location.pathname.indexOf('/community') >= 0)
                             }}
                             className="md-fake-btn md-pointer--hover md-fake-btn--no-outline md-bottom-nav md-bottom-nav--active md-bottom-nav--fixed">
                        <img src={Src0} alt="Community"/>
                        <div className="md-bottom-nav-label">Community</div>
                    </NavLink>

                    <NavLink to="/reservation" activeClassName="footer__button--selected"
                             className="md-fake-btn md-pointer--hover md-fake-btn--no-outline md-bottom-nav md-bottom-nav--active md-bottom-nav--fixed">
                        <img src={Src1} alt="Reservation"/>
                        <div className="md-bottom-nav-label">Reservation</div>
                    </NavLink>

                    <NavLink to="/iot" activeClassName="footer__button--selected"
                             className="md-fake-btn md-pointer--hover md-fake-btn--no-outline md-bottom-nav md-bottom-nav--active md-bottom-nav--fixed">
                        <img src={Src2} alt="HomeIoT"/>
                        <div className="md-bottom-nav-label">Smart HOME</div>
                    </NavLink>

                    <NavLink to="/info" activeClassName="footer__button--selected"
                             className="md-fake-btn md-pointer--hover md-fake-btn--no-outline md-bottom-nav md-bottom-nav--active md-bottom-nav--fixed">
                        <img src={Src3} alt="Life Info"/>
                        <div className="md-bottom-nav-label">Info</div>
                    </NavLink>

                </footer>

            </div>

        );
    }
}

export default Footer;