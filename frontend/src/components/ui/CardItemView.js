import React, {Component} from 'react';


class CardItemView extends Component{


    render(){
        return <div className="cl-fixed-page">
            <header
                className="md-paper md-toolbar md-background--primary md-toolbar--text-white md-toolbar--fixed">

                <button type="button"
                        className="md-btn md-btn--icon md-pointer--hover md-inline-block md-btn--toolbar md-toolbar--action-left">
                    <div className="md-ink-container"></div>
                    {/*<img src={logo} alt="로고 이미지"/>*/}
                </button>

                <select className="md-title md-title--toolbar">
                    <option value="">새글 보기</option>
                </select>

                <div className="md-cell--right md-toolbar--action-right">
                    <div className="md-layover md-layover--simplified md-inline-block md-menu-container">
                        <button type="button"
                                className="md-btn md-btn--icon md-pointer--hover md-inline-block md-btn--toolbar">
                            {/*<img src={alert} alt="알림 이미지"/>*/}
                        </button>
                    </div>
                </div>

            </header>

            <div className="cl-content">
                카드 아이템 뷰어
            </div>
        </div>
    }
}


export default CardItemView;