import React from 'react';
import Metric from "./metric.react";
import PageNavigation from "./pageNavigation.react";


export default class Page extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        if (!this.props) { return false };

        const metrics = this.props.data.metrics.map( metric => {
            return <Metric key={metric.metricSettings.fbMetricName} data={metric} />
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
                        {metrics}
                    </div>
                </div>
            </div>
        );
    }
}