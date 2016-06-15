package com.gu.bard.stores

import com.gu.bard.models.{ DateParameters, FacebookPageConfig }
import com.gu.bard.services.FB
import com.restfb.types.Insight
import com.typesafe.scalalogging.StrictLogging

object FacebookPageInsights extends StrictLogging {

  def getPageInsights(dateParameters: DateParameters, fbPageConfig: FacebookPageConfig): Option[Seq[Insight]] = {
    val fbClient = FB(fbPageConfig.accessToken)
    val connection = s"${fbPageConfig.id}/insights"
    val period = "day"

    FB.get[Insight](connection, dateParameters, period, fbClient)
  }

}
