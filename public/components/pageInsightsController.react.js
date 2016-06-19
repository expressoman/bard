import React from 'react';
import Page from './page.react';
import PageInsightsService from '../services/PageInsightsService';
import Loader from 'react-loader';

export default class PageInsightsController extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            loaded: false,
            pageInsightsData: undefined
        }
    }

    componentDidMount() {
        this.loadPageInsights(this.props.params.page, this.props.params.timeframe);
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.params !== nextProps.params) {
            this.loadPageInsights(nextProps.params.page, nextProps.params.timeframe);
        }
    }

    loadPageInsights(page, timeframe) {
        /* Means spinner will show when changing between page insight pages. */
        this.setState({loaded: false, pageInsightsData: this.state.pageInsightsData});
        PageInsightsService.getPageInsights(page, timeframe).then( response => {
            this.setState({loaded: true, pageInsightsData: response});
        });
    }

    render() {
        return (
            <Loader loaded={this.state.loaded} lines={13} length={20} width={10} radius={30}
                corners={1} rotate={0} direction={1} color="#000" speed={1}
                trail={60} shadow={false} hwaccel={false} className="spinner"
                zIndex={2e9} top="50%" left="50%" scale={1.00}
                loadedClassName="loadedContent" >

                <Page data={this.state.pageInsightsData} />
            </Loader>
        );
    }
}