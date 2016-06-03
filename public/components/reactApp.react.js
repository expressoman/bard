import React from 'react';

export default class ReactApp extends React.Component {
    constructor(props) {
        super(props);

    }

    render() {
        return (
            <div id="wrapper">
                {this.props.children}
            </div>
        );
    }
}