import React from 'react';
import { Link } from 'react-router';

export default class NavigationComponent extends React.Component {

    constructor(props) {
        super(props);
    }

    render () {
        
        const pages = this.props.data.map( page => {
            let route = '/page-insights/' + page + '/last-4-weeks'; // default to last 4 weeks
            return (
                <li key={page}><Link to={route}>{page}</Link></li>
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
                        <a className="navbar-brand" href="#">Bard</a>
                    </div>
                    <div id="navbar" className="navbar-collapse collapse">
                        <ul className="nav navbar-nav">
                            <li className="dropdown">
                                <a href="#" className="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Page Insights<span className="caret"></span></a>
                                <ul className="dropdown-menu">
                                    <li className="dropdown-header">Select page: </li>
                                    {pages}
                                </ul>
                            </li>
                        </ul>
                    </div>
            </nav>
        );
    }
}