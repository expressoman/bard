package com.gu.bard.models

import play.api.libs.json.Json

case class Page(prettyPageName: String, metrics: Seq[Metric])
object Page {
  implicit val pageFormats = Json.format[Page]
}
