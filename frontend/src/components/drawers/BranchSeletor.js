import React, {Component} from 'react';
import MapLink from "../ui/MapLink";
import classNames from 'classnames';

class BranchSeletor extends Component {

    constructor(props) {
        super(props);
        this.state = {
            index: 0,
        }
    }

    onClickBranch( idx ){
        this.setState({
           index: idx
        });
    }


    render() {

        let data = [ {}, {}, {}, {}, {}, {}, {}, {} ];

        const List = data.map( (item, index)=>{
            return <li key={index}
                       className={classNames("cl-branch-item", {"cl--selected":index===this.state.index })}
                       onClick={ ()=> this.onClickBranch( index ) }
            >
                <div className="cl-avatar-border">
                    {index < 5 &&
                    <img className="cl-avatar"
                         src="http://cfile8.uf.tistory.com/image/22429C35576C07E91CF009"
                         alt="이미지"/>
                    }
                </div>
                <h5>무슨무슨 하우스</h5>
                <p>서울시 서초구 강남대로 373</p>
                <MapLink/>
            </li>
        });


        return <div className="drawer-fitted-box--b">
                <div className="cl-status-vertical-list">
                    <div className="cl-card-item--dark">
                        <h4 className="cl-name">
                            역삼동 하우징
                            <span className="cl-desc">(서울시 서초구 강남대로 373)</span>
                        </h4>
                        <p className="cl-desc">
                            101동 1002호 <span>조성우 세대</span>
                        </p>
                    </div>
                </div>

                <div>
                    <h5 className="color-white fs-14 ml-2em mt-1em mb-1em">서울(Seoul)</h5>

                    <ul className="cl-branch-list">
                        {List}
                    </ul>

                </div>


                <footer className="cl-opts__footer cl-flex">
                    <button>
                        <span className="cl-bold">선택</span>
                    </button>


                    <button className="ml-auto pr-1em">
                        <span className="color-primary cl-bold">선택</span>
                    </button>
                </footer>
            </div>
    }
}


export default BranchSeletor;
