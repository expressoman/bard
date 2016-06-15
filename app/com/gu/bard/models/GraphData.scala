package com.gu.bard.models

import com.gu.bard.settings.{ GraphDataSettings, MetricSettings }
import play.api.libs.json.Json

case class GraphDataValue(dataPoint: String, label: String)
object GraphDataValue {
  implicit val graphDataValueFormats = Json.format[GraphDataValue]
}

case class GraphData(graphDataSettings: GraphDataSettings, values: Seq[GraphDataValue])
object GraphData {
  implicit val chartDataFormats = Json.format[GraphData]
}

case class Metric(metricSettings: MetricSettings, graphData: GraphData)

object Metric {
  implicit val metricFormats = Json.format[Metric]

  def create(metricSettings: MetricSettings, graphData: Seq[GraphDataValue]): Metric = {
    Metric(
      metricSettings = metricSettings,
      graphData = GraphData(
        graphDataSettings = metricSettings.graphDataSettings,
        values = graphData
      )
    )
  }
}

case class Page(prettyPageName: String, metrics: Seq[Metric])
object Page {
  implicit val pageFormats = Json.format[Page]
}
