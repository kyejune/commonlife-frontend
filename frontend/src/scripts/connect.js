
document.addEventListener('deviceready', function(){

    document.querySelector('body').classList.add('device--ready');

    if( window.Keyboard ) {
        window.Keyboard.shrinkView(true);
        window.Keyboard.hideFormAccessoryBar( false );
        window.Keyboard.disableScrollingInShrinkView( false );
    }


    window.addEventListener('keyboardHeightWillChange', function (event) {

        if(event.keyboardHeight > 0 ){

            document.querySelector('body').classList.add('cl-keyboard--open');
            // document.querySelectorAll('.cl-shrink').forEach( function( el, index ){
            //    el.style.height = 'calc( 96.1vh - ' + ( 56 + parseInt(event.keyboardHeight )) + 'px )';
            // });

        }else{
            document.querySelector('body').classList.remove('cl-keyboard--open');
        }

    });


}, false);
