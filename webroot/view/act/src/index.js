import React from 'react';
import ReactDOM from 'react-dom';

import {
    HashRouter as Router,
    Route
} from 'react-router-dom';


import 'antd/dist/antd.css';
import './index.css';
import './App.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';

import List from './component/List.jsx';

const pageRoute = (
    <Router>
        <App>
             <Route exact path="/" component={List} />
        </App>
    </Router>
);

ReactDOM.render(pageRoute, document.getElementById('root'));
registerServiceWorker();
