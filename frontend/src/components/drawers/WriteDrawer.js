/* WriteDrawer.jsx */
import React, {Component} from 'react';
import {Button, Drawer, Toolbar} from 'react-md';
import {observer} from 'mobx-react';

import BottomDrawer from 'components/drawers/BottomDrawer';
import addSrc from 'images/img-bt-gray@3x.png';
import completeSrc from 'images/complete-bt-blueicon@3x.png';
import previewSrc from 'images/img-preview-holder@3x.png';
import Store from "scripts/store";

class WriteDrawer extends BottomDrawer {

    handleSubmit() {
        console.log('submit');
    }

    render() {

        return <div>
                {/*<Toolbar*/}
                    {/*colored*/}
                    {/*fixed*/}
                    {/*nav={<Button icon onClick={() => Store.drawer = '' }>close</Button>}*/}
                    {/*title="새글 쓰기"*/}
                {/*/>*/}

                <section className="cl-write-section dialogs__content">

                    <textarea name="" id="" cols="30" rows="5" placeholder="새로운 글을 작성해 보세요."/>


                    <footer>
                        <div className="cl-preview-holder">
                            <img src={previewSrc} alt="이미지 미리보기" width="52" height="52"/>
                            <button>이미지 삭제</button>
                        </div>
                        <div className="cl-write-toolbar cl-flex-between">
                            <button>
                                <img src={addSrc} alt="이미지 추가" width="139" height="36"/>
                            </button>
                            <button>
                                <img src={completeSrc} alt="이미지 추가" width="97" height="36"/>
                            </button>
                        </div>
                    </footer>


                </section>
            </div>
    }
}

export default observer(WriteDrawer);

// {/*<BottomDrawer*/}
// {/*type={Drawer.DrawerTypes.TEMPORARY}*/}
// {/*visible={this.props.visible}*/}
// {/*onVisibilityChange={()=>{}}*/}
// {/*defaultMedia="mobile"*/}
// {/*portal={true}*/}
// {/*overlay={false}*/}
// {/*className="cl-write"*/}
// {/*>*/}