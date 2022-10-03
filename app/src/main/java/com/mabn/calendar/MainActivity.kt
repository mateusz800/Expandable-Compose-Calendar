package com.mabn.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mabn.calendarlibrary.ExpandableCalendar
import com.mabn.calendar.ui.theme.CalendarTheme
import com.mabn.calendarlibrary.core.calendarDefaultTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Calendar()
                }
            }
        }
    }
}

@Composable
fun Calendar() {
    val currentDate = remember { mutableStateOf(LocalDate.now()) }
    Column() {
        ExpandableCalendar(
            theme = calendarDefaultTheme.copy(
                dayShape = CircleShape,
                backgroundColor = Color.Black,
                selectedDayBackgroundColor = Color.White,
                dayValueTextColor = Color.White,
                selectedDayValueTextColor = Color.Black,
                headerTextColor = Color.White,
                weekDaysTextColor = Color.White
            ), onDayClick = {
                currentDate.value = it
            })
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text("Selected date: ${currentDate.value}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalendarTheme {
        Calendar()
    }
}