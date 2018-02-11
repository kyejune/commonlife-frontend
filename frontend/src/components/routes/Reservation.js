import React, {Component} from 'react';
import HeaderOfReservation from 'components/ui/HeaderOfReservation';
import NoCreditPopup from "components/ui/NoCreditPopup";
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
import ReserveIcTimeNext from 'images/shape-time-next@3x.png';
import ReserveIcTimePlus from 'images/shape-time-plus@3x.png';
import ReserveIcNotice from 'images/alert-icon-red@3x.png';

class Reservation extends Component {

    render() {
        return <div>
            {/*크레딧 에러 팝업 */}
            {/*<NoCreditPopup/>*/}

            <HeaderOfReservation/>

            <div className="cl-fitted-box">

                <SelectWithTitle/>

                <ul className="cl-reservation__list--group">
                    <li className="cl-reservation__list-item">
                        <a href="#">
                            <div className="cl-flex-between">
                                <img src={ReserveGroupHome} alt="" className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>생활 서비스</h5>
                                    <p className="cl-ellipsis">세탁배달, 청소서비스, 음식배달외 3</p>
                                </div>
                                <img src={ReserveGroupArrow} alt="" className="cl-reservation__list-item-bullet"/>
                            </div>
                        </a>
                    </li>
                    <li className="cl-reservation__list-item">
                        <a href="#">
                            <div className="cl-flex-between">
                                <img src={ReserveGroupTool} alt="" className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>생활 서비스</h5>
                                    <p className="cl-ellipsis">세탁배달, 청소서비스, 음식배달외 3</p>
                                </div>
                                <img src={ReserveGroupArrow} alt="" className="cl-reservation__list-item-bullet"/>
                            </div>
                        </a>
                    </li>
                    <li className="cl-reservation__list-item">
                        <a href="#">
                            <div className="cl-flex-between">
                                <img src={ReserveGroupStore} alt="" className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>생활 서비스</h5>
                                    <p className="cl-ellipsis">세탁배달, 청소서비스, 음식배달외 3</p>
                                </div>
                                <img src={ReserveGroupArrow} alt="" className="cl-reservation__list-item-bullet"/>
                            </div>
                        </a>
                    </li>
                    <li className="cl-reservation__list-item">
                        <a href="#">
                            <div className="cl-flex-between">
                                <img src={ReserveGroupEtc} alt="" className="cl-reservation__list-item-type-img"/>
                                <div className="cl-reservation__list-item-text">
                                    <h5>생활 서비스</h5>
                                    <p className="cl-ellipsis">세탁배달, 청소서비스, 음식배달외 3</p>
                                </div>
                                <img src={ReserveGroupArrow} alt="" className="cl-reservation__list-item-bullet"/>
                            </div>
                        </a>
                    </li>
                </ul>

                <SelectWithTitle/>

                <ul className="cl-reservation__list--service">
                    <li className="cl-reservation__list-item">
                        <a href="#">
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
                        </a>
                    </li>
                    <li className="cl-reservation__list-item cl-reservation__notice">
                        <a href="#">
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
                        </a>
                    </li>
                    <li className="cl-reservation__list-item">
                        <a href="#">
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
                        </a>
                    </li>
                    <li className="cl-reservation__list-item">
                        <a href="#">
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
                        </a>
                    </li>
                    <li className="cl-reservation__list-item">
                        <a href="#">
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
                        </a>
                    </li>
                </ul>

            </div>
        </div>
    }

}

export default Reservation;