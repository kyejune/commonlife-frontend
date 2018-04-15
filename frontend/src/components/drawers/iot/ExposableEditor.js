import React, { Component } from 'react';
import {withRouter} from "react-router-dom";
import Iot from "../../../scripts/iot";
import LiOfCheck from "./LiOfCheck";
import checkSrc from 'images/ic-check@3x.png';
   
class ExposableEditor extends Component {


    constructor(props) {
        super(props);

        const { option1 } = this.props.match.params;
        this.state = {
            target: option1.replace('add-', ''), // auto, info
            data: [], // 목록 리스트 데이터
            selects:[],//myIotId
        }
    }


    componentWillMount(){
        this.loadData();
    }

    loadData(){
        Iot.getExposableListOfDashboard( this.state.target === 'auto'?'automation':'valueInfo', data=>{
            // console.log( '노출 할수 있는 목록:', data );

            this.setState({
                data: data,
            })
        });
    }

    onChangeCheck=( bool, data )=>{
        let selects = this.state.selects;
        let idx = selects.indexOf( data.data.myIotId );
        if( bool && idx < 0 ) selects.push( data.data.myIotId );
        else if( !bool && idx >= 0 ) selects.splice( idx, 1 );

        // console.log( 'new selects:', selects );

        this.setState({
            selects: selects,
        });
    }

    exposeItem=()=>{
        // 26번 호출
        Iot.setExposeItemOfDashboard( this.state.selects, ()=>{
            this.loadData();
        });
    }


    render() {


        const List = this.state.data.map( (item, index) =>{

            console.log( " :", this.state.selects, item.myIotId );

            return <LiOfCheck key={index} name={item.mNm} icon={item.bgImgSrc} data={item}
                              checked={ this.state.selects.indexOf(item.myIotId) >= 0 }
                              onChange={this.onChangeCheck}/>
        });


        return (
            <div className="cl-bg--iot pb-3em">

                <ul className="cl-iot-vertical-list">
                    {List}
                </ul>


                <footer className="cl-opts__footer">
                    <button className="ml-auto cl-flex mr-1em" onClick={ this.exposeItem }>
                        <img src={checkSrc} alt="확인" width="28" height="28"/>
                        <span className="color-primary ml-03em">확인</span>
                    </button>
                </footer>
            </div>
        );
    }
}

    
export default withRouter(ExposableEditor);
