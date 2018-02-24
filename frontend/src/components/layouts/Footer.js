/* Community.jsx */
import React, {Component} from 'react';
import {NavLink, Switch, Route} from 'react-router-dom';
import {Button} from 'react-md';
import Store from "scripts/store";

import Src0 from 'images/ic-menu-0.png';
import Src1 from 'images/ic-menu-1.png';
import Src2 from 'images/ic-menu-2.png';
import Src3 from 'images/ic-menu-3.png';


const WriteButton = () => {
    return <Button floating primary
                   iconClassName="fa fa-pencil fa-2x"
                   className="cl-write__button--fixed"
                   onClick={() => Store.drawer.push('write')}
    />
}

class Footer extends Component {



    render() {

        return (

            <div id="cl-footer">

                <Switch>
                    <Route path="/" component={WriteButton} exact />
                    <Route path="/community" component={WriteButton} exact />
                    <Route path="/community/feed" component={WriteButton}/>
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
                        <div className="md-bottom-nav-label">Home IoT</div>
                    </NavLink>

                    <NavLink to="/life" activeClassName="footer__button--selected"
                             className="md-fake-btn md-pointer--hover md-fake-btn--no-outline md-bottom-nav md-bottom-nav--active md-bottom-nav--fixed">
                        <img src={Src3} alt="Life Info"/>
                        <div className="md-bottom-nav-label">Life Info</div>
                    </NavLink>

                </footer>

            </div>

        );
    }
}

export default Footer;