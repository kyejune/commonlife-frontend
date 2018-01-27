import Store from 'scripts/store.js';

export default {

    /* Community */
    getFeed(){
        setTimeout( ()=>{
            Store.feed = feed;
        }, 1000 );
    },


    getNews(){
        setTimeout( ()=>{
            Store.news = news;
        }, 1000 );
    },



    getEvent(){
        setTimeout( ()=>{
            Store.event = event;
        }, 1000 );
    },


    /* 글보기 */
    getCardContent( type, index, callback ){
        setTimeout( ()=>{
            callback( feed[index] );
        }, 500 );
    }

};




/*  임시 데이터 */
const feed =[
    {
        index: '1',
        type: 'news',
        author: {
            name: '김딸기',
            avatar: 'https://placeimg.com/100/100/people',
            place: '역삼하우스',
        },
        content: 'FEED 오늘의 뉴스 안녕하세요. 반갑습니다.',
        like_count: '10',
        created_at: '2018-01-09 00:00:00',
    },
    {
        index: '2',
        type: 'feed',
        author: {
            name: '김용',
            avatar: 'https://placeimg.com/100/100/people',
            place: '역삼하우스',
        },
        content: '안녕하세요. 반갑습니다.',
        thumbnail: 'https://placeimg.com/640/480/people',
        like_count: '10',
        event_title: '모임합니다',
        event_schedule: '2018-01-12 00:00:00',
        event_location: '모임공간',
        rsvp_count: '0',
        created_at: '2018-01-09 00:00:00',
    },
    {
        index: '3',
        type: 'event',
        author: {
            name: '한초코',
            avatar: 'https://placeimg.com/100/100/people',
            place: '역삼하우스',
        },
        thumbnail: 'https://placeimg.com/640/480/people',
        like_count: '10',
        event_title: '모임합니다',
        event_schedule: '2018-01-12 00:00:00',
        event_location: '모임공간',
        rsvp_count: '0',
        created_at: '2018-01-09 00:00:00',
    },
    {
        index: '4',
        type: 'event',
        author: {
            name: '조은풀',
            avatar: 'https://placeimg.com/100/100/people',
            place: '역삼하우스1',
        },
        thumbnail: 'https://placeimg.com/640/480/people',
        like_count: '11',
        event_title: '파티합니다',
        event_schedule: '2018-01-12 00:00:00',
        event_location: '파티룸',
        rsvp_count: '0',
        created_at: '2018-01-09 00:00:00',
    },
    {
        index: '6',
        type: 'feed',
        author: {
            name: '신쪼리',
            avatar: 'https://placeimg.com/100/100/people',
            place: '역삼하우스1',
        },
        content: '안녕하세요. 반갑습니다.1',
        thumbnail: 'https://placeimg.com/640/480/people',
        like_count: '11',
        event_title: '모임합니다1',
        event_schedule: '2018-01-12 00:00:00',
        event_location: '모임공간',
        rsvp_count: '0',
        created_at: '2018-01-09 00:00:00',
    }
];

const news = [
    {
        index: '1',
        type: 'news',
        author: {
            name: '김딸기',
            avatar: 'https://placeimg.com/100/100/people',
            place: '역삼하우스',
        },
        content: 'NEWS 오늘의 뉴스/안녕하세요. 반갑습니다.',
        like_count: '10',
        created_at: '2018-01-09 00:00:00',
    },
    {
        index: '2',
        type: 'news',
        author: {
            name: '민자몽',
            avatar: 'https://placeimg.com/100/100/people',
            place: '역삼하우스',
        },
        content: '오늘의 뉴스/오늘은 중요한날입니다.. 반갑습니다.1',
        like_count: '11',
        created_at: '2018-01-09 00:00:00',
    }
];



const event =  [
    {
        index: '3',
        type: 'event',
        author: {
            name: '한초코',
            avatar: 'https://placeimg.com/100/100/people',
            place: '역삼하우스',
        },
        thumbnail: 'https://placeimg.com/640/480/people',
        like_count: '10',
        event_title: 'EVENT 모임합니다',
        event_schedule: '2018-01-12 00:00:00',
        event_location: '모임공간',
        rsvp_count: '0',
        created_at: '2018-01-09 00:00:00',
    },
    {
        index: '4',
        type: 'event',
        author: {
            name: '조은풀',
            avatar: 'https://placeimg.com/100/100/people',
            place: '역삼하우스1',
        },
        thumbnail: 'https://placeimg.com/640/480/people',
        like_count: '11',
        event_title: '파티합니다',
        event_schedule: '2018-01-12 00:00:00',
        event_location: '파티룸',
        rsvp_count: '0',
        created_at: '2018-01-09 00:00:00',
    }
];