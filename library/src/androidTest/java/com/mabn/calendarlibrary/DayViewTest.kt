package com.mabn.calendarlibrary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.mabn.calendarlibrary.component.DayView
import com.mabn.calendarlibrary.utils.getBackgroundColor
import org.junit.*
import org.junit.runners.MethodSorters
import java.time.LocalDate

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
internal class DayViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun todayIsHighlightedTest() {
        val todayDate = LocalDate.now()
        val tomorrowDate = LocalDate.now().plusDays(1)
        composeTestRule.setContent {
            Surface(Modifier.background(Color.White)){
                Row {
                    DayView(todayDate, onDayClick = {})
                    DayView(tomorrowDate, onDayClick = {})
                }
            }
        }
        fun findNode(date:LocalDate) = hasTestTag("day_view_column").and(hasAnyChild(hasText(date.dayOfMonth.toString())))
        val todayBackgroundColor = composeTestRule.onNode(findNode(todayDate)).getBackgroundColor().toArgb()
        val tomorrowBackgroundColor = composeTestRule.onNode(findNode(tomorrowDate)).getBackgroundColor().toArgb()
        Assert.assertNotEquals(todayBackgroundColor, tomorrowBackgroundColor)
    }
}