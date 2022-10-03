package com.mabn.calendarlibrary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabn.calendarlibrary.core.CalendarIntent
import com.mabn.calendarlibrary.core.Period
import com.mabn.calendarlibrary.utils.*
import com.mabn.calendarlibrary.utils.getMonthStartDate
import com.mabn.calendarlibrary.utils.getNextDates
import com.mabn.calendarlibrary.utils.getRemainingDatesInWeek
import com.mabn.calendarlibrary.utils.getWeekStartDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

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

    val currentMonth: StateFlow<YearMonth>
        get() = calendarExpanded.zip(visibleDates) { isExpanded, dates ->
            if (isExpanded) {
                dates[1][dates[1].size / 2].yearMonth()
            } else {
                if (dates[1].count { it.month == dates[1][0].month } > 3)
                    dates[1][0].yearMonth()
                else
                    dates[1][dates[1].size - 1].yearMonth()
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, LocalDate.now().yearMonth())


    private val _calendarExpanded = MutableStateFlow(false)
    val calendarExpanded: StateFlow<Boolean> = _calendarExpanded


    fun onIntent(intent: CalendarIntent) {
        when (intent) {
            CalendarIntent.ExpandCalendar -> {
                calculateCalendarDates(
                    startDate = currentMonth.value.minusMonths(1).atDay(1),
                    period = Period.MONTH
                )
                _calendarExpanded.value = true
            }
            CalendarIntent.CollapseCalendar -> {
                calculateCalendarDates(
                    startDate = calculateCollapsedCalendarVisibleStartDay()
                        .getWeekStartDate()
                        .minusWeeks(1),
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

    private fun calculateCollapsedCalendarVisibleStartDay(): LocalDate {
        val halfOfMonth = visibleDates.value[1][visibleDates.value[1].size / 2]
        val visibleMonth = YearMonth.of(halfOfMonth.year, halfOfMonth.month)
        return if (selectedDate.value.month == visibleMonth.month && selectedDate.value.year == visibleMonth.year)
            selectedDate.value
        else visibleMonth.atDay(1)
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
