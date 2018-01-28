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
        setTimeout( ()=>{
            callback( feed[index] );
        }, 500 );
    }

};
