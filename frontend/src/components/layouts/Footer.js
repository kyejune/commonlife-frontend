/* Community.jsx */
import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';
import {FontIcon} from 'react-md';

class Footer extends Component {

    render() {

        return (

            <footer role="navigation"
                    className="md-paper md-paper--1 md-bottom-navigation md-bottom-navigation--dynamic">

                <NavLink to="/community" activeClassName="footer__button--selected"
                         isActive={( match, location )=>{ return ( location.pathname.indexOf('CoreSimulator') >= 0 ||  location.pathname === '' || location.pathname === '/' || location.pathname.indexOf( '/community' ) >= 0 ) }}
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