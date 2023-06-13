package com.example.foodlog.ui.components.calenderview.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.foodlog.ui.components.calenderview.core.CalendarTheme
import com.example.foodlog.utils.dayViewModifier
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