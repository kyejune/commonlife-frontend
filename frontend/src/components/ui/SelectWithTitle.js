import React, {Component} from 'react';

/* 예약하기 메인 페이지 및 상세 페이지에서 좌측 타이틀과, 우측 셀렉박스 구성 */
class SelectWithTitle extends Component {


    render() {

        return <div className="cl-flex-between">
                <h5>그룹4</h5>
                <select name="" id="">
                    <option value="">1</option>
                    <option value="">2</option>
                    <option value="">3</option>
                    <option value="">4</option>
                </select>
            </div>
        
    }

}

export default SelectWithTitle