import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {CardTitle, Avatar} from "react-md";
import Net from "scripts/net";
import Store from "scripts/store";
import LikeShareAndSome from "components/ui/LikeShareAndSome";
import reactStringReplace from 'react-string-replace';
import {Link} from "react-router-dom";


class CardItemDetailDrawer extends Component {

    constructor(props) {
        super(props);

        let match = this.props.match.params;
        this.state = {
            likeLink: '/community/' + match.tab + '/' + match.id + '/like',
        }
    }

    componentWillMount() {

        Net.getCardContent(this.props.match.params.tab, this.props.match.params.id, data => {

            this.setState(data);

            console.log('ddd:', data);
            this.props.updateTitle('글보기');//data.content || 'untitled' ); // ContentHolder에 전달

        });
    }

    onChangeLike=( likeCount, hasLiked )=>{
        let obj = Object.assign({}, this.state );
            obj.likesCount = likeCount;
            obj.myLikeFlag = hasLiked;

        this.setState( obj );
    }


    render() {

        if (!this.state.postIdx) return null;

        let Img;
        if (this.state.postFiles[0])
            Img = <img src={Store.api + this.state.postFiles[0].largePath} alt="관련 이미지" width="100%"/>;


            // 줄넘김 태그 변환, url -> a링크 생성
        let content = this.state.content;
        content = reactStringReplace( content, /(\n)/g, ( match, index  )=>{
            return <br key={ 'br-' + index} />;
        });

        content = reactStringReplace( content, /(https:\/\/\S+)/g, ( match, index, offset  )=>{
            return <a key={match + offset } href={match} target="_blank">{match}</a>;
        });

        content = reactStringReplace( content, /(http:\/\/\S+)/g, ( match, index, offset  )=>{
            return <a key={match + offset } href={match} target="_blank">{match}</a>;
        });

        content = reactStringReplace( content, /(www\.\S+)/g, ( match, index, offset  )=>{
            return <a key={match + offset } href={match} target="_blank">{match}</a>;
        });

        console.log( '링크생성후 결과:', content );

        return <div className="cl-card-detail">

            <div>
                {Img}
                <div className="cl-info">


                    {/* Event용 */}
                    {/*<h3 className="cl-dynamic-lines">{this.state.content || this.state.event_title}</h3>*/}

                    {/*<div className="cl-black">*/}
                    {/*<span className="cl-bold mr-1em">12월 28일(수)</span>*/}
                    {/*<span>오후 2:00 ~ 6:00</span>*/}
                    {/*</div>*/}
                    {/*<p>역삼하우스 5F, 커뮤니티룸</p>*/}


                    <CardTitle
                        // 작성자 이름
                        title={this.state.user.userNm}
                        // 작성자 프로필 이미지
                        avatar={<Avatar src={this.state.thumbnail} role="presentation"/>}
                        // 작성 시간
                        subtitle={"역삼하우스"}

                        className="p-0em cl-card-item"
                    />

                    {/*this.props.list + '/like/' + this.props.cardData.index*/}
                    {/* schedule, qa에 관한 데이터는 아직 기준이 명확하지 못해서 임시로 지정 */}
                    <LikeShareAndSome
                        like={ { to:this.props.list + '/' + this.state.postIdx + '/like', count:this.state.likesCount, liked:this.state.myLikeFlag } }
                        share
                        onChangeLike={ this.onChangeLike }
                    />
                </div>
            </div>


            <p className="cl-content--card">
                {content}
            </p>


            {this.state.postType === 'event' &&
            <footer className="cl-flex-between">
                <div>
                    <span className="cl-strong">25</span><span>/50참여</span>
                </div>

                <button className="cl-plus-label__button">참여</button>
            </footer>
            }
        </div>
    }
}


export default withRouter(CardItemDetailDrawer);