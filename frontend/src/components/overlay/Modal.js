import { Component } from 'react';
import ReactDOM from 'react-dom';


class Modal extends Component{

    constructor(props) {
        super(props);
        this.el = document.createElement('div');

        console.log('created modal');
    }

    componentDidMount() {
        document.querySelector('#modal').appendChild(this.el);
    }

    componentWillUnmount() {
        document.querySelector('#modal').removeChild(this.el);
    }

    render() {

        return ReactDOM.createPortal(
            this.props.children,
            this.el,
        );
    }
}

export default Modal