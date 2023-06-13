package com.example.foodlog.ui.components.calenderview


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.YearMonth
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodlog.ui.components.calenderview.component.InlineCalendar
import com.example.foodlog.ui.components.calenderview.component.MonthText
import com.example.foodlog.ui.components.calenderview.component.MonthViewCalendar
import com.example.foodlog.ui.components.calenderview.component.ToggleExpandCalendarButton
import com.example.foodlog.ui.components.calenderview.core.CalendarIntent
import com.example.foodlog.ui.components.calenderview.core.CalendarTheme
import com.example.foodlog.ui.components.calenderview.core.Period
import com.example.foodlog.ui.components.calenderview.core.calendarDefaultTheme
import com.example.foodlog.utils.getWeekStartDate
import java.time.LocalDate

@Composable
fun ExpandableCalendar(
    onDayClick: (LocalDate) -> Unit,
    theme: CalendarTheme = calendarDefaultTheme
) {
    val viewModel: CalendarViewModel = viewModel()
    val loadedDates = viewModel.visibleDates.collectAsState()
    val selectedDate = viewModel.selectedDate.collectAsState()
    val calendarExpanded = viewModel.calendarExpanded.collectAsState()
    val currentMonth = viewModel.currentMonth.collectAsState()
    ExpandableCalendar(
        loadedDates = loadedDates.value,
        selectedDate = selectedDate.value,
        currentMonth = currentMonth.value,
        onIntent = viewModel::onIntent,
        calendarExpanded = calendarExpanded.value,
        theme = theme,
        onDayClick = onDayClick
    )
}

@Composable
private fun ExpandableCalendar(
    loadedDates: Array<List<LocalDate>>,
    selectedDate: LocalDate,
    currentMonth: YearMonth,
    onIntent: (CalendarIntent) -> Unit,
    calendarExpanded: Boolean,
    theme: CalendarTheme,
    onDayClick: (LocalDate) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .animateContentSize()
            .background(theme.backgroundColor)
    ) {
        if (calendarExpanded) {
            MonthViewCalendar(
                loadedDates,
                selectedDate,
                theme = theme,
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
                theme = theme,
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
            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .fillMaxWidth()  //fill the max height
                    .height(1.dp),
            )
        }
    }
}
