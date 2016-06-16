package controllers

import com.gu.bard.auth.AuthActions
import com.gu.bard.views
import com.typesafe.scalalogging.StrictLogging
import play.api.Configuration
import play.api.libs.ws.WSClient
import play.api.mvc._

class Application(override val wsClient: WSClient, override val conf: Configuration) extends Controller with StrictLogging with AuthActions {

  def index = AuthAction {
    Ok(views.html.app("Bard"))
  }

}