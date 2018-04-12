import React, { Component } from 'react';
import Net from "../../../scripts/net";
import {withRouter} from "react-router-dom";
import numeral from 'numeral';
   
class StatusDetail extends Component {


    constructor(props) {
        super(props);

        numeral.defaultFormat('0,0');
    }

    componentDidMount(){
        const { cate, option1 } = this.props.match.params;

        Net.getInfoDetailOf( cate, option1, res=>{
            this.setState( res );
            this.props.updateTitle( res.myStatusNm );
        } );
    }


    render() {

        if( !this.state ) return <div/>

        const { chargesDate, downloadLink, electricityCharges, gasCharges, parkingCharges, rentalCharges, totalCharges, waterCharges } = this.state;


        return (
            <div className="cl-info-status-detail">

                <header className="cl-bg--darkgray">
                    <div className="cl-flex">
                        <h3 className="fs-16 color-white">{chargesDate} 청구액</h3>
                        <span className="color-secondary cl-bold fs-26 ml-auto">{numeral(totalCharges).format()}원</span>
                    </div>

                    <a className="cl__button--pdf mt-2em" target="_blank" href={downloadLink}>
                        <i/>
                        <span>청구서 다운로드(PDF)</span>
                    </a>
                </header>

                <div className="pt-1em pr-1em pl-1em">
                    <h5 className="color-white50 fs-12">상세내역</h5>

                    <ul>
                        <li className="pt-1em pb-1em cl-flex">
                            <h6 className="color-white fs-18 cl-bold mb-0em">임대료</h6>
                            <span className="ml-auto color-secondary fs-16 cl-bold">{numeral(rentalCharges).format()}원</span>
                        </li>

                        <li className="pt-1em pb-1em cl-flex">
                            <h6 className="color-white fs-18 cl-bold mb-0em">전기</h6>
                            <span className="ml-auto color-secondary fs-16 cl-bold">{numeral(electricityCharges).format()}원</span>
                        </li>

                        <li className="pt-1em pb-1em cl-flex">
                            <h6 className="color-white fs-18 cl-bold mb-0em">수도</h6>
                            <span className="ml-auto color-secondary fs-16 cl-bold">{numeral(waterCharges).format()}원</span>
                        </li>

                        <li className="pt-1em pb-1em cl-flex">
                            <h6 className="color-white fs-18 cl-bold mb-0em">가스</h6>
                            <span className="ml-auto color-secondary fs-16 cl-bold">{numeral(gasCharges).format()}원</span>
                        </li>

                        <li className="pt-1em pb-1em cl-flex">
                            <h6 className="color-white fs-18 cl-bold mb-0em">주차</h6>
                            <span className="ml-auto color-secondary fs-16 cl-bold">{numeral(parkingCharges).format()}원</span>
                        </li>
                    </ul>

                </div>
            </div>
        );
    }
}

    
export default withRouter(StatusDetail);
