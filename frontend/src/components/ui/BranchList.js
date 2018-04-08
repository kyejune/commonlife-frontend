import React, {Component} from 'react';
import classNames from "classnames";
import Net from "../../scripts/net";
import MapLink from "./MapLink";

class BranchList extends Component {


    constructor(props) {
        super(props);
        this.state = {
            items: [],
        }
    }


    componentDidMount() {
        Net.getBranch(data => {

            // common life 먼저 나오게 배열 정렬
            data.sort( (a,b)=>{
                if( a.cmplxGrp === 'COMMON Life' ) return -1;
                else if( b.cmplxGrp === 'COMMON Life') return 1;
                else return 0;
            });

            this.setState({ items: data  });
        });
    }


    onClickBranch = (item) => {
        if (this.props.onChange)
            this.props.onChange( item );
    }


    render() {

        let {value} = this.props;
        let {items} = this.state;

        console.log( value );
        if( !value ) value = { cmplxId: -1 }; // 기본값

        return <ul className="cl-branch-list pt-3em">
            {items.map((item, index) => {
                return <li key={index}
                           className={classNames("cl-branch-item", {"cl--selected": item.cmplxId === value })}>
                    <div className="cl-flex h-100">
                        <button onClick={() => this.onClickBranch(item)} className="cl-flex text-left">
                            <div className="cl-avatar-border">
                                {item.clLogoImgSrc &&
                                <img className="cl-avatar" src={item.clLogoImgSrc} alt="이미지"/>
                                }
                            </div>
                            <h5>{item.cmplxNm}</h5>
                            <p>{item.addr}</p>
                        </button>

                        <MapLink href={item.clMapSrc} className="mt-auto"/>
                    </div>
                </li>
            })}
        </ul>
    }
}


export default BranchList;
