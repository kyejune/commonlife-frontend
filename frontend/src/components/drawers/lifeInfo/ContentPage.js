import React, { Component } from 'react';
import {withRouter} from "react-router-dom";
import TagComponent from "../../ui/TagComponent";

class ContentPage extends Component {

    constructor(props) {
        super(props);

        const { cate, option1 } = props.match.params;
        this.props.updateTitle( cate==='benefit'?'Benefits 상세보기':'유용한 정보');
    }


    render() {

        let content = '나만의 크리스마스 쿠키를 만들어 보아요^^\n' +
            '                커먼라이프가 준비한 크리스마스 이벤트, 나만의 DIY쿠키를 만들어 소중한 사람에게 전해보세요.\n' +
            '\n' +
            '                *본 이벤트는 한국수공예협회 선생님 두분의 도움으로 이루어질 예정이며 50명 선착순으로 진행됩니다.\n' +
            '                설문조사 : http://google.com';

        return (
            <div className="pt-1em pr-08em pl-08em fs-15">
                <TagComponent content={content}/>
            </div>
        );
    }
}

export default withRouter(ContentPage);
