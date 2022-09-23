package com.mabn.calendar.core

import java.time.LocalDate


sealed class CalendarIntent {
    class LoadNextDates(
        val startDate: LocalDate,
        val period: Period = Period.WEEK
    ) : CalendarIntent()

    object ExpandCalendar : CalendarIntent()
    object CollapseCalendar : CalendarIntent()
}
