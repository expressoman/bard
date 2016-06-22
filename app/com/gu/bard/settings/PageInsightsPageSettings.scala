package com.gu.bard.settings

case class PageSettings(prettyPageName: String, graphSettings: Map[String, GraphSettings])

object PageInsightsPageSettings {

  private val graphSettings = Map(
    "totalPosts" -> GraphSettings(
      title = "Total Posts Per Week",
      description = "Shows how much content we're publishing each week. Publishing should be consistent as the number of posts will affect audience growth.",
      whatsSuccess = " ... ",
      `type` = "bar",
      axisXLabel = "Date",
      axisYLabel = "No of posts",
      Seq(MetricSettings.totalPosts)
    ),
    "totalPostLikeReactions" -> GraphSettings(
      title = "Total Likes/Comments/Shares",
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
