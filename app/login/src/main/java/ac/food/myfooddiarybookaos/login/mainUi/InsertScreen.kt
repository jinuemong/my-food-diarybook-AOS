package ac.food.myfooddiarybookaos.login.mainUi

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.component.ErrorPage
import ac.food.myfooddiarybookaos.data.component.ToastMessaging
import ac.food.myfooddiarybookaos.data.component.password.PasswordPolicyLayer
import ac.food.myfooddiarybookaos.data.component.password.Subject

import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.ui.addFocusCleaner
import ac.food.myfooddiarybookaos.data.ui.theme.EditTextBox
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.login.viewModel.LoginViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnrememberedMutableState")
@Composable
fun InsertScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val emailText = remember { mutableStateOf(TextFieldValue("")) }
    val passText = remember { mutableStateOf(TextFieldValue("")) }
    val rePassText = remember { mutableStateOf(TextFieldValue("")) }
    val isValidPass = remember { mutableStateOf(false) }
    val isSamePass = remember { mutableStateOf(false) }
    val allCheckBox = remember { mutableStateOf(false) }
    val serviceCheckBox = remember { mutableStateOf(false) }
    val userInfoCheckBox = remember { mutableStateOf(false) }

    val errorViewState = remember { mutableStateOf(false) }
    val createUserState = remember { mutableStateOf(false) }
    var goMainResult by remember {
        mutableStateOf(false)
    }
    val toastMessageState = remember { mutableStateOf(false) }

    if (createUserState.value) {
        viewModel.createUser(
            emailText.value.text,
            passText.value.text,
            userState = {
                if (it) {
                    goMainResult = true
                } else {
                    errorViewState.value = true
                }
                createUserState.value = false
            },
            toastState = {
                if (it) {
                    toastMessageState.value = true
                }
            }
        )
        createUserState.value = false
    }
    if (goMainResult) {
        viewModel.goMain(context)
        viewModel.saveEmailState(context, emailText.value.text)
        goMainResult = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .verticalScroll(scrollState)
            .addFocusCleaner(focusManager)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(24.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.pass_left_side),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }

            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "회원가입",
                fontFamily = FontFamily(Font(R.font.roboto_bold, FontWeight.W700)),
                fontWeight = FontWeight.W700,
                lineHeight = 21.scaledSp(),
                fontSize = 18.scaledSp(),
                color = colorResource(id = R.color._1A1D1D)
            )
        }
        if (errorViewState.value) {
            ErrorPage(
                load = {
                    createUserState.value = true
                }
            )
        } else {

            Spacer(modifier = Modifier.height(13.dp))
            Subject(text = "아이디")
            Spacer(modifier = Modifier.height(4.dp))
            EditTextBox(
                hintText = "이메일",
                editText = emailText,
                strokeColor = mutableStateOf(true)
            )

            Spacer(modifier = Modifier.height(28.dp))
            PasswordPolicyLayer(
                newPass = passText,
                newPassRe = rePassText,
                isSamePass = isSamePass,
                isValidPass = isValidPass,
                subjectName = "비밀번호"
            )

            Spacer(modifier = Modifier.height(12.dp))
            CheckBox(allCheckBox, "전체 약관동의", FontWeight.W700, null)
            if (allCheckBox.value) {
                serviceCheckBox.value = true
                userInfoCheckBox.value = true
            } else {
                if (serviceCheckBox.value || userInfoCheckBox.value) {
                    serviceCheckBox.value = false
                    userInfoCheckBox.value = false
                }
            }


            CheckBox(
                serviceCheckBox,
                "(필수) 서비스 이용약관 동의",
                FontWeight.W400,
                "https://majestic-amber-920.notion.site/4fedc5556f2247a9b353e82e9e9804b3"
            )
            CheckBox(
                userInfoCheckBox,
                "(필수) 개인정보 수집/이용 동의 ",
                FontWeight.W400,
                "https://majestic-amber-920.notion.site/ea4c8db57ba24e79a781f7432a4f4922"
            )

            val boxColor =
                if (emailText.value.text.isNotEmpty()
                    && serviceCheckBox.value
                    && userInfoCheckBox.value
                    && isSamePass.value
                    && isValidPass.value
                ) 1.0f
                else 0.3f

            Spacer(modifier = Modifier.height(8.dp))

            Surface( // 배경
                modifier = if (boxColor == 1.0f) {
                    Modifier
                        .clickable {
                            createUserState.value = true
                        }
                        .alpha(boxColor)
                } else Modifier.alpha(boxColor),

                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(
                    1.dp,
                    colorResource(id = R.color.weak_color)
                ),
                color = colorResource(id = R.color.main_color)
            ) {
                Text(
                    text = "가입하기",
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
                    lineHeight = 23.scaledSp()
                )
            }
            Spacer(
                modifier = Modifier.padding(top = 300.dp)
            )
        }
    }
    if (toastMessageState.value) {
        ToastMessaging(
            message = "이미 사용중인 사용자입니다.",
            removeView = {
                toastMessageState.value = false
            }
        )
    }
}


@Composable
@Preview
fun InsertPreview() {
    InsertScreen(rememberNavController())
}

@Composable
fun CheckBox(
    checkState: MutableState<Boolean>,
    text: String,
    fontWeight: FontWeight,
    link: String?
) {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .padding(3.dp)
                .clickable {
                    checkState.value = !checkState.value
                }, contentAlignment = Alignment.Center
        ) {
            Image(
                painter =
                if (checkState.value) painterResource(id = R.drawable.check_box_login)
                else painterResource(id = R.drawable.un_check_box_login),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            fontFamily = robotoRegular,
            fontSize = 14.scaledSp(),
            color = colorResource(id = R.color._1A1D1D),
            fontWeight = fontWeight,
            textDecoration = if (fontWeight != FontWeight.W700) TextDecoration.Underline else null,
            modifier = Modifier.clickable {
                if (link != null) {
                    val intent = Intent(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(link)
                        )
                    )
                    context.startActivity(intent)
                }
            }
        )
    }
}
