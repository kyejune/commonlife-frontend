/* DrawerContentHolder.jsx */
import React, {Component} from 'react';
import {Button, Toolbar} from 'react-md';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';
import Store from "scripts/store";
import queryString from "query-string";
import closeSrc from 'images/back@3x.jpg';

class DrawerContentHolder extends Component {

    constructor( props ){
        super( props );

        this.state = {
            title: props.title,
        }
    }


    onClose(){

        let paths = this.props.location.pathname.split('/');
            paths.length -= 2;
        this.props.history.replace(paths.join('/'));

        Store.drawer = '';
    }

    updateTitle( title ){
        this.setState({
            title: title,
        })
    }


    render() {

        let CustomHeader, CloseButton;
        if( this.props.match.params.drawer === 'like' ){
            /* LIKE용 헤더 */
            CustomHeader = <div>
                <span>LIKE</span>
                <span>{ queryString.parse(this.props.location.search).count }</span>
            </div>
        }

        if( this.props.back ){

            CloseButton = <Button onClick={ ()=> this.onClose() } flat className="cl-back__button">
                <img src={ closeSrc } alt="이전" width="40" height="40"/>
            </Button>

        }else{

            CloseButton = <Button icon onClick={ ()=> this.onClose() }>close</Button>;
        }


        const { children } = this.props;

        let childrenWithProps = React.Children.map( children, child =>
            React.cloneElement(child, { updateTitle: title=> this.updateTitle(title) }));

        return <div className="cl-drawer-content-holder">

                <Toolbar
                    colored
                    fixed
                    nav={CloseButton}
                    title={ this.state.title }
                    titleClassName="cl-ellipsis"
                    children={ CustomHeader }
                />

                <section className="dialogs__content">

                    {childrenWithProps}

                </section>

            </div>
    }
}

export default observer( withRouter(DrawerContentHolder) );