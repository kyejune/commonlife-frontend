import React, {Component} from 'react';
import Drawer from "react-md/src/js/Drawers/Drawer";
import Store from "../../scripts/store";

class DrawerWrapper extends Drawer {

    constructor(props) {

        let obj = {...Store.customDrawerProps};
        obj.renderNode = document.querySelector('.App');
        obj.visible = Store.hasDrawer(props.drawer);


        super(obj);
    }


    render() {
        return <DrawerContentHolder back>
            드로어 테스트...
        </DrawerContentHolder>
    }
}


export default DrawerWrapper;
