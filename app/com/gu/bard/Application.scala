package com.gu.bard

import com.typesafe.scalalogging.StrictLogging
import play.api.libs.json.Json
import play.api.mvc._

class Application extends Controller with StrictLogging {

  def healthcheck = Action {
    Ok("ok")
  }

  def index = Action {
    Ok(views.html.app("Bard"))
  }

}