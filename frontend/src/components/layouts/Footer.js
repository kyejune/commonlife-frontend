/* Community.jsx */
import React, {PureComponent, Component} from 'react';
import {BottomNavigation, FontIcon} from 'react-md';
import {NavLink} from 'react-router-dom';


// https://velopert.com/3417
class Footer extends Component {

    // constructor( prop ){
    //     super( prop );
    //
    //     this.state = {
    //         style:{ bottom:0 }
    //     }
    // }
    //
    // componentDidMount() {
    //     document.addEventListener('scroll', this.handleScroll.bind(this) );
    // }
    //
    // componentWillUnmount() {
    //     document.removeEventListener('scroll', this.handleScroll.bind(this) );
    // }
    //
    // handleScroll(){
    //     this.setState({
    //         style:{
    //             bottom: Math.max( -document.documentElement.scrollTop, -56 ),
    //         },
    //     });
    // }

    render() {
        return (

            <footer role="navigation"
                    className="md-paper md-paper--1 md-bottom-navigation md-bottom-navigation--dynamic">

                <NavLink exact to="/community" activeClassName="footer__button--selected"
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