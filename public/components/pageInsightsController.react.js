import React from 'react';
import Page from './page.react';
import PageInsightsService from '../services/PageInsightsService';

export default class PageInsightsController extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            pageInsightsData: undefined
        }
    }

    componentDidMount() {
        this.loadPageInsights(this.props.params.page, '', ''); // TODO - provide timeframe
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.params.page !== nextProps.params.page) {
            this.loadPageInsights(nextProps.params.page, '', ''); // TODO - provide timeframe
        }
    }

    loadPageInsights(page, from, to) {
        PageInsightsService.getPageInsights(page, from, to).then( response => {
            this.setState({pageInsightsData: response});
        });
    }

    render() {
        if (!this.state.pageInsightsData) { return false };

        return (
            <Page data={this.state.pageInsightsData} />
        );
    }
}