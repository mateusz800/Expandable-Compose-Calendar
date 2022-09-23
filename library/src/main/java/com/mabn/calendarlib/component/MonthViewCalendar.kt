package com.mabn.calendarlib.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mabn.calendarlib.core.CalendarIntent
import com.mabn.calendarlib.utils.dayViewModifier
import java.time.LocalDate
import java.time.YearMonth

@Composable
internal fun MonthViewCalendar(
    loadedDates: Array<List<LocalDate>>,
    selectedDate: LocalDate,
    currentMonth: YearMonth,
    loadDatesForMonth: (YearMonth) -> Unit,
    onDayClick: (LocalDate) -> Unit
) {
    CalendarPager(
        loadedDates = loadedDates,
        loadNextDates = { loadDatesForMonth(currentMonth) },
        loadPrevDates = { loadDatesForMonth(currentMonth.minusMonths(2)) } // why 2
    ) { currentPage ->
        LazyVerticalGrid(columns = GridCells.Fixed(7), verticalArrangement = Arrangement.Top) {
            itemsIndexed(loadedDates[currentPage]) { index, date ->
                DayView(
                    date,
                    isSelected = selectedDate == date,
                    onDayClick = { onDayClick(date) },
                    weekDayLabel = index < 7,
                    modifier = Modifier.dayViewModifier(date, currentMonth, monthView = true)
                )
            }
        }
    }
}