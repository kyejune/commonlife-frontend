import React, { Component } from 'react';
import { withRouter } from "react-router-dom";
import TagComponent from "../../ui/TagComponent";
import Net from "../../../scripts/net";

class ContentPage extends Component {

    constructor(props) {
        super(props);

        const { cate, option1 } = props.match.params;
        this.props.updateTitle( cate==='benefit'?'Benefits 상세보기':'유용한 정보');

        this.state = {
            desc:'',
            imageInfo:[],
        }
    }

    componentDidMount(){
        const { cate, option1 } = this.props.match.params;

        Net.getInfoDetailOf( cate, option1, res=>{
            console.log( res );
            this.setState( res );
        } );
    }


    render() {

        let Img;
        if( this.state.imageInfo.length > 0 ){
            const Src = this.state.imageInfo[0].mediumPath;
            Img = <img width="100%" src={Src} alt="본문 이미지"/>
        }

        return (
            <div className="pt-1em pr-08em pl-08em fs-15 pb-1em">
                <TagComponent content={this.state.desc}/>
                {Img}
            </div>
        );
    }
}

export default withRouter(ContentPage);
