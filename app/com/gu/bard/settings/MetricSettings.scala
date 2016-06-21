package com.gu.bard.settings

import play.api.libs.json.Json

case class GraphDataSettings(`type`: String, axisXLabel: String, axisYLabel: String)
object GraphDataSettings {
  implicit val graphDataSettingsFormats = Json.format[GraphDataSettings]
}

/**
 * contains the settings for all metrics used across the application, allowing them to be configurable and reusable, held in a single place.
 */
object MetricSettings {

  implicit val metricSettingsFormats = Json.format[MetricSettings]

  def averagePosts(graphDataSettings: GraphDataSettings) = MetricSettings(
    title = "Average Posts Per Day",
    description = "Shows how much content we're publishing each week. Publishing should be consistent as the number of posts will affect audience growth.",
    whatsSuccess = " ... ",
    metricType = "promotion", // TODO - should be enum
    fbMetricName = "average_posts",
    fbMetricDescription = "",
    graphDataSettings = graphDataSettings
  )

  def totalPosts(graphDataSettings: GraphDataSettings) = MetricSettings(
    title = "Total Posts Per Week",
    description = "Shows how much content we're publishing each week. Publishing should be consistent as the number of posts will affect audience growth.",
    whatsSuccess = " ... ",
    metricType = "content_effectiveness",
    fbMetricName = "total_posts",
    fbMetricDescription = "",
    graphDataSettings = graphDataSettings
  )

  def totalLikesCommentsShares(graphDataSettings: GraphDataSettings) = MetricSettings(
    title = "Total Likes/Comments/Shares",
    description = "As we optimise our articles on Facebook we should see an increase in the number of likes per post.",
    whatsSuccess = " ... ",
    metricType = "content_effectiveness",
    fbMetricName = "page_actions_post_reactions_like_total",
    fbMetricDescription = "",
    graphDataSettings = graphDataSettings
  )

  def totalNewPeopleWhoLike(graphDataSettings: GraphDataSettings) = MetricSettings(
    title = "Total number of new page likes",
    description = "Shows the number of people (unique) who are actively liking this page per week. Useful for observing the general health of a page.",
    whatsSuccess = " ... ",
    metricType = " health",
    fbMetricName = "page_fan_adds_unique",
    fbMetricDescription = "",
    graphDataSettings = graphDataSettings
  )

  def totalNewPeopleWhoUnlike(graphDataSettings: GraphDataSettings) = MetricSettings(
    title = "Total number of new page unlikes",
    description = "Shows the number of people (unique) who are actively unliking this page per week. Useful for observing the general health of a page.",
    whatsSuccess = " ... ",
    metricType = "health",
    fbMetricName = "page_fan_removes_unique",
    fbMetricDescription = "",
    graphDataSettings = graphDataSettings
  )

}

case class MetricSettings(
  title: String,
  description: String,
  whatsSuccess: String,
  metricType: String,
  fbMetricName: String,
  fbMetricDescription: String,
  graphDataSettings: GraphDataSettings
)
