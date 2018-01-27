/* WriteDrawer.jsx */
import React from 'react';
import {observer} from 'mobx-react';

import BottomDrawer from 'components/drawers/BottomDrawer';
import addSrc from 'images/img-bt-gray@3x.png';
import completeSrc from 'images/complete-bt-blueicon@3x.png';
import previewSrc from 'images/img-preview-holder@3x.png';

class WriteDrawer extends BottomDrawer {

    render() {

        return <div>

            <section className="cl-write-section dialogs__content">

                <textarea name="" id="" cols="30" rows="5" placeholder="새로운 글을 작성해 보세요."/>


                <footer className="cl-flex-between">
                    <div className="cl-preview-holder">
                        <img src={previewSrc} alt="이미지 미리보기" width="52" height="52"/>
                        <button>이미지 삭제</button>
                    </div>

                    <button>
                        <img src={addSrc} alt="이미지 추가" width="139" height="36"/>
                    </button>
                    <button>
                        <img src={completeSrc} alt="이미지 추가" width="97" height="36"/>
                    </button>
                </footer>


            </section>
        </div>
    }
}

export default observer(WriteDrawer);