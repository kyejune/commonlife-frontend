import React, { Component } from 'react';
import Swiper from 'react-id-swiper';
import {Link} from "react-router-dom";
   
class Walkt extends Component {

    constructor(props) {
        super(props);

        this.swiperParams = {
            pagination: {
                el: '.cl-pager',
                type: 'fraction',
            },
        }
    }


    render() {
        return <div className="cl-full-page cl-walkt cl-bg--white">


                <Swiper pagination={ { el:'.swiper-pagination', type:'fraction' } } >
                  <div className="cl-walkt-page">
                      <h2>Community</h2>
                      <p>COMMON Life 지점간, 지점내 다양한 멤버와의<br/>네트워크를 통해 새로운 삶의 방식을 발견하세요.</p>
                  </div>

                  <div className="cl-walkt-page">
                      <h2>Reservation</h2>
                      <p>개성이 넘치는 공간 부터 가구까지 생활에 필요한<br/>모든 것이 있습니다. 이제 간편하게 예약하세요.</p>
                  </div>

                  <div className="cl-walkt-page">
                      <h2>Smart IoT</h2>
                      <p>커먼라이프의 스마트IoT와 함께 더 효율적인 삶의<br/>방식을 만나보세요.</p>
                  </div>
                </Swiper>

                <footer className="fs-16 color-white">
                    <div className="cl-flex">
                        <Link to="/community/feed" className="cl-bold ml-auto">SKIP</Link>
                    </div>
                </footer>


            </div>;
    }
}

    
export default Walkt;
