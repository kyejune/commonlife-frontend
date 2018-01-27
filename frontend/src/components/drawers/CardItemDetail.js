import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';
import { Button, Toolbar} from "react-md";
import Store from "scripts/store";


class CardItemDetailDrawer extends Component{

    onClose(){

        let paths = this.props.location.pathname.split('/');
        paths.length -= 2;
        this.props.history.replace(paths.join('/'));

        Store.drawer = '';
    }


    render(){

        console.log( 'CardItemDetailDrawer', this.props );

        return <div className="cl-card-detail">
            <Toolbar
                colored
                fixed
                nav={<Button icon onClick={() => this.onClose() }>close</Button>}
                title="새글 보기"
            />


            <section className="cl-write-section dialogs__content">
                카드 아이템 뷰어
            </section>

        </div>
    }
}


export default observer( withRouter(CardItemDetailDrawer) );