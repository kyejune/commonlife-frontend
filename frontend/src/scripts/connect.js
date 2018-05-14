
document.addEventListener('deviceready', function(){

    const DEVICE = window.device;
    document.querySelector('body').classList.add('device--ready' );
    document.querySelector('body').classList.add('platform--' + DEVICE.platform.toLowerCase());

    const MODEL = DEVICE.model.toLowerCase();
    if( MODEL.indexOf('iphone10') >= 0 )
        document.querySelector('body').classList.add('model--iphone10');
    else
        document.querySelector('body').classList.add('model--' + MODEL );


    if( window.Keyboard ) {
        window.Keyboard.shrinkView(true);
        window.Keyboard.hideFormAccessoryBar( false );
        window.Keyboard.disableScrollingInShrinkView( false );
    }

    let Badge = window.cordova.plugins.notification.badge;
    if( window.cordova.plugins.notification ){


        // Badge.configure({ autoClear: true });

        Badge.hasPermission(function (granted) {
            if( !granted ){
                Badge.requestPermission(function (granted) {
                    // alert( granted );
                });
            }
        });
    }

    //http://www.itpaper.co.kr/cordova-fcm-%ED%81%B4%EB%9D%BC%EC%9D%B4%EC%96%B8%ED%8A%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-%ED%91%B8%EC%8B%9C%EB%A9%94%EC%8B%9C%EC%A7%80/
    if( window.FCMPlugin ){

        alert('FCMPlugin START');

        window.FCMPlugin.getToken(function(token){
            alert( 'FCM: ' + token);
            // window.cordova.plugins.firebase.messaging.setBadge( 0 );
        });

        window.FCMPlugin.onTokenRefresh(function(token){
            // alert( 'Token Refresh:' + token );
        });

        window.FCMPlugin.onNotification(function(data){

            if(data.wasTapped){ // 알림창 클릭해서 들어온거
                //Notification was received on device tray and tapped by the user.
                // alert( JSON.stringify(data) );
                console.log( data );
            }else{
                //Notification was received in foreground. Maybe the user needs to be notified.
                // alert( JSON.stringify(data) );
                console.log( data );
            }
        });
    }

    window.addEventListener('keyboardHeightWillChange', function (event) {

        if(event.keyboardHeight > 0 ){
            document.querySelector('body').classList.add('cl-keyboard--open');
        }else{
            document.querySelector('body').classList.remove('cl-keyboard--open');
        }

    });

}, false);
