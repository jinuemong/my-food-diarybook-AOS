package ac.food.myfooddiarybookaos.timeline

import ac.food.myfooddiarybookaos.Layout.NotDataView
import ac.food.myfooddiarybookaos.data.component.TopCalendarLayout
import ac.food.myfooddiarybookaos.data.dataCalendar.viewModel.TodayViewModel
import ac.food.myfooddiarybookaos.data.state.ApplicationState
import ac.food.myfooddiarybookaos.data.state.DiaryState
import ac.food.myfooddiarybookaos.timeline.item.TimeLineItem
import ac.food.myfooddiarybookaos.timeline.viewModel.TimeLineViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TimeLineScreen(
    isUpdateView: MutableState<Boolean>,
    appState: ApplicationState,
    diaryState: DiaryState,
    todayViewModel: TodayViewModel = hiltViewModel(),
    timeLineViewModel: TimeLineViewModel = hiltViewModel()
) {
    val viewUpdate = rememberSaveable { mutableStateOf(true) }
    val timeLineData = timeLineViewModel.timeLine.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        timeLineViewModel.initState(appState, diaryState)
        timeLineViewModel.setTimeLineData(todayViewModel.getCurrentTimeLineKey())
        delay(100)
        viewUpdate.value = false
    }

    if (isUpdateView.value) {
        viewUpdate.value = true
        isUpdateView.value = false
    }

    if (viewUpdate.value) {
        rememberCoroutineScope().launch {
            timeLineViewModel.setTimeLineData(todayViewModel.getCurrentTimeLineKey())
            delay(100)
            viewUpdate.value = false
        }
    }


    Column {
        // 캘린더 초기화
        TopCalendarLayout(
            resetData = {
                viewUpdate.value = true
            },
        )

        if (!viewUpdate.value) {
            if (timeLineData.itemCount == 0) {
                NotDataView()
            } else {
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp

                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    items(timeLineData.itemCount) { index ->
                        timeLineData[index]?.let { timeLine ->
                            TimeLineItem(timeLine = timeLine, screenWidth = screenWidth)
                        }
                    }
                    item(1) {
                        Box(Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}
