import React, { Component } from 'react';
import c3 from 'c3';


class ChartCombination extends Component {

	componentDidMount () {

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
				colors: {
					data1: '#1654A3',
					data2: '#000000',
				}
			},
			axis: {
				x: {
					tick: {
						values: [ 12, 1, 2, 3, 4, 5 ],
					}
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
			bar: {
				width: 14,
			},
		} )

	}

	render () {
		return (
			<div id="cl-chart__combination" className="cl-chart__combination"/>
		);
	}
}

export default ChartCombination;
