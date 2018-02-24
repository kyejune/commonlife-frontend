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

    handleChange(event) {
        this.setState({content: event.target.value});
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

    gettedPicture(base64) {

        this.setState({
            base64Img: 'data:image/jpeg;base64,' + base64
        });

        // {
        //     "postFileIdx": 3,
        //     "postIdx": 0,
        //     "usrId": 0,
        //     "host": null,
        //     "mimeType": "image/png",
        //     "fileName": null,
        //     "filePath": "origin/article/1519456812764.png",
        //     "delYn": "N",
        //     "regDttm": "2018-02-24 16:20:13.0",
        //     "updDttm": "2018-02-24 16:20:13.0",
        //     "post": null,
        //     "originPath": "/postFiles/3",
        //     "smallPath": "/postFiles/3/s/",
        //     "mediumPath": "/postFiles/3/m/",
        //     "largePath": "/postFiles/3/l/"
        // }
        Net.uploadImg(this.state.base64Img, data => {

            this.imgId = data.postFileIdx;

        }).bind(this);

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
            postType: "feed",
            content: this.state.content,
            postFiles: []
        };

        if( this.imgId )
            data.postFiles.push( this.imgId );

        console.log( 'complete:', data );


        Net.makePost(data, () => {
            this.props.close();
        });
    }

    render() {

        return <div>

            <section className="cl-write-section dialogs__content">

                <textarea name="" id="" cols="30" rows="5" placeholder="새로운 글을 작성해 보세요." value={this.state.content}
                          onChange={event => this.handleChange(event)}/>


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