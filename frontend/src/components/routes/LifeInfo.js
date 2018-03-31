/* LifeInfo.jsx */
import React, {Component} from 'react';
import Store from 'scripts/store';
import {Link, withRouter} from 'react-router-dom';
import {observer} from "mobx-react";
import DrawerWrapper from "../drawers/DrawerWrapper";
import NoticeDetail from "../drawers/lifeInfo/NoticeDetail";
import LivingSupportCategory from "../drawers/lifeInfo/LivingSupportCategory";
import SubjectList from "../drawers/lifeInfo/SubjectList";
import Status from "../drawers/lifeInfo/Status";
import Profile from "../drawers/lifeInfo/Profile";
import ContentPage from "../drawers/lifeInfo/ContentPage";

class LifeInfo extends Component {

    componentDidMount() {
        this.updateRoute();
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location)
            this.updateRoute();
    }

    updateRoute(data) {

        const params = this.props.match.params;
        const compMap = {
            notice: {
                notice: 'info-notice',
            },

            support: {
                support: 'info-support'
            },

            guide: {
                guide: 'info-subject',
            },

            benefit: {
                benefit: 'info-subject',
            },

            profile: {
                profile: 'info-profile',
            },

            status: {
                status: 'info-status',
            }


        };


        const cate = this.props.match.params.cate;
        const map = compMap[cate];

        Store.clearDrawer();
        if (cate === undefined) return;

        let prevSeg = null;
        for (var p in params) {
            let d = map[params[p]];
            if (RegExp(/\d+/).test(params[p])) d = map[prevSeg] + '-detail';
            if (d) Store.pushDrawer(d);
            prevSeg = params[p];
        }
    }

    render() {

        return <div className="cl-info-container cl-tab--info">
            <div className="cl-bg--black">

                <div className="cl-second-header cl-profile-card">
                    <div className="cl-flex cl-summary">
                        <div className="cl-avatar"/>
                        <div>
                            <h4>김정신</h4>
                            <p>역삼하우스 101동 202호</p>
                        </div>
                    </div>

                    <Link className="cl-edit color-primary" to="#">ProfileEdit</Link>

                    <footer className="cl-profile-card__footer cl-flex">
                        <p>입주일: 2017년 4월 1일</p>
                        <p className="ml-auto">포인트: 25</p>
                    </footer>
                </div>

                <div className="cl-fitted-box">

                    <div className="cl-info-notice">
                        <h4>Notice</h4>
                        <Link to="/info/notice/0" className="cl-ellipsis--3" style={{'WebkitBoxOrient': 'vertical'}}>
                            역삼동 하우징 엘레베이터 정기검진 안내말씀 드립니다.<br/>
                            8월 30일 오후1시 부터 약 1시간동안 역삼동 하우징 101동<br/>
                            엘레베이터 정기검진으로 인하여...
                        </Link>
                    </div>


                    <ul className="cl-info-dashboard__list">

                        <li>
                            <Link to="/community/event">
                                <img className="cl__thumb--rounded" src={"icons/cl_life-10.svg"} alt="svg"/>
                                <p>Event Feed</p>
                            </Link>
                        </li>

                        <li>
                            <Link to="/info/support">
                                <img className="cl__thumb--rounded" src={"icons/cl_life-11.svg"} alt="svg"/>
                                <p>Living Support</p>
                            </Link>
                        </li>

                        <li>
                            <Link to="/info/guide">
                                <img className="cl__thumb--rounded" src={"icons/cl_life-12.svg"} alt="svg"/>
                                <p>Living Guide</p>
                            </Link>
                        </li>

                        <li>
                            <Link to="/info/benefit">
                                <img className="cl__thumb--rounded" src={"icons/cl_life-9.svg"} alt="svg"/>
                                <p>Benefits</p>
                            </Link>
                        </li>

                        <li>
                            <Link to="/info/status">
                                <img className="cl__thumb--rounded" src={"icons/cl_life-7.svg"} alt="svg"/>
                                <p>My Status</p>
                            </Link>
                        </li>

                        <li>
                            <Link to="/info/profile">
                                <img className="cl__thumb--rounded" src={"icons/cl_life-8.svg"} alt="svg"/>
                                <p>Profile</p>
                            </Link>
                        </li>

                    </ul>
                </div>

            </div>


            {/* 공지 상세보기 */}
            <DrawerWrapper drawer="info-notice-detail" title="" deepdark back>
                <NoticeDetail/>
            </DrawerWrapper>

            {/* 리빙서포트 카테고리 */}
            <DrawerWrapper drawer="info-support" title="Living Support" back>
                <LivingSupportCategory/>
            </DrawerWrapper>

            {/* Benefit 목록, 리빙가이드 목록 */}
            <DrawerWrapper drawer="info-subject" back>
                <SubjectList/>
            </DrawerWrapper>

            {/* Benefit 목록, 리빙가이드 상세 */}
            <DrawerWrapper drawer="info-subject-detail" back>
                <ContentPage/>
            </DrawerWrapper>


            {/* 리방가이드 목록 */}
            {/*<DrawerWrapper drawer="info-guide" title="Living Guide" back >*/}
            {/*<SubjectList type="guide"/>*/}
            {/*</DrawerWrapper>*/}

            {/* My Status */}
            <DrawerWrapper drawer="info-status" title="My Status" back>
                <Status/>
            </DrawerWrapper>

            {/* Profile */}
            <DrawerWrapper drawer="info-profile" title="사용자 정보 관리" back>
                <Profile/>
            </DrawerWrapper>

        </div>
    }
}

export default withRouter(observer(LifeInfo));