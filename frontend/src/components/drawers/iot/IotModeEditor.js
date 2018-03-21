import React, {Component} from 'react';
import {observer} from "mobx-react";
import { Modes } from 'scripts/iot';
import EditableList from "../../ui/EditableList";
import Link from "react-router-dom/es/Link";
import RightArrowSrc from "images/ic-arrow-right@3x.png";
import classNames from "classnames";
import Iot from "scripts/iot";

/* 모드 목록 (편집용) */
class IotModeEditor extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedId: null,
        }
    }

    componentWillUnmount(){
        Iot.getMode();
    }

    onCheck = ( values )=>{
        this.setState( { selectedId: values.length===0?null:values[0].mode });
    }

    onAlign =( values )=>{
        const alignMap = values.map( ( values, index ) => {
            return { seqNo:values.seqNo, sortOrder: index + 1 }
        });

        Iot.reAlignIotMode( alignMap, ()=>{
            console.log( '정렬 성공' );
        });
    }

    render() {

        const items = Modes.map( (mode, index) =>{
            return { idx: index, name: mode.modeNm, ...mode }
        });

        if( items.length === 0 ) return <div/>;

        return (
            <div>
                <EditableList items={ items } radio onCheck={ this.onCheck } onAlign={ this.onAlign } />

                <footer className={ classNames( "cl-opts__footer", {"cl-opts__footer--hide":this.state.selectedId === null })  }>
                    <Link to={`/iot/mode/${this.state.selectedId}`} className="cl-flex">
                        <span className="name--strong">상세설정 변경</span>
                        <img className="ml-auto opacity-70" src={RightArrowSrc} alt="다음" width="30"/>
                    </Link>
                </footer>
            </div>
        )
    }
}

export default observer(IotModeEditor);