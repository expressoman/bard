package com.gu.bard.metrics

import com.gu.bard.models._
import com.gu.bard.settings.{ GraphSettings, MetricSettings }
import com.restfb.types.{ Post }
import com.typesafe.scalalogging.StrictLogging

/**
 * Provides functions for the computation of Post metrics in graph form for rendering.
 */
class PostGraphs(graphSettingsMap: Map[String, GraphSettings], posts: Seq[Post], weekRanges: Seq[WeekRange]) extends StrictLogging with Graphing {

  def totalPostsPerDay: Option[Graph] = {
    val GraphSettingsKey = "totalPosts"
    getGraph(graphSettingsMap.get(GraphSettingsKey))
  }

  override def createMetric(metricName: String, metricSettings: MetricSettings) = {
    val graphData = GraphData(values = graphDataFn(posts))
    Metric(metricSettings, graphData)
  }

  private def graphDataFn: Seq[Post] => Seq[GraphDataValue] = {
    def dateSource(post: Post): Long = post.getCreatedTime.toInstant.toEpochMilli
    def total(data: Seq[Post]) = data.size.toString

    graphDataValues[Post, Long](weekRanges, _: Seq[Post])(dateSource(_))(total(_))
  }

}
