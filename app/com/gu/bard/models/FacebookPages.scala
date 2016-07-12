package com.gu.bard.models

import play.api.libs.json.Json

case class PageName(name: String, prettyName: String)
object PageName {
  implicit val pageNameFormats = Json.format[PageName]
}

case class FacebookPages(pages: Seq[PageName])
object FacebookPages {
  implicit val facebookPagesFormats = Json.format[FacebookPages]
}
