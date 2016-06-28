package com.gu.bard.settings

import play.api.libs.json.Json

case class MetricSettings(metricType: String, fbMetricName: String)

/**
 * contains the settings for all metrics used across the application, allowing them to be configurable and reusable, held in a single place.
 */
object MetricSettings {

  implicit val metricSettingsFormats = Json.format[MetricSettings]

  def totalPosts = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "total_posts"
  )

  def totalPostReactionLikes = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_like_total"
  )

  def totalPostReactionLoves = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_love_total"
  )

  def totalPostReactionWows = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_wow_total"
  )

  def totalPostReactionHahas = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_haha_total"
  )

  def totalPostReactionAngers = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_anger_total"
  )

  def totalPostReactionSorrys = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_sorry_total"
  )

  def totalNewPeopleWhoLike = MetricSettings(
    metricType = " health",
    fbMetricName = "page_fan_adds_unique"
  )

  def totalNewPeopleWhoUnlike = MetricSettings(
    metricType = "health",
    fbMetricName = "page_fan_removes_unique"
  )

  def totalPostsImpressions = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_impressions"
  )

  def totalPostsImpressionsUnique = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_impressions_unique"
  )

  def totalPageFans = MetricSettings(
    metricType = "health",
    fbMetricName = "page_fans"
  )

  def totalPageVideoViews = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_views"
  )

  def totalPageVideoViewsUnique = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_views_unique"
  )

  def totalPageVideoCompleteViews30s = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_complete_views_30s"
  )

  def totalPageVideoCompleteViews30sUnique = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_complete_views_30s_unique"
  )

  def totalPageVideoCompleteViews10s = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_views_10s"
  )

  def totalPageVideoCompleteViews10sUnique = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_views_10s_unique"
  )

}
