import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';
import { Button, Drawer, Toolbar} from "react-md";
import Store from "../../scripts/store";


class CardItemDetailDrawer extends Component{

    onClose(){

        let paths = this.props.location.pathname.split('/');
        paths.length -= 2;
        this.props.history.replace(paths.join('/'));

        Store.drawer = '';
    }


    render(){

        return <Drawer
            type={Drawer.DrawerTypes.TEMPORARY}
            visible={ Store.drawer === 'card-item-detail' }
            onVisibilityChange={()=>{}}
            defaultMedia="mobile"
            portal={true}
            overlay={false}
            className="cl-card-detail"
            position="right"
        >

            <Toolbar
                colored
                fixed
                nav={<Button icon onClick={() => this.onClose() }>close</Button>}
                title="새글 보기"
            />


            <section className="cl-write-section dialogs__content">
                카드 아이템 뷰어
            </section>

        </Drawer>
    }
}


export default observer( withRouter(CardItemDetailDrawer) );