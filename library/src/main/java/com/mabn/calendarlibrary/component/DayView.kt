package com.example.foodlog.ui.components.calenderview.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodlog.ui.components.calenderview.core.CalendarTheme
import com.example.foodlog.ui.theme.MontserratFont
import com.example.foodlog.ui.theme.appGradientStart
import com.example.foodlog.ui.theme.textColor
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
    /*val dayValueModifier =
        if (isCurrentDay) modifier.background(
            theme.selectedDayBackgroundColor.copy(alpha = 0.5f),
            shape = theme.dayShape
        )
        else if (isSelected) modifier.background(
            theme.selectedDayBackgroundColor,
            shape = theme.dayShape
        )
        else modifier.background(theme.dayBackgroundColor, shape = theme.dayShape)        )*/

        Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .heightIn(max = if (weekDayLabel) 50.dp + 20.dp else 50.dp)
            .widthIn(max = 50.dp)
            .testTag("day_view_column")
    ) {

        Box(
            modifier
                .clickable { onDayClick(date) },
            contentAlignment = Alignment.Center
        ) {

            Column {

                Text(
                    date.dayOfMonth.toString(),
                    fontWeight = FontWeight.Bold,
                    fontFamily = MontserratFont ,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = if (isSelected || isCurrentDay) theme.selectedDayValueTextColor else MaterialTheme.colors.textColor
                )

                if (weekDayLabel) {
                    Text(
                        DayOfWeek.values()[date.dayOfWeek.value - 1].getDisplayName(
                            TextStyle.SHORT,
                            LocalContext.current.resources.configuration.locales[0]
                        ),
                        fontSize = 10.sp,
                        fontFamily = MontserratFont ,
                        color =   if (isSelected || isCurrentDay) theme.selectedDayValueTextColor else MaterialTheme.colors.textColor,
                        modifier = Modifier.padding(top = 3.dp, bottom = 3.dp)
                    )
                }

            }
        }

        Card(
            backgroundColor = appGradientStart,
            modifier = Modifier
                .padding(top = 2.dp)
                .fillMaxWidth()  //fill the max height
                .height(if (isSelected || isCurrentDay) 5.dp else 0.dp),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 2.dp,
                bottomStart = 2.dp,
            )
        ){}
    }
}