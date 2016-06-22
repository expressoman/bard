package com.gu.bard.settings

import play.api.libs.json.Json

case class GraphSettings(
  title: String,
  description: String,
  whatsSuccess: String,
  `type`: String,
  axisXLabel: String,
  axisYLabel: String,
  metricSettings: Seq[MetricSettings]
)

object GraphSettings {

  implicit val graphSettingsFormats = Json.format[GraphSettings]

}
