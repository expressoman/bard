package com.gu.bard.settings

case class PageSettings(prettyPageName: String, graphSettings: Map[String, GraphSettings])

object PageInsightsPageSettings {

  private val graphSettings = Map(
    "totalPosts" -> GraphSettings(
      title = "Total posts per week",
      description = "Shows how much content we're publishing each week. Publishing should be consistent as the number of posts will affect audience growth.",
      whatsSuccess = " ... ",
      `type` = "bar",
      axisXLabel = "Date",
      axisYLabel = "No of posts",
      Seq(MetricSettings.totalPosts)
    ),
    "totalPagePostReactionsBreakdown" -> GraphSettings(
      title = "Total page post reactions by type",
      description = "Shows the general reactions to page posts",
      whatsSuccess = " ... ",
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
      description = "Shows the number of people (unique) who are actively liking/unliking this page per week. Useful for observing the general health of a page.",
      whatsSuccess = " ... ",
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of Likes/Unlikes",
      Seq(MetricSettings.totalNewPeopleWhoLike, MetricSettings.totalNewPeopleWhoUnlike)
    ),
    "postsImpressionsTotalAndUnique" -> GraphSettings(
      title = "Total number of impressions for the page",
      description = "Shows the total number of posts impressions and unique posts impressions",
      whatsSuccess = " ... ",
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of Impressions",
      Seq(MetricSettings.totalPostsImpressions, MetricSettings.totalPostsImpressionsUnique)
    ),
    "totalPageFans" -> GraphSettings(
      title = "Lifetime total likes",
      description = "Shows the number of people who have liked your page.",
      whatsSuccess = " ... ",
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of Fans",
      Seq(MetricSettings.totalPageFans)
    ),
    "totalPageVideoViewsAndUnique" -> GraphSettings(
      title = "Total number of page video views",
      description = "Total number of times videos have been viewed for more than 3 seconds.",
      whatsSuccess = " ... ",
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of views",
      Seq(MetricSettings.totalPageVideoViews, MetricSettings.totalPageVideoViewsUnique)
    ),
    "totalPageVideoViewsComplete10sAnd30sAndUnique" -> GraphSettings(
      title = "Total number of page video views (breakdown by view duration)",
      description = "Total number of times page's videos was viewed for at least 10 or 30 seconds.",
      whatsSuccess = " ... ",
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
    )
  )
}
