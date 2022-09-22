package com.mabn.calendarlib

import com.mabn.calendarlib.utils.getWeekStartDate
import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate


internal class UtilsTest {
    @Test
    fun `get date for the first day of the week (default monday)`() {
        val date = LocalDate.of(2022, 9, 21)
        Assert.assertEquals(date.dayOfWeek, DayOfWeek.WEDNESDAY)
        val startWeekDate = LocalDate.now().getWeekStartDate()
        Assert.assertEquals(startWeekDate.dayOfWeek, DayOfWeek.MONDAY)
        Assert.assertEquals(startWeekDate.dayOfMonth, 19)
        Assert.assertEquals(startWeekDate.monthValue, 9)
        Assert.assertEquals(startWeekDate.year, 2022)
    }

    @Test
    fun `get date for the first day of the week which is sunday`() {
        val date = LocalDate.of(2022, 9, 21)
        Assert.assertEquals(date.dayOfWeek, DayOfWeek.WEDNESDAY)
        val startWeekDate = LocalDate.now().getWeekStartDate(DayOfWeek.SUNDAY)
        Assert.assertEquals(startWeekDate.dayOfWeek, DayOfWeek.SUNDAY)
        Assert.assertEquals(startWeekDate.dayOfMonth, 18)
        Assert.assertEquals(startWeekDate.monthValue, 9)
        Assert.assertEquals(startWeekDate.year, 2022)
    }
}