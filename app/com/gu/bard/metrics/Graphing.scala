package com.gu.bard.metrics

import com.gu.bard.settings.{ GraphSettings, MetricSettings }
import com.typesafe.scalalogging.StrictLogging
import com.gu.bard.models._
import org.joda.time.DateTime

trait Graphing[T] extends StrictLogging {

  trait GraphHelper[A, U] {
    def getDataForMetric(metricName: String): Seq[U]
    def dateSource(u: U): DateTime
    def compute(computationName: String): Seq[U] => String
  }

  val weekRanges: Seq[WeekRange]
  val metricData: Seq[T]

  def createMetric[T, U](
    metricName: String,
    metricSettings: MetricSettings,
    computationName: String
  )(implicit gh: GraphHelper[T, U]) = {
    val dataForMetric = gh.getDataForMetric(metricName)
    val values = graphDataValues[T, U](dataForMetric, computationName)

    Metric(metricSettings, GraphData(values))
  }

  def getGraph[T, U](
    maybeGraphSettings: Option[GraphSettings],
    computationName: String
  )(implicit gh: GraphHelper[T, U]) = {
    maybeGraphSettings map { graphSettings =>
      Graph.create(graphSettings, getMetricsForGraph(graphSettings, computationName))
    } orElse {
      logger.warn(s"Could not retrieve graph.")
      None
    }
  }

  def graphDataValues[T, U](dataForMetric: Seq[U], computationName: String)(implicit gh: GraphHelper[T, U]): Seq[GraphDataValue] = {

    def inThisWeek(range: WeekRange, u: U) = range.isInThisWeek(date = gh.dateSource(u).toLocalDate)

    weekRanges map { range =>
      val dataForWeek = dataForMetric.filter { datum => inThisWeek(range, datum) }
      GraphDataValue((gh.compute(computationName)(dataForWeek)).toString, range.toString)
    }
  }

  private def getMetricsForGraph[T, U](
    graphSettings: GraphSettings,
    computationName: String
  )(implicit gh: GraphHelper[T, U]): Seq[Metric] = {
    val metricNames = graphSettings.metricSettings.map(_.fbMetricName)

    metricNames flatMap { metricName =>
      graphSettings.metricSettings
        .find(_.fbMetricName == metricName)
        .map { metricSettings => createMetric(metricName, metricSettings, computationName) }
    }
  }

}
