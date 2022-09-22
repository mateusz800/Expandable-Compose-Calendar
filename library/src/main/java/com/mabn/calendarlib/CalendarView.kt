package com.mabn.calendarlib

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.YearMonth
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mabn.calendarlib.component.InlineCalendar
import com.mabn.calendarlib.component.MonthViewCalendar
import com.mabn.calendarlib.core.CalendarIntent
import com.mabn.calendarlib.core.Period
import com.mabn.calendarlib.utils.getWeekStartDate
import java.time.LocalDate

@Composable
fun CalendarView() {
    val viewModel: CalendarViewModel = viewModel()
    val loadedDates = viewModel.visibleDates.collectAsState()
    val calendarExpanded = viewModel.calendarExpanded.collectAsState()
    CalendarView(
        loadedDates = loadedDates.value,
        onIntent = viewModel::onIntent,
        calendarExpanded = calendarExpanded.value
    )
}


@Composable
private fun CalendarView(
    loadedDates: Array<List<LocalDate>>,
    onIntent: (CalendarIntent) -> Unit,
    calendarExpanded: Boolean
) {
    val currentMonth = if (!calendarExpanded) YearMonth.of(
        loadedDates[1][0].year,
        loadedDates[1][0].month
    ) else YearMonth.of(
        loadedDates[1][loadedDates[1].size / 2].year,
        loadedDates[1][loadedDates[1].size / 2].month
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
            MonthViewCalendar(loadedDates,
                currentMonth = currentMonth,
                loadDatesForMonth = { yearMonth ->
                    onIntent(
                        CalendarIntent.LoadNextDates(
                            yearMonth.atDay(
                                1
                            ), period = Period.MONTH
                        )
                    )
                }
            )
        } else {
            InlineCalendar(
                loadedDates,
                loadNextWeek = { nextWeekDate -> onIntent(CalendarIntent.LoadNextDates(nextWeekDate)) },
                loadPrevWeek = { endWeekDate ->
                    onIntent(
                        CalendarIntent.LoadNextDates(
                            endWeekDate.minusDays(1).getWeekStartDate()
                        )
                    )
                })
        }
    }
}








