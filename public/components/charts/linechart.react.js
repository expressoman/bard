import React from 'react';
import ChartistGraph from 'react-chartist';
import ChartistAxisTitle from 'chartist-plugin-axistitle'
import ChartistPluginLegend from 'chartist-plugin-legend'
import ChartistPluginTooltip from 'chartist-plugin-tooltip'
import ChartService from '../../services/ChartService'

export default class Linechart extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        if (!this.props) { return false };

        const graph = this.props.data;

        /* Whether we are showing one data series or two on a single graph the labels will be the same. */
        const graphLabels = ChartService.labels(graph.metrics[0].graphData);
        const graphData = graph.metrics.map(metric => metric.graphData.values);
        const series = graphData.map( values =>values.map( value => value.dataPoint) );

        const lineChartData = {
            labels: graphLabels,
            series: series
        };

        const lineChartOptions = {
            low: graph.low,
            showArea: true,
            chartPadding: {top: 20, right: 20, bottom: 20, left: 20 },
            axisY: { onlyInteger: true },
            plugins: [
                ChartistAxisTitle({
                    axisX: {
                        axisTitle: graph.axisXLabel,
                        axisClass: 'ct-axis-title',
                        offset: { x: 0, y: 50 },
                        textAnchor: 'middle'
                    },
                    axisY: {
                        axisTitle: graph.axisYLabel,
                        axisClass: 'ct-axis-title',
                        offset: { x: 0, y: 0 },
                        textAnchor: 'middle',
                        flipTitle: false
                    }
                }),
                ChartistPluginLegend({
                    legendNames: graph.metrics.map(metric => metric.metricSettings.prettyFbMetricName ),
                }),
                ChartistPluginTooltip({
                    anchorToPoint: true
                })
            ]
        };

        return (
            <ChartistGraph data={lineChartData} options={lineChartOptions} type={'Line'} />
        );
    }
}