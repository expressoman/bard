package com.gu.bard.stores

import com.gu.bard.models.{ DateParameters, FacebookPageConfig }
import com.gu.bard.services.FB
import com.restfb.types.Insight
import com.typesafe.scalalogging.StrictLogging

object FacebookPageInsights extends StrictLogging {

  def getPageInsightsForDay(dateParameters: DateParameters, fbPageConfig: FacebookPageConfig): Option[Seq[Insight]] = {
    val period = "day"
    getPageInsights(period, dateParameters, fbPageConfig)
  }

  def getPageInsightsForLifetime(dateParameters: DateParameters, fbPageConfig: FacebookPageConfig): Option[Seq[Insight]] = {
    val period = "lifetime"
    getPageInsights(period, dateParameters, fbPageConfig)
  }

  private def getPageInsights(period: String, dateParameters: DateParameters, fbPageConfig: FacebookPageConfig): Option[Seq[Insight]] = {
    val cacheKey = FacebookPageInsightsCache.key(fbPageConfig.name, dateParameters, period)

    FacebookPageInsightsCache.get(cacheKey) orElse {
      val fbClient = FB(fbPageConfig.accessToken)
      val connection = s"${fbPageConfig.id}/insights"

      val maybeInsights = FB.getData[Insight](connection, dateParameters, Some(period), fbClient)
      maybeInsights foreach (insights => FacebookPageInsightsCache.put(cacheKey, insights))

      maybeInsights
    }
  }

}
