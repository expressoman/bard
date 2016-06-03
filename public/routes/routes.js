import React from 'react';
import { Route, IndexRoute } from 'react-router';
import ReactApp from '../components/reactApp.react';
import Home from '../components/home.react';

export default [
    <Route path="/" component={ReactApp}>
        <IndexRoute component={Home}/>
    </Route>
];