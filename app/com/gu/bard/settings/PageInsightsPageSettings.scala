package com.gu.bard.settings

case class PageSettings(prettyPageName: String, metricSettings: Seq[MetricSettings])

object PageInsightsPageSettings {

  private val metricSettings = Seq(
    MetricSettings.totalPosts(GraphDataSettings(
      `type` = "bar",
      axisXLabel = "Date",
      axisYLabel = "No of posts"
    )),
    MetricSettings.totalLikesCommentsShares(GraphDataSettings(
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of Shares"
    )),
    MetricSettings.totalNewPeopleWhoLike(GraphDataSettings(
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of Likes"
    )),
    MetricSettings.totalNewPeopleWhoUnlike(GraphDataSettings(
      `type` = "line",
      axisXLabel = "Date",
      axisYLabel = "No of Unlikes"
    ))
  )

  val config = Map(

    "cook-perfect" -> PageSettings(
      prettyPageName = "Cook Perfect",
      metricSettings = metricSettings
    ),

    "guardian-technology" -> PageSettings(
      prettyPageName = "Guardian Technology",
      metricSettings = metricSettings
    ),

    "the-guardian" -> PageSettings(
      prettyPageName = "The Guardian",
      metricSettings = metricSettings
    )
  )
}
