import React, {Component} from 'react';
import {withRouter} from 'react-router';
import { Button, Drawer, Toolbar} from "react-md";


class People extends Component{

    render(){

        return <div>
                {this.props.match.params.id }번 아이디 게시물의 사람,사람,사람,,, 을 출력해주세여.
            </div>
    }
}


export default withRouter(People);