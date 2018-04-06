import React, {Component} from 'react';
import Store from "../../scripts/store";
import ReservationDetail from "components/drawers/ReservationDetail";
import ReservationHistory from "components/drawers/ReservationHistory";
import DrawerSwiperViewer from "components/drawers/DrawerSwiperViewer";

import {Link} from 'react-router-dom';
import HeaderOfReservation from 'components/ui/HeaderOfReservation';
import SelectWithTitle from 'components/ui/SelectWithTitle';
import ReserveGroupArrow from 'images/ic-favorite-24-px-blue@3x.png';
import ReserveGroupHome from 'images/r-icon-1@3x.png';
import ReserveGroupTool from 'images/r-icon-2@3x.png';
import ReserveGroupStore from 'images/r-icon-3@3x.png';
import ReserveGroupEtc from 'images/r-icon-4@3x.png';
import ReserveServiceLaundry from 'images/rs-icon-1@3x.png';
import ReserveServiceCleaning from 'images/rs-icon-2@3x.png';
import ReserveServiceFood from 'images/rs-icon-3@3x.png';
import ReserveServiceCarwash from 'images/rs-icon-4@3x.png';
import ReserveIcPlus from 'images/page-1@3x.png';
import {observer} from "mobx-react";
import DrawerWrapper from "../drawers/DrawerWrapper";
import net from '../../scripts/net';

class Reservation extends Component {

