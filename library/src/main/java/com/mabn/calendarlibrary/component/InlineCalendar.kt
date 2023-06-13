package com.mabn.calendarlibrary.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.mabn.calendarlibrary.core.CalendarTheme
import com.mabn.calendarlibrary.utils.dayViewModifier
import java.time.LocalDate

@Composable
internal fun InlineCalendar(
    loadedDates: Array<List<LocalDate>>,
    selectedDate: LocalDate,
    theme: CalendarTheme,
    loadNextWeek: (nextWeekDate: LocalDate) -> Unit,
    loadPrevWeek: (endWeekDate: LocalDate) -> Unit,
    onDayClick: (LocalDate) -> Unit
) {
    val itemWidth = LocalConfiguration.current.screenWidthDp / 8
    CalendarPager(
        loadedDates = loadedDates,
        loadNextDates = loadNextWeek,
        loadPrevDates = loadPrevWeek
    ) { currentPage ->
        Row() {
            loadedDates[currentPage]
                .forEach { date ->
                    Box(
                        modifier = Modifier
                            .width(itemWidth.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DayView(
                            date,
                            theme = theme,
                            isSelected = selectedDate == date,
                            onDayClick = onDayClick,
                            modifier = Modifier.dayViewModifier(date)
                        )
                    }
                }
        }
    }
}