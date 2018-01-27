import { observable } from 'mobx';
import { Drawer } from 'react-md';

const Store = observable({

    /* Community별 CardItem 데이터 */
    feed: [],
    event: [],
    news: [],

    /* 현재 열려있는 Drawer */
    drawer: '',
    drawerOptions: null,

    /* Drawer에 기본적으로 넘겨주는 파라미터 */
    customDrawerProps: {
        type:Drawer.DrawerTypes.TEMPORARY,
        onVisibilityChange: ()=>{},
        defaultMedia: 'mobile',
        portal: true,
        position: 'right',
    },

});



export default Store;