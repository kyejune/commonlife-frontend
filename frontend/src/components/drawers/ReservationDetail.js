import React, {Component} from 'react';
import {withRouter} from 'react-router';
import TimeScheduler from 'components/ui/TimeScheduler';
import SwiperViewer from 'components/ui/SwiperViewer';
import Counter from 'components/ui/Counter';
import DB from "scripts/net";
import completeSrc from 'images/complete-bt-blueicon@3x.png';
import DateOne from "components/ui/DateOne";
import DatePeriod from "components/ui/DatePeriod";
import { Link } from 'react-router-dom';


class ReservationDetail extends Component {

    constructor(props) {
        super(props);

        this.state = {
            loaded: false,
            available: false,
            booked: false,
            reserved: false,
        };


        DB.getReservation(this.props.match.params.id, data => {
            data.loaded = true;

            data.options.map( opt => {
                if (opt.type === 'date')
                    data.dates = [ new Date( opt.value ), new Date( opt.value ) ];
                else if( opt.type === 'dateRange')
                    data.dates = [ new Date( opt.value[0] ), new Date( opt.value[1] ) ];
                return null;
            });

            this.setState(data);
            this.props.updateTitle(data.where); // ContentHolder에 전달
        });
    }

    addFloat(integer) {
        if (parseInt(integer, 10) === integer) integer += '.0';
        return integer;
    }


    // 예약하기
    reserve() {
        this.setState({reserved: true});
    }

    // 대기요청하기
    book() {
        this.setState({booked: true});
    }

    // 타임스케쥴러 업데이트
    onUpdateTimeSchedule( data ){
        let { start, end } = data;
        let starts = start.split(':');
        let ends = end.split(':');

        this.state.dates[0].setHours( starts[0] );
        this.state.dates[0].setMinutes( starts[1] );

        this.state.dates[1].setHours( ends[0] );
        this.state.dates[1].setMinutes( ends[1] );
    }

    // 날짜 업데이트
    onUpdateDate( fromTo ){

        for( let i=0; i<2; i++ ){
            let d = this.state.dates[i];
            let n = fromTo[i];

            let hour = d.getHours();
            let minute = d.getMinutes();

            this.state.dates[i].setFullYear( n.getFullYear() );
            this.state.dates[i].setMonth( n.getMonth() );
            this.state.dates[i].setDate( n.getDate() );
            this.state.dates[i].setHours( hour );
            this.state.dates[i].setMinutes( minute );
        }
    }

    // 캘린더에 추가 =
    addSchedule(){

        if( window.plugins )
            window.plugins.calendar.createEventInteractively( this.state.where, this.state.branch, this.state.description, this.state.dates[0], this.state.dates[1], this.addedSchedule, null );
        else
            this.addedSchedule();
    }

    addedSchedule(){
        alert('추가됨');
    }

