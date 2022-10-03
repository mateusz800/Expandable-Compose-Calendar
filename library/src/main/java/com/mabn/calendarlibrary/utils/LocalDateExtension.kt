package com.mabn.calendarlibrary.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

/**
 * @return list of [@param count] next dates
 */
internal fun LocalDate.getNextDates(count: Int): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    repeat(count) { day ->
        dates.add(this.plusDays((day).toLong()))
    }
    return dates
}

/**
 * @return week start date - default monday
 */
internal fun LocalDate.getWeekStartDate(weekStartDay: DayOfWeek = DayOfWeek.MONDAY): LocalDate {
    var date = this
    while (date.dayOfWeek != weekStartDay) {
        date = date.minusDays(1)
    }
    return date
}

/**
 * @return first date of the month
 */
internal fun LocalDate.getMonthStartDate(): LocalDate {
    return LocalDate.of(this.year, this.month, 1)
}

/**
 * @return list of dates remaining in the week
 */
internal fun LocalDate.getRemainingDatesInWeek(weekStartDay: DayOfWeek = DayOfWeek.MONDAY): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    var date = this.plusDays(1)
    while (date.dayOfWeek != weekStartDay) {
        dates.add(date)
        date = date.plusDays(1)
    }
    return dates
}

/**
 * @return list of dates remaining in the month
 */
internal fun LocalDate.getRemainingDatesInMonth(): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    repeat(this.month.length(this.isLeapYear) - this.dayOfMonth + 1) {
        dates.add(this.plusDays(it.toLong()))
    }
    return dates
}

/**
 * @return YearMonth object of given date
 */
internal fun LocalDate.yearMonth(): YearMonth = YearMonth.of(this.year, this.month)

