import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {CardTitle, Avatar} from "react-md";
import Net from "scripts/net";
import Store from "scripts/store";
import LikeShareAndSome from "components/ui/LikeShareAndSome";
import reactStringReplace from 'react-string-replace';
import {Link} from "react-router-dom";
import CardItem from "../ui/CardItem";
import moment from "moment/moment";


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

    /* 링크 태그 생성 및 줄넘김 처리 */
    makeContentComponent( content ){
        content = reactStringReplace(content, /(\n)/g, (match, index) => {
            return <br key={'br-' + index}/>;
        });

        content = reactStringReplace(content, /(https:\/\/\S+)/g, (match, index, offset) => {
            return <a key={match + offset} href={match} target="_blank">{match}</a>;
        });

        content = reactStringReplace(content, /(http:\/\/\S+)/g, (match, index, offset) => {
            return <a key={match + offset} href={match} target="_blank">{match}</a>;
        });

        content = reactStringReplace(content, /(www\.\S+)/g, (match, index, offset) => {
            return <a key={match + offset} href={match} target="_blank">{match}</a>;
        });

        return content;
    }


    render() {

        if (!this.state.postIdx) return null;


        const PostType = this.state.postType;
        const PostLink = this.props.list + '/' + this.state.postIdx;
        const Content = this.makeContentComponent( this.state.content );

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


        return <div className="cl-card-detail">
            {/* event 타입 아니면 이미지 노출 */}
            { imgAddr && PostType !== 'event' &&
            <img className="cl-card-item__img" src={imgAddr} width="100%" alt="첨부 이미지"/>
            }

            {/* event 타입이면 타이틀과 bg를 노출 */}
            {PostType === 'event' && imgAddr &&
            <div key={this.state.postIdx + '-bg'} className="cl-card-item__bg--event" style={{ backgroundImage:`url(${ imgAddr })`}}>
                <h5 className="cl-title">{ this.state.title }</h5>
            </div>
            }

            <div className="cl-card-item-wrapper">



                <div className={`cl-card-item cl-card-item--${PostType}`} key="item-info">

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

                    <hr/>

                    {/* schedule, qa에 관한 데이터는 아직 기준이 명확하지 못해서 임시로 지정 */}
                    <LikeShareAndSome
                        like={{
                            to: this.state.likeLink,
                            count: this.state.likesCount,
                            liked: this.state.myLikeFlag
                        }}
                        share={this.state.postType !== 'feed'}
                        onChangeLike={this.onChangeLike}
                    />

                </div>
            </div>

            <div className="cl-card-item-detail-content">
                {Content}
            </div>
        </div>
    }
}


export default withRouter(CardItemDetailDrawer);