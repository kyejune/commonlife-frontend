import React, {Component} from 'react';
import {withRouter} from 'react-router';
// import {Avatar, Card, CardActions, CardText, CardTitle, Media, MediaOverlay} from 'react-md';
import {Link} from 'react-router-dom';
import LikeShareAndSome from "components/ui/LikeShareAndSome";
import moment from 'moment';
import 'moment/locale/ko';
import Store from "../../scripts/store";
import TagComponent from "./TagComponent";
import Net from "../../scripts/net";

class CardItem extends Component {

    constructor(props) {
        super(props);
        moment.locale('ko');
        this.state = props.cardData;
    }

    componentWillReceiveProps(nextProps) {
        if (this.state.postIdx !== nextProps.cardData.postIdx)
            this.setState(nextProps.cardData);
    }

    onChangeLike = (likeCount, hasLiked) => {
        let obj = Object.assign({}, this.state);
        obj.likesCount = likeCount;
        obj.myLikeFlag = hasLiked;

        this.setState(obj);
    }

    onChangeJoin=(bool)=>{

        Net.setJoin( this.state.postIdx, bool, res => {
           let obj = Object.assign({}, this.state);
           obj.rsvFlag = bool;// 실서버 반영되면 반전 시켜줘야됨
           this.setState(obj);
        });

    }

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

        const PostLink = this.props.list + '/' + this.state.postIdx;

        if (this.state) {
            return <div className="cl-card-item-wrapper">

                <div className={`cl-card-item cl-card-item--${PostType}`} key="item-info">

                    {/* event 타입이면 타이틀과 bg를 노출 */}
                    {PostType === 'event' && imgAddr &&
                    <Link to={PostLink}>
                        <div key={this.state.postIdx + '-bg'} className="cl-card-item__bg--event" style={{ backgroundImage:`url(${ imgAddr })`}}>
                            <h5 className="cl-title">{ this.state.title }</h5>
                        </div>
                    </Link>
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

                    {/* event 타입 아니면 이미지 노출 */}
                    { imgAddr && PostType !== 'event' &&
                    <img className="cl-card-item__img" src={imgAddr} alt="첨부 이미지"/>
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

                    {/*/!* event가 아니고 상세화면 아니면 내용 보여주기 *!/*/}
                    {PostType !== 'event' &&
                    <div className="cl-card-item-content mb-1em mt-1em">
                        <Link to={PostLink}>
                            <p>
                                <TagComponent content={this.state.content} nolink />
                            </p>
                        </Link>
                    </div>
                    }

                    <hr/>

                    {/* schedule, qa에 관한 데이터는 아직 기준이 명확하지 못해서 임시로 지정 */}
                    <LikeShareAndSome
                        like={{
                            to: this.props.list + '/' + this.state.postIdx + '/like',
                            count: this.state.likesCount,
                            liked: this.state.myLikeFlag
                        }}
                        onChangeLike={this.onChangeLike}
                        onChangeJoin={this.onChangeJoin}

                        share={this.state.shareYn === 'Y'}
                        join={this.state.rsvYn === 'Y' && !this.state.rsvFlag}
                        joined={this.state.rsvYn === 'Y' && this.state.rsvFlag}
                        joinFulled={this.state.rsvCount >= this.state.rsvMaxCnt}
                        qa={ (this.state.inquiryYn === 'Y' )?(this.state.inquiryType === 'P'?'tel:':'mailto:') + this.state.inquiryInfo:false }

                        data={{...this.props.cardData}}
                    />

                </div>

            </div>
        }
        else {
            return '';
        }
    }
}

export default withRouter(CardItem);
