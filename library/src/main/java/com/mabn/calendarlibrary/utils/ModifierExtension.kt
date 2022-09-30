package com.mabn.calendarlibrary.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import java.time.LocalDate
import java.time.YearMonth

internal fun Modifier.dayViewModifier(
    date: LocalDate,
    currentMonth: YearMonth? = null,
    monthView: Boolean = false
): Modifier = this.then(
    Modifier.alpha(
        if ((!monthView && date.isBefore(LocalDate.now())) ||
            (currentMonth != null && date.isAfter(currentMonth.atEndOfMonth())) ||
            (monthView && currentMonth != null && date.isBefore(currentMonth.atDay(1))))
            0.5f else 1f
    )
)
