import React, { Component } from 'react';
import c3 from 'c3';


class ChartGauge extends Component {

    constructor(props) {
        super(props);

        this.value = 60;
    }


    componentDidMount () {

    	const CommonProps = {
            gauge: { label: { show: false }, expand: false, width: 45 },
            size: { height: 180 },
            legend: { show: false },
            tooltip: { show: false },
            axis: { x: { show: false }}
		}

    	// 배경용
		c3.generate({
			...CommonProps,
            color: { pattern: ['#D1D1D1'] },
			bindto:'.cl-gauge-bg',
            data: { columns: [[ 'data', 100 ]], type: 'gauge'},
		});


		// 값 용
		c3.generate( {
			...CommonProps,
			bindto: '#cl-chart__gauge',
			data: {
				columns: [[ 'data', this.value ]],
				type: 'gauge',
			},

            color: {
                pattern: ['#80d6ff', '#42a5f5', '#1052a5', '#002b75', '#ff8c72', '#de4c35'],
                threshold: {
                    values: [ 20, 40, 60, 80, 90, 100 ]
                }
            }
		});

	}

	render () {


        // cl-gauge-arrow 는 -90 ~ 90 deg를 가짐
        const STYLE = { transform:`rotate(${ (this.value/100)*180 - 90 }deg)`};


		return <div className="cl-chart-gauge-container">
			<div className="cl-gauge-bg"/>
			<div id="cl-chart__gauge" className="cl-chart__gauge"/>
			<div className="cl-gauge-arrow" style={STYLE}/>
			<div className="cl-gauge-arrow-dot"/>
        </div>
	}
}

export default ChartGauge;
