package com.gu.bard.metrics

import com.gu.bard.models.{ Graph, GraphDataValue, Metric, WeekRange }
import com.gu.bard.settings.{ GraphSettings, MetricSettings }
import com.typesafe.scalalogging.StrictLogging
import org.joda.time.DateTime

trait Graphing extends StrictLogging {

  def createMetric(metricName: String, metricSettings: MetricSettings): Metric

  def getGraph(maybeGraphSettings: Option[GraphSettings]) = {
    maybeGraphSettings map { graphSettings =>
      Graph.create(graphSettings, getMetricsForGraph(graphSettings))
    } orElse {
      logger.warn(s"Could not retrieve graph.")
      None
    }
  }

  def graphDataValues[T, U](weekRanges: Seq[WeekRange], data: Seq[T])(dateSource: T => U)(compute: Seq[T] => String): Seq[GraphDataValue] = {

    def inThisWeek(range: WeekRange, t: T) = range.isInThisWeek(date = new DateTime(dateSource(t)).toLocalDate)

    weekRanges map { range =>
      val dataForWeek = data.filter { datum => inThisWeek(range, datum) }
      GraphDataValue((compute(dataForWeek)).toString, range.toString)
    }
  }

  private def getMetricsForGraph(graphSettings: GraphSettings): Seq[Metric] = {
    val metricNames = graphSettings.metricSettings.map(_.fbMetricName)

    metricNames flatMap { metricName =>
      graphSettings.metricSettings
        .find(_.fbMetricName == metricName)
        .map { metricSettings => createMetric(metricName, metricSettings) }
    }
  }

}
