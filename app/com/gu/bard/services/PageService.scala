package com.gu.bard.services

import com.gu.bard.metrics.{ PageInsightGraphs, PostGraphs }
import com.gu.bard.models.{ DateParameters, FacebookPageConfig, Page }
import com.gu.bard.settings.PageInsightsPageSettings
import com.gu.bard.stores.{ FacebookPageInsights, FacebookPosts }

/**
 * Responsible for retrieving a dashboard page, containing all the metrics and relevant information displayable by
 * the client side.
 */
object PageService {

  def getPageInsightsPage(dateParameters: DateParameters, fbPageConfig: FacebookPageConfig): Option[Page] = {

    val maybePageSettings = PageInsightsPageSettings.config.get(fbPageConfig.name)
    val maybeGraphSettings = maybePageSettings.map(_.graphSettings)
    val weekRanges = DateRangeGenerator.generateWeekRanges(startDate = dateParameters.from, endDate = dateParameters.to)

    val maybePageInsightsGraphsForDay = for {
      pageInsightsDay <- FacebookPageInsights.getPageInsightsForDay(dateParameters, fbPageConfig)
      graphSettings <- maybeGraphSettings
    } yield {
      new PageInsightGraphs(graphSettings, pageInsightsDay, weekRanges)
    }

    val maybePageInsightsGraphsForLifeTime = for {
      pageInsightsLifetime <- FacebookPageInsights.getPageInsightsForLifetime(dateParameters, fbPageConfig)
      graphSettings <- maybeGraphSettings
    } yield {
      new PageInsightGraphs(graphSettings, pageInsightsLifetime, weekRanges)
    }

    val maybePostGraphs = for {
      posts <- FacebookPosts.getPosts(dateParameters, fbPageConfig)
      graphSettings <- maybeGraphSettings
    } yield {
      new PostGraphs(graphSettings, posts, weekRanges)
    }

    for {
      pageInsightsGraphsForDay <- maybePageInsightsGraphsForDay
      pageInsightsGraphsForLifetime <- maybePageInsightsGraphsForLifeTime
      postGraphs <- maybePostGraphs
      pageSettings <- maybePageSettings
    } yield {
      Page(
        prettyPageName = pageSettings.prettyPageName,
        fbPageName = fbPageConfig.name,
        graphs = Seq(
          pageInsightsGraphsForLifetime.totalPageFans,
          postGraphs.totalPostsPerDay,
          pageInsightsGraphsForDay.combinedGraphs.pageImpressionsTotalAndUnique,
          pageInsightsGraphsForDay.combinedGraphs.totalPostLikeReactionsBreakdown,
          pageInsightsGraphsForDay.combinedGraphs.pageVideoViewsTotalAndUnique,
          pageInsightsGraphsForDay.combinedGraphs.pageVideoViewsComplete10sAnd30sTotalAndUnique,
          pageInsightsGraphsForDay.combinedGraphs.totalNewPeopleWhoLikeAndUnlike
        ).flatten
      )
    }

  }

}
