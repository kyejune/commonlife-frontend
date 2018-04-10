import React, { Component } from 'react';

import ChartCombination from '../../ui/ChartCombination';
import ChartGauge from "../../ui/ChartGauge";

import IotIcGraph from 'images/iot-info-status-1@3x.png';
import IotIcWater from 'images/iot-info-status-6@3x.png';
import IotIcHot from 'images/iot-info-status-5@3x.png';
import IotIcValve from 'images/iot-info-device-6@3x.png';
import IotIcHeater from 'images/iot-info-io-t-heater-3@3x.png';

class Information extends Component {
	constructor ( props ) {
		super( props );

		this.state = {
			tabIndex: 0,
			tabData: 0,
		};
	}

	toggleTab = ( index )=> {
		this.setState( { tabIndex: index, tabData: index, } );
	};

	InfoContent () {
		switch ( this.state.tabIndex ) {
			case 0:
				return <div>
					<div className="cl-flex cl-info-month-select">
						<div className="cl-info-month-select__month">
							<select name="" id="">
								<option value="">2018년 5월</option>
								<option value="">2018년 4월</option>
								<option value="">2018년 3월</option>
								<option value="">2018년 2월</option>
								<option value="">2018년 1월</option>
								<option value="">2017년 12월</option>
							</select>
						</div>
						<div className="cl-info-month-select__type">
							<select name="" id="" >
								<option value="">월별보기</option>
								<option value="">월별보기</option>
							</select>
						</div>
					</div>
					<ChartCombination/>

					<ul className="cl-info-month-box">
						<li className="cl-flex cl-info-month-box__item">
							<h3>229.3Kw</h3>
							<p>전월누적</p>
						</li>
						<li className="cl-flex cl-info-month-box__item">
							<h3>1258.2Kw</h3>
							<p>당월누적</p>
						</li>
						<li className="cl-flex cl-info-month-box__item">
							<h3>1350.4Kw</h3>
							<p>당월예상</p>
						</li>
					</ul>
					<ChartGauge/>

					<ul className="cl-info-month-box">
						<li className="cl-flex cl-info-month-box__item">
							<h3>2단계</h3>
							<p>누진단계</p>
						</li>
					</ul>
				</div>;
				break;
			case 1:
				return <div>
					<div className="cl-flex cl-info-month-select">
						<div className="cl-info-month-select__month">
							<select name="" id="">
								<option value="">2018년 5월</option>
								<option value="">2018년 4월</option>
								<option value="">2018년 3월</option>
								<option value="">2018년 2월</option>
								<option value="">2018년 1월</option>
								<option value="">2017년 12월</option>
							</select>
						</div>
						<div className="cl-info-month-select__type">
							<select name="" id="" >
								<option value="">월별보기</option>
								<option value="">월별보기</option>
							</select>
						</div>
					</div>
					<ChartCombination/>

					<ul className="cl-info-month-box">
						<li className="cl-flex cl-info-month-box__item">
							<h3>229.3Kw</h3>
							<p>전월누적</p>
						</li>
						<li className="cl-flex cl-info-month-box__item">
							<h3>1258.2Kw</h3>
							<p>당월누적</p>
						</li>
						<li className="cl-flex cl-info-month-box__item">
							<h3>1350.4Kw</h3>
							<p>당월예상</p>
						</li>
					</ul>
					<ChartGauge/>

					<ul className="cl-info-month-box">
						<li className="cl-flex cl-info-month-box__item">
							<h3>2단계</h3>
							<p>누진단계</p>
						</li>
					</ul>
				</div>;
				break;
			case 2:
				return <div>
					<div className="cl-flex cl-info-month-select">
						<div className="cl-info-month-select__month">
							<select name="" id="">
								<option value="">2018년 5월</option>
								<option value="">2018년 4월</option>
								<option value="">2018년 3월</option>
								<option value="">2018년 2월</option>
								<option value="">2018년 1월</option>
								<option value="">2017년 12월</option>
							</select>
						</div>
						<div className="cl-info-month-select__type">
							<select name="" id="" >
								<option value="">월별보기</option>
								<option value="">월별보기</option>
							</select>
						</div>
					</div>
					<ChartCombination/>

					<ul className="cl-info-month-box">
						<li className="cl-flex cl-info-month-box__item">
							<h3>229.3Kw</h3>
							<p>전월누적</p>
						</li>
						<li className="cl-flex cl-info-month-box__item">
							<h3>1258.2Kw</h3>
							<p>당월누적</p>
						</li>
						<li className="cl-flex cl-info-month-box__item">
							<h3>1350.4Kw</h3>
							<p>당월예상</p>
						</li>
					</ul>
					<ChartGauge/>

					<ul className="cl-info-month-box">
						<li className="cl-flex cl-info-month-box__item">
							<h3>2단계</h3>
							<p>누진단계</p>
						</li>
					</ul>
				</div>;
				break;
			case 3:
				return <div>
					<div className="cl-flex cl-info-month-select">
						<div className="cl-info-month-select__month">
							<select name="" id="">
								<option value="">2018년 5월</option>
								<option value="">2018년 4월</option>
								<option value="">2018년 3월</option>
								<option value="">2018년 2월</option>
								<option value="">2018년 1월</option>
								<option value="">2017년 12월</option>
							</select>
						</div>
						<div className="cl-info-month-select__type">
							<select name="" id="" >
								<option value="">월별보기</option>
								<option value="">월별보기</option>
							</select>
						</div>
					</div>
					<ChartCombination/>

					<ul className="cl-info-month-box">
						<li className="cl-flex cl-info-month-box__item">
							<h3>229.3Kw</h3>
							<p>전월누적</p>
						</li>
						<li className="cl-flex cl-info-month-box__item">
							<h3>1258.2Kw</h3>
							<p>당월누적</p>
						</li>
						<li className="cl-flex cl-info-month-box__item">
							<h3>1350.4Kw</h3>
							<p>당월예상</p>
						</li>
					</ul>
					<ChartGauge/>

					<ul className="cl-info-month-box">
						<li className="cl-flex cl-info-month-box__item">
							<h3>2단계</h3>
							<p>누진단계</p>
						</li>
					</ul>
				</div>;
				break;
			case 4:
				return <div>
					<div className="cl-flex cl-info-month-select">
						<div className="cl-info-month-select__month">
							<select name="" id="">
								<option value="">2018년 5월</option>
								<option value="">2018년 4월</option>
								<option value="">2018년 3월</option>
								<option value="">2018년 2월</option>
								<option value="">2018년 1월</option>
								<option value="">2017년 12월</option>
							</select>
						</div>
						<div className="cl-info-month-select__type">
							<select name="" id="" >
								<option value="">월별보기</option>
								<option value="">월별보기</option>
							</select>
						</div>
					</div>
					<ChartCombination/>

					<ul className="cl-info-month-box">
						<li className="cl-flex cl-info-month-box__item">
							<h3>229.3Kw</h3>
							<p>전월누적</p>
						</li>
						<li className="cl-flex cl-info-month-box__item">
							<h3>1258.2Kw</h3>
							<p>당월누적</p>
						</li>
						<li className="cl-flex cl-info-month-box__item">
							<h3>1350.4Kw</h3>
							<p>당월예상</p>
						</li>
					</ul>
					<ChartGauge/>

					<ul className="cl-info-month-box">
						<li className="cl-flex cl-info-month-box__item">
							<h3>2단계</h3>
							<p>누진단계</p>
						</li>
					</ul>
				</div>;
				break;
			default:
				return <div>내용이 없습니다</div>;
				break;
		}
	};

