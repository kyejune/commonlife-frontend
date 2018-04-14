import React, {Component} from 'react';
import nl2br from 'react-nl2br';
import Net from "../../scripts/net";

class PrivatePolicy extends Component {


    constructor(props) {
        super(props);
        this.state = {
            content:'',
        };

        Net.getPolicy( data=>{
           this.setState( { content: data.content });
        });
    }




    render() {

        return <div className="cl-private-policy">
            <h3 className="color-white cl-bold mb-1em">개인정보 취급방침</h3>

            <p>{nl2br( this.state.content )}</p>
        </div>
    }
}


export default PrivatePolicy;
