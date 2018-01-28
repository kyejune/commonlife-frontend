import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';
import {Button, CardTitle, Avatar} from "react-md";
import DB from "scripts/db";
import LikeShareAndSome from "components/ui/LikeShareAndSome";


class CardItemDetailDrawer extends Component {

    componentWillMount() {

        DB.getCardContent( this.props.match.params.tab, this.props.match.params.id, data => {

            this.setState(data);
            this.props.updateTitle(data.content || data.event_title); // ContentHolder에 전달

        });
    }


    render() {

        if (this.state === null) return null;


        let Img;
        if (this.state.thumbnail)
            Img = <img src={this.state.thumbnail} alt="관련 이미지" width="100%"/>


        return <div className="cl-card-detail">

            {Img}

            <div className="cl-info">

                <h3 className="cl-dynamic-lines">{this.state.content || this.state.event_title}</h3>

                <div className="cl-black">
                    <span className="cl-bold mr-1em">12월 28일(수)</span>
                    <span>오후 2:00 ~ 6:00</span>
                </div>
                <p>역삼하우스 5F, 커뮤니티룸</p>


                <CardTitle
                    // 작성자 이름
                    title="제목제목"
                    // 작성자 프로필 이미지
                    avatar={<Avatar src={this.state.thumbnail} role="presentation"/>}
                    // 작성 시간
                    subtitle="부제목"

                    className="p-0em cl-card-item"
                />

                {/* schedule, qa에 관한 데이터는 아직 기준이 명확하지 못해서 임시로 지정 */}
                <LikeShareAndSome like={ { to:'/', count:0 } } share schedule={this.state.type === 'event'} qa={this.state.type !== 'event'} />

            </div>


            <div className="cl-content--card">
                { this.state.content }
            </div>


            <footer className="cl-flex-between">
                <div>
                    <span className="cl-strong">25</span><span>/50참여</span>
                </div>

                <Button flat className="cl-icon cl-card-item__rsvp"/>
            </footer>
        </div>
    }
}


export default observer(withRouter(CardItemDetailDrawer));