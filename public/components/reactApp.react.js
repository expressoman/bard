import React from 'react';
import PageInsightsService from '../services/PageInsightsService';
import Navigation from './navigation.react';


export default class ReactApp extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            pages: []
        };

        this.loadPages = this.loadPages.bind(this);
    }

    loadPages() {
        PageInsightsService.getPages().then(response => {
            this.setState({
                pages: response.pages
            });
        });
    }

    componentDidMount () {
        this.loadPages();
    }

    render() {
        return (
            <div id="wrapper">
                <Navigation data={this.state.pages} />
                {this.props.children}
            </div>
        );
    }
}