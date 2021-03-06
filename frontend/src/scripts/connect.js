import Store from 'scripts/store.js';


document.addEventListener('deviceready', function(){

    console.log('deviceready', window.device );

    if( window.device ) {
        const DEVICE = window.device;
        document.querySelector('body').classList.add('device--ready');
        document.querySelector('body').classList.add('platform--' + DEVICE.platform.toLowerCase());

        const MODEL = DEVICE.model.toLowerCase();
        if (MODEL.indexOf('iphone10') >= 0)
            document.querySelector('body').classList.add('model--iphone10');
        else
            document.querySelector('body').classList.add('model--' + MODEL.replace(/\s/g, ''));

    }

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
