import React, {Component} from 'react';
import {arrayMove, SortableContainer, SortableElement, SortableHandle} from "react-sortable-hoc";
import Checkbox from "./Checkbox";
import SampleSrc from 'images/io-t-icon-1@3x.png';
import Link from "react-router-dom/es/Link";
import IconLoader from "./IconLoader";

class CheckRowList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            checkedIndexes:[]
        }
    }


    onCheckboxChange = (bool, index) => {
        let a = this.state.checkedIndexes.slice();
        const idx = this.state.checkedIndexes.indexOf( index );
        if( bool && idx < 0 ) a.push( index );
        else if( !bool && idx >= 0 ) a.splice( idx, 1 );

        this.setState({
            checkedIndexes: a,
        });

        if( this.props.onChange )
            this.props.onChange( a );
    }

    render() {

        const List = this.props.items.map((item, index) => {
            return <li key={index}>
                <Checkbox index={index} onChange={this.onCheckboxChange}/>
                <IconLoader className="ml-09em cl__thumb--rounded" src={undefined}/>
                <h4 className="cl__title">{item.name}</h4>
            </li>
        });

        return (
            <ul className="cl-iot-vertical-list cl-bg--dark">
                {List}
            </ul>
        );
    }
}


export default CheckRowList;
