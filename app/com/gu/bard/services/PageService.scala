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

    val maybePageInsightsGraphs = for {
      pageInsights <- FacebookPageInsights.getPageInsights(dateParameters, fbPageConfig)
      graphSettings <- maybeGraphSettings
    } yield {
      new PageInsightGraphs(graphSettings, pageInsights, weekRanges)
    }

    val maybePostGraphs = for {
      posts <- FacebookPosts.getPosts(dateParameters, fbPageConfig)
      graphSettings <- maybeGraphSettings
    } yield {
      new PostGraphs(graphSettings, posts, weekRanges)
    }

    for {
      pageInsightsGraphs <- maybePageInsightsGraphs
      postGraphs <- maybePostGraphs
      pageSettings <- maybePageSettings
    } yield {
      Page(
        prettyPageName = pageSettings.prettyPageName,
        fbPageName = fbPageConfig.name,
        graphs = Seq(
          pageInsightsGraphs.combinedGraphs.totalNewPeopleWhoLikeAndUnlike,
          pageInsightsGraphs.totalPostLikeReactions,
          pageInsightsGraphs.combinedGraphs.postsImpressionsTotalAndUnique,
          postGraphs.totalPostsPerDay
        ).flatten
      )
    }

  }

}
