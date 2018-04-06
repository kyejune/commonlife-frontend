import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import 'scripts/connect.js';
import registerServiceWorker from './registerServiceWorker';

if( window.location.protocol === 'file:' ) {
    document.write('<script src="cordova.js"></script>');
}

ReactDOM.render(<App />, document.getElementById('root'));
registerServiceWorker();



