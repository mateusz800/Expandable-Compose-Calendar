package com.mabn.calendarlibrary

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.YearMonth
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mabn.calendarlibrary.core.CalendarIntent
import com.mabn.calendarlibrary.core.Period
import com.mabn.calendarlibrary.utils.getWeekStartDate
import com.mabn.calendarlibrary.component.InlineCalendar
import com.mabn.calendarlibrary.component.MonthViewCalendar
import java.time.LocalDate

@Composable
fun ExpandableCalendar(onDayClick: (LocalDate) -> Unit) {
    val viewModel: CalendarViewModel = viewModel()
    val loadedDates = viewModel.visibleDates.collectAsState()
    val selectedDate = viewModel.selectedDate.collectAsState()
    val calendarExpanded = viewModel.calendarExpanded.collectAsState()
    ExpandableCalendar(
        loadedDates = loadedDates.value,
        selectedDate = selectedDate.value,
        onIntent = viewModel::onIntent,
        calendarExpanded = calendarExpanded.value,
        onDayClick = onDayClick
    )
}

@Composable
private fun ExpandableCalendar(
    loadedDates: Array<List<LocalDate>>,
    selectedDate: LocalDate,
    onIntent: (CalendarIntent) -> Unit,
    calendarExpanded: Boolean,
    onDayClick: (LocalDate) -> Unit
) {
    val currentMonth = if (!calendarExpanded) YearMonth.of(
        loadedDates[1][0].year,
        loadedDates[1][0].month
    ) else YearMonth.of(
        loadedDates[1][loadedDates[1].size / 2].year,
        loadedDates[1][loadedDates[1].size / 2].month
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.animateContentSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Spacer(Modifier.weight(1f))
            MonthText(selectedMonth = currentMonth)
            Spacer(Modifier.weight(1f))
            ToggleExpandCalendarButton(isExpanded = calendarExpanded,
                expand = { onIntent(CalendarIntent.ExpandCalendar) },
                collapse = { onIntent(CalendarIntent.CollapseCalendar) })
        }
        if (calendarExpanded) {
            MonthViewCalendar(
                loadedDates,
                selectedDate,
                currentMonth = currentMonth,
                loadDatesForMonth = { yearMonth ->
                    onIntent(
                        CalendarIntent.LoadNextDates(
                            yearMonth.atDay(
                                1
                            ), period = Period.MONTH
                        )
                    )
                },
                onDayClick = {
                    onIntent(CalendarIntent.SelectDate(it))
                    onDayClick(it)
                }
            )
        } else {
            InlineCalendar(
                loadedDates,
                selectedDate,
                loadNextWeek = { nextWeekDate -> onIntent(CalendarIntent.LoadNextDates(nextWeekDate)) },
                loadPrevWeek = { endWeekDate ->
                    onIntent(
                        CalendarIntent.LoadNextDates(
                            endWeekDate.minusDays(1).getWeekStartDate()
                        )
                    )
                },
                onDayClick = {
                    onIntent(CalendarIntent.SelectDate(it))
                    onDayClick(it)
                }
            )
        }
    }
}








