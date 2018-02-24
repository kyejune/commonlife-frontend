/* WriteDrawer.jsx */
import React from 'react';
import {observer} from 'mobx-react';
import Net from 'scripts/net';

import BottomDrawer from 'components/drawers/BottomDrawer';
import addSrc from 'images/img-bt-gray@3x.png';
import completeSrc from 'images/complete-bt-blueicon@3x.png';
import previewSrc from 'images/img-preview-holder@3x.png';

class WriteDrawer extends BottomDrawer {

    constructor(props) {
        super(props);

        this.state = {
            base64Img: null,
        }
    }


    selectPicture() {
        let gettedPicture = (base64) => this.gettedPicture(base64);
        let failedPicture = (msg) => this.failedPicture(msg);

        navigator.camera.getPicture(
            gettedPicture,
            failedPicture,
            {
                quality: 100,
                sourceType: 0,
                destinationType: 0
            });
    }

    gettedPicture(base64){

        this.setState({
            base64Img: 'data:image/jpeg;base64,' + base64
        });

        // {
        //     "fileIdx": 8,
        //     "boardIdx": 0,
        //     "fileType": "image/png",
        //     "fileNm": null,
        //     "fileOrgNm": null,
        //     "filePath": "origin/article/1518962448266.png",
        //     "fileSize": "0",
        //     "delYn": "N",
        //     "dispOrder": "1",
        //     "regDttm": "2018-02-18 23:00:48.0",
        //     "regUserid": null,
        //     "updDttm": "2018-02-18 23:00:48.0",
        //     "updUserid": null,
        //     "post": null
        // }
        Net.uploadImg( this.state.base64Img, data =>{
            alert('이미지 올라감');
        } );

    }

    failedPicture(message) {
        alert(message);
    }

    clearPicture() {
        this.setState({
            base64Img: null,
        });
    }

    complete() {
        let data = {
            "boardType": "feed",
            "title": "피드 제목.ㄴㅁㅇㄹㅁㄴㅇㄹ",
            "content": "피드내옹...."
        };

        Net.makePost( data, response =>{
            this.props.close();
        });
    }

    render() {

        return <div>

            <section className="cl-write-section dialogs__content">

                <textarea name="" id="" cols="30" rows="5" placeholder="새로운 글을 작성해 보세요."/>


                <footer className="cl-flex-between">
                    <div className="cl-preview-holder">
                        <img src={this.state.base64Img || previewSrc} alt="이미지 미리보기" width="52" height="52"/>
                        <button onClick={() => this.clearPicture()}>이미지 삭제</button>
                    </div>

                    <button onClick={() => this.selectPicture()}>
                        <img src={addSrc} alt="이미지 추가" width="139" height="36"/>
                    </button>
                    <button onClick={() => this.complete()}>
                        <img src={completeSrc} alt="완료" width="97" height="36"/>
                    </button>
                </footer>


            </section>
        </div>
    }
}

export default observer(WriteDrawer);