import React from 'react';
import {timeframes} from '../constants/timeframes';
import { Link } from 'react-router';



export default class PageNavigationComponent extends React.Component {

    constructor(props) {
        super(props);
    }

    render () {

        const timeframeLinks = timeframes.map ( timeframe => {
            let page = this.props.data.fbPageName;
            let key = page + '-' + timeframe.queryParamValue;
            let route = '/page-insights/' + page +  '/' + timeframe.queryParamValue;
            return (
                <li key={key}><Link to={route}>{timeframe.prettyName}</Link></li>
            );
        });

        return (
            <nav className="navbar navbar-default">
                <div className="navbar-header">
                    <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span className="sr-only">Toggle navigation</span>
                        <span className="icon-bar"></span>
                        <span className="icon-bar"></span>
                        <span className="icon-bar"></span>
                    </button>
                    <a className="navbar-brand" href="#">
                        <span className="label label-default">{this.props.data.prettyPageName}</span>
                    </a>
                </div>
                <div id="navbar" className="navbar-collapse collapse">
                    <ul className="nav navbar-nav navbar-right">
                        <li className="dropdown">
                            <a href="#" className="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Select time frame <span className="caret"></span></a>
                            <ul className="dropdown-menu">
                                <li className="dropdown-header">Timeframe: </li>
                                {timeframeLinks}
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
        );
    }
}