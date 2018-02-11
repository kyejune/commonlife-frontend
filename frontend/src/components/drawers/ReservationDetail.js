import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';
import moment from 'moment';
import TimeScheduler from 'components/ui/TimeScheduler';
import Counter from 'components/ui/Counter';
import DB from "scripts/db";


class ReservationDetail extends Component {

    constructor(props){
        super( props );

        this.state = {
            loaded: false
        };

        DB.getReservation( this.props.match.params.id, data => {

            data.loaded = true;

            // 날짜
            if( data.date.value ) data.date.value = new Date( data.date.value );
            else                  data.date.value = new Date();

            // 기간? 날짜?
            if( data.date.type === 'date' ) data.dateName = "오늘";
            else                            data.dateName = "기간";


            console.log( moment( data.date.value ).format('YYYY-MM-DD'));
            this.setState( data );

            this.props.updateTitle(data.where); // ContentHolder에 전달
        });
    }

    addFloat( integer ){
        if( parseInt( integer, 10 ) === integer ) integer += '.0';
        return integer;
    }

    render() {

        if( this.state.loaded === false ) {
            return <div>loading..</div>;
        }


        // 포함사항 아이콘
        let Icons = this.state.inclusion.icons.map( (label, i)=>{
           return <i key={i} className={`cl-inclusion-ic cl-${label}`}/>
        });

        // 공지사항
        let Notices = this.state.notice.map( ( n, i )=>{
           return <li key={i}>{n}</li>
        });

        // 옵션들 추가
        let Options = this.state.options.map( ( opt, i )=>{

            if( opt.type === 'select' )
            {
                let Opts = opt.options.map( ( o, i ) =>{
                    return <option value={i} key={i}>{o}</option>
                });

                return <div className="cl-opt-sec cl-flex" key="select">
                    <div className="cl-label">옵션</div>
                    <select name="" id="">
                        { Opts }
                    </select>
                </div>
            }
            else if( opt.type === 'time' )
            {
                return <TimeScheduler // 24시간 기준으로 기입!!
                    key="timescheduler"
                    maxWidth={3} // 최대시간, min-width는 0.5로 고정
                    min={10} // 예약가능한 시작 시간
                    max={20} // 예야가능한 마지막 시간
                    scheduled={ [{ start:10, end:11}, {start:15, end:17}] } // 기 예약된 내용
                />
            }
            else if( opt.type === 'counter' )
            {
                return <div className="cl-opt-sec" key="counter">
                    <div className="cl-label">{opt.title}</div>
                    <Counter {...opt} />
                </div>
            }
            else if( opt.type === 'info' )
            {
                return <div className="cl-opt-sec cl-flex" key="info">
                    <div className="cl-label">{ opt.title }</div>
                    <div>{ opt.info }</div>
                </div>
            }
            else
            {
                return '';
            }

        });


        return <div className="cl-reservation-detail cl-fitted-box--both">

            <div style={{ height:248, backgroundColor:'gray' }}>갤러리 출력 부분</div>


            {/* 선택 옵션 */}
            <div className="cl-reservation-option">

                <div className="cl-opt-sec cl-opt-sec--title">
                    <h3>{ this.state.where }</h3>
                    <p>{ this.state.description }</p>
                </div>

                <div className="cl-opt-sec cl-flex-between cl-opt-sec--branch">
                    <div className="cl-label">지점</div>
                    <div>{ this.state.branch }</div>
                    <button className="cl-map__link"/>
                </div>

                <div className="cl-opt-sec cl-flex cl-opt-sec--date">
                    <div className="cl-label">{ this.state.dateName }</div>
                    <input type="date" name="date"
                           defaultValue={ moment( this.state.date.value ).format('YYYY-MM-DD') }
                    />
                </div>

                { Options }

            </div>


            {/* 하단 정보 출력 부분 */}
            <div className="cl-reservation-info">

                <div className="cl-credit cl-flex-between">
                    <div className="cl-label">예약 크레딧/잔여 크레딧</div>
                    <div className="cl-secondary">
                        <span className="cl-bold">-{ this.addFloat(this.state.cost) }</span>
                        /{ this.addFloat(this.state.balance) }
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

            </div>

            <footer className="cl-flex-between">
                <button>취소</button>
                <button className="cl-plus-label__button">예약하기</button>
            </footer>
        </div>
    }
}


export default observer(withRouter(ReservationDetail));