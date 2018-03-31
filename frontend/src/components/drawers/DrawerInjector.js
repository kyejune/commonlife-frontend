import React, {Component} from 'react';
import {withRouter} from 'react-router';
import Store from "../../scripts/store";
import Modal from "../overlay/Modal";
import IotProgressOverlay from "../overlay/IotProgressOverlay";
import IotModeChange from "../overlay/IotModeChange";
import {observer} from "mobx-react";
import DrawerWrapper from "./DrawerWrapper";
import BottomDrawer from "./BottomDrawer";
import DrawerContentHolder from "./DrawerContentHolder";
import WriteDrawer from "./WriteDrawer";



class DrawerInjector extends Component{

    constructor(props){
        super(props);

        // this.state = {
        //     drawer:[],
        // };

        setTimeout( ()=> this.updateRoute(), 0 );
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location) this.updateRoute();
    }

    updateRoute(){

        // 프로필 페이지
        if( this.props.location.pathname.match(/\/profile\/\d/) )
            Store.pushDrawer( 'profile' );
    }


    render(){


        return <div className="drawerInjector">

            {/*<DrawerWrapper drawer="profile" title="프로필">*/}
                {/*<Profile/>*/}
            {/*</DrawerWrapper>*/}


            <BottomDrawer visible={Store.hasDrawer('write')} renderNode={document.querySelector('.App')}
                          onVisibilityChange={()=>{}}
                          portal={true}>
                <DrawerContentHolder title="새글쓰기" drawer="write" close >
                    <WriteDrawer/>
                </DrawerContentHolder>
            </BottomDrawer>


            {/* 모달 - IotProgressOverlay */}
            {Store.modeModal&&
            <Modal>
                <IotModeChange { ...Store.modeModal }  />
            </Modal>
            }

			{/* 모달 - IotModeChange */}
            {Store.myModal&&
            <Modal>
                <IotProgressOverlay { ...Store.myModal }  />
            </Modal>
            }


        </div>
    }

}


export default withRouter(observer(DrawerInjector))