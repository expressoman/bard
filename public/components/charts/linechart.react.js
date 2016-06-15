import React from 'react';
import ChartistGraph from 'react-chartist';
import ChartistAxisTitle from 'chartist-plugin-axistitle';

export default class Linechart extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        if (!this.props) { return false };

        const graphData = this.props.data;

        const lineChartData = {
            labels: graphData.values.map( dataValues => { return dataValues.label }),
            series: [ graphData.values.map( dataValues => { return dataValues.dataPoint }) ]
        };

        const lineChartOptions = {
            low: 0,
            showArea: true,
            chartPadding: {top: 20, right: 20, bottom: 20, left: 20 },
            axisY: { onlyInteger: true },
            plugins: [
                ChartistAxisTitle({
                    axisX: {
                        axisTitle: graphData.graphDataSettings.axisXLabel,
                        axisClass: 'ct-axis-title',
                        offset: { x: 0, y: 50 },
                        textAnchor: 'middle'
                    },
                    axisY: {
                        axisTitle: graphData.graphDataSettings.axisYLabel,
                        axisClass: 'ct-axis-title',
                        offset: { x: 0, y: 0 },
                        textAnchor: 'middle',
                        flipTitle: false
                    }
                })
            ]
        };

        return (
            <ChartistGraph data={lineChartData} options={lineChartOptions} type={'Line'} />
        );
    }
}