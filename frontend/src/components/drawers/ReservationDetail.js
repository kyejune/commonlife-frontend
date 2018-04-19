import React, { Component } from 'react';
import { withRouter } from 'react-router';
import TimeScheduler from 'components/ui/TimeScheduler';
import SwiperViewer from 'components/ui/SwiperViewer';
import Counter from 'components/ui/Counter';
import DB from "scripts/net";
import completeSrc from 'images/complete-bt-blueicon@3x.png';
import DateOne from "components/ui/DateOne";
import DatePeriod from "components/ui/DatePeriod";
import { Link } from 'react-router-dom';
import Store from "../../scripts/store";
import moment from "moment";
import nl2br from 'react-nl2br';
import queryString from 'query-string';

class ReservationDetail extends Component {

	getReservationOnDate( date ) {
		DB.getReservationOnDate( this.props.match.params.id, date, data => {
			this.setState( { reservedSchedules: data.map( res => {
				let start = moment( res.startTime, 'HH:mm:ss.000000' ).format( 'HH:mm' ).replace( ':00', '' ).replace( ':30', '.5' );
				let end = moment( res.endTime, 'HH:mm:ss.000000' ).format( 'HH:mm' ).replace( ':00', '' ).replace( ':30', '.5' );
				return {
					start: Number( start ),
					end: Number( end ),
				}
			} ) } );
		} );
	}

