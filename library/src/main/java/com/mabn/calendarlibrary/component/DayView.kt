package com.mabn.calendarlibrary.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mabn.calendarlibrary.core.CalendarTheme
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
    theme: CalendarTheme,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    weekDayLabel: Boolean = true
) {
    val isCurrentDay = date == LocalDate.now()
    val dayValueModifier =
        if (isCurrentDay) modifier.background(
            theme.selectedDayBackgroundColor.copy(alpha = 0.5f),
            shape = theme.dayShape
        )
        else if (isSelected) modifier.background(
            theme.selectedDayBackgroundColor,
            shape = theme.dayShape
        )
        else modifier.background(theme.dayBackgroundColor, shape = theme.dayShape)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .heightIn(max = if (weekDayLabel) 50.dp + 20.dp else 50.dp)
            .widthIn(max = 50.dp)
            .testTag("day_view_column")
    ) {
        if (weekDayLabel) {
            Text(
                DayOfWeek.values()[date.dayOfWeek.value - 1].getDisplayName(
                    TextStyle.SHORT,
                    LocalContext.current.resources.configuration.locales[0]
                ),
                fontSize = 10.sp,
                color = theme.weekDaysTextColor
            )
        }
        Box(
            dayValueModifier
                .padding(5.dp)
                .aspectRatio(1f)
                .clickable { onDayClick(date) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                date.dayOfMonth.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = if (isSelected || isCurrentDay) theme.selectedDayValueTextColor else theme.dayValueTextColor
            )
        }
    }
}