import React, { Component } from 'react';
import Net from "../../../scripts/net";
import Link from "react-router-dom/es/Link";

class Status extends Component {


    constructor(props) {
        super(props);
        this.state = {
            branch:'',
            desc:'',
            list:[],
        }
    }

    componentDidMount(){
        Net.getInfoSubjectListOf('status', res=>{
            console.log( 'status:', res );
            this.setState({
                branch: res.cmplxNm,
                desc: `${res.headNm} 세대`,// | ${}동 ${}호`,
                list: res.infoList,
            })
        });
    }


    render() {
        return <div className="cl-info-status">

            <li className="cl-card-item">
                <h4 className="cl-name">{this.state.branch}</h4>
                <p className="cl-desc">
                    {this.state.desc}
                </p>
            </li>

            <ul className="cl-status-vertical-list">

                {this.state.list.map( (item, key)=>{
                  return <li key={key}>
                      <Link className="cl-flex w-100" to={`/info/status/${item.myStatusIdx}`}>
                          <h4 className="cl__title">{item.myStatusNm}</h4>
                          <div className="cl-next__button ml-auto"/>
                      </Link>
                  </li>
                })}

            </ul>

        </div>
    }
}


export default Status;
