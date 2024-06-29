package ac.food.myfooddiarybookaos.myaccount.myInfo

import ac.food.myfooddiarybookaos.api.NetworkManager.Companion.LOGIN_NONE
import ac.food.myfooddiarybookaos.api.UserInfoSharedPreferences
import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.TextBox
import ac.food.myfooddiarybookaos.data.component.coloredInnerShadow
import ac.food.myfooddiarybookaos.data.robotoBold
import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.myaccount.popUp.DeleteDiaryLayer
import ac.food.myfooddiarybookaos.myaccount.viewModel.MyViewModel
import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dnd_9th_3_android.gooding.data.root.ScreenRoot

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyInfoScreen(
    mainNavi: NavHostController,
    myNavi: NavHostController,
    viewModel: MyViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isBackState = remember { mutableStateOf(false) }
    val passwordChangeState = remember { mutableStateOf(false) }
    val allDiaryDeleteState = remember { mutableStateOf(false) }
    val logoutState = remember { mutableStateOf(false) }
    val deleteUserState = remember { mutableStateOf(false) }
    val requestDiaryDeleteState = remember { mutableStateOf(false) }
    val isNormalLogin = remember {
        UserInfoSharedPreferences(context).loginForm == LOGIN_NONE
    }

    if (logoutState.value) {
        viewModel.userLogout(context) {
            val intent = Intent(
                context,
                Class.forName("ac.food.myfooddiarybookaos.LoginActivity")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            UserInfoSharedPreferences(context).resetUserInfo()
            context.startActivity(intent)
        }
        logoutState.value = false
    }

    if (isBackState.value) {
        myNavi.popBackStack()
        isBackState.value = false
    }

    if (passwordChangeState.value) {
        mainNavi.navigate(ScreenRoot.CHANGE_PASSWORD)
        passwordChangeState.value = false
    }

    if (allDiaryDeleteState.value) {
        Dialog(onDismissRequest = {
            allDiaryDeleteState.value = false
        }) {
            DeleteDiaryLayer(
                onClose = {
                    allDiaryDeleteState.value = false
                },
                onPassword = {
                    requestDiaryDeleteState.value = true
                }
            )
        }
    }


    if (deleteUserState.value) {
        mainNavi.navigate(ScreenRoot.DELETE_USER)
        deleteUserState.value = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Column(
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .align(Alignment.BottomStart)
                            .padding(bottom = 2.dp)
                            .clickable(onClick = { isBackState.value = true }),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.chevron_left_24px_no_box),
                            contentDescription = "",
                        )
                    }

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.BottomCenter)
                            .padding(14.75.dp)
                    ) {
                        TextBox(
                            text = "내 정보",
                            fontWeight = 500,
                            fontFamily = robotoBold,
                            fontSize = 18.scaledSp(),
                            color = colorResource(id = R.color.black)
                        )
                    }
                }


                Spacer(modifier = Modifier.height(1.dp))

                Divider(
                    modifier = Modifier
                        .height(2.dp)
                        .coloredInnerShadow(
                            color = colorResource(id = R.color.black_10),
                            offsetY = 1.dp,
                            blurRadius = 4.dp
                        )
                )
            }

            Spacer(modifier = Modifier.height(29.dp))

            if (isNormalLogin) {
                Box(
                    modifier = Modifier
                        .padding(start = 22.dp, end = 18.dp)
                        .fillMaxWidth()
                        .clickable {
                            passwordChangeState.value = true
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.create_24px),
                                contentDescription = null
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "비밀번호 변경",
                            fontFamily = robotoRegular,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W400,
                            lineHeight = 24.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(34.dp))
            }

            Box(
                modifier = Modifier
                    .padding(start = 22.dp, end = 18.dp)
                    .fillMaxWidth()
                    .clickable {
                        allDiaryDeleteState.value = true
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Box(
                        modifier = Modifier.size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.component_1),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "모든 식사일기 삭제",
                        fontFamily = robotoRegular,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 24.sp,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(34.dp))

            Box(
                modifier = Modifier
                    .padding(start = 22.dp, end = 18.dp)
                    .fillMaxWidth()
                    .clickable {
                        deleteUserState.value = true
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Box(
                        modifier = Modifier.size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.close_24px),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "회원탈퇴",
                        fontFamily = robotoRegular,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 24.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(34.dp))

            Box(
                modifier = Modifier
                    .padding(start = 22.dp, end = 18.dp)
                    .fillMaxWidth()
                    .clickable {
                        logoutState.value = true
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Box(
                        modifier = Modifier.size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.component_2),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "로그아웃",
                        fontFamily = robotoRegular,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 24.sp,
                        color = Color.Black
                    )
                }
            }
        }
        if (requestDiaryDeleteState.value) {
            Box(
                modifier = Modifier
                    .background(colorResource(R.color.white_80))
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .padding(
                            start = 12.dp,
                            end = 8.dp,
                            bottom = 130.dp
                        )
                        .clip(RoundedCornerShape(4.dp))
                        .background(colorResource(R.color.toast_back))
                        .fillMaxWidth()
                        .padding(
                            horizontal = 14.dp,
                            vertical = 12.dp
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "모든 식사 일기를 삭제 하고 있습니다. \n" +
                                "잠시만 기다려 주세요. ",
                        fontFamily = robotoRegular,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 20.sp,
                        letterSpacing = (-0.4).sp,
                        color = Color.White
                    )
                }
            }
            // request 응답 오면 false로 전환
            LaunchedEffect(Unit) {
                viewModel.deleteAllDiary {
                    if (it) {
                        requestDiaryDeleteState.value = false
                    }
                }
            }
        }

    }

}
