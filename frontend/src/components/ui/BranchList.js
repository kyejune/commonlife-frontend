import React, {Component} from 'react';
import classNames from "classnames";
import Net from "../../scripts/net";
import MapLink from "./MapLink";
import {MakingUserData} from "../../scripts/store";
import {observer} from "mobx-react";
import Store from "../../scripts/store";


/* Store.isAuthorized 로 로그인 여부를 판단해서 가입단계에서 사용될때와, 가입 후 지점 선택시 사용될때를 구분 */
class BranchList extends Component {


    constructor(props) {
        super(props);
        this.state = {
            items: [],
            selectedId: String(props.defaultValue)
        }
    }


    componentDidMount() {

        // 전체목록이나 내가 속한 지점만 가져오기
        Net.getBranch( !Store.isAuthorized, data => {

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
        this.setState( { selectedId: item.cmplxId.toString() });
        if( this.props.onChange )
            this.props.onChange( item );
        // if( Store.isAuthorized ){
        //
        // }else{
        //     MakingUserData.branch = item;
        // }
    }


    render() {
        let {items} = this.state;

        // let selectedId;
        // if( Store.isAuthorized )
        //     selectedId = Store.communityCmplxId;
        // else
        //     selectedId = MakingUserData.branch.cmplxId;
        //
        // console.log("선택된 지점 아이디:", selectedId );



        return <ul className={ "cl-branch-list " + this.props.className }>
            {items.map((item, index) => {
                return <li key={index}
                           className={classNames("cl-branch-item", {"cl--selected": item.cmplxId.toString() === this.state.selectedId })}>
                    <div className="cl-flex h-100">
                        <button onClick={() => this.onClickBranch(item)} className="cl-flex text-left w-100">
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


export default observer(BranchList);
