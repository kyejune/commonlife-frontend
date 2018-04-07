import React, {Component} from 'react';
import {Link} from "react-router-dom";
import Iot from "../../../scripts/iot";
import IconLoader from "../../ui/IconLoader";
import newAutomationSrc from 'images/etc-2@3x.png';

class Automations extends Component {

    constructor(props) {
        super(props);
        this.props.setBackPath('/iot');

        this.state = {
            items: [],
            editable: false,
        }
    }

    componentDidMount() {
        this.loadData();
    }

    loadData=()=>{
        Iot.getScenarioes(data => {
            this.setState({items: data.data});
        });
    }

    onRemoveItem( item ){
        Iot.removeAutomation( item.scnaId, this.loadData );
    }


    render() {

        const List = this.state.items.map(item => {
            return <li key={item.scnaId}>
                <IconLoader src={item.imgSrc}/>
                <h4 className="cl__title">{item.scnaNm || 'scnaNm 안들어옴'}</h4>

                {this.state.editable?
                    <button className="cl-delete__button ml-auto" onClick={ ()=> this.onRemoveItem( item ) }/>
                    :
                    <Link to={`/iot/scenario/${item.scnaId}`} className="ml-auto cl-ctrl__button"/>
                }
            </li>
        });


        return <div>

            <header className="cl-mode-setting__header">
                <Link to={'/iot/scenario/add'} className="cl-iot-mode__button cl-iot-mode__button--expand">
                    <img src={newAutomationSrc} alt="아이콘이미지"/>
                    <span className="cl-name ml-2em">자동화 만들기</span>
                </Link>
            </header>

            <div className="cl-flex fs-14 ml-1em mr-1em mt-1em mb-1em">
                <h5 className="fs-14 cl-bold">자동화 <span className="color-primary">{ List.length }</span></h5>

                <button className="ml-auto cl--underline" onClick={ ()=> this.setState({ editable: !this.state.editable }) }>
                    목록 편집
                </button>
            </div>


            <ul className="cl-iot-vertical-list cl-bg--transparent">
                {List}
            </ul>

        </div>
    }
}

    export default Automations;
