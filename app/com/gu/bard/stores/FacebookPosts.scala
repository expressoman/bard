package com.gu.bard.stores

import com.gu.bard.models.{ DateParameters, FacebookPageConfig }
import com.gu.bard.services.FB
import com.restfb.types.Post
import com.typesafe.scalalogging.StrictLogging
import scala.collection.JavaConverters._

object FacebookPosts extends StrictLogging {

  def getPosts(dateParameters: DateParameters, fbPageConfig: FacebookPageConfig): Option[Seq[Post]] = {
    val cacheKey = FacebookPostsCache.key(fbPageConfig.name, dateParameters, period = "")

    FacebookPostsCache.get(cacheKey) orElse {
      val fbClient = FB(fbPageConfig.accessToken)
      val connection = s"${fbPageConfig.id}/posts"

      val maybePageFeed = FB.getConnection[Post](connection, dateParameters, None, fbClient)
      val maybePosts = maybePageFeed map (_.iterator.asScala.toList.flatMap(_.asScala.toList))

      maybePosts foreach (posts => FacebookPostsCache.put(cacheKey, posts))
      maybePosts
    }
  }
}
