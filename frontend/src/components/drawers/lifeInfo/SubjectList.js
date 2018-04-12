import React, { Component } from 'react';
import IconLoader from "../../ui/IconLoader";
import Link from "react-router-dom/es/Link";
import classNames from "classnames";
import {withRouter} from "react-router-dom";
import Net from "../../../scripts/net";

class SubjectList extends Component {


    constructor(props) {
        super(props);

        const type = props.match.params.cate;
        this.state = {
            data:[],
            type: type,
        };

        this.props.updateTitle(type==='guide'?'Living Guide':'Benefits');
        this.loadData( type );
    }

    loadData( type ){

        Net.getInfoSubjectListOf( type, data=>{
            this.setState({ data:data.data });
        });
    }

    render() {

        console.log( this.state.data );

        const List = this.state.data.map( (item, index)=>{
           return  <li key={index}>
               {this.state.type === 'benefits' &&
               <IconLoader src={item.imgSrc}/>
               }
               <h4 className={classNames("cl__title cl-ellipsis", this.state.type==="benefits"?'w-75':'w-90')}>
                   {item.itemNm}
               </h4>
               <Link className="cl-next__button ml-auto" to={`/info/${this.state.type}/${item.Itemid}`} />
           </li>
        });

        return (
            <ul className="cl-iot-vertical-list cl-subject-list">
                {List}

                {this.state.type === 'guide' &&
                <li className="cl-version">
                    <h4 className="cl__title">소프트웨어 버전</h4>
                    <p className="ml-auto">
                        <span className="cl-bold">1.0</span> / 2018.06.01
                    </p>
                </li>
                }
            </ul>
        );
    }
}


export default withRouter(SubjectList);
