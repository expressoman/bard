package com.gu.bard.metrics

import com.gu.bard.models._
import com.gu.bard.settings.GraphSettings
import com.restfb.json.JsonObject
import com.restfb.types.Insight
import com.typesafe.scalalogging.StrictLogging
import org.joda.time.DateTime
import com.gu.bard.metrics.Graphing._
import scala.collection.JavaConverters._

/**
 * Provides functions for the computation of Insight metrics in graph form for rendering.
 */
class PageInsightGraphs(graphSettingsMap: Map[String, GraphSettings], val metricData: Seq[Insight], val weekRanges: Seq[WeekRange]) extends StrictLogging {

  implicit object PageInsightsGraphHelper extends GraphHelper[Insight, JsonObject] {
    val graphWeekRanges: Seq[WeekRange] = weekRanges

    def getDataForMetric(metricName: String): Seq[JsonObject] =
      metricData.find(_.getName == metricName).map(_.getValues.asScala.toList).toList.flatten

    def dateSource(json: JsonObject) = new DateTime(json.getString("end_time"))

    def compute(computationName: String): Seq[JsonObject] => String = {
      computationName match {
        case "SUM" => _.map(_.getInt("value")).sum.toString
        case "LAST" => _.map(_.getInt("value")).lastOption.getOrElse(0).toString
      }
    }
  }

  def totalPostLikeReactions: Option[Graph] = {
    val GraphSettingsKey = "totalPostLikeReactions"
    getGraph(graphSettingsMap.get(GraphSettingsKey), "SUM")
  }

  def totalPageFans: Option[Graph] = {
    val GraphSettingsKey = "totalPageFans"
    getGraph(graphSettingsMap.get(GraphSettingsKey), "LAST")
  }

  object combinedGraphs {

    def totalNewPeopleWhoLikeAndUnlike: Option[Graph] = {
      val GraphSettingsKey = "totalNewPeopleWhoLikeAndUnlike"
      getGraph(graphSettingsMap.get(GraphSettingsKey), "SUM")
    }

    def postsImpressionsTotalAndUnique: Option[Graph] = {
      val GraphSettingsKey = "postsImpressionsTotalAndUnique"
      getGraph(graphSettingsMap.get(GraphSettingsKey), "SUM")
    }

    def pageVideoViewsTotalAndUnique: Option[Graph] = {
      val GraphSettingsKey = "totalPageVideoViewsAndUnique"
      getGraph(graphSettingsMap.get(GraphSettingsKey), "SUM")
    }

    def pageVideoViewsComplete10sAnd30sTotalAndUnique: Option[Graph] = {
      val GraphSettingsKey = "totalPageVideoViewsComplete10sAnd30sAndUnique"
      getGraph(graphSettingsMap.get(GraphSettingsKey), "SUM")
    }

  }

}
