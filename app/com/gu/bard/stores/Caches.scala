package com.gu.bard.stores

import java.util.concurrent.TimeUnit

import com.github.benmanes.caffeine.cache.Caffeine
import com.gu.bard.models.DateParameters
import com.restfb.types.{ Insight, Post }

import scala.collection.JavaConverters._

trait CacheKey {
  def key(fbPageName: String, dp: DateParameters) = s"$fbPageName-${dp.from}-${dp.to}".hashCode
}

trait FacebookPageInsightsCache {
  def get(key: Integer): Option[Seq[Insight]]
  def put(key: Integer, value: Seq[Insight])
}

trait FacebookPostsCache {
  def get(key: Integer): Option[Seq[Post]]
  def put(key: Integer, value: Seq[Post])
}

class Cache[T <: Object, R <: Object] {

  private lazy val cache = Caffeine.newBuilder()
    .expireAfterAccess(1, TimeUnit.DAYS)
    .maximumSize(200L)
    .build[T, R]()

  def get(key: T): Option[R] = Option(cache.getIfPresent(key))
  def put(key: T, value: R) { cache.put(key, value) }
  def putAll(all: Map[T, R]) { cache.putAll(all.asJava) }
}

object FacebookPageInsightsCache extends Cache[Integer, Seq[Insight]] with FacebookPageInsightsCache with CacheKey
object FacebookPostsCache extends Cache[Integer, Seq[Post]] with FacebookPostsCache with CacheKey