	render () {
		return (
			<div>
				<div className="cl-iot-info-tab">
					<button onClick={()=>this.toggleTab( 0 )}
							className={( this.state.tabIndex === 0 ? 'cl-iot-info-tab__item--active' : 'cl-iot-info-tab__item' )}>
						<img src={IotIcGraph} alt="img"/>
						<span>전기</span>
					</button>
					<button onClick={()=>this.toggleTab( 1 )}
							className={( this.state.tabIndex === 1 ? 'cl-iot-info-tab__item--active' : 'cl-iot-info-tab__item' )}>
						<img src={IotIcWater} alt="img"/>
						<span>수도</span>
					</button>
					<button onClick={()=>this.toggleTab( 2 )}
							className={( this.state.tabIndex === 2 ? 'cl-iot-info-tab__item--active' : 'cl-iot-info-tab__item' )}>
						<img src={IotIcHot} alt="img"/>
						<span>온수</span>
					</button>
					<button onClick={()=>this.toggleTab( 3 )}
							className={( this.state.tabIndex === 3 ? 'cl-iot-info-tab__item--active' : 'cl-iot-info-tab__item' )}>
						<img src={IotIcValve} alt="img"/>
						<span>가스</span>
					</button>
					<button onClick={()=>this.toggleTab( 4 )}
							className={( this.state.tabIndex === 4 ? 'cl-iot-info-tab__item--active' : 'cl-iot-info-tab__item' )}>
						<img src={IotIcHeater} alt="img"/>
						<span>난방</span>
					</button>
				</div>

				<div className="cl-iot-info-content">
					{this.InfoContent()}
				</div>
			</div>
		);
	}
}


export default Information;
