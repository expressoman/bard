package com.gu.bard.metrics

import com.gu.bard.models.{ GraphDataValue, Metric }
import com.gu.bard.services.WeekRange
import com.gu.bard.settings.MetricSettings
import com.restfb.json.JsonObject
import com.restfb.types.Insight
import com.typesafe.scalalogging.StrictLogging
import scala.collection.JavaConverters._

/**
 * Provides functions for the computation of Insight metrics in graph form for rendering.
 */
object InsightMetrics extends StrictLogging with Graphing {

  def totalPostLikeReactions(metricSettings: Seq[MetricSettings], pageInsights: Seq[Insight], weekRanges: Seq[WeekRange]): Option[Metric] = {
    val fbMetricName = "page_actions_post_reactions_like_total"
    def graphDataFn: Seq[JsonObject] => Seq[GraphDataValue] =
      graphDataValues[JsonObject, String](weekRanges, _: Seq[JsonObject])(dateSource(_))(sum(_))

    getMetric(fbMetricName, metricSettings, pageInsights, graphDataFn)
  }

  def totalNewPeopleWhoLike(metricSettings: Seq[MetricSettings], pageInsights: Seq[Insight], weekRanges: Seq[WeekRange]): Option[Metric] = {
    val fbMetricName = "page_fan_adds_unique"
    def graphDataFn: Seq[JsonObject] => Seq[GraphDataValue] =
      graphDataValues[JsonObject, String](weekRanges, _: Seq[JsonObject])(dateSource(_))(sum(_))

    getMetric(fbMetricName, metricSettings, pageInsights, graphDataFn)
  }

  def totalNewPeopleWhoUnlike(metricSettings: Seq[MetricSettings], pageInsights: Seq[Insight], weekRanges: Seq[WeekRange]): Option[Metric] = {
    val fbMetricName = "page_fan_removes_unique"
    def graphDataFn: Seq[JsonObject] => Seq[GraphDataValue] =
      graphDataValues[JsonObject, String](weekRanges, _: Seq[JsonObject])(dateSource(_))(sum(_))

    getMetric(fbMetricName, metricSettings, pageInsights, graphDataFn)
  }

  private def getMetric(
    fbMetricName: String,
    metricSettings: Seq[MetricSettings],
    pageInsights: Seq[Insight],
    graphDataFn: Seq[JsonObject] => Seq[GraphDataValue]
  ) = {

    val maybeMetric = for {
      metricSettings <- metricSettings.find(_.fbMetricName == fbMetricName)
      metricData <- pageInsights.find(_.getName == fbMetricName).map(_.getValues.asScala)
    } yield {
      val graphData = graphDataFn(metricData)
      Metric.create(metricSettings, graphData)
    }

    maybeMetric orElse {
      logger.warn(s"Could not retrieve metric with fbMetricName: $fbMetricName")
      None
    }
  }

  private def dateSource(json: JsonObject) = json.getString("end_time")
  private def sum(data: Seq[JsonObject]) = data.map(_.getInt("value")).sum.toString

}
