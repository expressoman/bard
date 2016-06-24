package com.gu.bard.metrics

import com.gu.bard.settings.{ GraphSettings, MetricSettings }
import com.typesafe.scalalogging.StrictLogging
import com.gu.bard.models._
import org.joda.time.DateTime

trait GraphHelper[A, U] {
  val graphWeekRanges: Seq[WeekRange]
  def getDataForMetric(metricName: String): Seq[U]
  def dateSource(u: U): DateTime
  def compute(computationName: String): Seq[U] => String
}

object Graphing extends StrictLogging {

  private def createMetric[A, U](
    metricName: String,
    metricSettings: MetricSettings,
    computationName: String
  )(implicit gh: GraphHelper[A, U]) = {
    val dataForMetric = gh.getDataForMetric(metricName)
    val values = graphDataValues[A, U](dataForMetric, computationName)

    Metric(metricSettings, GraphData(values))
  }

  def getGraph[A, U](
    maybeGraphSettings: Option[GraphSettings],
    computationName: String
  )(implicit gh: GraphHelper[A, U]) = {
    maybeGraphSettings map { graphSettings =>
      Graph.create(graphSettings, getMetricsForGraph(graphSettings, computationName))
    } orElse {
      logger.warn(s"Could not retrieve graph.")
      None
    }
  }

  private def graphDataValues[A, U](dataForMetric: Seq[U], computationName: String)(implicit gh: GraphHelper[A, U]): Seq[GraphDataValue] = {

    def inThisWeek(range: WeekRange, u: U) = range.isInThisWeek(date = gh.dateSource(u).toLocalDate)

    gh.graphWeekRanges map { range =>
      val dataForWeek = dataForMetric.filter { datum => inThisWeek(range, datum) }
      GraphDataValue((gh.compute(computationName)(dataForWeek)).toString, range.toString)
    }
  }

  private def getMetricsForGraph[A, U](
    graphSettings: GraphSettings,
    computationName: String
  )(implicit gh: GraphHelper[A, U]): Seq[Metric] = {
    val metricNames = graphSettings.metricSettings.map(_.fbMetricName)

    metricNames flatMap { metricName =>
      graphSettings.metricSettings
        .find(_.fbMetricName == metricName)
        .map { metricSettings => createMetric(metricName, metricSettings, computationName) }
    }
  }

}
