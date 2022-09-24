package com.mabn.calendarlibrary.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.time.LocalDate

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CalendarPager(
    loadedDates: Array<List<LocalDate>>,
    loadNextDates: (date: LocalDate) -> Unit,
    loadPrevDates: (date: LocalDate) -> Unit,
    content: @Composable (currentPage: Int) -> Unit
) {
    val initialized = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(initialPage = 1)
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == 2) {
            loadNextDates(loadedDates[1][0])
            pagerState.scrollToPage(1)
        }
        if (pagerState.currentPage == 0 && initialized.value) {
            loadPrevDates(loadedDates[0][0])
            pagerState.scrollToPage(1)
        }
    }
    LaunchedEffect(Unit) {
        initialized.value = true
    }
    HorizontalPager(
        count = 3,
        state = pagerState,
        verticalAlignment = Alignment.Top
    ) { currentPage ->
        content(currentPage)
    }
}