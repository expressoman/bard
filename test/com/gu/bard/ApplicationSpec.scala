package com.gu.bard

import com.gu.bard.api.Application
import com.gu.googleauth.GoogleAuthConfig
import org.scalatest.{ FlatSpec, Matchers }
import play.api.test.FakeRequest
import play.api.test.Helpers._

class ApplicationSpec extends FlatSpec with Matchers {

  val fakeAuthConfig = GoogleAuthConfig("", "", "", None)
  val application = new Application(fakeAuthConfig)

  it should "return a 200 if application is up and running when hitting healthcheck endpoint" in {
    val resp = application.healthcheck.apply(FakeRequest())
    status(resp) should be(OK)
    contentAsString(resp) should be("ok")
  }

}
