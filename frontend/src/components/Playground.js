import React, {Component} from 'react';
import TimeScheduler from 'components/ui/TimeScheduler';
import Store from "../scripts/store";


class Playground extends Component{

    render(){

        return <div>

            <h1>컴퍼넌트 테스트 페이지</h1>


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