package com.mabn.calendarlibrary.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mabn.calendarlibrary.core.CalendarTheme
import java.time.YearMonth
import java.time.format.TextStyle

@Composable
fun MonthText(selectedMonth: YearMonth, theme: CalendarTheme, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Text(
        selectedMonth.month.getDisplayName(
            TextStyle.FULL_STANDALONE,
            context.resources.configuration.locales[0]
        ).uppercase() + " " + selectedMonth.year,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = theme.headerTextColor,
        modifier = modifier
    )
}