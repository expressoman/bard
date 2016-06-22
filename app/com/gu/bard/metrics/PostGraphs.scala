package com.gu.bard.metrics

import com.gu.bard.models._
import com.gu.bard.settings.{ GraphSettings, MetricSettings }
import com.restfb.json.JsonObject
import com.restfb.types.{ Insight, Post }
import com.typesafe.scalalogging.StrictLogging

/**
 * Provides functions for the computation of Post metrics in graph form for rendering.
 */
class PostGraphs(graphSettingsMap: Map[String, GraphSettings], posts: Seq[Post], weekRanges: Seq[WeekRange]) extends StrictLogging with Graphing {

  def totalPostsPerDay: Option[Graph] = {
    val GraphSettingsKey = "totalPosts"
    getGraph(GraphSettingsKey)
  }

  // TODO - This is pretty much the same now as in PageInsightsGraphs.
  private def getGraph(graphSettingsKey: String, graphDataFn: Seq[Post] => Seq[GraphDataValue] = this.graphDataFn) = {

    def createMetric(metricName: String, metricSettings: MetricSettings) = {
      val graphData = GraphData(values = graphDataFn(posts))
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

  private def graphDataFn: Seq[Post] => Seq[GraphDataValue] = {
    def dateSource(post: Post): Long = post.getCreatedTime.toInstant.toEpochMilli
    def total(data: Seq[Post]) = data.size.toString

    graphDataValues[Post, Long](weekRanges, _: Seq[Post])(dateSource(_))(total(_))
  }

}
