package com.example.foodlog.ui.components.calenderview.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.foodlog.ui.components.calenderview.core.CalendarTheme
import com.example.foodlog.utils.dayViewModifier
import com.google.accompanist.flowlayout.FlowRow
import java.time.LocalDate
import java.time.YearMonth

@Composable
internal fun MonthViewCalendar(
    loadedDates: Array<List<LocalDate>>,
    selectedDate: LocalDate,
    theme: CalendarTheme,
    currentMonth: YearMonth,
    loadDatesForMonth: (YearMonth) -> Unit,
    onDayClick: (LocalDate) -> Unit
) {
    val itemWidth = LocalConfiguration.current.screenWidthDp / 7
    CalendarPager(
        loadedDates = loadedDates,
        loadNextDates = { loadDatesForMonth(currentMonth) },
        loadPrevDates = { loadDatesForMonth(currentMonth.minusMonths(2)) } // why 2
    ) { currentPage ->
        FlowRow(Modifier.height(355.dp)) {
            loadedDates[currentPage].forEachIndexed { index, date ->
                Box(
                    Modifier
                        .width(itemWidth.dp)
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DayView(
                        date,
                        theme = theme,
                        isSelected = selectedDate == date,
                        onDayClick = { onDayClick(date) },
                        weekDayLabel = index < 7,
                        modifier = Modifier.dayViewModifier(date, currentMonth, monthView = true)
                    )
                }
            }
        }
    }
}