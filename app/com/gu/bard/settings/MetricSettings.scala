package com.gu.bard.settings

import play.api.libs.json.Json

case class MetricSettings(metricType: String, fbMetricName: String, prettyFbMetricName: String)

/**
 * contains the settings for all metrics used across the application, allowing them to be configurable and reusable, held in a single place.
 */
object MetricSettings {

  implicit val metricSettingsFormats = Json.format[MetricSettings]

  def totalPosts = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "total_posts",
    prettyFbMetricName = "Total posts"
  )

  def totalPostReactionLikes = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_like_total",
    prettyFbMetricName = "Like"
  )

  def totalPostReactionLoves = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_love_total",
    prettyFbMetricName = "Love"
  )

  def totalPostReactionWows = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_wow_total",
    prettyFbMetricName = "Wow"
  )

  def totalPostReactionHahas = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_haha_total",
    prettyFbMetricName = "Haha"
  )

  def totalPostReactionAngers = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_anger_total",
    prettyFbMetricName = "Anger"
  )

  def totalPostReactionSorrys = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_sorry_total",
    prettyFbMetricName = "Sorry"
  )

  def totalNewPeopleWhoLike = MetricSettings(
    metricType = " health",
    fbMetricName = "page_fan_adds_unique",
    prettyFbMetricName = "Page fans (unique)"
  )

  def totalNewPeopleWhoUnlike = MetricSettings(
    metricType = "health",
    fbMetricName = "page_fan_removes_unique",
    prettyFbMetricName = "Page fan removes (unique)"
  )

  def totalPageImpressions = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_impressions",
    prettyFbMetricName = "Page impressions"
  )

  def totalPageImpressionsUnique = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_impressions_unique",
    prettyFbMetricName = "Page impressions (unique)"
  )

  def totalPageFans = MetricSettings(
    metricType = "health",
    fbMetricName = "page_fans",
    prettyFbMetricName = "Page fans"
  )

  def totalPageVideoViews = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_views",
    prettyFbMetricName = "Page video views"
  )

  def totalPageVideoViewsUnique = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_views_unique",
    prettyFbMetricName = "Page video views (unique)"
  )

  def totalPageVideoCompleteViews30s = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_complete_views_30s",
    prettyFbMetricName = "Video views (30 seconds)"
  )

  def totalPageVideoCompleteViews30sUnique = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_complete_views_30s_unique",
    prettyFbMetricName = "Video views (30 seconds, unique)"
  )

  def totalPageVideoCompleteViews10s = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_views_10s",
    prettyFbMetricName = "Video views (10 seconds)"
  )

  def totalPageVideoCompleteViews10sUnique = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_video_views_10s_unique",
    prettyFbMetricName = "Video views (10 seconds, unique)"
  )

}
