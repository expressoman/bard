package com.gu.bard.models

import com.gu.bard.settings.GraphSettings
import play.api.libs.json.Json

case class Graph(
  title: String,
  description: String,
  whatsSuccess: String,
  `type`: String,
  axisXLabel: String,
  axisYLabel: String,
  low: Float,
  metrics: Seq[Metric] // If a graph has more than one metric associated with it,
// we assume that the two metrics have the same labels for each datapoint.
)

object Graph {
  implicit val graphFormats = Json.format[Graph]

  def create(graphSettings: GraphSettings, metrics: Seq[Metric]): Graph = {
    val low: Float = metrics.flatMap(_.graphData.values.map(_.dataPoint.toFloat)).min

    Graph(graphSettings.title, graphSettings.description, graphSettings.whatsSuccess, graphSettings.`type`, graphSettings.axisXLabel,
      graphSettings.axisYLabel, low, metrics)
  }
}
