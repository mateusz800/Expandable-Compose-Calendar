package com.mabn.calendarlib

import com.mabn.calendarlib.utils.getRemainingDatesInMonth
import com.mabn.calendarlib.utils.getRemainingDatesInWeek
import org.junit.Assert
import org.junit.Test
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
}