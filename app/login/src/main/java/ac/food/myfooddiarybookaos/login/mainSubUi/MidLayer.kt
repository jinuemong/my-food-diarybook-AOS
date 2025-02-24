package ac.food.myfooddiarybookaos.login.mainSubUi

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.ui.theme.EditTextBox
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.login.passUi.FindPasswordPopUp
import ac.food.myfooddiarybookaos.login.popUp.ChangePasswordPopUp
import ac.food.myfooddiarybookaos.login.viewModel.LoginViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MidLayout(
    findPassword: () -> Unit,
    changePassword: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val emailText = remember { mutableStateOf(TextFieldValue("")) }
    val pwText = remember { mutableStateOf(TextFieldValue("")) }

    var goMainResult by remember { mutableStateOf(false) }
    val loginRequestState = remember { mutableStateOf(false) }
    val pwExpiredState = remember { mutableStateOf(false) }

    if (goMainResult) {
        viewModel.goMain(LocalContext.current)
        viewModel.saveEmailState(LocalContext.current, emailText.value.text)
    }
    var checkEnter by remember { mutableStateOf(0.3f) }

    val findPassPopState = remember { mutableStateOf(false) }
    val isValid = remember { mutableStateOf(true) }
    val loginFailCount = remember { mutableStateOf(0) }
    if (loginFailCount.value > 0) {
        isValid.value = false
        if (loginFailCount.value == 5) {
            findPassPopState.value = true
        }
    }

    if (loginRequestState.value) {
        viewModel.loginUser(
            emailText.value.text,
            pwText.value.text,
            userState = { state, pwState ->
                if (pwState) {
                    pwExpiredState.value = true
                } else {
                    if (state) {
                        goMainResult = true
                    } else {
                        if (loginFailCount.value < 5) {
                            loginFailCount.value += 1
                        }
                    }
                }
            },
        )
        loginRequestState.value = false
    }

    if (pwExpiredState.value) {
        Dialog(onDismissRequest = {
            pwExpiredState.value = false
        }) {
            ChangePasswordPopUp(
                goChange = {
                    pwExpiredState.value = false
                    changePassword()
                },
                resetPassword = {
                    pwExpiredState.value = false

                }
            )
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // EditText - email

        Box(Modifier.padding(horizontal = 16.dp)) { EditTextBox("이메일", emailText, isValid) }
        Spacer(modifier = Modifier.height(8.dp))
        // EditText - pw
        Box(Modifier.padding(horizontal = 16.dp)) {
            EditTextBox(
                hintText = "비밀번호",
                pwText,
                isValid
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (!isValid.value) "*아이디 또는 비밀번호를 잘못 입력했습니다. (${loginFailCount.value}/5)"
            else "",
            color = colorResource(id = R.color.not_valid_text_color),
            fontFamily = robotoRegular,
            fontWeight = FontWeight(500),
            fontSize = 12.scaledSp(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(7.dp))

        checkEnter =
            if (emailText.value.text.isNotEmpty() && pwText.value.text.isNotEmpty()) 1.0f
            else 0.3f
        // LoginButton
        Surface( // 배경 , 1.0f -> 클릭 가능
            modifier =
            if (checkEnter == 1.0f) Modifier
                .clickable {
                    loginRequestState.value = true
                }
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                )
                .alpha(checkEnter)
            else Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                )
                .alpha(checkEnter),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(
                1.dp,
                colorResource(id = R.color.weak_color)
            ),
            color = colorResource(id = R.color.main_color)
        ) {
            Text(
                text = "로그인",
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight(700),
                fontSize = 16.scaledSp(),
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()

                    .padding(
                        top = 10.5.dp,
                        bottom = 10.5.dp
                    ),
                textAlign = TextAlign.Center, // 중앙
            )
        }


    }

    if (findPassPopState.value) {
        Dialog(
            onDismissRequest = {
                findPassPopState.value = false
            }
        ) {
            FindPasswordPopUp(
                offDialog = {
                    loginFailCount.value = 0
                    findPassPopState.value = false
                },
                goFind = {
                    loginFailCount.value = 0
                    findPassPopState.value = false
                    findPassword()
                }
            )
        }
    }
}
