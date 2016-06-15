package com.gu.bard.services

import org.joda.time.LocalDate
import scala.annotation.tailrec

object DateRangeGenerator {

  /**
   * computes all of the week ranges between two given dates. For example if given a startDate of `2016-05-30` and
   * an endDate of `2016-06-06` this will produce a Seq of:
   * Seq(2016-05-16 - 2016-05-22, 2016-05-23 - 2016-05-29, 2016-05-30 - 2016-06-05, 2016-06-06 - 2016-06-12, 2016-06-13 - 2016-06-19)
   *
   * @param startDate
   * @param endDate
   * @return a sequence of week ranges for the given startDate and endDate
   */
  def generateWeekRanges(startDate: LocalDate, endDate: LocalDate): Seq[WeekRange] = {

    @tailrec
    def exec(startDate: LocalDate, endDate: LocalDate, ranges: Seq[WeekRange] = Nil): Seq[WeekRange] = {
      val weekStart = startDate.withDayOfWeek(1)
      val weekEnd = startDate.withDayOfWeek(7)

      if (weekStart.isAfter(endDate))
        ranges
      else
        exec(
          startDate = weekStart.plusWeeks(1),
          endDate = endDate,
          ranges = ranges :+ WeekRange(weekStart, weekEnd)
        )

    }

    exec(startDate, endDate)
  }

}
