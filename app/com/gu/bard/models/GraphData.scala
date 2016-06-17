package com.gu.bard.models

import com.gu.bard.settings.GraphDataSettings
import play.api.libs.json.Json

case class GraphDataValue(dataPoint: String, label: String)
object GraphDataValue {
  implicit val graphDataValueFormats = Json.format[GraphDataValue]
}

case class GraphData(graphDataSettings: GraphDataSettings, values: Seq[GraphDataValue])
object GraphData {
  implicit val chartDataFormats = Json.format[GraphData]
}
