import React, { Component } from 'react';
import checkSrc from 'images/ic-check@3x.png';
import addSrc from 'images/add-light@3x.png';
import RightArrowSrc from "images/ic-arrow-right@3x.png";
import CheckRowList from "../../ui/CheckRowList";
   
class IotSensorEditor extends Component {

    constructor(props){
        super(props);

        this.state = {
            items: [{name:'특정시간'}, {name:'특정온도'}, {name:'특정일자'}],
            isMultipleCheck:false,
        }
    }

    onChangeCheckList =( indexes )=>{
        this.setState({
            isMultipleCheck: indexes.length > 0,
        })
    }

    render() {
        return (
            <div className="cl-bg--dark">

                <CheckRowList
                    onChange={ this.onChangeCheckList }
                    items={this.state.items}
                />



                <button className="cl-iot-add__button--light">
                    <img src={addSrc} alt="센서추가" width="40" height="40"/>
                    <p>IoT센서추가</p>
                </button>

                {this.state.isMultipleCheck ?

                    <footer className="cl-opts__footer cl-flex">
                        <span className="name--strong">삭제</span>
                        <img className="ml-auto opacity-70" src={RightArrowSrc} alt=">" width="30"/>
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

    
export default IotSensorEditor;
