import React, {Component} from 'react';
import IconLoader from "../../ui/IconLoader";
import Link from "react-router-dom/es/Link";

class LiOfCtrl extends Component {
    render() {
        return (
            <li>
                <Link to={this.props.to || ''} className="w-100 cl-flex" >
                    <IconLoader className="cl__thumb--rounded" src={undefined}/>
                    <div>
                        <h4 className="cl__title">{this.props.name}</h4>
                        {this.props.desc &&
                        <p className="cl__desc mt-05em">{this.props.desc}</p>
                        }
                    </div>

                    <span className="ml-auto cl-ctrl__button"/>
                </Link>
            </li>
        );
    }
}


export default LiOfCtrl;
