package com.gu.bard.models

import com.gu.bard.settings.MetricSettings
import com.typesafe.scalalogging.StrictLogging
import play.api.libs.json.Json

case class Metric(metricSettings: MetricSettings, graphData: GraphData)
object Metric extends StrictLogging {
  implicit val metricFormats = Json.format[Metric]
}
