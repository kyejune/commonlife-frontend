import { observable } from 'mobx';
import addSrc from 'images/img-bt-gray@3x.png';

const Store = observable({

    /* Community */
    feed: [],
    event: [],
    news: [],

});

export default Store;