package com.gu.bard.services

import akka.actor.{ ActorSystem, Props, Actor }
import com.gu.bard.models.{ DateParameters, FacebookPageConfig }
import com.gu.bard.services.CacheWarmup.PreWarm
import com.gu.bard.stores.{ FacebookPageInsightsCache, FacebookPostsCache }
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalactic.{ Bad, Good }
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object CacheWarmup {

  case object PreWarm

  def props(pageNames: Seq[String], fbPageConfig: Map[String, FacebookPageConfig]) = Props(new CacheWarmup(pageNames, fbPageConfig))
}

class CacheWarmup(pageNames: Seq[String], fbPageConfig: Map[String, FacebookPageConfig]) extends Actor with LazyLogging {

  val fbPageConf = pageNames flatMap fbPageConfig.get
  val DateFormat = DateTimeFormat.forPattern("yyyy-MM-dd")

  def receive = {
    case PreWarm =>

      val today = new DateTime()
      val firstDayOfTheWeek = new DateTime().weekOfWeekyear().roundFloorCopy()
      val toDate = DateFormat.print(today)
      val fromDates = List(DateFormat.print(firstDayOfTheWeek.minusWeeks(4)), DateFormat.print(firstDayOfTheWeek.minusWeeks(8)))

      invalidateAllCaches()

      fbPageConf foreach { pageConf =>
        fromDates foreach { fromDate =>
          DateParameters(fromDate, toDate) match {
            case Good(dp: DateParameters) => reloadPage(dp, pageConf)
            case Bad(error) => logger.warn(s"Wrong date parameters - the error was $error. Unable to prewarm cache for page ${pageConf.name}")
          }
        }
      }
  }

  private def reloadPage(dateParameters: DateParameters, facebookPageConfig: FacebookPageConfig): Unit = Future {
    PageService.getPageInsightsPage(dateParameters, facebookPageConfig)
  } foreach { _ =>
    logger.info(s"Prewarming cache for ${facebookPageConfig.name} from ${dateParameters.fromDateTime} to ${dateParameters.toDateTime} complete")
  }

  private def invalidateAllCaches(): Unit = {
    FacebookPostsCache.invalidateAll()
    FacebookPageInsightsCache.invalidateAll()
  }
}

