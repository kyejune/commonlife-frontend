import React, { Component } from 'react';
import checkSrc from 'images/ic-check@3x.png';
import addSrc from 'images/add-light@3x.png';
import RightArrowSrc from "images/ic-arrow-right@3x.png";
import CheckRowList from "../../ui/CheckRowList";
import Link from "react-router-dom/es/Link";
import {withRouter} from 'react-router';
   
class IotSensorEditor extends Component {

    constructor(props){
        super(props);

        this.state = {
            items: [],//[{name:'특정시간'}, {name:'특정온도'}, {name:'특정일자'}],
            isMultipleCheck:false,
        }
    }

    onChangeCheckList =( indexes )=>{
        this.setState({
            isMultipleCheck: indexes.length > 0,
        })
    }

    render() {
        const { pathname } = this.props.location;

        return (
            <div className="cl-bg--dark">

                {/* 생성하려고 모아둔 아이템 출력 */}
                <CheckRowList
                    onChange={ this.onChangeCheckList }
                    items={this.state.items}
                />



                <Link to={`${pathname}/add-sensor`} className="cl-iot-add__button--light">
                    <img src={addSrc} alt="센서추가" width="40" height="40"/>
                    <p>IoT 센서 추가</p>
                </Link>

                {this.state.isMultipleCheck ?

                    <footer className="cl-opts__footer cl-flex">
                        <span className="name--strong">삭제</span>
                        {/*<img className="ml-auto opacity-70" src={RightArrowSrc} alt=">" width="30"/>*/}
                    </footer>
                    :<footer className="cl-opts__footer">
                        <button className="ml-auto cl-flex mr-1em">
                            <img src={checkSrc} alt="확인" width="28" height="28"/>
                            <span className="color-primary ml-03em">확인</span>
                        </button>
                    </footer>
                }
            </div>
        );
    }
}

    
export default withRouter(IotSensorEditor);
