package com.gu.bard.models

import play.api.libs.json.Json

case class Page(prettyPageName: String, fbPageName: String, graphs: Seq[Graph])
object Page {
  implicit val pageFormats = Json.format[Page]
}
