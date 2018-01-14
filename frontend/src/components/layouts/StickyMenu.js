import React, {Component, ExecutionEnvironment} from 'react';

class StickyMenu extends Component{

    constructor( prop ){
        super( prop );

        this.state = {
            style:{ top:56 }
        }
    }

    componentDidMount() {
        document.addEventListener('scroll', this.handleScroll.bind(this) );
    }

    componentWillUnmount() {
        document.removeEventListener('scroll', this.handleScroll.bind(this) );
    }

    handleScroll(){
        this.setState({
            style:{
                top: Math.max( 56 - document.documentElement.scrollTop, 0 ),
            },
        });
    }

    render(){

        return(
            <div className="sticky-second-header md-tabs" style={this.state.style}>
                {this.props.children}
            </div>
        )
    }
}

export default StickyMenu;