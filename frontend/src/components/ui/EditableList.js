import React, {Component} from 'react';
import {arrayMove, SortableContainer, SortableElement, SortableHandle} from "react-sortable-hoc";
import Checkbox from "./Checkbox";
import SampleSrc from 'images/io-t-icon-1@3x.png';
import Link from "react-router-dom/es/Link";

class EditableList extends Component {


    constructor(props) {
        super(props);

        this.state = {
            items: this.props.items,
        }
    }

    onSortEnd = ({oldIndex, newIndex}) => {
        this.setState({
            items: arrayMove(this.state.items, oldIndex, newIndex),
        });
    }

    onCheckboxChange =( value, index )=>{
        console.log( value, index );
    }


    render() {

        const Handle = SortableHandle(() =>
            <span className="ml-auto cl__handle"/>
        );

        const SortableItem = SortableElement(({value }) =>
            <li>
                <Checkbox className="mr-1em" onCheckboxChange={ this.onCheckboxChange }/>
                {/*<Link to={value.to} className="cl-flex">*/}
                    <img className="cl__thumb--rounded" src={SampleSrc}/>
                    <div>
                        <h4 className="cl__title">{value.name}</h4>
                        {value.desc && <span className="cl__desc">{value.desc}</span>}
                    </div>
                {/*</Link>*/}
                <Handle/>
            </li>
        );

        const SortableList = SortableContainer(({items}) => {
            return (
                <ul className="cl-iot-vertical-list cl-bg--dark">
                    {items.map((value, index) => (
                        <SortableItem key={`item-${index}`} index={index} value={value}/>
                    ))}
                </ul>
            );
        });


        return (
            <SortableList items={this.state.items}
                          onSortEnd={this.onSortEnd}
                          lockToContainerEdges={true}
                          lockAxis="y"
                          helperClass="cl__list--dragging"
                          useDragHandle={true}
            />
        );
    }
}


export default EditableList;
