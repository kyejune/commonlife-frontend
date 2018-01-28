import Store from 'scripts/store.js';
import axios from 'axios';

export default {

    /* Community */
    getFeed(){
        axios.get( '/dummy/feeds.json' )
            .then( response => {
				Store.feed = response.data;
            } );
    },

    getNews(){
		axios.get( '/dummy/news.json' )
			.then( response => {
				Store.news = response.data;
			} );
    },

    getEvent(){
		axios.get( '/dummy/events.json' )
			.then( response => {
				Store.event = response.data;
			} );
    },

    /* 글보기 */
    getCardContent( type, index, callback ){
        axios.get( '/dummy/feed.json' )
            .then( response => {
                callback( response.data[0] );
                } );
    },

    /* Likey */
    getLikey( id, callback ) {
        axios.get( '/dummy/likey.json' )
            .then( response => {
            	callback( response.data );
				// Store.likey = response.data;
            } );
    }

};