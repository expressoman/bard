package com.gu.bard.settings

case class PageSettings(prettyPageName: String, graphSettings: Map[String, GraphSettings])

object PageInsightsPageSettings {

  private val graphSettings = Map(
    "totalPosts" -> GraphSettings(
      title = "Total posts per week",
      description = "",
      whatsSuccess = "",
      `type` = "bar",
      axisXLabel = "Date",
      axisYLabel = "No of posts",
      Seq(MetricSettings.totalPosts)
    ),
    "totalPagePostReactionsBreakdown" -> GraphSettings(
      title = "Total page post reactions by type",
      description = "",
      whatsSuccess = "",
      `type` = "bar",
      axisXLabel = "Date",
      axisYLabel = "No of Reactions",
      Seq(
        MetricSettings.totalPostReactionLikes,
        MetricSettings.totalPostReactionHahas,
        MetricSettings.totalPostReactionLoves,
        MetricSettings.totalPostReactionSorrys,
        MetricSettings.totalPostReactionWows,
        MetricSettings.totalPostReactionAngers
      )
    ),
    "totalNewPeopleWhoLikeAndUnlike" -> GraphSettings(
      title = "Total number of new page likes/unlikes",
      description = "",
      whatsSuccess = "",
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of Likes/Unlikes",
      Seq(MetricSettings.totalNewPeopleWhoLike, MetricSettings.totalNewPeopleWhoUnlike)
    ),
    "pageImpressionsTotalAndUnique" -> GraphSettings(
      title = "Total number of impressions for the page",
      description = "",
      whatsSuccess = "",
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of Impressions",
      Seq(MetricSettings.totalPageImpressions, MetricSettings.totalPageImpressionsUnique)
    ),
    "totalPageFans" -> GraphSettings(
      title = "Lifetime total likes",
      description = "",
      whatsSuccess = "",
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of Fans",
      Seq(MetricSettings.totalPageFans)
    ),
    "totalPageVideoViewsAndUnique" -> GraphSettings(
      title = "Total number of page video views",
      description = "",
      whatsSuccess = "",
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of views",
      Seq(MetricSettings.totalPageVideoViews, MetricSettings.totalPageVideoViewsUnique)
    ),
    "totalPageVideoViewsComplete10sAnd30sAndUnique" -> GraphSettings(
      title = "Total number of page video views (breakdown by view duration)",
      description = "",
      whatsSuccess = "",
      `type` = "bar",
      axisXLabel = "Date",
      axisYLabel = "No of views",
      Seq(
        MetricSettings.totalPageVideoCompleteViews30s,
        MetricSettings.totalPageVideoCompleteViews30sUnique,
        MetricSettings.totalPageVideoCompleteViews10s,
        MetricSettings.totalPageVideoCompleteViews10sUnique
      )
    )
  )

  val config = Map(
    "cook-perfect" -> PageSettings(
      prettyPageName = "Cook Perfect",
      graphSettings = graphSettings
    ),

    "guardian-technology" -> PageSettings(
      prettyPageName = "Guardian Technology",
      graphSettings = graphSettings
    ),

    "the-guardian" -> PageSettings(
      prettyPageName = "The Guardian",
      graphSettings = graphSettings
    ),

    "the-guardian-us" -> PageSettings(
      prettyPageName = "The Guardian US",
      graphSettings = graphSettings
    ),

    "the-guardian-au" -> PageSettings(
      prettyPageName = "The Guardian AU",
      graphSettings = graphSettings
    ),

    "guardian-football" -> PageSettings(
      prettyPageName = "Guardian Football",
      graphSettings = graphSettings
    ),

    "guardian-sport" -> PageSettings(
      prettyPageName = "Guardian Sport",
      graphSettings = graphSettings
    ),

    "guardian-culture" -> PageSettings(
      prettyPageName = "Guardian Culture",
      graphSettings = graphSettings
    ),

    "guardian-video" -> PageSettings(
      prettyPageName = "Guardian Video",
      graphSettings = graphSettings
    ),

    "guardian-fashion" -> PageSettings(
      prettyPageName = "Guardian Fashion",
      graphSettings = graphSettings
    )
  )

  def pageInsightsFbMetricNames: Seq[String] = {
    /* Metrics that are not from insights API */
    val excludedMetrics = Set("total_posts")

    config.values.toSeq
      .flatMap(_.graphSettings.values)
      .flatMap { graphSettings => graphSettings.metricSettings.map(_.fbMetricName).distinct.filter(!excludedMetrics.contains(_)) }
  }

}
