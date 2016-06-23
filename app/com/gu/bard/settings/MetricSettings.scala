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

  def totalLikesCommentsShares = MetricSettings(
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_like_total"
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
}
