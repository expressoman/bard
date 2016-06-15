package com.gu.bard.api

import com.gu.bard.views
import com.gu.googleauth.GoogleAuthConfig
import com.typesafe.scalalogging.StrictLogging
import play.api.mvc._

class Application(val authConfig: GoogleAuthConfig) extends Controller with StrictLogging with AuthActions {

  def healthcheck = Action {
    Ok("ok")
  }

  def index = AuthAction {
    Ok(views.html.app("Bard"))
  }

}