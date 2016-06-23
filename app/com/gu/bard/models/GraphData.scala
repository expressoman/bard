package com.gu.bard.models

import play.api.libs.json.Json

case class GraphDataValue(dataPoint: String, label: String)
object GraphDataValue {
  implicit val graphDataValueFormats = Json.format[GraphDataValue]
}

case class GraphData(values: Seq[GraphDataValue])
object GraphData {
  implicit val chartDataFormats = Json.format[GraphData]
}
