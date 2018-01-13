import React from 'react';
import {BottomNavigation, FontIcon} from 'react-md';
import {NavLink} from 'react-router-dom';
import {Route} from 'react-dom';

class Header extends React.Component {
    render() {

        return (
            <div>

                <header className="md-paper md-paper--2 md-toolbar md-background--primary md-toolbar--text-white md-toolbar--fixed md-toolbar--over-drawer">
                    <button type="button"
                            className="md-btn md-btn--icon md-pointer--hover md-inline-block md-btn--toolbar md-toolbar--action-left">
                        <div className="md-ink-container"></div>
                        <i className="md-icon material-icons md-text--inherit">menu</i></button>
                    <select className="md-title md-title--toolbar">
                        <option value="">역삼하우징</option>
                    </select>
                </header>

            </div>

        );
    }
}

export default Header;