import React, {Component} from 'react';
import IconLoader from "../../ui/IconLoader";
import Store from "../../../scripts/store";

class LivingSupportCategory extends Component {


    viewWrite( index ){

        Store.pushDrawer('write', { type:'support', index:0 });
    }

    render() {
        return (
            <div className="cl-support cl-bg--light">
                <header>
                    <h4>무엇을 도와드릴까요?</h4>
                    <p>관리자에게 의견을 남겨주세요.(카테고리 선택 후 글 작성)</p>
                </header>

                <ul className="cl-iot-vertical-list cl-bg--transparent">
                    <li>
                        <IconLoader icon={undefined}/>
                        <h4 className="cl__title">빌딩관리</h4>
                        <button className="cl-next__button ml-auto" onClick={ ()=> this.viewWrite(0) }/>
                    </li>

                    <li>
                        <IconLoader icon={undefined}/>
                        <h4 className="cl__title">편의시설</h4>
                        <button className="cl-next__button ml-auto" onClick={ ()=> this.viewWrite(1) }/>
                    </li>

                    <li>
                        <IconLoader icon={undefined}/>
                        <h4 className="cl__title">예약</h4>
                        <button className="cl-next__button ml-auto" onClick={ ()=> this.viewWrite(2) }/>
                    </li>

                    <li>
                        <IconLoader icon={undefined}/>
                        <h4 className="cl__title">포인트</h4>
                        <button className="cl-next__button ml-auto" onClick={ ()=> this.viewWrite(3) }/>
                    </li>

                    <li>
                        <IconLoader icon={undefined}/>
                        <h4 className="cl__title">청구</h4>
                        <button className="cl-next__button ml-auto" onClick={ ()=> this.viewWrite(4) }/>
                    </li>

                    <li>
                        <IconLoader icon={undefined}/>
                        <h4 className="cl__title">온라인 시스템</h4>
                        <button className="cl-next__button ml-auto" onClick={ ()=> this.viewWrite(5) }/>
                    </li>
                </ul>

            </div>
        );
    }
}


export default LivingSupportCategory;
