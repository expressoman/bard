import React from 'react';
import Linechart from './charts/linechart.react';
import Barchart from './charts/barchart.react';

export default class Metric extends React.Component {

    constructor(props) {
        super(props);
    }

    renderGraph(graphData) {
        if ('line' === graphData.graphDataSettings.type) {
            return <Linechart data={graphData} />
        } else if ('bar' === graphData.graphDataSettings.type) {
            return <Barchart data={graphData} />
        } else {
            return null
        }
    }

    render() {
        if (!this.props) { return false };

        const metric = this.props.data;
        const graphData = this.props.data.graphData;

        return (
            <div className="col-md-6">
                <div className="panel panel-default">
                    <div className="panel-heading">
                        <strong>Metric: </strong>{metric.metricSettings.title}
                        <br/>
                        <p><strong>Why is this important? </strong> {metric.metricSettings.description}</p>
                        <p><strong>What does success look like? </strong>{metric.metricSettings.whatsSuccess}</p>
                    </div>
                    <div className="panel-body">
                        {this.renderGraph(graphData)}
                    </div>
                </div>
            </div>
        );
    }
}