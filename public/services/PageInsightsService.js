import Reqwest from 'reqwest';

export default {

    getPages:() => {
        return Reqwest({
            url: '/api/pages',
            contentType: 'text/json',
            method: 'get'
        })
    },
    
    getPageInsights:(page, from, to) => {
        return Reqwest({
            url: '/api/page-insights/' + page + '?from=2016-04-25&to=2016-06-12', // TODO handle from/to params
            contentType: 'text/json',
            method: 'get'
        })
    }
};