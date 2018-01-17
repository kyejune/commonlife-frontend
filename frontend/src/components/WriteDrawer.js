/* WriteDrawer.jsx */
import React, {Component} from 'react';
import { Button, Drawer, Toolbar, FocusContainer } from 'react-md';

import BottomDrawer from 'components/BottomDrawer';


class WriteDrawer extends Component{

    handleSubmit(){
        console.log( 'submit' );
    }

    render(){

        return (
            <BottomDrawer
                type={ Drawer.DrawerTypes.TEMPORARY }
                visible={ this.props.visible }
                onVisibilityChange={ this.props.onWriteChange }
                onMediaTypeChange={ ()=>{} }
                defaultMedia="mobile"
                portal={true}
                overlay={false}
            >

                <Toolbar
                    colored
                    fixed
                    nav={<Button icon onClick={() => this.props.onWriteChange(false) }>close</Button>}
                    title="새글 쓰기"
                />

                <section className="md-toolbar-relative dialogs__content drawers__content__scrollable">

                    <FocusContainer
                        focusOnMount
                        onSubmit={this.props.onWriteChange}
                    >
                        <textarea name="" id="" cols="30" rows="10" placeholder="새로운 글을 작성해 보세요." />
                    </FocusContainer>

                </section>
            </BottomDrawer>
        )
    }
}

export default WriteDrawer;