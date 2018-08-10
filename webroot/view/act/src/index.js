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
import Instance from './component/Instance.jsx';
import History from './component/History.jsx';
const pageRoute = (
    <Router>
        <App>
             <Route exact path="/" component={List} />
             <Route exact path="/definition" component={List} />
             <Route exact path="/instance" component={Instance} />
             <Route exact path="/history" component={History} />
        </App>
    </Router>
);

ReactDOM.render(pageRoute, document.getElementById('root'));
registerServiceWorker();
