import React, {Component} from 'react';
import IconLoader from "../ui/IconLoader";

class NotificationCenter extends Component {

    constructor(props) {
        super(props);

        this.state = {
            tabIndex: 0
        }
        this.props.updateTitle('12개의 신규 알림');
    }

    onClickTab( index ){
        this.setState({tabIndex: index});
    }


    render() {
        return <div className="cl-notification-center">
            <div className="cl-iot-control-tab">
                {['일반', '예약', 'IoT', '주차' ].map( (item, index)=>{
                    return <button key={index} className={`cl-iot-control-tab__item${index===this.state.tabIndex?'--active':''}`}
                            onClick={ ()=>this.onClickTab( index ) }>
                        {item}
                    </button>
                })}
            </div>


            <div>
                <ul className="cl-iot-vertical-list cl-bg--dark">

                    <li className="cl--new">
                        <IconLoader src={undefined}/>
                        <div>
                            <h4 className="cl__title">노티제목</h4>
                            <p className="cl__desc mt-02em">노티 구구절절</p>
                        </div>

                        <div className="ml-auto">
                            <p className="cl__desc">
                                2017.3.12 <br/> AM 05:00
                            </p>
                        </div>
                    </li>

                    <li>
                        <IconLoader src={undefined}/>
                        <div>
                            <h4 className="cl__title">노티제목</h4>
                            <p className="cl__desc mt-02em">
                                노티 구구절절<br/>
                                예약내용 구구절절
                            </p>
                        </div>

                        <button className="ml-auto cl-next__button--gray" />
                    </li>



                </ul>
            </div>

        </div>
    }
}


export default NotificationCenter;
