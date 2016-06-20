package com.gu.bard.stores

import com.gu.bard.models.{ DateParameters, FacebookPageConfig }
import com.gu.bard.services.FB
import com.restfb.types.Insight
import com.typesafe.scalalogging.StrictLogging

object FacebookPageInsights extends StrictLogging {

  def getPageInsights(dateParameters: DateParameters, fbPageConfig: FacebookPageConfig): Option[Seq[Insight]] = {

    val cacheKey = FacebookPageInsightsCache.key(fbPageConfig.name, dateParameters)

    FacebookPageInsightsCache.get(cacheKey) orElse {
      val fbClient = FB(fbPageConfig.accessToken)
      val connection = s"${fbPageConfig.id}/insights"
      val period = Some("day")

      val maybeInsights = FB.getData[Insight](connection, dateParameters, period, fbClient)
      maybeInsights foreach (insights => FacebookPageInsightsCache.put(cacheKey, insights))

      maybeInsights
    }

  }

}
