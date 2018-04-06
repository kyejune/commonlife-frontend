import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import 'scripts/connect.js';
import registerServiceWorker from './registerServiceWorker';

if( window.location.protocol === 'file:' ) {
    const head = document.querySelector( 'head' );
    const cordova = document.createElement( 'script' );
    cordova.src = 'cordova.js';
    head.appendChild( cordova );
}

ReactDOM.render(<App />, document.getElementById('root'));
registerServiceWorker();



