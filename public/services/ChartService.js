import Moment from 'moment';

export default {

    labels:(graphData) => {

        const labelFormat = "DD/MM"

        const labels = graphData.values.map( dataValues => {
            let labelsArray = dataValues.label.split(" - ");
            let from = new Moment(labelsArray[0]).format(labelFormat);
            let to = new Moment(labelsArray[1]).format(labelFormat);
            let label = from + ' - ' + to;
            return label
        });

        return labels;
    }

}
