import React, {Component} from 'react';
import Draggable from 'react-draggable';
import classNames from 'classnames';
import minusSrc from 'images/ic-minus-24-px@3x.png';
import plusSrc from 'images/ic-plus-24-px@3x.png';


class TimeScheduler extends Component {

    constructor(props) {
        super(props);


        const W = 26;
        const MIN = this.props.min;

        // 최초 시작타임 계산용
        let starts = [...Array((this.props.max - MIN) * 2)].map((e, i) => {
            return i
        });

        // 기 예약된 부분 빗금 부분 미리 생성
        let offset = 0;
        let disabled = props.scheduled.map((schedule, i) => {

            let h = (schedule.start - MIN) * 2;
            let hLen = (schedule.end - schedule.start) * 2;

            starts.splice(h - offset, hLen); // 이미 예약된 부분을 시작 영역에서 삭제
            offset = hLen; // 시작위치를 다시 조정

            return <div className="cl-area--disabled" key={i}
                        style={{left: W * h, width: hLen * W + 1}}
            />
        });

        this.state = {
            start: starts[0]*.5 + props.min,
            hour: 0.5,
            W: W,
            remains: starts,
            disabledEl: disabled,
            incorrect: false,
        }
    }

    // 핸들 드래그용 이벤트 헨들러
    handleStart() {
        this.timeEl.classList.add('cl-controlling');
    }

    handleDrag() {
        setTimeout(() => this.syncTimeSize(), 0);
    }

    handleStop() {
        this.timeEl.classList.remove('cl-controlling');
        this.syncTimeSize();
    }//


    syncTimeSize() {
        let x = this.handleEl.style.transform.match(/\d+/g)[0];
        this.timeEl.style.width = `${x}px`;

        this.setState({hour: (x / this.state.W) * .5});
        this.updateTimeSize();
    }

    // 타임존 드래그용 이벤트 헨들러
    timeDrag() {
        setTimeout(() => {
            this.setState({start: (this.timeEl.style.transform.match(/\d+/g)[0] / this.state.W) * .5 + this.props.min});
            this.updateTimeSize();
        }, 0);
    }

    // 타임존 위치나 크기가 변경될때, 기 예약된 부분과 hitTest
    updateTimeSize() {
        // start, hour은 시간단위로 기록되있고, remains는 index로 기록되있음
        const S = this.state.start;
        const H = this.state.hour;
        const R = this.state.remains;

        // console.log( 'updateTimeSize) start:', S, ' hour:', H );

        let selected = [...Array(H * 2)].map((e, i) => {
            return ( S - this.props.min )*2 + i;
        });

        // 인덱스 값 기준으로 hitTest
        let available = selected.every(val => {
            return R.includes(val);
        });

        this.setState({ incorrect: !available });
    }

    // 버튼으로 시간 조절
    timeOffset( offsetTime ){
        let targetHour = this.state.hour + offsetTime;
        if( targetHour <= 0 || targetHour > this.props.maxWidth ) return;

        this.timeEl.style.width = `${ targetHour * this.state.W * 2 }px`;
        this.handleEl.style.transform = `translate(${ targetHour * this.state.W * 2 }px, 0px)`;

        // !! draggable 내부의 x값도 바꿔놔야 버튼으로 조절 후 드래그 할때 오차가 안생김
        this.draggableHandle.setState({ x: targetHour * this.state.W * 2 });

        this.setState({ hour: targetHour });
    }



    render() {

        const W = this.state.W;
        const MIN = this.props.min;
        const HALF_LEN = (this.props.max - MIN) * 2;

        // 30분 단위로 그리기
        let halfHours = [...Array(HALF_LEN)].map((e, i) => {
            let timeString;
            let h = i / 2 + this.props.min;
            let disabledAreas, selectedArea;

            // 시간단위 표시
            if (i % 2 === 0) timeString =
                <span className="cl-hour__label">{((h < 12) ? '오전' + h : '오후' + (h - 12)).replace('오후0', '오후12')}</span>;

            // 첫번째 블록에 같이 스크롤될 요소들 추가: 기예약된 부분, 컨트롤 영역
            if (i === 0) {
                selectedArea = <Draggable key="timezone"
                                           axis="x"
                                           grid={[W]}
                                           defaultPosition={{x: W * (this.state.start - this.props.min)*2, y: 0}}
                                           bounds={{left: 0, right: HALF_LEN * W - (W * this.state.hour)*2 }}
                                           cancel=".cl-area-handle"
                                           onDrag={() => this.timeDrag()}
                >
                    <div className="cl-area--selected" ref={ref => this.timeEl = ref}>
                        <Draggable key="time-handler"
                                   ref={ ref => this.draggableHandle = ref }
                                   axis="x"
                                   grid={[W]}
                                   defaultPosition={{x: W, y: 0}}
                                   bounds={{left: W, right: W * 2 * this.props.maxWidth}}
                                   onStart={() => this.handleStart()}
                                   onDrag={() => this.handleDrag()}
                                   onStop={() => this.handleStop()}
                        >
                            <span className="cl-area-handle" ref={ref => this.handleEl = ref}/>
                        </Draggable>

                        {/*<div className="cl-area-handle"*/}
                             {/*ref={ref => this.handleEl = ref}*/}
                             {/*draggable="true"*/}
                             {/*ondragstart={ ()=>this.handleStart() }*/}
                             {/*ondrag={ ()=>this.handleDrag() }*/}
                             {/*ondragend={ ()=>this.handleStop() }*/}
                        />
                    </div>
                </Draggable>;

                disabledAreas = this.state.disabledEl;
            }

            return <div className="cl-30min" key={i}>
                {timeString}
                {disabledAreas}
                {selectedArea}
            </div>
        });


        return <div className={ classNames( "cl-time-scheduler", { "cl-time-scheduler--incorrect": this.state.incorrect } ) }>

            <span className="cl-30mins">
                {halfHours}
            </span>

            <div className="cl-time-ctrl cl-flex-between">
                <button onClick={ ()=> this.timeOffset( -0.5) }>
                    <img src={minusSrc} alt="시간 감축 버튼" width="24"/>
                </button>

                <p>{ this.state.hour * 60 }분</p>

                <button onClick={ ()=> this.timeOffset( 0.5) }>
                    <img src={plusSrc} alt="시간 증감 버튼" width="24"/>
                </button>
            </div>

        </div>

    }
}


export default TimeScheduler