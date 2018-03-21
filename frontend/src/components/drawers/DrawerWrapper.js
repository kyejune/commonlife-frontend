import React, {Component} from 'react';
import DrawerContentHolder from "components/drawers/DrawerContentHolder";
import Store from "scripts/store";
import {Drawer} from "react-md";
import {observer} from "mobx-react";

class DrawerWrapper extends Component {

    render() {
        return <Drawer {...Store.customDrawerProps}
                       renderNode={document.querySelector('.App')}
                       className={this.props.className}
                       visible={Store.hasDrawer(this.props.drawer)}>
            <DrawerContentHolder {...this.props} >
                {this.props.children}
            </DrawerContentHolder>
        </Drawer>
    }
}


export default observer( DrawerWrapper );
