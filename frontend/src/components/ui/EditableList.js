import React, {Component} from 'react';
import {arrayMove, SortableContainer, SortableElement, SortableHandle} from "react-sortable-hoc";
import Checkbox from "./Checkbox";
import SampleSrc from 'images/io-t-icon-1@3x.png';
import Link from "react-router-dom/es/Link";
import IconLoader from "./IconLoader";

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
        this.setState({
            items: arrayMove(this.state.items, oldIndex, newIndex),
        });

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

        console.log('this.state.checkeds: ', this.state.checkeds );

        const SortableItem = SortableElement(({ value }) =>
            <li>
                <Checkbox className="mr-1em" index={value.idx} value={ this.state.checkeds[value.idx] } onChange={ this.onCheckboxChange }/>
                {/*<Link to={value.to} className="cl-flex">*/}
                    <IconLoader className="cl__thumb--rounded" src={value.imgSrc}/>
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
