import React from 'react';
import { Route, IndexRoute } from 'react-router';
import ReactApp from '../components/reactApp.react';
import Home from '../components/home.react';
import PageInsightsController from '../components/pageInsightsController.react';

export default [
    <Route path="/" component={ReactApp}>
        <Route name="pageinsights" path="/page-insights/:page/:timeframe" component={PageInsightsController} />
        <IndexRoute component={Home}/>
    </Route>
];