	constructor( props ) {
		super( props );

		this.state = {
			loaded: false,
			available: true,
			booked: false,
			reserved: false,
            correctTime: true,
		};

		DB.getReservationScheme( this.props.match.params.id, data => {
			data.loaded = true;

			const result = {
				loaded: true,
				pictures: [
					{
						"thumbnail": "https://placeimg.com/640/480/arch"
					},
					{
						"thumbnail": "https://placeimg.com/480/640/nature"
					},
					{
						"thumbnail": "https://placeimg.com/300/300/tech"
					},
					{
						"thumbnail": "https://placeimg.com/640/480/animals"
					},
					{
						"thumbnail": "https://placeimg.com/640/480/arch"
					}
				],
				where: data.title,
				description: data.description,
				branch: data.complex.cmplxNm,
				map: "https://www.google.com/maps/search/" + data.complex.addr.toString().split( ' ' ).join( '+' ),
				"options": [
					// {
					//     "type": "info",
					//     "title": "예약가능",
					//     "info": "20석 / 총 80석"
					// },
					// {
					//     "type": "select",
					//     "title": "옵션",
					//     "value": 0,
					//     "options": [
					//         "소형창고 222m",
					//         "중형창고 333m",
					//         "대형창고 444m"
					//     ]
					// },
					// {
					//     "type": "counter",
					//     "title": "수량",
					//     "value": 1,
					//     "min": 1,
					//     "max": 3
					// },

				],
				"cost": data.point,
				"balance": 0,
				"inclusion": {
					"comment": data.options,
					"icons": [
						// "fridge",
						// "tv",
						// "cabinet"
					]
				},
				"notice": data.precautions.split( '\n' )
			};

			if ( data.images ) {
				result.pictures = data.images.split( ',' ).map( image => {
					return { thumbnail: Store.api + "/imageStore/" + image }
				} );
			}

			// 오늘 그리고 내일
			const now = moment();
			const tommorow = moment().add( 1, 'days' );
			let queries = {};

			if( window.location.hash.split( '?' ).length > 1 ) {
				queries = queryString.parse( window.location.hash.split( '?' ).pop() );
			}

			if ( data.reservationType === 'A' ) {
				result.options.push( {
					type: "date",
					value: ( queries[ 'date' ] ) ? queries[ 'date' ] : now.format( 'YYYY-MM-DD' ),
					min: moment( data.startDt, 'YYYY-MM-DD' ).format( 'YYYY-MM-DD' ),
					max: moment( data.endDt, 'YYYY-MM-DD' ).format( 'YYYY-MM-DD' )
				} );

				// start, end에는 반드시 Number 형 데이터가 들어가야 한다.
				result.options.push( {
					type: "time",
					value: parseInt( now.format( 'HH' ), 10 ),
					start: parseInt( moment( data.openTime, 'HH:mm:ss.000000' ).format( 'HH' ), 10 ),
					end: parseInt( moment( data.closeTime, 'HH:mm:ss.000000' ).format( 'HH' ), 10 ),
					// selected: []
					// "maxWidth": 3,
					// "selected": [
					//     {
					//         "start": 9,
					//         "end": 10
					//     },
					//     {
					//         "start": 12,
					//         "end": 14
					//     }
					// ]
				} );

				// TimeScheduler 조정용 date 객체 삽입
				result.dates = [ now.toDate(), now.toDate() ];

				// 기존 예약 내용 로드
				this.getReservationOnDate( moment().format( 'YYYY-MM-DD' ) );
			}
			else if ( data.reservationType === 'B' ) {
				result.options.push( {
					type: "dateRange",
					value: [
						( queries[ 'date' ] ) ? queries[ 'date' ] : now.format( 'YYYY-MM-DD' ),
						moment().add( 1, 'day' ).format( 'YYYY-MM-DD' )
					],
					min: moment( data.startDt, 'YYYY-MM-DD' ).format( 'YYYY-MM-DD' ),
					max: moment( data.endDt, 'YYYY-MM-DD' ).format( 'YYYY-MM-DD' )
				} );

				// TimeScheduler 조정용 date 객체 삽입
				result.dates = [ now.toDate(), tommorow.toDate() ];
			}
			else if ( data.reservationType === 'C' ) {
				result.options.push( {
					type: "date",
					value: ( queries[ 'date' ] ) ? queries[ 'date' ] : now.format( 'YYYY-MM-DD' ),
					min: moment( data.startDt, 'YYYY-MM-DD' ).format( 'YYYY-MM-DD' ),
					max: moment( data.endDt, 'YYYY-MM-DD' ).format( 'YYYY-MM-DD' )
				} );
				result.options.push( {
					type: 'info',
					title: '수량',
					info: `${data.maxQty} / ${data.maxQty}`
				} );

				// TimeScheduler 조정용 date 객체 삽입
				result.dates = [ now.toDate(), tommorow.toDate() ];
			}
			else if ( data.reservationType === 'D' ) {
				result.options.push( {
					type: "date",
					value: ( queries[ 'date' ] ) ? queries[ 'date' ] : now.format( 'YYYY-MM-DD' ),
					min: moment( data.startDt, 'YYYY-MM-DD' ).format( 'YYYY-MM-DD' ),
					max: moment( data.endDt, 'YYYY-MM-DD' ).format( 'YYYY-MM-DD' )
				} );

				result.options.push( {
					type: 'input',
					title: data.userMemoLabel,
					placeholder: data.userMemoPlaceholder
				} );

				// TimeScheduler 조정용 date 객체 삽입
				result.dates = [ now.toDate(), tommorow.toDate() ];
			}

			if( data.schemeOptions && data.schemeOptions.length ) {
				result.options.push( {
					type: 'select',
					options: data.schemeOptions
				} );
				result.options.push( {
					type: 'counter',
					title: '수량',
				} );
			}

			// 원본 데이터도 바인딩 시켜둔다
			result.scheme = data;
			result.reservedSchedules = [];

			this.setState( result );
			this.props.updateTitle( data.title );
		} );

		// 함수 스코프 바인딩
		this.reserve = this.reserve.bind( this );
		this.getReservationOnDate = this.getReservationOnDate.bind( this );
	}

	addFloat( integer ) {
		if ( parseInt( integer, 10 ) === integer ) integer += '.0';
		return integer;
	}


