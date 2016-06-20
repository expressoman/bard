import R from 'ramda';
import Moment from 'moment';
import {timeframes} from '../constants/timeframes';


export default {

    getToFromDates:(timeframeValue) => {

        function formattedDate(minusWeeks) {
            return Moment().startOf('isoweek').subtract(minusWeeks, 'week').format('YYYY-MM-DD')
        }

        const matchedTimeframe = timeframes.filter( timeframe => {
            return timeframeValue === timeframe.queryParamValue
        });
        
        const timeframe = R.defaultTo('', R.head(matchedTimeframe));

        let decideStartDate = R.cond([
            [R.equals("last-week"),        R.always(formattedDate(1))],
            [R.equals("last-two-weeks"),   R.always(formattedDate(2))],
            [R.equals("last-four-weeks"),  R.always(formattedDate(4))],
            [R.equals("last-eight-weeks"), R.always(formattedDate(8))],
            [R.T,                          R.always(formattedDate(4))]
        ]);

        return {
            from : decideStartDate(timeframe.queryParamValue),
            to : Moment().format('YYYY-MM-DD')
        }

    }

}
