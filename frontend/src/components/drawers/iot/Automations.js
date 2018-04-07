import React, { Component } from 'react';
import {Link} from "react-router-dom";
import Iot from "../../../scripts/iot";
import IconLoader from "../../ui/IconLoader";

class Automations extends Component {

    constructor(props) {
        super(props);
        this.props.setBackPath('/iot');

        this.state = {
            items:[],
        }
    }

    componentDidMount(){
        Iot.getScenarioes( data=>{
             this.setState({ items: data.data });
        });
    }


    render() {

        const List = this.state.items.map( item=>{
           return <li key={item.scnaId}>
               <IconLoader src={item.imgSrc}/>
               <Link to={`/iot/scenario/${item.scnaId}`}>
                   { item.scnaNm || 'scnaNm 안들어옴' }
               </Link>
           </li>
        });


        return (
            <div>


                <ul className="cl-iot-vertical-list">
                    {List}
                </ul>

                <br/><br/><br/>

                <Link to={'/iot/scenario/add'}>
                    생성
                </Link>


                <br/><br/>

            </div>
        );
    }
}


export default Automations;
