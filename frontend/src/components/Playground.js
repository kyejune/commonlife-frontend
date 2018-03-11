import React, {Component} from 'react';
import TimeScheduler from 'components/ui/TimeScheduler';
import SampleSrc from 'images/io-t-icon-1@3x.png';


class Playground extends Component{

    render(){

        return <div>

            <h1>컴퍼넌트 테스트 페이지</h1>


            <h3>Iot탭에 있는 세로 리스트들</h3>
            <ul className="cl-iot-vertical-list cl-bg--white">
                {/* Iot_기기제어 목록, 기기별 보기 */}
                <li>
                    <img className="cl__thumb" src={SampleSrc}/>
                    <h4 className="cl__title">거실</h4>
                    <div className="cl-next__button ml-auto"/>
                </li>

                {/* Iot_기기제어 상세 */}
                <li>
                    <img className="cl__thumb--rounded" src={SampleSrc}/>
                    <div>
                        <h4 className="cl__title">거실</h4>
                        <p className="cl__desc">스위치 | OFF</p>
                    </div>

                    <div className="ml-auto">라디오 컴퍼넌트</div>
                </li>
            </ul>

            <ul className="cl-iot-vertical-list cl-bg--dark">

                {/* IoT_history 켜짐상태 */}
                <li>
                    <img className="cl__thumb--rounded" src={SampleSrc}/>
                    <div>
                        <h4 className="cl__title">거실조명</h4>
                        <p className="cl__desc">조성우 | ON</p>
                    </div>

                    <div className="ml-auto cl__desc text-right">
                        2017. 3.15<br/>
                        AM 05:00
                    </div>
                </li>

                {/* IoT_history 꺼짐상태 */}
                <li>
                    <img className="cl__thumb--rounded--off" src={SampleSrc}/>
                    <div>
                        <h4 className="cl__title">거실조명</h4>
                        <p className="cl__desc">조성우 | OFF</p>
                    </div>

                    <div className="ml-auto cl__desc text-right">
                        2017. 3.15<br/>
                        AM 05:00
                    </div>
                </li>


            </ul>



            <h3>타입 스케쥴러</h3>
            <TimeScheduler // 24시간 기준으로 기입!!
                maxWidth={3} // 최대시간, min-width는 0.5로 고정
                min={10} // 예약가능한 시작 시간
                max={20} // 예야가능한 마지막 시간
                scheduled={ [{ start:10, end:11}, {start:15, end:17}] } // 기 예약된 내용
            />

        </div>

    }
}


export default Playground