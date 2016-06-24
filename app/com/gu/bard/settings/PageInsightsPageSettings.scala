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
    "totalPostLikeReactions" -> GraphSettings(
      title = "Total likes/comments/shares",
      description = "As we optimise our articles on Facebook we should see an increase in the number of likes per post.",
      whatsSuccess = " ... ",
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of Shares",
      Seq(MetricSettings.totalLikesCommentsShares)
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
