import React, { Component } from 'react';
import TagComponent from "../../ui/TagComponent";
   
class NoticeDetail extends Component {
    render() {

        let content = "역삼동 하우징 엘레베이터 정기검진 안내말씀 드립니다.\n" +
            "                    8월 30일 오후1시 부터 약 1시간동안 역삼동 하우징 101동\n" +
            "                    엘레베이터 정기검진으로 인하여 다소 불편이 예상됩니다.\n" +
            "                    멤버분들의 양해를 부탁드리며, 본 공지는 테스트용 공지이오니,\n" +
            "                    \n" +
            '<img src="https://i.pinimg.com/originals/35/8a/96/358a96f38804c3f27648159c6017a06d.gif" alt="이미지"/>' +
            "                    내용확인 및 가이드로만 이해해 주시길 부탁드립니다."

        return (
            <div className="cl-notice-detail">
                <p className="cl-notice-detail__content">
                    <TagComponent content={content}/>
                </p>

                <div className="cl-card-item--dark cl-flex mb-3em">
                    <div className="cl-avatar"/>
                    <div>
                        <h6 className="cl-name">최수영 CM</h6>
                        <p className="cl-desc">역삼하우스, 5시간전</p>
                    </div>
                </div>
            </div>
        );
    }
}

    
export default NoticeDetail;
