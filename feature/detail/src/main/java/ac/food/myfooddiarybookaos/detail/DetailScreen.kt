package ac.food.myfooddiarybookaos.detail

import ac.food.myfooddiarybookaos.data.dataCalendar.viewModel.TodayViewModel
import ac.food.myfooddiarybookaos.data.state.ApplicationState
import ac.food.myfooddiarybookaos.data.state.DetailFixState
import ac.food.myfooddiarybookaos.data.state.DiaryState
import ac.food.myfooddiarybookaos.detail.function.DiaryViewState
import ac.food.myfooddiarybookaos.detail.locationUi.ui.DetailLocationScreen
import ac.food.myfooddiarybookaos.detail.mainUi.ui.DetailMemoScreen
import ac.food.myfooddiarybookaos.detail.mainUi.ui.MainDetailScreen
import ac.food.myfooddiarybookaos.detail.viewModel.DetailViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(
    isViewUpdate: MutableState<Boolean>,
    appState: ApplicationState,
    diaryState: DiaryState,
    diaryFixState: DetailFixState,
    detailViewModel: DetailViewModel = hiltViewModel(),
    todayViewModel: TodayViewModel = hiltViewModel()
) {
    BackHandler(enabled = true, onBack = {
        detailViewModel.goBack()
    })

    val diaryDetail = detailViewModel.diaryDetail.collectAsState().value
    val topDate = todayViewModel.getTopDate(diaryDetail.date)
    // diary state
    val currentViewState = remember { mutableStateOf(DiaryViewState.MAIN) }
    val viewUpdate = rememberSaveable { mutableStateOf(true) }

    if (viewUpdate.value) {
        LaunchedEffect(Unit) {
            isViewUpdate.value = true
            detailViewModel.initAppState(appState, diaryState)
            detailViewModel.setDiaryDetail(
                initData = {
                    diaryFixState.initMemo(it)
                }
            )
            viewUpdate.value = false
        }
    }

    // 현재 뷰 상태
    when (currentViewState.value) {
        DiaryViewState.MAIN -> {
            MainDetailScreen(
                viewUpdate,
                diaryState,
                diaryFixState,
                topDate,
                initMemo = {
                    diaryFixState.initMemo(diaryDetail)
                },
                currentViewState
            )
        }

        DiaryViewState.MEMO -> {
            DetailMemoScreen(
                diaryFixState,
                currentViewState
            )
        }

        DiaryViewState.LOCATION -> {
            DetailLocationScreen(diaryFixState, currentViewState)
        }
    }
}
