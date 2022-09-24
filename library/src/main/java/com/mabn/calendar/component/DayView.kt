package com.mabn.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle

/**
 * View that represent one day in the calendar
 * @param date date that view should represent
 * @param weekDayLabel flag that indicates if name of week day should be visible above day value
 * @param modifier view modifier
 */
@Composable
fun DayView(
    date: LocalDate,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    weekDayLabel: Boolean = true
) {
    val isCurrentDay = date == LocalDate.now()
    val dayValueModifier =
        if (isCurrentDay) modifier.background(MaterialTheme.colors.primary)
        else if (isSelected) modifier.background(MaterialTheme.colors.primaryVariant)
        else modifier
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .testTag("day_view_column")
    ) {
        if (weekDayLabel) {
            Text(
                DayOfWeek.values()[date.dayOfWeek.value - 1].getDisplayName(
                    TextStyle.SHORT,
                    LocalContext.current.resources.configuration.locales[0]
                ),
                fontSize = 10.sp
            )
        }
        Box(
            dayValueModifier
                .padding(10.dp)
                .clickable { onDayClick(date) }) {
            Text(
                date.dayOfMonth.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}