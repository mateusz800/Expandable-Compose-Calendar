package com.mabn.calendarlibrary.core

import java.time.LocalDate


sealed class CalendarIntent {
    class LoadNextDates(
        val startDate: LocalDate,
        val period: Period = Period.WEEK
    ) : CalendarIntent()

    class SelectDate(val date: LocalDate) : CalendarIntent()

    data object ExpandCalendar : CalendarIntent()
    data object CollapseCalendar : CalendarIntent()
}
