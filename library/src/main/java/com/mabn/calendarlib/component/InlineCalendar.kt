package com.mabn.calendarlib.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.mabn.calendarlib.DayView
import com.mabn.calendarlib.utils.dayViewModifier
import java.time.LocalDate

@Composable
internal fun InlineCalendar(
    loadedDates: Array<List<LocalDate>>,
    loadNextWeek: (nextWeekDate: LocalDate) -> Unit,
    loadPrevWeek: (endWeekDate: LocalDate) -> Unit
) {
    val itemWidth = LocalConfiguration.current.screenWidthDp / 7
    CalendarPager(
        loadedDates = loadedDates,
        loadNextDates = loadNextWeek,
        loadPrevDates = loadPrevWeek
    ) { currentPage ->
        Row {
            loadedDates[currentPage]
                .forEach { date ->
                    Box(
                        modifier = Modifier.width(itemWidth.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DayView(
                            date,
                            modifier = Modifier.dayViewModifier(date)
                        )
                    }
                }
        }
    }
}