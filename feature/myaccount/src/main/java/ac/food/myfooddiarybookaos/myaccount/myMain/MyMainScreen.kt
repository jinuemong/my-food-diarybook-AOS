package ac.food.myfooddiarybookaos.myaccount.myMain

import ac.food.myfooddiarybookaos.api.UserInfoSharedPreferences
import ac.food.myfooddiarybookaos.api.appVersion
import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.TextBox
import ac.food.myfooddiarybookaos.data.component.coloredInnerShadow
import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.myaccount.component.EmailInfo
import ac.food.myfooddiarybookaos.myaccount.component.OptionBox
import ac.food.myfooddiarybookaos.myaccount.component.Statistics
import ac.food.myfooddiarybookaos.myaccount.component.Subject
import ac.food.myfooddiarybookaos.myaccount.navi.MyScreenRoot
import ac.food.myfooddiarybookaos.myaccount.popUp.SendEmailLayer
import ac.food.myfooddiarybookaos.myaccount.viewModel.MyViewModel
import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun MyMainScreen(
    myNavi: NavHostController,
    isUpdateView: MutableState<Boolean>,
    viewModel: MyViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.resetView()
    }

    if (isUpdateView.value) {
        viewModel.resetView()
        isUpdateView.value = false
    }

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val email = remember { mutableStateOf(UserInfoSharedPreferences(context).userEmail ?: "") }
    val infoClickState = remember { mutableStateOf(false) }
    val sendEmailState = remember { mutableStateOf(false) }
    if (infoClickState.value) {
        myNavi.navigate(MyScreenRoot.INFO)
        infoClickState.value = false
    }
    if (sendEmailState.value) {
        Dialog(onDismissRequest = {
            sendEmailState.value = false
        }) {
            SendEmailLayer(
                onClose = {
                    sendEmailState.value = false
                },
                onState = {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.setType("plain/text")
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("my.food.diarybook@gmail.com"))
                    intent.putExtra(Intent.EXTRA_TEXT, it)
                    context.startActivity(intent)
                }
            )
        }
    }
    Column {
        Box(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(bottom = 14.75.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            TextBox(
                text = "마이",
                fontWeight = 500,
                fontFamily = robotoRegular,
                fontSize = 18.scaledSp(),
                color = colorResource(id = R.color.black),
                lineHeight = 18.scaledSp(),
            )
        }
        Divider(
            modifier = Modifier
                .height(2.dp)
                .coloredInnerShadow(
                    color = colorResource(id = R.color.black_10),
                    offsetY = 1.dp,
                    blurRadius = 4.dp
                )
        )


        // 상세 탭
        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 12.dp
                )
                .fillMaxSize()
                // 스크롤 부여
                .verticalScroll(scrollState)
        ) {
            Subject("내 정보")
            // 임시 이메일 -> 실제 이메일 전달
            Surface(
                modifier = Modifier
                    .padding(
                        top = 3.dp,
                        bottom = 12.dp
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.line_color_deep),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        infoClickState.value = true
                    }
            ) {
                EmailInfo(email.value)
            }
            Subject("통계")
            Statistics()
            Subject("일반")
            Spacer(modifier = Modifier.height(7.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { myNavi.navigate(MyScreenRoot.NOTICE) }
            ) {
                OptionBox("공지사항", R.drawable.right_side_my, null)
            }

            OptionBox("앱 버전 정보", null, appVersion)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        sendEmailState.value = true
                    }
            ) {
                OptionBox("의견보내기", R.drawable.component_3, null)
            }

            Spacer(modifier = Modifier.height(80.dp))

        }
    }
}
