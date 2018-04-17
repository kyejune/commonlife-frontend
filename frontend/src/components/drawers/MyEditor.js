import React, {Component} from 'react';
import {observer} from "mobx-react";
import { MyIots } from 'scripts/iot';
import Link from "react-router-dom/es/Link";
import RightArrowSrc from "images/ic-arrow-right@3x.png";
import classNames from "classnames";
import Iot from "scripts/iot";
import EditableList from "../ui/EditableList";
import addIotSrc from 'images/add-iot@3x.png';
import addInfoSrc from 'images/add-info@3x.png';
import addAutoSrc from 'images/add-auto@3x.png';
import {withRouter} from "react-router-dom";
import {observe} from "mobx/lib/mobx";


/* 모드 목록 (편집용) */
class MyEditor extends Component {

    constructor(props) {
        super(props);
        this.state = {
            checks:[], // 삭제용 btId저장용
            deviceIds:[], // 링크용
            lastDeviceId:null,
            items:[],
        };

        observe( MyIots, change=>{
            if( this.state.items.length === 0 )
                this.setState({ items:MyIots });
            return change;
        } );
    }

    onCheck = ( values )=>{

        let list = values.map( item=>{
           return  item.btId;
        });

        let ds = values.map( item=>{
           return item.deviceId;
        });

        this.setState( {
            checks: list,
            deviceIds: ds,
        } );
    }

    onAlign =( values )=>{
        const alignMap = values.map( ( values, index ) => {
            return { btId:values.btId, sortOrder: index + 1 }
        });

        this.setState({ items: values });

        Iot.reAlignMyIot( alignMap, ()=>{
            console.log('정렬 성공');
        });
    }


    removeItem=()=>{
        Iot.removeMyIot( this.state.checks, ()=>{
           this.setState({
               checks:[],
           });
        });
    }

    render() {

        const items = this.state.items.map( (m, index) =>{
            return { idx: index, name: m.btTitle, imgSrc:m.btImgSrc,  ...m }
        });

        console.log('items:', items );

        // if( MyIots.length === 0 ) return <div/>;

        const selectedLen = this.state.checks.length;

        return (
            <div className="pb-3em">
                <EditableList items={ items }
                              strongdark
                              onCheck={ this.onCheck } clear={ selectedLen === 0 }
                              onAlign={ this.onAlign }
                />


                <div className="cl-iot-expose-link-group">
                    <Link to={`${this.props.location.pathname}/add-device`} className="cl-iot-add__button--light">
                        <img src={addIotSrc} alt="노출될 기기 추가" width="40" height="40"/>
                        <p>IoT기기 추가</p>
                    </Link>

                    <Link to={`${this.props.location.pathname}/add-auto`} className="cl-iot-add__button--light">
                        <img src={addAutoSrc} alt="센서추가" width="40" height="40"/>
                        <p>자동화 추가</p>
                    </Link>

                    <Link to={`${this.props.location.pathname}/add-info`} className="cl-iot-add__button--light">
                        <img src={addInfoSrc} alt="센서추가" width="40" height="40"/>
                        <p>가치정보 추가</p>
                    </Link>
                </div>



                <footer className={ classNames( "cl-opts__footer", {"cl-opts__footer--hide":selectedLen === 0 })  }>

                    <div>
                        {/*{selectedLen===1&&*/}
                        {/*<Link to={`/iot/my/device/${this.state.deviceIds[0]}`} className="cl-flex mb-03em">*/}
                            {/*<span className="name--strong">상세설정 변경</span>*/}
                            {/*<img className="ml-auto opacity-70" src={RightArrowSrc} alt="다음" width="30"/>*/}
                        {/*</Link>*/}
                        {/*}*/}

                        {selectedLen >= 1 &&
                        <button className="cl-flex w-100" onClick={ this.removeItem }>
                            <span className="name--strong">삭제</span>
                            <img className="ml-auto opacity-70" src={RightArrowSrc} alt="다음" width="30"/>
                        </button>
                        }
                    </div>
                </footer>
            </div>
        )
    }
}

export default withRouter(observer(MyEditor));