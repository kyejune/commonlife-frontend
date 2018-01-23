import { observable } from 'mobx';
import addSrc from 'images/img-bt-gray@3x.png';

const Store = observable({

    /* Community별 CardItem 데이터 */
    feed: [],
    event: [],
    news: [],

    /* 현재 열려있는 Drawer */
    drawer: '',


});



export default Store;