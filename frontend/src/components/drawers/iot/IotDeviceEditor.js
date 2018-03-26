import React, { Component } from 'react';
import checkSrc from 'images/ic-check@3x.png';
import addSrc from 'images/add-light@3x.png';
import CheckRowList from "../../ui/CheckRowList";
import {withRouter} from 'react-router';
import Link from "react-router-dom/es/Link";
class IotDeviceEditor extends Component {

    constructor(props){
        super(props);

        this.state = {
            items: [],// [{name:'공부방 조명'}, {name:'주방조명'}, {name:'보일러'}],
            isMultipleCheck:false,
        }
    }

    onChangeCheckList =( indexes )=>{
        this.setState({
            isMultipleCheck: indexes.length > 0,
        })
    }

    render() {

        const {pathname} = this.props.location;

        return (
            <div className="cl-bg--dark">

                <CheckRowList
                    onChange={ this.onChangeCheckList }
                    items={this.state.items}
                />



                <Link to={`${pathname}/add-device`} className="cl-iot-add__button--light">
                    <img src={addSrc} alt="센서추가" width="40" height="40"/>
                    <p>IoT 기기 추가</p>
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

    
export default withRouter(IotDeviceEditor);
