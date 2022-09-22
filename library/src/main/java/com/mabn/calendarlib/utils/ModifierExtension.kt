package com.mabn.calendarlib.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import java.time.LocalDate
import java.time.YearMonth

internal fun Modifier.dayViewModifier(
    date: LocalDate,
    currentMonth: YearMonth? = null,
    monthView: Boolean = false
): Modifier = composed {
    val alpha: Float by animateFloatAsState(
        targetValue = if ((!monthView && date.isBefore(LocalDate.now())) ||
            (currentMonth != null && date.isAfter(currentMonth.atEndOfMonth())) ||
            (monthView && currentMonth != null && date.isBefore(currentMonth.atDay(1)))
        ) 0.5f else 1f,
        animationSpec = tween()
    )
    this.then(Modifier.alpha(alpha))
}