import React, {Component} from 'react';
import {Button} from 'react-md';
import WriteDrawer from "components/WriteDrawer";


class CommunityFeed extends Component{

    constructor( props ){
        super( props );

        this.state = {
            style:{
                height: 3000,
                width: '100%',
                backgroundColor: 'orange',
            },

            isWrite: false,
        }
    }

    onWriteChange( isWrite ){

        console.log( 'FEED change:', isWrite)

        this.setState({
            isWrite: isWrite,
        })
    }


    render(){
        return(
            <div className="cl-fitted-box">

                <div style={this.state.style}>
                    <h2>
                        커뮤니티 피드
                    </h2>

                    컨덴츠 내용
                </div>


                <Button floating primary
                        iconClassName="fa fa-pencil fa-2x"
                        className="cl-write__button--fixed"
                        onClick={()=>this.onWriteChange( true )}
                />

                <WriteDrawer
                    visible={this.state.isWrite}
                    onWriteChange={ ( value )=> this.onWriteChange( value ) }/>
            </div>
        )
    }
}

export default CommunityFeed;