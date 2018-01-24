import { observable } from 'mobx';

const Store = observable({

    /* Community별 CardItem 데이터 */
    feed: [],
    event: [],
    news: [],

    /* 현재 열려있는 Drawer */
    drawer: '',


});



export default Store;