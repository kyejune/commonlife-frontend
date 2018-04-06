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
import BranchSeletor from "./BranchSeletor";
import NotificationCenter from "./NotificationCenter";
import ConditionalExpression from "./ConditionalExpression";
import TimeSelector from "../ui/TimeSelector";
import TimesSelector from "../ui/TimesSelector";
import IotDeviceOfScan from "./iot/IotDeviceOfScan";



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

            {/*  글쓰기 */}
            <BottomDrawer visible={Store.hasDrawer('write')} renderNode={document.querySelector('.App')}
                          onVisibilityChange={()=>{}}
                          portal={true}>
                <DrawerContentHolder title="새글쓰기" drawer="write" close >
                    <WriteDrawer/>
                </DrawerContentHolder>
            </BottomDrawer>


            {/* 지점 셀랙터 */}
            <BottomDrawer visible={Store.hasDrawer('branch')} renderNode={document.querySelector('.App')}
                          onVisibilityChange={()=>{}}
                          portal={true}>
                <DrawerContentHolder title="지점 선택" drawer="write" close >
                    <BranchSeletor/>
                </DrawerContentHolder>
            </BottomDrawer>


            {/* 센서 편집 - 조건식 */}
            <DrawerWrapper drawer="edit-sensor-if" className="cl-bg--lightgray" close >
                <ConditionalExpression/>
            </DrawerWrapper>

            {/* 센서 편집 - 특정시간 */}
            <DrawerWrapper drawer="edit-sensor-time" className="cl-bg--lightgray"  close >
                <TimeSelector/>
            </DrawerWrapper>

            {/* 센서 편집 - 구간시간 */}
            <DrawerWrapper drawer="edit-sensor-duration" className="cl-bg--lightgray"  close >
                <TimesSelector/>
            </DrawerWrapper>

            {/* 모드-> 기기편집 */}
            <DrawerWrapper drawer="edit-scan-device" title="기기정보" className="cl-bg--lightgray"  close >
                <IotDeviceOfScan/>
            </DrawerWrapper>




            {/* 신규 알림... */}
            <DrawerWrapper drawer="notifications">
                <NotificationCenter/>
            </DrawerWrapper>


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