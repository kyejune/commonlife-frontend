import React, {Component} from 'react';
import {observer} from "mobx-react";
import { Modes } from 'scripts/iot';
import EditableList from "../../ui/EditableList";
import Link from "react-router-dom/es/Link";


/* 모드 목록 (편집용) */
class IotModeEditor extends Component {

    constructor(props) {
        super(props);
        this.state = {}
    }

    onCheck =( values )=>{
        console.log( 'onchecked:', values );
    }

    onAlign =( values )=>{
        console.log( 'onSwap:', values );
    }

    render() {


        const items = Modes.map( (mode, index) =>{
            return { idx: index, name: mode.modeNm, ...mode }
        });

        if( items.length === 0 ) return <div/>;

        return (
            <div>
                <EditableList items={ items } radio onCheck={ this.onCheck } onAlign={ this.onAlign } />

                <footer>
                    <Link to="/iot/mode/0">상세</Link>
                </footer>
            </div>
        )
    }
}

export default observer(IotModeEditor);