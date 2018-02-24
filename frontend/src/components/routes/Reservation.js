import React, {Component} from 'react';
import Store from "../../scripts/store";
import ReservationDetail from "components/drawers/ReservationDetail";
import ReservationHistory from "components/drawers/ReservationHistory";
import DrawerContentHolder from "components/drawers/DrawerContentHolder";
import ThumbnailDetail from "components/drawers/ThumbnailDetail";
import {Drawer} from 'react-md';
import { Link } from 'react-router-dom';
import HeaderOfReservation from 'components/ui/HeaderOfReservation';
// import NoCreditPopup from "components/ui/NoCreditPopup";
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
// import ReserveIcTimeNext from 'images/shape-time-next@3x.png';
// import ReserveIcTimePlus from 'images/shape-time-plus@3x.png';
// import ReserveIcNotice from 'images/alert-icon-red@3x.png';

class Reservation extends Component {

    constructor( props ){
        super( props );

        this.state = {
            drawer: Store.drawer,
        }
    }

    componentDidMount(){
        this.updateRoute();
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location)
            this.updateRoute();
    }

    updateRoute(){

        // id위치에 특정 단어가 들어올때 처리
        if( this.props.match.params.id  === 'history' )
            Store.drawer.push( 'reservation-history' );

        // id위치에 일반적으로 숫자가 들어오면 상세보기
        else if( this.props.match.params.id  )
            Store.drawer.push( 'reservation-detail' );

        else{
            Store.drawer.length = 0;
        }

        // 썸네일 뷰 추가
        if( this.props.match.params.add === 'thumbnails' )
            Store.drawer.push( 'reservation-thumbnails');


        this.setState({
            drawer: Store.drawer || [],
        });
    }

    render() {


        return <div>
            {/*크레딧 에러 팝업 */}
            {/*<NoCreditPopup/>*/}

            <HeaderOfReservation/>

            <div className="cl-fitted-box">

                <SelectWithTitle/>

                <ul className="cl-reservation__list--group">
                    <li className="cl-reservation__list-item">
                        <Link to={ '/reservation/0' }>
                            <div className="cl-flex-between">
                                <img src={ReserveGroupHome} alt="" className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>생활 서비스</h5>
                                    <p className="cl-ellipsis">세탁배달, 청소서비스, 음식배달외 3</p>
                                </div>
                                <img src={ReserveGroupArrow} alt="" className="cl-reservation__list-item-bullet"/>
                            </div>
                        </Link>
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
                                    <span>예약하기</span>
                                </button>
                            </div>
                        </div>
                    </li>
                    <li className="cl-reservation__list-item cl-reservation__notice">
                        <div>
                            <div className="cl-flex-between">
                                <img src={ReserveServiceCleaning} alt="" className="cl-reservation__list-item-type-img"/>
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
                                <img src={ReserveServiceCleaning} alt="" className="cl-reservation__list-item-type-img"/>
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
            <Drawer {...Store.customDrawerProps}
                    visible={this.state.drawer.indexOf( 'reservation-detail' ) >= 0}>
                <DrawerContentHolder back>
                    <ReservationDetail/>
                </DrawerContentHolder>
            </Drawer>

            {/* 히스토리 */}
            <Drawer {...Store.customDrawerProps}
                    visible={this.state.drawer.indexOf( 'reservation-history' ) >= 0}>
                <DrawerContentHolder back>
                    <ReservationHistory/>
                </DrawerContentHolder>
            </Drawer>

			{/* 이미지상세보기 */}
            <Drawer {...Store.customDrawerProps}
                    visible={this.state.drawer.indexOf( 'reservation-thumbnails' ) >= 0}>
                <DrawerContentHolder back>
                    <ThumbnailDetail/>
                </DrawerContentHolder>
            </Drawer>

        </div>
    }

}

export default Reservation;