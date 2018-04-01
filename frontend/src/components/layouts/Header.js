import React, {Component} from 'react';

import logo from 'images/logo@2x.png';
import alert from 'images/alert-icon-top-28@2x.png';
import Store from "../../scripts/store";

class Header extends Component {


    openBranchList=()=>{
        Store.pushDrawer( 'branch' );
    }


    render() {

        return (
            <header
                className="md-paper md-toolbar md-background--primary md-toolbar--text-white">

                <button type="button"
                        className="md-btn md-btn--icon md-pointer--hover md-inline-block md-btn--toolbar md-toolbar--action-left">
                    <div className="md-ink-container"></div>
                    <img src={logo} alt="로고 이미지"/>
                </button>

                <button className="md-title md-title--toolbar" onClick={ this.openBranchList }>
                    역삼하우징
                </button>

                <div className="md-cell--right md-toolbar--action-right">
                    <div className="md-layover md-layover--simplified md-inline-block md-menu-container">
                        <button id="toolbar-colored-kebab-menu-toggle"
                                type="button"
                                className="md-btn md-btn--icon md-pointer--hover md-inline-block md-btn--toolbar">
                            <img src={alert} alt="알림 이미지"/>
                        </button>
                    </div>
                </div>

            </header>
        );
    }
}

export default Header;