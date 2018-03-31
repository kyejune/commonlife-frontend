import React, { Component } from 'react';
import MapLink from "../ui/MapLink";

class BranchSeletor extends Component {
    render() {
        return (
            <div>
                <ul className="cl-status-vertical-list">
                    <li className="cl-card-item--dark">
                        <h4 className="cl-name">
                            역삼동 하우징
                            <span className="cl-desc">(서울시 서초구 강남대로 373)</span>
                        </h4>
                        <p className="cl-desc">
                            101동 1002호 <span>조성우 세대</span>
                        </p>
                    </li>
                </ul>

                <div>
                    <h5 className="color-white">서울(Seoul)</h5>

                    <ul className="grid-horizontal--2">

                        <li className="cl-avatar-border">
                            <div className="cl-avatar"/>
                            <h5>무슨무슨 하우스</h5>
                            <p>서울시 서초구 강남대로 373</p>
                            <MapLink/>
                        </li>

                    </ul>

                </div>


                <footer className="cl-opts__footer cl-flex">
                    <button className="ml-auto pr-1em">
                        <span className="color-primary cl-bold">선택</span>
                    </button>
                </footer>
            </div>
        );
    }
}


export default BranchSeletor;
