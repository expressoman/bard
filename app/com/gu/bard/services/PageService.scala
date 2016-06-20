package com.gu.bard.services

import com.gu.bard.metrics.{ InsightMetrics, PostMetrics }
import com.gu.bard.models.{ DateParameters, FacebookPageConfig, Page }
import com.gu.bard.settings.PageInsightsPageSettings
import com.gu.bard.stores.{ FacebookPageInsights, FacebookPosts }

/**
 * Responsible for retrieving a dashboard page, containing all the metrics and relevant information displayable by
 * the client side.
 */
object PageService {

  def getPageInsightsPage(dateParameters: DateParameters, fbPageConfig: FacebookPageConfig): Option[Page] = {
    val weekRanges = DateRangeGenerator.generateWeekRanges(startDate = dateParameters.from, endDate = dateParameters.to)

    for {
      pageInsights <- FacebookPageInsights.getPageInsights(dateParameters, fbPageConfig)
      posts <- FacebookPosts.getPosts(dateParameters, fbPageConfig)
      pageSettings <- PageInsightsPageSettings.config.get(fbPageConfig.name)
      metricSettings = pageSettings.metricSettings
    } yield {
      Page(
        prettyPageName = pageSettings.prettyPageName,
        fbPageName = fbPageConfig.name,
        metrics = Seq(
          PostMetrics.averagePostsPerDay(metricSettings, posts, weekRanges),
          PostMetrics.totalPostsPerDay(metricSettings, posts, weekRanges),
          InsightMetrics.totalPostLikeReactions(metricSettings, pageInsights, weekRanges),
          InsightMetrics.totalNewPeopleWhoLike(metricSettings, pageInsights, weekRanges),
          InsightMetrics.totalNewPeopleWhoUnlike(metricSettings, pageInsights, weekRanges)
        ).flatten
      )
    }

  }

}
