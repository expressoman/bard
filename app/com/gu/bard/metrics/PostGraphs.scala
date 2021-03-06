package com.gu.bard.metrics

import com.gu.bard.models._
import com.gu.bard.settings.{ GraphSettings }
import com.restfb.types.{ Post }
import com.typesafe.scalalogging.StrictLogging
import org.joda.time.DateTime
import com.gu.bard.metrics.Graphing._

/**
 * Provides functions for the computation of Post metrics in graph form for rendering.
 */
class PostGraphs(graphSettingsMap: Map[String, GraphSettings], val metricData: Seq[Post], val weekRanges: Seq[WeekRange]) extends StrictLogging {

  implicit object PostGraphHelper extends GraphHelper[Post, Post] {
    val graphWeekRanges: Seq[WeekRange] = weekRanges

    def getDataForMetric(metricName: String): Seq[Post] = metricData
    def dateSource(post: Post) = new DateTime(post.getCreatedTime.toInstant.toEpochMilli)

    def compute(computationName: String): Seq[Post] => String = {
      computationName match {
        case "TOTAL" => _.size.toString
      }
    }
  }

  def totalPostsPerDay: Option[Graph] = {
    val GraphSettingsKey = "totalPosts"
    getGraph(graphSettingsMap.get(GraphSettingsKey), "TOTAL")
  }

}
