import React, { Component } from 'react';
import IconLoader from "../../ui/IconLoader";
import Link from "react-router-dom/es/Link";
   
class LiOfCtrl extends Component {
    render() {
        return (
            <li>
                <IconLoader className="cl__thumb--rounded" src={undefined}/>
                <div>
                    <h4 className="cl__title">{ this.props.name }</h4>
                    {this.props.desc&&
                    <p className="cl__desc mt-05em">{ this.props.desc }</p>
                    }
                </div>

                <Link to={ this.props.to || '' } className="ml-auto cl-ctrl__button" />
            </li>
        );
    }
}

    
export default LiOfCtrl;
