package com.mabn.calendarlib

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabn.calendarlib.core.CalendarIntent
import com.mabn.calendarlib.core.Period
import com.mabn.calendarlib.utils.*
import com.mabn.calendarlib.utils.getMonthStartDate
import com.mabn.calendarlib.utils.getNextDates
import com.mabn.calendarlib.utils.getRemainingDatesInWeek
import com.mabn.calendarlib.utils.getWeekStartDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel : ViewModel() {
    private val _visibleDates =
        MutableStateFlow(
            calculateCollapsedCalendarDays(
                startDate = LocalDate.now().getWeekStartDate().minusWeeks(1)
            )
        )
    val visibleDates: StateFlow<Array<List<LocalDate>>> = _visibleDates

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _calendarExpanded = MutableStateFlow(false)
    val calendarExpanded: StateFlow<Boolean> = _calendarExpanded


    fun onIntent(intent: CalendarIntent) {
        when (intent) {
            CalendarIntent.ExpandCalendar -> {
                calculateCalendarDates(
                    startDate = LocalDate.now().minusMonths(1).getMonthStartDate(),
                    period = Period.MONTH
                )
                _calendarExpanded.value = true
            }
            CalendarIntent.CollapseCalendar -> {
                calculateCalendarDates(
                    startDate = LocalDate.now().getWeekStartDate().minusWeeks(1),
                    period = Period.WEEK
                )
                _calendarExpanded.value = false
            }
            is CalendarIntent.LoadNextDates -> {
                calculateCalendarDates(intent.startDate, intent.period)
            }
            is CalendarIntent.SelectDate -> {
                viewModelScope.launch {
                    _selectedDate.emit(intent.date)
                }
            }
        }
    }

    private fun calculateCalendarDates(
        startDate: LocalDate,
        period: Period = Period.WEEK
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _visibleDates.emit(
                when (period) {
                    Period.WEEK -> calculateCollapsedCalendarDays(startDate)
                    Period.MONTH -> calculateExpandedCalendarDays(startDate)
                }
            )
        }
    }

    private fun calculateCollapsedCalendarDays(startDate: LocalDate): Array<List<LocalDate>> {
        val dates = startDate.getNextDates(21)
        return Array(3) {
            dates.slice(it * 7 until (it + 1) * 7)
        }
    }

    private fun calculateExpandedCalendarDays(startDate: LocalDate): Array<List<LocalDate>> {
        val array = Array(3) { monthIndex ->
            val monthFirstDate = startDate.plusMonths(monthIndex.toLong())
            val monthLastDate = monthFirstDate.plusMonths(1).minusDays(1)
            val weekBeginningDate = monthFirstDate.getWeekStartDate()
            if (weekBeginningDate != monthFirstDate) {
                weekBeginningDate.getRemainingDatesInMonth()
            } else {
                listOf()
            } +
                    monthFirstDate.getNextDates(monthFirstDate.month.length(monthFirstDate.isLeapYear)) +
                    monthLastDate.getRemainingDatesInWeek()
        }
        return array
    }
}
