import React, { Component } from 'react';
import c3 from 'c3';


class ChartCombination extends Component {

	componentDidMount () {

		let selectedIndex = 5; // 선택된 놈
		let startNum = 12;

		/*
		* path 그리기
		* M x y 이동
		* L x y 라인 그리기
		* H x 수평 그리기
		* V y 수직 그리기
		* // 상대좌표는 소문자
		* Q 꺽이는 x y 도착 x y
		*
		* */
        c3.chart.internal.fn.generateDrawBar = function (barIndices, isSub) {
                let getPoints = this.generateGetBarPoints(barIndices, isSub);
            return function (d, i) {
                // 4 points that make a bar
                const PS = getPoints(d, i); // 데이터는 LB 위치부터 반시계 방향으로 들어옴
				let path = '';

				const OFFSET_Y = 8; // 밑에 axis랑 떨어뜨릴 목적

				const BL = { x:PS[0][0], y:PS[0][1] - OFFSET_Y };
				const TL = { x:PS[1][0], y:PS[1][1] - OFFSET_Y };
				const TR = { x:PS[2][0], y:PS[2][1] - OFFSET_Y };
				const BR = { x:PS[3][0], y:PS[3][1] - OFFSET_Y };
				const R = 7;

                path += `M ${BL.x}, ${BL.y - R} L ${TL.x}, ${TL.y + R}, Q ${TL.x}, ${TL.y}, ${TL.x + R}, ${TL.y}, Q ${TR.x}, ${TR.y}, ${TR.x}, ${TR.y + R}, `;
				path += `L ${BR.x}, ${BR.y - R}, Q ${BR.x}, ${BR.y}, ${BR.x - R}, ${BR.y} Q ${BL.x}, ${BL.y}, ${BL.x}, ${BL.y - R} z`

                return path;
            }
        }


		let chart = c3.generate( {
			bindto: '#cl-chart__combination',

			data: {
				columns: [
					[ 'data1', 200, 130, 90, 240, 130, 220 ],
					[ 'data2', 200, 130, 90, 240, 130, 220 ],
				],
				types: {
					data1: 'bar',
					data2: 'line',
				},

                color:(color, data )=>{
					const {id, index} = data;

					switch( id ){
						case 'data1':
							return ( index === selectedIndex )?'#80D6FF':'#D1D1D1';
							break;

						case 'data2':
                            if( data.id_org ) return '#9B9B9B';
                            return ( index === selectedIndex )?'#1052a5':'#FFFFFF';
							break;
					}
				},
			},
			axis: {
				x: {
					tick: {
						format: x => {
							let num = (x + startNum)%12;
							if( num === 0 ) return '12월';
							else			return `${num}월`;
						},
					},
				},
				y: {
					show: false
				}
			},
			legend: {
				show: false
			},
			tooltip: {
				show: false
			},
			size: {
				height: 160
			},
            point: {
                r: 8,
				tick: 2,
            },
			bar: {
				width: 14,
			}
		} )

	}

	render () {
		return (
			<div id="cl-chart__combination" className="cl-chart__combination"/>
		);
	}
}

export default ChartCombination;
