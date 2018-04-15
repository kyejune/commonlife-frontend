
document.addEventListener('deviceready', function(){

    document.querySelector('body').classList.add('device--ready' );
    document.querySelector('body').classList.add('platform--' + window.device.platform.toLowerCase());


    if( window.Keyboard ) {
        window.Keyboard.shrinkView(true);
        window.Keyboard.hideFormAccessoryBar( false );
        window.Keyboard.disableScrollingInShrinkView( false );
    }


    window.addEventListener('keyboardHeightWillChange', function (event) {

        if(event.keyboardHeight > 0 ){
            document.querySelector('body').classList.add('cl-keyboard--open');
        }else{
            document.querySelector('body').classList.remove('cl-keyboard--open');
        }

    });

}, false);
