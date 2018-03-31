import React, { Component } from 'react';

class Status extends Component {
    render() {
        return <div>
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


            <footer className="cl-opts__footer cl-flex">
                <button className="ml-auto pr-1em">
                    <span className="color-primary cl-bold">확인</span>
                </button>
            </footer>

        </div>
    }
}


export default Status;
