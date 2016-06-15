package com.gu.bard.models

import play.api.libs.json.Json

case class FacebookPages(pages: Seq[String])
object FacebookPages {
  implicit val facebookPagesFormats = Json.format[FacebookPages]
}
