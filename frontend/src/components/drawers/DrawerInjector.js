import React, {Component} from 'react';
import {withRouter} from 'react-router';
import Store from "../../scripts/store";
import {Drawer} from 'react-md';
import DrawerContentHolder from 'components/drawers/DrawerContentHolder';
import Profile from "components/drawers/Profile";
import Modal from "../overlay/Modal";
import IotProgressOverlay from "../overlay/IotProgressOverlay";
import IotModeChange from "../overlay/IotModeChange";
import {observer} from "mobx-react";



class DrawerInjector extends Component{

    constructor(props){
        super(props);

        this.state = {
            drawer:[],
        };

        setTimeout( ()=> this.updateRoute(), 0 );
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location) this.updateRoute();
    }

    updateRoute(){

        // 프로필 페이지
        if( this.props.location.pathname.match(/\/profile\/\d/) )
            Store.drawer.push( 'profile' );


        this.setState({
            drawer: Store.drawer,
        });

    }


    render(){

        return <div className="drawerInjector">

            <Drawer {...Store.customDrawerProps} renderNode={document.querySelector('.App')}
                    visible={this.state.drawer.indexOf('profile') >= 0}>
                <DrawerContentHolder back title="프로필">
                    <Profile/>
                </DrawerContentHolder>
            </Drawer>



            {/* 모달 - IotProgressOverlay */}
            {Store.ipo&&
            <Modal>
                <IotProgressOverlay { ...Store.ipo }  />
            </Modal>
            }

			{/* 모달 - IotModeChange */}
            {Store.imc&&
            <Modal>
                <IotModeChange { ...Store.imc }  />
            </Modal>
            }


        </div>
    }

}


export default withRouter(observer(DrawerInjector))