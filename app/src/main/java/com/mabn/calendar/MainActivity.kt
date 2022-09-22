package com.mabn.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mabn.calendar.ui.theme.CalendarTheme
import com.mabn.calendarlib.CalendarView
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
    Box(Modifier.padding(vertical=20.dp)) {
        CalendarView()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalendarTheme {
        Calendar()
    }
}