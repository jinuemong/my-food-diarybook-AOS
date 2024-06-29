package ac.food.myfooddiarybookaos.detail.mainUi.ui

import ac.food.myfooddiarybookaos.data.component.ErrorPage
import ac.food.myfooddiarybookaos.data.component.LoadPage
import ac.food.myfooddiarybookaos.data.state.AddScreenState
import ac.food.myfooddiarybookaos.data.state.DetailFixState
import ac.food.myfooddiarybookaos.data.state.DiaryState
import ac.food.myfooddiarybookaos.data.state.LoadState
import ac.food.myfooddiarybookaos.detail.function.DiaryViewState
import ac.food.myfooddiarybookaos.detail.mainUi.component.DetailData
import ac.food.myfooddiarybookaos.detail.mainUi.component.DetailMenuTime
import ac.food.myfooddiarybookaos.detail.mainUi.component.DetailTopLayer
import ac.food.myfooddiarybookaos.detail.mainUi.imageSlider.ImageSliderScreen
import ac.food.myfooddiarybookaos.detail.viewModel.DetailViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainDetailScreen(
    viewUpdate: MutableState<Boolean>,
    diaryState: DiaryState,
    diaryFixState: DetailFixState,
    topDate: String,
    initMemo: () -> Unit,
    currentViewState: MutableState<DiaryViewState>,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val state = detailViewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val diaryDetail = detailViewModel.diaryDetail.collectAsState().value
    val pagerState = rememberPagerState(pageCount = { diaryDetail.images.size })
    // 다이어리 업데이트
    if (diaryState.isSelectedGallery.value) {
        when (diaryState.addScreenState.value) {

            AddScreenState.FIX_IMAGE_IN_DETAIL -> {
                diaryState.fixImageId.value = diaryDetail.images[pagerState.currentPage].imageId
                diaryState.multiPartList.firstOrNull()?.let { firstImage ->
                    detailViewModel.fixDiaryImage(
                        diaryState.fixImageId.value, firstImage,
                        addState = {
                            viewUpdate.value = true
                        }
                    )
                    diaryState.fixImageId.value = -1
                }

            }

            AddScreenState.ADD_IMAGE_IN_DETAIL -> {
                detailViewModel.addDiaryImages(
                    diaryState.currentDiaryDetail.value,
                    diaryState.multiPartList,
                    addState = {
                        viewUpdate.value = true
                    }
                )
            }

            else -> {}
        }
        diaryState.resetSelectedInfo()
    }

    Column {
        DetailTopLayer(
            diaryDetail.images.size,
            topDate,
            memoFixState = {
                initMemo()
                currentViewState.value = DiaryViewState.MEMO
            }
        )

        when (state.value) {
            LoadState.Loading -> {
                LoadPage()
            }

            LoadState.Fail -> {
                ErrorPage {
                    detailViewModel.setDiaryDetail(
                        initData = {
                            diaryFixState.initMemo(it)
                        }
                    )
                }
            }

            LoadState.Init -> {
                Column(
                    modifier = Modifier.verticalScroll(scrollState)
                ) {
                    ImageSliderScreen(diaryDetail.images, pagerState)
                    Surface(
                        modifier = Modifier
                            .clickable {
                                initMemo()
                                currentViewState.value = DiaryViewState.MEMO
                            }
                            .padding(start = 21.dp, top = 25.dp)
                    ) {
                        DetailMenuTime(diaryDetail = diaryDetail)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    DetailData(
                        diaryDetail,
                        fixMemo = {
                            initMemo()
                            currentViewState.value = DiaryViewState.MEMO
                        },
                        fixLocation = {
                            currentViewState.value = DiaryViewState.LOCATION
                        },
                        fixTag = {
                            initMemo()
                            currentViewState.value = DiaryViewState.MEMO
                        }
                    )
                }
            }
        }
    }
}
