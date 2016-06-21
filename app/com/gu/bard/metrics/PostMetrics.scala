package com.gu.bard.metrics

import com.gu.bard.models.{ GraphDataValue, Metric }
import com.gu.bard.services.WeekRange
import com.gu.bard.settings.MetricSettings
import com.restfb.types.Post
import com.typesafe.scalalogging.StrictLogging

/**
 * Provides functions for the computation of Post metrics in graph form for rendering.
 */
object PostMetrics extends StrictLogging with Graphing {

  def totalPostsPerDay(metricSettings: Seq[MetricSettings], posts: Seq[Post], weekRanges: Seq[WeekRange]): Option[Metric] = {
    val fbMetricName = "total_posts"
    def total(data: Seq[Post]) = data.size.toString

    val graphData: Seq[GraphDataValue] =
      graphDataValues[Post, Long](weekRanges, posts)(dateSource(_))(total(_))

    getMetric(fbMetricName, metricSettings, graphData)

  }

  private def getMetric(
    fbMetricName: String,
    metricSettings: Seq[MetricSettings],
    graphData: Seq[GraphDataValue]
  ) = {

    val maybeMetric = for {
      metricSettings <- metricSettings.find(_.fbMetricName == fbMetricName)
    } yield {
      Metric.create(metricSettings, graphData)
    }

    maybeMetric orElse {
      logger.warn(s"Could not retrieve metric with fbMetricName: $fbMetricName")
      None
    }
  }

  private def dateSource(post: Post): Long = post.getCreatedTime.toInstant.toEpochMilli

}
