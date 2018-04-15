import React, {Component} from 'react';
import MapLink from "../ui/MapLink";
import classNames from 'classnames';
import BranchList from "../ui/BranchList";
import Store from '../../scripts/store';

class BranchSeletor extends Component {

    constructor(props) {
        super(props);

    }

    onChangeBranch=( item )=>{
        this.selectedBranchId = item.cmplxId;
    }

    selectBranch=()=>{
        Store.communityCmplxId = this.selectedBranchId;
        Store.popDrawer();
    }


    render() {

        return <div className="drawer-fitted-box--b cl-branch-selector cl-bg--dark">
                <div className="cl-bg--black30">
                    <div className="cl-card-item--dark">
                        <h4 className="color-white fs-16">
                            역삼동 하우징
                            <span className="cl-desc ml-1em">(서울시 서초구 강남대로 373)</span>
                        </h4>
                    </div>
                </div>

                <div>
                    <h5 className="color-white fs-14 ml-2em mt-1em">서울(Seoul)</h5>
                    <BranchList className="pt-2em" defaultValue={ Store.communityCmplxId } onChange={ this.onChangeBranch }/>
                </div>


                <footer className="cl-opts__footer cl-flex">
                    <button onClick={ Store.popDrawer }>
                        <span className="cl-bold">취소</span>
                    </button>


                    <button className="ml-auto pr-1em" onClick={ this.selectBranch }>
                        <span className="color-primary cl-bold">선택</span>
                    </button>
                </footer>
            </div>
    }
}


export default BranchSeletor;
