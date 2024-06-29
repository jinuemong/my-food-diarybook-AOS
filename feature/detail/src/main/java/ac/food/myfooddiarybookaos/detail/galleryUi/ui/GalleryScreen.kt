package ac.food.myfooddiarybookaos.detail.galleryUi.ui

import ac.food.myfooddiarybookaos.data.state.AddScreenState
import ac.food.myfooddiarybookaos.data.state.ApplicationState
import ac.food.myfooddiarybookaos.data.state.DiaryState
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.detail.galleryUi.component.GalleryItemContent
import ac.food.myfooddiarybookaos.detail.galleryUi.component.GalleryTopBar
import ac.food.myfooddiarybookaos.detail.viewModel.GalleryViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun GalleryScreen(
    appState: ApplicationState,
    diaryState: DiaryState,
    isMultiSelectView: Boolean,
    prevCount: Int,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.customGalleryPhotoList.collectAsLazyPagingItems()
    LaunchedEffect(viewModel.currentFolder.value) {
        viewModel.getGalleryPagingImages()
    }
    LaunchedEffect(Unit) {
        viewModel.getFolder()
    }

    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        GalleryTopBar(
            isMultiSelectView = isMultiSelectView,
            prevCount = prevCount,
            selectedImages = viewModel.selectedImages,
            currentDirectory = viewModel.currentFolder.value,
            directories = viewModel.folders,
            setCurrentDirectory = { folder ->
                viewModel.setCurrentFolder(folder)
            },
            backStage = {
                // back
                appState.navController.popBackStack()
            },
            nextStage = {
                // 저장
                if (pagingItems.itemCount > 0) {

                    diaryState.multiPartList = viewModel
                        .getMultiPartFromUri(diaryState.addScreenState.value == AddScreenState.FIX_IMAGE_IN_DETAIL)
                    diaryState.isSelectedGallery.value = true
                    diaryState.updateView()
                    appState.navController.popBackStack()
                }
            }
        )

        if (pagingItems.itemCount == 0) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                Text(
                    text = "이미지가 존재하지 않습니다.",
                    fontSize = 19.scaledSp(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White),
                columns = GridCells.Fixed(3),
            ) {
                items(pagingItems.itemCount) { index ->
                    pagingItems[index]?.let { galleryImage ->
                        GalleryItemContent(
                            galleryImage = galleryImage,
                            selectedImages = viewModel.selectedImages,
                            isMultiSelectView = isMultiSelectView,
                            setSelectImage = { image ->// 이미지 셋
                                if (viewModel.selectedImages.size + prevCount < 5) {
                                    viewModel.addSelectedImage(image)
                                } else {
                                    appState.toastState.value = "사진은 최대5개까지 등록할 수 있어요."
                                }
                            },
                            removeImage = { id ->// 이미지 삭제
                                viewModel.removeSelectedImage(id)
                            }
                        )
                    }
                }
            }
        }
    }

}
