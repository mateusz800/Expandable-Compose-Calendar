package com.mabn.calendarlibrary

import com.mabn.calendarlibrary.utils.getRemainingDatesInMonth
import com.mabn.calendarlibrary.utils.getRemainingDatesInWeek
import com.mabn.calendarlibrary.utils.getWeekStartDate
import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate


internal class LocalDateExtensionTest{
    @Test
    fun `get remaining dates in month`(){
        val date = LocalDate.of(2022, 8, 29)
        val remainingDays = date.getRemainingDatesInMonth()
        Assert.assertEquals(3,remainingDays.size)
        Assert.assertEquals(LocalDate.of(2022, 8, 29), remainingDays[0])
        Assert.assertEquals( LocalDate.of(2022, 8,31),remainingDays[2])
    }

    @Test
    fun `get remaining dates in week`(){
        val date = LocalDate.of(2022,9,30)
        val remainingDays = date.getRemainingDatesInWeek()
        Assert.assertEquals(2, remainingDays.size)
        Assert.assertEquals(LocalDate.of(2022, 10, 1), remainingDays[0])
        Assert.assertEquals( LocalDate.of(2022, 10,2),remainingDays[1])
    }

    @Test
    fun `get date for the first day of the week (default monday)`() {
        val date = LocalDate.of(2022, 9, 21)
        Assert.assertEquals(date.dayOfWeek, DayOfWeek.WEDNESDAY)
        val startWeekDate = date.getWeekStartDate()
        Assert.assertEquals(startWeekDate.dayOfWeek, DayOfWeek.MONDAY)
        Assert.assertEquals(startWeekDate.dayOfMonth, 19)
        Assert.assertEquals(startWeekDate.monthValue, 9)
        Assert.assertEquals(startWeekDate.year, 2022)
    }

    @Test
    fun `get date for the first day of the week which is sunday`() {
        val date = LocalDate.of(2022, 9, 21)
        Assert.assertEquals(date.dayOfWeek, DayOfWeek.WEDNESDAY)
        val startWeekDate = date.getWeekStartDate(DayOfWeek.SUNDAY)
        Assert.assertEquals(startWeekDate.dayOfWeek, DayOfWeek.SUNDAY)
        Assert.assertEquals(startWeekDate.dayOfMonth, 18)
        Assert.assertEquals(startWeekDate.monthValue, 9)
        Assert.assertEquals(startWeekDate.year, 2022)
    }
}