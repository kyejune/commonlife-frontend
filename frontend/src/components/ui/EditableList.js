import React, {Component} from 'react';
import {arrayMove, SortableContainer, SortableElement, SortableHandle} from "react-sortable-hoc";
import Checkbox from "./Checkbox";
import IconLoader from "./IconLoader";
import classNames from "classnames";

class EditableList extends Component {


    constructor(props) {
        super(props);

        this.state = {
            items: props.items.slice(),
            checkeds:[],
        }
    }

    componentWillReceiveProps(nextProps){
        let p = { ...nextProps };

        if( nextProps.clear )
            p['checkeds'] = [];

        this.setState( p );
    }

    onSortEnd = ({oldIndex, newIndex}) => {
        console.log( '정렬:', oldIndex, newIndex );

        let newItems = arrayMove(this.state.items, oldIndex, newIndex);
        this.setState({
            items: newItems,
        });

        console.log( 'NEW ITEMS:', newItems );

        if( this.props.onAlign ) {
            this.props.onAlign( this.state.items );
        }


    }

    onCheckboxChange =( bool, index )=>{
        let a;

        if( this.props.radio ) a = [];
        else                   a = this.state.checkeds.slice();

        a[index] = bool?this.state.items[index]:undefined;
        this.setState( { checkeds: a } );


        if( this.props.onCheck ) {
            let cks = [];
            this.props.items.forEach( (item, idx)=>{
                if( a[idx] ) cks.push( item );
            });

            this.props.onCheck( cks );
        }
    }


    render() {

        const Handle = SortableHandle(() =>
            <span className="ml-auto cl__handle"/>
        );


        const SortableItem = SortableElement(({ value }) => {

            // console.log( value.name, value.idx, this.state.items, this.state.checkeds );

            return <li>
                <Checkbox className="mr-1em" index={value.idx} checked={this.state.checkeds[value.idx]}
                          onChange={this.onCheckboxChange}/>
                {/*<Link to={value.to} className="cl-flex">*/}
                <IconLoader className="cl__thumb--rounded" src={value.imgSrc}/>
                <div>
                    <h4 className="cl__title">{value.name}</h4>
                    {value.desc && <span className="cl__desc">{value.desc}</span>}
                </div>
                {/*</Link>*/}
                <Handle/>
            </li>
        });

        const SortableList = SortableContainer(({items}) => {

            console.log( items, this.state.checkeds );

            return (
                <ul className={ classNames(
                    "cl-iot-vertical-list",
                    (this.props.strongdark?'cl-bg--strongdark':'cl-bg--dark')
                ) }>
                    {items.map((value, index) => (
                        <SortableItem key={`item-${index}`} index={index} value={value}/>
                    ))}
                </ul>
            );
        });

        console.log( 'item:', this.state.items );


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
