import React, {Component} from 'react';
import IconLoader from "../../ui/IconLoader";
import Link from "react-router-dom/es/Link";

class LiOfCtrl extends Component {

    render() {

        const WrapClass = this.props.to ? 'a':'div';

        return (
            <li onClick={ this.props.onClick }>
                <WrapClass href={ '#' + this.props.to} className="w-100 cl-flex" >
                    <IconLoader className="cl__thumb--rounded" src={this.props.icon}/>
                    <div>
                        <h4 className="cl__title">{this.props.name}</h4>
                        {this.props.desc &&
                        <p className="cl__desc mt-05em">{this.props.desc}</p>
                        }
                    </div>

                    <span className="ml-auto cl-ctrl__button"/>
                </WrapClass>
            </li>
        );
    }
}


export default LiOfCtrl;
