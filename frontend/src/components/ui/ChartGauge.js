import React, { Component } from 'react';
import c3 from 'c3';


class ChartGauge extends Component {

	componentDidMount () {

		let chart = c3.generate( {
			bindto: '#cl-chart__gauge',
			data: {
				columns: [
					[ 'data', 90 ]
				],
				type: 'gauge',
			},
			gauge: {
				label: {
					show: false
				},
				expand: false,
//    min: 0, // 0 is default, //can handle negative min e.g. vacuum / voltage / current flow / rate of change
//    max: 100, // 100 is default
//    units: ' %',
//    width: 39 // for adjusting arc thickness
			},
			colors: {
				data: '#1654A3'
			},
			size: {
				height: 180
			},
			legend: {
				show: false
			},
			tooltip: {
				show: false
			},
			axis: {
				x: {
					show: false
				}
			},
		} )

	}

	render () {
		return (
			<div id="cl-chart__gauge" className="cl-chart__gauge"/>
		);
	}
}

export default ChartGauge;
