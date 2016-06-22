import React from 'react';
import Graph from "./graph.react.js";
import PageNavigation from "./pageNavigation.react";


export default class Page extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        if (!this.props) { return false };

        const graphs = this.props.data.graphs.map( graph => {
            let key = graph.metrics[0].metricSettings.fbMetricName;
            return <Graph key={key} data={graph} />
        });

        return (
            <div className="page-wrapper">
                <div className="container-fluid">
                    <div className="row">
                        <PageNavigation data={this.props.data} />
                        <div className="col-md-3">
                        </div>
                        <div className="col-md-3">
                        </div>
                        <div className="col-md-3">
                        </div>
                        <div className="col-md-3">
                        </div>
                    </div>
                    <div className="row">
                        {graphs}
                    </div>
                </div>
            </div>
        );
    }
}