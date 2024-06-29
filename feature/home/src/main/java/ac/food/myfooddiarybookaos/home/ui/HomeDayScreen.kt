package ac.food.myfooddiarybookaos.home.ui

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.component.ErrorPage
import ac.food.myfooddiarybookaos.data.component.LoadPage
import ac.food.myfooddiarybookaos.data.component.coloredInnerShadow
import ac.food.myfooddiarybookaos.data.dataCalendar.viewModel.TodayViewModel
import ac.food.myfooddiarybookaos.data.state.AddScreenState
import ac.food.myfooddiarybookaos.data.state.ApplicationState
import ac.food.myfooddiarybookaos.data.state.DiaryState
import ac.food.myfooddiarybookaos.data.state.LoadState
import ac.food.myfooddiarybookaos.home.component.HomeDayTopLayer
import ac.food.myfooddiarybookaos.home.item.ItemHomeDay
import ac.food.myfooddiarybookaos.home.viewModel.HomeViewModel
import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeDayScreen(
    isViewUpdate: MutableState<Boolean>,
    diaryState: DiaryState,
    appState: ApplicationState,
    todayViewModel: TodayViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val state = homeViewModel.state.collectAsState()
    BackHandler(enabled = true, onBack = {
        backStage(diaryState, appState)
    })

    val homeDays = homeViewModel.homeDayInDiary.collectAsState()
    val prevDay = homeViewModel.homeDayPrev.collectAsState()
    val nextDay = homeViewModel.homeDayNext.collectAsState()
    val viewUpdate = rememberSaveable { mutableStateOf(true) }
    val currentDate = diaryState.currentHomeDay.value

    fun updateView() {
        homeViewModel.getHomeDayInDiary(currentDate)
        scope.launch {
            delay(500)
            viewUpdate.value = false
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.initState(appState, diaryState)
        updateView()
    }

    if (viewUpdate.value) {
        updateView()
    }

    if (isViewUpdate.value) {
        viewUpdate.value = true
        isViewUpdate.value = false
    }

    if (diaryState.isSelectedGallery.value) {
        if (diaryState.addScreenState.value == AddScreenState.ADD_HOME_DAY) {
            homeViewModel.makeNewDiary(
                currentDate,
                diaryState.multiPartList,
                diaryState = { isUpdate ->
                    if (isUpdate) {
                        viewUpdate.value = true
                    }
                },
                toastMessage = {
                    appState.toastState.value = "하루에 식사일기는 최대10건까지 등록할 수 있어요."
                }
            )
            diaryState.resetSelectedInfo()
        }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(start = 10.dp, bottom = 9.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clickable {
                        backStage(diaryState, appState)
                    }
                    .align(Alignment.BottomStart),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.main_left),
                    contentDescription = null
                )
            }
        }
        Divider(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .coloredInnerShadow(
                    color = colorResource(id = R.color.black_10),
                    offsetY = 1.dp,
                    blurRadius = 4.dp
                )
        )

        Spacer(modifier = Modifier.height(6.dp))

        when (state.value) {
            LoadState.Loading -> {
                LoadPage()
            }

            LoadState.Fail -> {
                ErrorPage {

                }
            }

            LoadState.Init -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp)
                ) {
                    todayViewModel.apply {
                        HomeDayTopLayer(
                            currentDate = getTopDate(currentDate),
                            prevDate = getTopDate(prevDay.value),
                            nextDate = getTopDate(nextDay.value),
                            onPrev = {
                                if (prevDay.value.isNotEmpty()) {
                                    diaryState.currentHomeDay.value = prevDay.value
                                    viewUpdate.value = true
                                }
                            },
                            onNext = {
                                if (nextDay.value.isNotEmpty()) {
                                    diaryState.currentHomeDay.value = nextDay.value
                                    viewUpdate.value = true
                                }
                            },
                        )
                    }
                }

                // item 상, 하로 8dp, 첫 item 상 (17dp - 8dp = 9dp)
                Spacer(modifier = Modifier.height(9.dp))

                if (!viewUpdate.value) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        state = rememberLazyListState()
                    ) {
                        items(homeDays.value) {
                            ItemHomeDay(
                                homeDay = it,
                                clickDiary = {
                                    homeViewModel.goDetailView(it.id)
                                },
                            )
                        }
                    }
                }
            }
        }
    }

}

fun backStage(diaryState: DiaryState, appState: ApplicationState) {
    diaryState.resetHomeDay()
    appState.navController.popBackStack()
}
