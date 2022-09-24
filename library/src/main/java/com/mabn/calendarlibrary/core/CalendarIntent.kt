package com.mabn.calendarlibrary.core

import java.time.LocalDate


sealed class CalendarIntent {
    class LoadNextDates(
        val startDate: LocalDate,
        val period: Period = Period.WEEK
    ) : CalendarIntent()

    class SelectDate(val date: LocalDate) : CalendarIntent()

    object ExpandCalendar : CalendarIntent()
    object CollapseCalendar : CalendarIntent()
}
