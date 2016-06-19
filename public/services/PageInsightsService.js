import Reqwest from 'reqwest';
import TimeframeService from '../services/TimeframeService'


export default {

    getPages:() => {
        return Reqwest({
            url: '/api/pages',
            contentType: 'text/json',
            method: 'get'
        })
    },
    
    getPageInsights:(page, timeframe) => {

        const timeframeDates = TimeframeService.getToFromDates(timeframe);
        
        return Reqwest({
            url: '/api/page-insights/' + page + '?from=' + timeframeDates.from + '&to=' + timeframeDates.to,
            contentType: 'text/json',
            method: 'get'
        })
    }
};