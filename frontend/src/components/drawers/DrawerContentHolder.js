/* DrawerContentHolder.jsx */
import React, {Component} from 'react';
import {Button, Drawer, Toolbar} from 'react-md';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';
import Store from "scripts/store";
import queryString from "query-string";

class DrawerContentHolder extends Component {


    onClose(){

        let paths = this.props.location.pathname.split('/');
            paths.length -= 2;
        this.props.history.replace(paths.join('/'));

        Store.drawer = '';
    }


    render() {

        console.log( 'holder:', this.props.match.params.drawer );
        let CustomHeader = null;
        if( this.props.match.params.drawer === 'like' ){

            let count = queryString.parse(this.props.location.search).count;

            CustomHeader = <div>
                <span>LIKE</span>
                <span>{ count }</span>
            </div>
        }

        return (
            <div className="cl-drawer-content-holder">

                <Toolbar
                    colored
                    fixed
                    nav={<Button icon onClick={ ()=> this.onClose() }>close</Button>}
                    title={ this.props.title }
                    children={ CustomHeader }
                />

                <section className="dialogs__content">

                    {this.props.children}

                </section>

            </div>
        )
    }
}

export default observer( withRouter(DrawerContentHolder) );