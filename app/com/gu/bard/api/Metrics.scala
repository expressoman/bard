package com.gu.bard.api

import com.gu.bard.models._
import com.gu.bard.services.WeekRange
import com.gu.bard.settings.MetricSettings
import com.restfb.json.JsonObject
import com.restfb.types.{ Insight, Post }
import com.typesafe.scalalogging.StrictLogging
import org.joda.time.DateTime

import scala.collection.JavaConverters._

/**
 * Provides functions for the computation of Metrics in graph form for rendering.
 */
object Metrics extends StrictLogging {

  def averagePostsPerDay(metricSettings: Seq[MetricSettings], posts: Seq[Post], weekRanges: Seq[WeekRange]): Option[Metric] = {
    val fbMetricName = "average_posts" // TODO - Move to constants
    def dateSource(post: Post): Long = post.getCreatedTime.toInstant.toEpochMilli
    def metricComputaton(data: Seq[Post]) = (data.size.toFloat / 7).toString

    val maybeMetric = for {
      metricSettings <- metricSettings.find(_.fbMetricName == fbMetricName)
    } yield {
      val graphData = graphDataValues[Post, Long](weekRanges, posts)(dateSource(_))(metricComputaton(_))
      Metric.create(metricSettings, graphData)
    }

    maybeMetric orElse {
      logger.warn(s"Could not retrieve average posts per day with fbMetricName: $fbMetricName")
      None
    }

  }

  def totalPostsPerDay(metricSettings: Seq[MetricSettings], posts: Seq[Post], weekRanges: Seq[WeekRange]): Option[Metric] = {
    val fbMetricName = "total_posts"
    def dateSource(post: Post): Long = post.getCreatedTime.toInstant.toEpochMilli
    def metricComputaton(data: Seq[Post]) = data.size.toString

    val maybeMetric = for {
      metricSettings <- metricSettings.find(_.fbMetricName == fbMetricName)
    } yield {
      val graphData = graphDataValues[Post, Long](weekRanges, posts)(dateSource(_))(metricComputaton(_))
      Metric.create(metricSettings, graphData)
    }

    maybeMetric orElse {
      logger.warn(s"Could not retrieve total posts per day with fbMetricName: $fbMetricName")
      None
    }

  }

  def totalPostLikeReactions(metricSettings: Seq[MetricSettings], pageInsights: Seq[Insight], weekRanges: Seq[WeekRange]): Option[Metric] = {
    val fbMetricName = "page_actions_post_reactions_like_total"
    val maybePostLikeReactions = pageInsights.find(_.getName == fbMetricName).map(_.getValues.asScala)
    def dateSource(json: JsonObject): String = json.getString("end_time")
    def metricComputaton(data: Seq[JsonObject]) = data.map(_.getInt("value")).sum.toString

    val maybeMetric = for {
      metricSettings <- metricSettings.find(_.fbMetricName == fbMetricName)
      postLikeReactions <- maybePostLikeReactions
    } yield {
      val graphData = graphDataValues[JsonObject, String](weekRanges, postLikeReactions)(dateSource(_))(metricComputaton(_))
      Metric.create(metricSettings, graphData)
    }

    maybeMetric orElse {
      logger.warn(s"Could not retrieve total post like reactions with fbMetricName: $fbMetricName")
      None
    }
  }

  private def graphDataValues[T, U](weekRanges: Seq[WeekRange], data: Seq[T])(dateSource: T => U)(compute: Seq[T] => String): Seq[GraphDataValue] = {

    def inThisWeek(range: WeekRange, t: T) = range.isInThisWeek(date = new DateTime(dateSource(t)).toLocalDate)

    weekRanges map { range =>
      val dataForWeek = data.filter { datum => inThisWeek(range, datum) }
      GraphDataValue((compute(dataForWeek)).toString, range.toString)
    }

  }

}
