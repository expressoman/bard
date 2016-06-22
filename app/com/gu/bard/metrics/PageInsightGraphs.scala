package com.gu.bard.metrics

import com.gu.bard.models._
import com.gu.bard.settings.{ GraphSettings, MetricSettings }
import com.restfb.json.JsonObject
import com.restfb.types.Insight
import com.typesafe.scalalogging.StrictLogging

import scala.collection.JavaConverters._

/**
 * Provides functions for the computation of Insight metrics in graph form for rendering.
 */
class PageInsightGraphs(graphSettingsMap: Map[String, GraphSettings], pageInsights: Seq[Insight], weekRanges: Seq[WeekRange]) extends StrictLogging with Graphing {

  def totalPostLikeReactions: Option[Graph] = {
    val GraphSettingsKey = "totalPostLikeReactions"
    getGraph(GraphSettingsKey)
  }

  object combinedGraphs {

    def totalNewPeopleWhoLikeAndUnlike: Option[Graph] = {
      val GraphSettingsKey = "totalNewPeopleWhoLikeAndUnlike"
      getGraph(GraphSettingsKey)
    }

  }

  // TODO - This is pretty much the same now as in PostGraphs.
  private def getGraph(graphSettingsKey: String, graphDataFn: Seq[JsonObject] => Seq[GraphDataValue] = this.graphDataFn) = {

    def createMetric(metricName: String, metricSettings: MetricSettings) = {
      val insights = pageInsights.find(_.getName == metricName).map(_.getValues.asScala.toList).toList.flatten
      val graphData = GraphData(values = graphDataFn(insights))

      Metric(metricSettings, graphData)
    }

    val maybeGraph = graphSettingsMap.get(graphSettingsKey) map { graphSettings =>
      val metricNames = graphSettings.metricSettings.map(_.fbMetricName)

      val metrics = metricNames flatMap { metricName =>
        graphSettings.metricSettings
          .find(_.fbMetricName == metricName)
          .map { metricSettings => createMetric(metricName, metricSettings) }
      }

      Graph.create(graphSettings, metrics)
    }

    maybeGraph orElse {
      logger.warn(s"Could not retrieve graph with graphSettingKey: $graphSettingsKey")
      None
    }

  }

  private def graphDataFn: Seq[JsonObject] => Seq[GraphDataValue] = {
    def dateSource(json: JsonObject) = json.getString("end_time")
    def sum(data: Seq[JsonObject]) = data.map(_.getInt("value")).sum.toString

    graphDataValues[JsonObject, String](weekRanges, _: Seq[JsonObject])(dateSource(_))(sum(_))
  }

}
