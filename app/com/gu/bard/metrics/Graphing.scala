package com.gu.bard.metrics

import com.gu.bard.models.{ GraphDataValue, WeekRange }
import org.joda.time.DateTime

trait Graphing {

  def graphDataValues[T, U](weekRanges: Seq[WeekRange], data: Seq[T])(dateSource: T => U)(compute: Seq[T] => String): Seq[GraphDataValue] = {

    def inThisWeek(range: WeekRange, t: T) = range.isInThisWeek(date = new DateTime(dateSource(t)).toLocalDate)

    weekRanges map { range =>
      val dataForWeek = data.filter { datum => inThisWeek(range, datum) }
      GraphDataValue((compute(dataForWeek)).toString, range.toString)
    }

  }

}
