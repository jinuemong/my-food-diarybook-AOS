package ac.food.myfooddiarybookaos.detail.mainUi.component

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.state.AddScreenState
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.detail.mainUi.popUp.DeleteDiaryPopUp
import ac.food.myfooddiarybookaos.detail.popup.DetailPopupScreen
import ac.food.myfooddiarybookaos.detail.viewModel.DetailViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.dnd_9th_3_android.gooding.data.root.ScreenRoot

@Composable
fun DetailTopLayer(
    imageSize: Int,
    topDate: String,
    memoFixState: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val popUpState = remember { mutableStateOf(false) }
    val deletePopupState = remember { mutableStateOf(false) }

    Box(
        Modifier
            .height(90.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .align(Alignment.CenterStart)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {
                            detailViewModel.goBack()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.main_left),
                        contentDescription = null
                    )
                }
            }

            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = topDate,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.W500)),
                fontSize = 18.scaledSp(),
                lineHeight = 18.scaledSp(),
            )

            Surface(
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {
                            popUpState.value = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.more_vert_24px),
                        contentDescription = null
                    )
                }

                if (popUpState.value) {
                    DetailPopupScreen(
                        popUpState,
                        fixImage = {
                            popUpState.value = false
                            detailViewModel.diaryState.value
                                ?.addScreenState
                                ?.value = AddScreenState.FIX_IMAGE_IN_DETAIL
                            detailViewModel.appState.value
                                ?.navController
                                ?.navigate("${ScreenRoot.GALLERY}/false/0")
                        },
                        addImage = {
                            popUpState.value = false
                            detailViewModel.diaryState.value
                                ?.addScreenState
                                ?.value = AddScreenState.ADD_IMAGE_IN_DETAIL
                            detailViewModel.appState.value
                                ?.navController
                                ?.navigate("${ScreenRoot.GALLERY}/true/${imageSize}")
                        },
                        fixMemo = {
                            popUpState.value = false
                            memoFixState()
                        },
                        deleteDiary = {
                            popUpState.value = false
                            deletePopupState.value = true
                        }
                    )
                }

                if (deletePopupState.value) {
                    Dialog(onDismissRequest = {
                        deletePopupState.value = false
                    }) {
                        DeleteDiaryPopUp(
                            close = {
                                deletePopupState.value = false
                            },
                            onDelete = {
                                detailViewModel.deleteDiary()
                            }
                        )
                    }
                }
            }
        }

    }
}
