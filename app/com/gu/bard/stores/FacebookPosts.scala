package com.gu.bard.stores

import com.gu.bard.models.{ DateParameters, FacebookPageConfig }
import com.gu.bard.services.FB
import com.restfb.types.Post

object FacebookPosts {

  def getPosts(dateParameters: DateParameters, fbPageConfig: FacebookPageConfig): Option[Seq[Post]] = {
    val fbClient = FB(fbPageConfig.accessToken)
    val connection = s"${fbPageConfig.id}/posts"
    val period = "day"

    FB.get[Post](connection, dateParameters, period, fbClient)
  }
}
