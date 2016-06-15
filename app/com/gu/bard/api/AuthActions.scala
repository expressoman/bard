package com.gu.bard.api

import com.gu.googleauth.Actions
import play.api.mvc.Call

trait AuthActions extends Actions {
  val loginTarget: Call = routes.Auth.login
}
