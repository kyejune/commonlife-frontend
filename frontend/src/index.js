import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import 'scripts/connect.js';
import registerServiceWorker from './registerServiceWorker';

try {
    document.write('<script src="cordova.js"></script>');
}
catch (error) {
    console.warn('cannot load cordova.js');
}


ReactDOM.render(<App />, document.getElementById('root'));
registerServiceWorker();



