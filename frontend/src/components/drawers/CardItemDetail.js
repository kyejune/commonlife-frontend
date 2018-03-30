import React, {Component} from 'react';
import {withRouter} from 'react-router';
import Net from "scripts/net";
import Store from "scripts/store";
import LikeShareAndSome from "components/ui/LikeShareAndSome";
import reactStringReplace from 'react-string-replace';
import moment from "moment/moment";
import joinSrc from 'images/rsvp-normal@3x.png';
import joinedSrc from 'images/rsvp-activity@3x.png';
import TagComponent from "../ui/TagComponent";


class CardItemDetailDrawer extends Component {

    constructor(props) {
        super(props);

        let match = this.props.match.params;
        this.state = {
            likeLink: '/community/' + match.tab + '/' + match.id + '/like',
        }
    }

    componentWillMount() {
        this.props.updateTitle('글보기');

        Net.getCardContent( this.props.match.params.tab, this.props.match.params.id, data => {
            this.setState(data);
        });
    }

    onChangeLike = (likeCount, hasLiked) => {
        let obj = Object.assign({}, this.state);
        obj.likesCount = likeCount;
        obj.myLikeFlag = hasLiked;

        this.setState(obj);
    }

    onChangeJoin=(bool)=>{
        let obj = Object.assign({}, this.state);
        obj.rsvFlag = bool;// 실서버 반영되면 반전 시켜줘야됨

        this.setState(obj);
    }


    /* 이벤트 날짜 구성 컴퍼넌트 생성 */
    makeDateComponent( fromDate, toDate ){
        let result = [];
        let fm = moment(fromDate);
        let tm = moment(toDate);

        // 날짜같고
        if( fm.format('YYYY/MM/DD') === tm.format('YYYY/MM/DD') ) {

            // 시간도 같을경우
            if( fm.format('hh:mm') === tm.format('hh:mm') ){
                result = [<div key='date' className="cl__date">{ fm.format('MM월 DD일(ddd)')}</div>,
                    <div key='time' className="cl__time">{ fm.format('a hh:mm')}</div>];
                // 시간은 다를경우
            }else{
                result = [<div key='date' className="cl__date">{ fm.format('MM월 DD일(ddd)')}</div>,
                    <div key='time' className="cl__time">{ fm.format('a hh:mm') } ~ { tm.format('a hh:mm') }</div>];
            }

        }
        // 날짜가 아예 다르면
        else{
            result = [<div key='datefrom' className="cl__date">{ fm.format('MM월 DD일(ddd)')}</div>,
                <div key='timefrom' className="cl__time">{ fm.format('a hh:mm') }</div>,
                <span key="~">~</span>,
                <div key='dateto' className="cl__date">{ tm.format('MM월 DD일(ddd)')}</div>,
                <div key='timeto' className="cl__time">{ tm.format('a hh:mm') }</div>];
        }
        return result;
    }

    render() {

        if (!this.state.postIdx) return null;


        const PostType = this.state.postType;

        let userThumb = {};
        if (this.state.user.imgSrc )
            userThumb['background-image'] = `url(${ Store.api + this.state.user.imgSrc})`;

        let imgAddr;
        if (this.state.postFiles.length > 0 ) {
            imgAddr = Store.api + this.state.postFiles[0].largePath;
        }

        let duration;
        if( PostType === 'event' ){
            duration = this.makeDateComponent( this.state.eventBeginDttm, this.state.eventEndDttm );
        }

        // 참여버튼
        let join;
        if( this.state.rsvYn ){
            join = <button  className="cl-card-item__button" onClick={ ()=> this.onChangeJoin( !this.state.rsvFlag ) }>
                <img src={ this.state.rsvFlag?joinedSrc:joinSrc } alt="참석여부 결정" height="30"/>
            </button>;
        }



        return <div className="cl-card-detail">
            {/* event 타입 아니면 이미지 노출 */}
            { imgAddr && PostType !== 'event' &&
            <img className="cl-card-item__img" src={imgAddr} width="100%" alt="첨부 이미지"/>
            }

            {/* event 타입이면 타이틀과 bg를 노출 */}
            {PostType === 'event' && imgAddr &&
            <div key={this.state.postIdx + '-bg'} className="cl-card-item__bg--event" style={{ backgroundImage:`url(${ imgAddr })`}}/>
            }

            <div className="cl-card-item-wrapper">



                <div className={`cl-card-item cl-card-item--${PostType}`} key="item-info">

                    {/* event 타입이면 타이틀과 bg를 노출 */}
                    {PostType === 'event' && imgAddr &&
                    <h5 className="cl-title">{ this.state.title }</h5>
                    }

                    {/* 작성자 정보 - 피드, 뉴스 */}
                    {PostType !== 'event' &&
                    <div className="cl-flex">
                        <div className="cl-avatar" style={userThumb}/>
                        <div>
                            <h6 className="cl-name">{this.state.user.userNm }</h6>
                            <p className="cl-desc">
                                {this.state.user.cmplxNm},
                                {moment(this.state.regDttm).fromNow()}
                            </p>
                        </div>
                    </div>
                    }


                    {/* event라면 장소등 노출.. */}
                    {PostType === 'event' &&
                    <div className="mt-06em mb-1em">
                        <h6 className="cl-flex cl-dates">
                            {duration}
                        </h6>
                        <p className="cl-desc pb-05em">
                            {this.state.eventCmplxNm} {this.state.eventPlaceNm}
                        </p>
                    </div>
                    }

                    {/* schedule, qa에 관한 데이터는 아직 기준이 명확하지 못해서 임시로 지정 */}
                    <LikeShareAndSome
                        like={{
                            to: this.state.likeLink,
                            count: this.state.likesCount,
                            liked: this.state.myLikeFlag
                        }}

                        onChangeLike={this.onChangeLike}

                        share={this.state.shareYn === 'Y'}
                        qa={this.state.inquiryYn === 'E' || this.state.inquiryYn === 'P' }
                        calendar={this.state.postType === 'event'?[this.state.eventBeginDttm, this.state.eventEndDttm]:false }

                        data={ { ...this.state } }
                    />

                </div>
            </div>

            <div className="cl-card-item-detail-content">
                <TagComponent content={this.state.content}/>
            </div>


            {PostType === 'event' &&
            <footer className="cl-opts__footer cl-flex">
                <div className="cl-join-status mr-auto">
                    <span>{this.state.rsvCurrCnt}</span>
                    <span>/{this.state.rsvMaxCnt} 참여</span>
                </div>

                {join}
            </footer>
            }
        </div>
    }
}


export default withRouter(CardItemDetailDrawer);