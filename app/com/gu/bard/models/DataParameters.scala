package com.gu.bard.models

import org.joda.time.LocalDate
import org.joda.time.format.{ DateTimeFormat, DateTimeFormatter }
import org.scalactic.{ Bad, Good, Or }

import scala.util.{ Success, Try }

case class DateParameters(fromDateTime: LocalDate, toDateTime: LocalDate) {

  def to = toDateTime
  def from = fromDateTime
  def fromAsString: String = this.fromDateTime.toString(DateParameters.formatter)
  def toAsString: String = this.toDateTime.toString(DateParameters.formatter)

}

object DateParameters {

  private val DateFormatPattern = "yyyy-MM-dd"
  val formatter: DateTimeFormatter = DateTimeFormat.forPattern(DateFormatPattern)

  def apply(from: String, to: String): DateParameters Or CustomError = {
    val fromDate = Try(parseDateTime(from))
    val toDate = Try(parseDateTime(to))

    (fromDate, toDate) match {
      case (Success(f), Success(t)) => Good(DateParameters(f, t))
      case _ => Bad(InvalidDateTimeParameter(s"Date parameter(s) provided is invalid. Dates must be in the format $DateFormatPattern"))
    }
  }

  private def parseDateTime(dateTime: String) = formatter.parseLocalDate(dateTime)
}