package com.gu.bard.models

import org.joda.time.LocalDate

case class WeekRange(startOfWeek: LocalDate, endOfWeek: LocalDate) {
  override def toString = s"$startOfWeek - $endOfWeek"

  def isInThisWeek(date: LocalDate) =
    // add 1 to each day so the beginning and end of week are included.
    date.isAfter(startOfWeek.minusDays(1)) && date.isBefore(endOfWeek.plusDays(1))
}
