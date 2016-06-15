package com.gu.bard.services

import com.gu.bard.models.DateParameters
import com.restfb.{ DefaultFacebookClient, FacebookClient, Parameter, Version }
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

  def get[T](connection: String, dateParameters: DateParameters, period: String, client: FacebookClient)(implicit tag: ClassTag[T]): Option[Seq[T]] = {

    val tryFetch = Try[List[T]](
      client.fetchConnection[T](connection, tag.runtimeClass.asInstanceOf[Class[T]],
      Parameter.`with`(Since, dateParameters.fromAsString),
      Parameter.`with`(Until, dateParameters.toAsString),
      Parameter.`with`(Period, period)).getData.asScala.toList
    )

    tryFetch match {
      case Failure(e) =>
        logger.warn(s"Could not retrieve data for connection: $connection", e)
        None
      case Success(insights) => Some(insights)
    }

  }

}