    render() {


        if (this.state.loaded === false) {
            return <div>loading..</div>;
        }


        // 포함사항 아이콘
        let Icons = this.state.inclusion.icons.map((label, i) => {
            return <i key={i} className={`cl-inclusion-ic cl-${label}`}/>
        });

        // 공지사항
        let Notices = this.state.notice.map((n, i) => {
            return <li key={i}>{n}</li>
        });

        // 옵션들 추가
        let Options = this.state.options.map((opt, i) => {

            if( opt.type === 'date' )
            {
                return <DateOne key="date" {...opt}
                                onUpdate={ data => this.onUpdateDate( [new Date(data), new Date(data)] ) }/>
            }
            else if( opt.type === 'dateRange')
            {
                return <DatePeriod key="date-range" {...opt}
                                   onUpdate={ (from, to ) => this.onUpdateDate( [new Date(from), new Date(to)] ) } />
            }
            else if (opt.type === 'select')
            {
                let Opts = opt.options.map((o, i) => {
                    return <option value={i} key={i}>{o}</option>
                });

                return <div className="cl-opt-sec cl-flex" key="select">
                    <div className="cl-label">{opt.title||"옵션"}</div>
                    <select name="" id="">{Opts}</select>
                </div>
            }
            else if (opt.type === 'time')
            {
                return <TimeScheduler // 24시간 기준으로 기입!!
                    key="time"
                    maxWidth={3} // 최대시간, min-width는 0.5로 고정
                    min={10} // 예약가능한 시작 시간
                    max={20} // 예야가능한 마지막 시간
                    scheduled={[{start: 10, end: 11}, {start: 15, end: 17}]} // 기 예약된 내용
                    onUpdate={ data => this.onUpdateTimeSchedule( data ) }
                />
            }
            else if (opt.type === 'counter')
            {
                return <div className="cl-opt-sec" key="counter">
                    <div className="cl-label">{opt.title}</div>
                    <Counter {...opt} />
                </div>
            }
            else if (opt.type === 'info')
            {
                return <div className="cl-opt-sec cl-flex" key="info">
                    <div className="cl-label">{opt.title}</div>
                    <div>{opt.info}</div>
                </div>
            }
            else
            {
                return '';
            }

        });

        // 푸터 버튼
        let FooterBtns;
        if (this.state.reserved || this.state.booked) {
            FooterBtns = [
                <span key="blank"/>,
                <button key="confirm"><img src={completeSrc} alt="완료버튼" width="97" height="36"/></button>
            ];
        } else {
            FooterBtns = [
                <button key="cancel">취소</button>,
                <button key="reserve" className="cl-plus-label__button" onClick={() => this.reserve()}>예약하기</button>
            ];
        }


        return <div className="cl-reservation-detail pb-3em">

            {!this.state.reserved &&
            <Link to={ '/reservation/0/thumbnails' }>
			    <SwiperViewer thumbnails={this.state.pictures} viewType={'rectangle'}/>
            </Link>
            }


            {/* 선택 옵션 */}
            <div className="cl-reservation-option">

                <div className="cl-opt-sec cl-opt-sec--title">
                    <h3>
                        {this.state.where}
                        {this.state.reserved&&" 예약이 완료되었습니다."}
                        {this.state.booked&&<span className="cl-booked">(대기요청 완료)</span>}
                    </h3>

                    {!this.state.reserved && !this.state.booked &&
                    <p>{this.state.description}</p>
                    }
                </div>

                <div className="cl-opt-sec cl-flex-between cl-opt-sec--branch">
                    <div className="cl-label">지점</div>
                    <div>{this.state.branch}</div>
                    <button className="cl-map__link"/>
                </div>

                {Options}

                {!this.state.available && <div>
                    <button onClick={() => this.book()} className="cl-reservation-book__button">대기요청 예약</button>
                </div>}


            </div>


            {/* 하단 정보 출력 부분 */}
            <div className="cl-reservation-info">

                <div className="cl-credit cl-flex-between">
                    <div className="cl-label">{this.state.reserved ? "잔여 크레딧" : "예약 크레딧/잔여 크레딧"}</div>
                    <div className="cl-secondary">
                        { !this.state.reserved&&
                            <span className="cl-bold">
                                -{this.addFloat(this.state.cost)}/
                            </span>
                        }{this.addFloat(this.state.balance)}
                    </div>
                </div>

                <div className="cl-inclusion">
                    <div className="cl-label">포함사항</div>
                    <p className="cl-desc">
                        {this.state.inclusion.comment}
                    </p>
                    <div className="cl-icons">
                        {Icons}
                    </div>
                </div>

                <ul className="cl-notice">
                    {Notices}
                </ul>

                {( this.state.reserved || this.state.booked )&&
                <div className="cl-reserved">
                    <button onClick={()=> this.addSchedule() }>나의 캘린더에 등록</button>
                    <button>예약변경 및 취소</button>
                </div>}

            </div>

            <footer className="cl-flex-between">
                {FooterBtns}
            </footer>
        </div>
    }
}


export default withRouter(ReservationDetail);