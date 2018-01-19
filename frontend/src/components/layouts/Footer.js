/* Community.jsx */
import React, {PureComponent, Component} from 'react';
import {BottomNavigation, FontIcon} from 'react-md';
import {NavLink} from 'react-router-dom';
import Store from 'scripts/store';


class Footer extends Component {

    render() {

        return (

            <footer role="navigation"
                    className="md-paper md-paper--1 md-bottom-navigation md-bottom-navigation--dynamic">

                <NavLink exact to="/community" activeClassName="footer__button--selected"
                         isActive={( match, location )=>{ return ( location.pathname.indexOf('CoreSimulator') >= 0 ||  location.pathname == '' || location.pathname == '/' || location.pathname == '/community') }}
                         className="md-fake-btn md-pointer--hover md-fake-btn--no-outline md-bottom-nav md-bottom-nav--active md-bottom-nav--fixed">
                    <FontIcon>book</FontIcon>
                    <div className="md-bottom-nav-label">Community</div>
                </NavLink>

                <NavLink to="/iot" activeClassName="footer__button--selected"
                         className="md-fake-btn md-pointer--hover md-fake-btn--no-outline md-bottom-nav md-bottom-nav--active md-bottom-nav--fixed">
                    <FontIcon>book</FontIcon>
                    <div className="md-bottom-nav-label">HomeIoT</div>
                </NavLink>

                <NavLink to="/life" activeClassName="footer__button--selected"
                         className="md-fake-btn md-pointer--hover md-fake-btn--no-outline md-bottom-nav md-bottom-nav--active md-bottom-nav--fixed">
                    <FontIcon>book</FontIcon>
                    <div className="md-bottom-nav-label">Life</div>
                </NavLink>

                <NavLink to="/reservation" activeClassName="footer__button--selected"
                         className="md-fake-btn md-pointer--hover md-fake-btn--no-outline md-bottom-nav md-bottom-nav--active md-bottom-nav--fixed">
                    <FontIcon>book</FontIcon>
                    <div className="md-bottom-nav-label">Reservation</div>
                </NavLink>

            </footer>

        );
    }
}

export default Footer;