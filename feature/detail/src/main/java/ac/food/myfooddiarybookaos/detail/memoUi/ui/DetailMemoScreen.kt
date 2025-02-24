package ac.food.myfooddiarybookaos.detail.mainUi.ui

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.state.DetailFixState
import ac.food.myfooddiarybookaos.detail.function.DiaryViewState
import ac.food.myfooddiarybookaos.detail.mainUi.component.DetailLocation
import ac.food.myfooddiarybookaos.detail.memoUi.component.MemoTopLayer
import ac.food.myfooddiarybookaos.detail.memoUi.component.SelectTimeLayer
import ac.food.myfooddiarybookaos.detail.memoUi.component.TypeMemo
import ac.food.myfooddiarybookaos.detail.memoUi.component.TypeTag
import ac.food.myfooddiarybookaos.detail.viewModel.DetailViewModel
import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailMemoScreen(
    diaryFixState: DetailFixState,
    currentViewState: MutableState<DiaryViewState>,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val memoTopColorState = animateColorAsState(
        targetValue = if (
            diaryFixState.checkPrevData(detailViewModel.diaryDetail.value)
        ) {
            colorResource(id = R.color.line_color_deep)
        } else {
            colorResource(id = R.color.main_color)
        }, label = ""
    )

    // 뒤로가기 제어
    BackHandler(enabled = true, onBack = {
        currentViewState.value = DiaryViewState.MAIN
    })

    Column {
        MemoTopLayer(
            backStage = {
                currentViewState.value = DiaryViewState.MAIN
            },
            nextStage = {
                //  상태 저장
                if (!diaryFixState.checkPrevData(detailViewModel.diaryDetail.value)) {
                    detailViewModel.setFixResult(
                        diaryFixState,
                        initCurrentData = {
                            currentViewState.value = DiaryViewState.MAIN
                        }
                    )
                }
            },
            memoTopColorState = memoTopColorState
        )
        Spacer(modifier = Modifier.height(22.dp))
        SelectTimeLayer(diaryFixState.diaryTimeData)

        Column(
            modifier = Modifier.padding(start = 20.dp, end = 10.dp, top = 12.dp)
        ) {
            TypeMemo(
                text = diaryFixState.memo,
                editMemo = { diaryFixState.setMemo(it) },
                focusRequester = focusRequester
            )
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier.clickable {
                    currentViewState.value = DiaryViewState.LOCATION
                }
            ) {
                DetailLocation(diaryFixState.place.value)
            }
            Spacer(modifier = Modifier.height(15.dp))
            TypeTag(
                tags = diaryFixState.tags,
                addTag = {
                    diaryFixState.addTag(it)
                },
            )
        }
    }
}
