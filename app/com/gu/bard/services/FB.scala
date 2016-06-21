package com.gu.bard.services

import com.gu.bard.models.DateParameters
import com.restfb._
import com.typesafe.scalalogging.StrictLogging

import scala.collection.JavaConverters._
import scala.reflect.ClassTag
import scala.util.{ Failure, Success, Try }

object FB extends StrictLogging {

  private val Since = "since"
  private val Until = "until"
  private val Period = "period"

  def apply(accessToken: String, version: Version = Version.LATEST): DefaultFacebookClient = {
    new DefaultFacebookClient(accessToken, Version.LATEST)
  }

  def getData[T](connection: String, dateParameters: DateParameters, maybePeriod: Option[String] = None, client: FacebookClient)(implicit tag: ClassTag[T]): Option[Seq[T]] = {
    getConnection[T](connection, dateParameters, maybePeriod, client).map(_.getData.asScala.toList)
  }

  def getConnection[T](connection: String, dateParameters: DateParameters, maybePeriod: Option[String] = None, client: FacebookClient)(implicit tag: ClassTag[T]): Option[Connection[T]] = {

    val parameters = Seq(
      Some(Parameter.`with`(Since, dateParameters.fromAsString)),
      Some(Parameter.`with`(Until, dateParameters.toAsString)),
      maybePeriod map (Parameter.`with`(Period, _))
    ).flatten

    val tryFetch = Try[Connection[T]](
      client.fetchConnection[T](connection, tag.runtimeClass.asInstanceOf[Class[T]], parameters: _*)
    )

    tryFetch match {
      case Failure(e) =>
        logger.warn(s"Could not retrieve for connection: $connection", e)
        None
      case Success(insights) => Some(insights)
    }

  }

}
