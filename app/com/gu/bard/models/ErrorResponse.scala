package com.gu.bard.models

import play.api.libs.json.Json

sealed trait CustomError {
  val message: String
}

case class InvalidDateTimeParameter(message: String) extends CustomError

case class ErrorResponse(errorMessage: String)

object ErrorResponse {
  implicit val errorResponseFormats = Json.format[ErrorResponse]
}