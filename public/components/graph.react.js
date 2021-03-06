import React from 'react';
import Linechart from './charts/linechart.react';
import Barchart from './charts/barchart.react';

export default class Graph extends React.Component {

    constructor(props) {
        super(props);
    }

    renderGraph(graph) {
        if ('line' === graph.type) {
            return <Linechart data={graph} />
        } else if ('bar' === graph.type) {
            return <Barchart data={graph} />
        } else {
            return null
        }
    }

    render() {
        if (!this.props) { return false };

        const graph = this.props.data;

        return (
            <div className="col-md-6">
                <div className="panel panel-default">
                    <div className="panel-heading">
                        <strong>{graph.title}</strong>
                    </div>
                    <div className="panel-body">
                        {this.renderGraph(graph)}
                    </div>
                </div>
            </div>
        );
    }
}