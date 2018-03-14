import React, {Component} from 'react';
import IconLoader from 'components/ui/IconLoader';


/*
* 상세제어,
* 진입경로: MyIot, MyIot추가,
* */
class IotDeviceDetail extends Component {
    render() {
        return <div>
            <div className="cl-flex">
                <IconLoader src={"undefined"} className="mr-auto"/>
                <p>실내온도 39도씨</p>
            </div>

            {/* 적용되는 옵션들을 밑에 주르륵....*/}
            <ul>
                <li>
                    토글
                </li>

                <li>
                    슬라이더
                </li>

                <li>
                    등등
                </li>
            </ul>


            <footer>
                <button className="ml-auto">확인</button>
            </footer>

        </div>
    }
}


export default IotDeviceDetail;