	// 예약하기
	reserve() {

	    if( this.timeEl && !this.state.correctTime ){
	        Store.alert('유효한 시간을 선택하여 주시기 바랍니다.');
	        return
        }

		let parentIdx = this.props.match.params.id;
		let startDt = moment( this.state.dates[ 0 ] ).format( 'YYYY-MM-DD' );
		let startTime = moment( this.state.dates[ 0 ] ).format( 'HH:mm' );
		let endDt = moment( this.state.dates[ 1 ] ).format( 'YYYY-MM-DD' );
		let endTime = moment( this.state.dates[ 1 ] ).format( 'HH:mm' );

		const params = {
			parentIdx,
			startDt,
			startTime,
			endDt,
			endTime,
		};

		if( this.refs && this.refs.selector ) {
			params.optionId = this.refs.selector.value;
		}
		if( this.refs && this.refs.counter ) {
			params.qty = this.refs.counter.val();
		}
		if( this.refs && this.refs.userMemo ) {
			params.userMemo = this.refs.userMemo.value;
		}

		DB.createReservation( params, () => {
			this.setState( { reserved: true } );
		} );
	}

	// 대기요청하기
	book() {
		this.setState( { booked: true } );
	}

	// 타임스케쥴러 업데이트
	onUpdateTimeSchedule=( data )=> {
		let { start, end } = data;
		let starts = start.split( ':' );
		let ends = end.split( ':' );

		this.state.dates[ 0 ].setHours( starts[ 0 ] );
		this.state.dates[ 0 ].setMinutes( starts[ 1 ] );

		this.state.dates[ 1 ].setHours( ends[ 0 ] );
		this.state.dates[ 1 ].setMinutes( ends[ 1 ] );

		this.setState({ correctTime: data.correct });
	}

	// 날짜 업데이트
	onUpdateDate( fromTo ) {

		for ( let i = 0; i < 2; i++ ) {
			let d = this.state.dates[ i ];
			let n = fromTo[ i ];

			let hour = d.getHours();
			let minute = d.getMinutes();

			this.state.dates[ i ].setFullYear( n.getFullYear() );
			this.state.dates[ i ].setMonth( n.getMonth() );
			this.state.dates[ i ].setDate( n.getDate() );
			this.state.dates[ i ].setHours( hour );
			this.state.dates[ i ].setMinutes( minute );
		}

		this.getReservationOnDate( moment( fromTo[ 0 ] ).format( 'YYYY-MM-DD' ) );
	}

	// 캘린더에 추가 =
	addSchedule() {

		if ( window.plugins )
			window.plugins.calendar.createEventInteractively( this.state.where, this.state.branch, this.state.description, this.state.dates[ 0 ], this.state.dates[ 1 ], this.addedSchedule, null );
		else
			this.addedSchedule();
	}

	addedSchedule() {
		Store.alert( '추가됨' );
	}