    componentDidMount() {
        this.updateRoute();

        // TODO: 어차피 Store 에서 관리할거면 로드하는 위치가 여기가 아니어도 될 것 같은데..
        net.getComplexes( data => {
            Store.complexes = data;
        } );
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location)
            this.updateRoute();
    }

    updateRoute() {

        console.log('updateRoute', Store.drawer);

        // id위치에 특정 단어가 들어올때 처리
        if (this.props.match.params.id === 'history')
            Store.pushDrawer('reservation-history');

        // id위치에 일반적으로 숫자가 들어오면 상세보기
        else if (this.props.match.params.id)
            Store.pushDrawer('reservation-detail');
        else {
            Store.clearDrawer();
        }

        // 썸네일 뷰 추가
        if (this.props.match.params.add === 'thumbnails')
            Store.pushDrawer('reservation-thumbnails');


        setTimeout(() => this.render(), 1000);
        // this.render();
    }

    render() {

        return <div className="cl-tab--reservation">
            {/*크레딧 에러 팝업 */}
            {/*<NoCreditPopup/>*/}

            <HeaderOfReservation/>

            <div className="cl-fitted-box">

                <SelectWithTitle/>

                <ul className="cl-reservation__list--group">
                    <li className="cl-reservation__list-item">
                        <div className="cl-flex-between">
                            <img src={ReserveGroupHome} alt="" className="cl-reservation__list-item-type-img"/>
                            <div className="cl-reservation__list-item-text">
                                <h5>생활 서비스</h5>
                                <p className="cl-ellipsis">세탁배달, 청소서비스, 음식배달외 3</p>
                            </div>
                            <img src={ReserveGroupArrow} alt="" className="cl-reservation__list-item-bullet"/>
                        </div>
                    </li>
                    <li className="cl-reservation__list-item">
                        <div>
                            <div className="cl-flex-between">
                                <img src={ReserveGroupTool} alt="" className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>생활 서비스</h5>
                                    <p className="cl-ellipsis">세탁배달, 청소서비스, 음식배달외 3</p>
                                </div>
                                <img src={ReserveGroupArrow} alt="" className="cl-reservation__list-item-bullet"/>
                            </div>
                        </div>
                    </li>
                    <li className="cl-reservation__list-item">
                        <div>
                            <div className="cl-flex-between">
                                <img src={ReserveGroupStore} alt="" className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>생활 서비스</h5>
                                    <p className="cl-ellipsis">세탁배달, 청소서비스, 음식배달외 3</p>
                                </div>
                                <img src={ReserveGroupArrow} alt="" className="cl-reservation__list-item-bullet"/>
                            </div>
                        </div>
                    </li>
                    <li className="cl-reservation__list-item">
                        <div>
                            <div className="cl-flex-between">
                                <img src={ReserveGroupEtc} alt="" className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>생활 서비스</h5>
                                    <p className="cl-ellipsis">세탁배달, 청소서비스, 음식배달외 3</p>
                                </div>
                                <img src={ReserveGroupArrow} alt="" className="cl-reservation__list-item-bullet"/>
                            </div>
                        </div>
                    </li>
                </ul>

                <SelectWithTitle/>

                <ul className="cl-reservation__list--service">
                    <li className="cl-reservation__list-item">
                        <div>
                            <div className="cl-flex-between">
                                <img src={ReserveServiceLaundry} alt="" className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>세탁 서비스</h5>
                                    <p className="cl-ellipsis">배달 1일전 예약가능</p>
                                </div>
                                <button type="button" className="cl-reservation__list-item-bullet">
                                    <img src={ReserveIcPlus} alt=""/>
                                    <Link to={ '/reservation/0' }>예약하기</Link>
                                </button>
                            </div>
                        </div>
                    </li>
                    <li className="cl-reservation__list-item cl-reservation__notice">
                        <div>
                            <div className="cl-flex-between">
                                <img src={ReserveServiceCleaning} alt=""
                                     className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>청소 서비스</h5>
                                    <p className="cl-ellipsis">외부 서비스업체 사정에 따라 변경 또는 취소의 여지가 있을 수도 있습니다.</p>
                                </div>
                                <button type="button" className="cl-reservation__list-item-bullet">
                                    <img src={ReserveIcPlus} alt=""/>
                                    <span>예약하기</span>
                                </button>
                            </div>
                        </div>
                    </li>
                    <li className="cl-reservation__list-item">
                        <div>
                            <div className="cl-flex-between">
                                <img src={ReserveServiceCleaning} alt=""
                                     className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>청소 서비스</h5>
                                    <p className="cl-ellipsis">외부 서비스업체 사정에 따라 변경 또는 취소의 여지가 있을 수도 있습니다.</p>
                                </div>
                                <button type="button" className="cl-reservation__list-item-bullet">
                                    <img src={ReserveIcPlus} alt=""/>
                                    <span>예약하기</span>
                                </button>
                            </div>
                        </div>
                    </li>
                    <li className="cl-reservation__list-item">
                        <div>
                            <div className="cl-flex-between">
                                <img src={ReserveServiceFood} alt="" className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>음식배달</h5>
                                    <p className="cl-ellipsis">배달 1일전 예약가능</p>
                                </div>
                                <button type="button" className="cl-reservation__list-item-bullet">
                                    <img src={ReserveIcPlus} alt=""/>
                                    <span>예약하기</span>
                                </button>
                            </div>
                        </div>
                    </li>
                    <li className="cl-reservation__list-item">
                        <div>
                            <div className="cl-flex-between">
                                <img src={ReserveServiceCarwash} alt="" className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>세차서비스</h5>
                                    <p className="cl-ellipsis">배달 1일전 예약가능</p>
                                </div>
                                <button type="button" className="cl-reservation__list-item-bullet">
                                    <img src={ReserveIcPlus} alt=""/>
                                    <span>예약하기</span>
                                </button>
                            </div>
                        </div>
                    </li>
                </ul>

            </div>

            {/* 예약 상세 화면 */}
            <DrawerWrapper drawer="reservation-detail">
                <ReservationDetail/>
            </DrawerWrapper>

            {/* 히스토리 */}
            <DrawerWrapper drawer="reservation-history">
                <ReservationHistory/>
            </DrawerWrapper>

            {/* 이미지상세보기 */}
            <DrawerWrapper drawer="reservation-thumbnails">
                <DrawerSwiperViewer/>
            </DrawerWrapper>

        </div>
    }

}

export default observer(Reservation);