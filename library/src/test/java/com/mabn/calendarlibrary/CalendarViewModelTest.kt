package com.mabn.calendarlibrary

import com.mabn.calendarlibrary.core.CalendarIntent
import com.mabn.calendarlibrary.core.Period
import com.mabn.calendarlibrary.core.RelativePosition
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import java.time.LocalDate

class CalendarViewModelTest {

    @InjectMocks
    private lateinit var calendarViewModel: CalendarViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()


    @Before
    @OptIn(ExperimentalCoroutinesApi::class)
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `should expand calendar`() {
        calendarViewModel.onIntent(CalendarIntent.ExpandCalendar)
        assertTrue(calendarViewModel.calendarExpanded.value)
    }

    @Test
    fun `should collapse calendar`() {
        calendarViewModel.onIntent(CalendarIntent.CollapseCalendar)
        assertFalse(calendarViewModel.calendarExpanded.value)
    }

    @Test
    fun `should select date`() {
        val date = LocalDate.now()
        calendarViewModel.onIntent(CalendarIntent.SelectDate(date))
        assertEquals(date, calendarViewModel.selectedDate.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should load next week`() {
        val beginningOfCurrentWeek = LocalDate.now()
            .minusDays(LocalDate.now().dayOfWeek.value.toLong() - 1)
        val beginningOfNextWeek = beginningOfCurrentWeek
            .plusWeeks(1)
        val endOfNextWeek = beginningOfCurrentWeek
            .plusWeeks(2)
            .minusDays(1)
        runTest {
            calendarViewModel.onIntent(
                CalendarIntent.LoadNextDates(
                    beginningOfCurrentWeek,
                    Period.WEEK
                )
            )
            advanceUntilIdle()
            assertEquals(
                beginningOfNextWeek,
                calendarViewModel.visibleDates.value[RelativePosition.CURRENT.ordinal].first()
            )
            assertEquals(
                endOfNextWeek,
                calendarViewModel.visibleDates.value[RelativePosition.CURRENT.ordinal].last()
            )
        }
    }
}