	render() {


		if ( this.state.loaded === false ) {
			return <div>loading..</div>;
		}


		// 포함사항 아이콘
		let Icons = this.state.inclusion.icons.map( ( label, i ) => {
			return <i key={i} className={`cl-inclusion-ic cl-${label}`}/>
		} );

		// 공지사항
		let Notices = this.state.notice.map( ( n, i ) => {
			return <li key={i}>{n}</li>
		} );

		// 옵션들 추가
		let Options = this.state.options.map( ( opt, i ) => {

			if ( opt.type === 'date' ) {
				return <DateOne key="date" {...opt}
								onUpdate={data => this.onUpdateDate( [ new Date( data ), new Date( data ) ] )}/>
			}
			else if ( opt.type === 'dateRange' ) {
				return <DatePeriod key="date-range" {...opt}
								   onUpdate={( from, to ) => this.onUpdateDate( [ new Date( from ), new Date( to ) ] )}/>
			}
			else if ( opt.type === 'select' ) {
				let Opts = opt.options.map( ( o, i ) => {
					return <option value={o.idx} key={i}>{o.name}</option>
				} );

				return <div className="cl-opt-sec cl-flex" key="select">
					<div className="cl-label">{opt.title || "옵션"}</div>
					<select ref="selector">{Opts}</select>
				</div>
			}
			else if ( opt.type === 'time' ) {
				return <TimeScheduler // 24시간 기준으로 기입!!
					key="time"
                    ref={ r => this.timeEl = r }
					// maxWidth={3} // 최대시간, min-width는 0.5로 고정
					min={opt.start} // 예약가능한 시작 시간
					max={opt.end} // 예야가능한 마지막 시간
					scheduled={this.state.reservedSchedules} // 기 예약된 내용
					onUpdate={ this.onUpdateTimeSchedule }
				/>
			}
			else if ( opt.type === 'counter' ) {
				return <div className="cl-opt-sec" key="counter">
					<div className="cl-label">{opt.title}</div>
					<Counter ref="counter" {...opt} />
				</div>
			}
			else if ( opt.type === 'info' ) {
				return <div className="cl-opt-sec cl-flex" key="info">
					<div className="cl-label">{opt.title}</div>
					<div>{opt.info}</div>
				</div>
			}
			else if( opt.type === 'input' ){
				return <div className="cl-opt-sec cl-flex" key="input">
                    <div className="cl-label">{opt.title}</div>
					<div>
                    	<input ref="userMemo" className="w-100" type="text" placeholder={opt.placeholder} defaultValue={opt.info||''}/>
                    </div>
                </div>
			}
			else {
				return '';
			}

		} );

		// 푸터 버튼
		let FooterBtns;
		if ( this.state.reserved || this.state.booked ) {
			FooterBtns = [
				<span key="blank"/>,
				<button key="confirm" onClick={() => window.history.back()}><img src={completeSrc} alt="완료버튼" width="97" height="36"/></button>
			];
		} else {
			FooterBtns = [
				<button key="cancel" onClick={() => window.history.back()}>취소</button>,
				<button key="reserve" className="cl-plus-label__button" onClick={() => this.reserve()}>예약하기</button>
			];
		}


		return [<div className="cl-reservation-detail pb-3em drawer-fitted-box" key="rd">

			{!this.state.reserved &&
			<Link to={'/reservation/' + this.props.match.params.id + '/thumbnails'}>
				<SwiperViewer thumbnails={this.state.pictures} viewType={'rectangle'}/>
			</Link>
			}


			{/* 선택 옵션 */}
			<div className="cl-reservation-option">

				<div className="cl-opt-sec cl-opt-sec--title">
					<h3>
						{this.state.where}
						{this.state.reserved && " 예약이 완료되었습니다."}
						{this.state.booked && <span className="cl-booked">(대기요청 완료)</span>}
					</h3>

					{!this.state.reserved && !this.state.booked &&
					<p>{ nl2br( this.state.description ) }</p>
					}
				</div>

				<div className="cl-opt-sec cl-flex-between cl-opt-sec--branch">
					<div className="cl-label">지점</div>
					<div>{this.state.branch}</div>
					<button className="cl-map__link" onClick={ () => window.open( this.state.map )}/>
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
						{!this.state.reserved &&
						<span className="cl-bold">
                                -{this.addFloat( this.state.cost )}/
                            </span>
						}{this.addFloat( this.state.balance )}
					</div>
				</div>

				<div className="cl-inclusion">
					<div className="cl-label">포함사항</div>
					<p className="cl-desc">
						{this.state.inclusion.comment}
					</p>
					<div className="cl-icons">
						{
							this.state.scheme.amenities.map( ( item, key ) => {
								return <span key={ key } style={{ marginRight: '1em' }}>
									<img src={ Store.api + '/reservation-amenities/' + item.iconIdx + '/icon' }
										 style={{ height: '12px', display: 'inline-block', marginRight: '0.3em' }} alt=""/>
									{ item.name }
								</span>
							} )
						}
						{Icons}
					</div>
				</div>

				<ul className="cl-notice">
					{Notices}
				</ul>

				{( this.state.reserved || this.state.booked ) &&
				<div className="cl-reserved">
					<button onClick={() => this.addSchedule()}>나의 캘린더에 등록</button>
					<button>예약변경 및 취소</button>
				</div>}

			</div>
		</div>,
		<footer className="cl-flex-between" key="rd-footer">
			{FooterBtns}
		</footer>]
	}
}


export default withRouter( ReservationDetail );