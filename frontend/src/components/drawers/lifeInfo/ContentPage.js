import React, { Component } from 'react';
import { withRouter } from "react-router-dom";
import TagComponent from "../../ui/TagComponent";

class ContentPage extends Component {

    constructor(props) {
        super(props);

        const { cate, option1 } = props.match.params;
        this.props.updateTitle( cate==='benefit'?'Benefits 상세보기':'유용한 정보');

        this.state = {
            content:'',
        }
    }

    componentDidMount(){
        // Net.getInfoDetailOf( cate, option1, res=>{
        //
        // } );

        const { cate, option1 } = this.props.match.params;
    }


    render() {

        return (
            <div className="pt-1em pr-08em pl-08em fs-15">
                <TagComponent content={this.state.content}/>
            </div>
        );
    }
}

export default withRouter(ContentPage);
