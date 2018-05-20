
import Store from 'scripts/store.js';
import Net from "./net";

const Push = {

    init(){

        console.log('push init' );

        if( window.FCMPlugin ) {
            window.FCMPlugin.getToken(Push.gettedToken);
            window.FCMPlugin.onTokenRefresh(Push.gettedToken);
            window.FCMPlugin.onNotification(Push.gettedMessage);
        }
    },


    gettedToken(token){
        if( token !== null ) {
            Store.firebaseToken = token;
            console.log( 'GET TOKEN:' + token );
            //alert('GET TOKEN:' + token);

            Net.sendFirebaseToken();
        }
    },

    gettedMessage(data){

        if(data.wasTapped){ // 알림창 클릭해서 들어온거
            console.log( data );
            Store.pushDrawer( 'notifications' );
        }else{
            //Notification was received in foreground. Maybe the user needs to be notified.
            // alert( JSON.stringify(data) );
            console.log( data );
            document.querySelector('#noti-toast').classList.add('cl--show');
            document.querySelector('#noti-toast p').innerHTML = data.aps.alert.body || '알림';

            setTimeout( ()=>{
                document.querySelector('#noti-toast').classList.remove('cl--show');
            }, 3000 );

        }
    }

}

export default Push;