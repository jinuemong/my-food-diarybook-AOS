package ac.food.myfooddiarybookaos.data.component.password

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.component.ToastMessaging
import ac.food.myfooddiarybookaos.data.robotoBold
import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.ui.theme.EditTextBox
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnrememberedMutableState")
@Composable
fun SetNewPassword(
    navController: NavHostController,
    passwordViewModel: PasswordViewModel = hiltViewModel(),
) {
    var checkEnter by remember { mutableStateOf(0.3f) }

    val currentPass = remember { mutableStateOf(TextFieldValue("")) }
    val newPass = remember { mutableStateOf(TextFieldValue("")) }
    val newPassRe = remember { mutableStateOf(TextFieldValue("")) }

    val isValidPass = remember { mutableStateOf(false) } // new pass valid
    val isSamePass = remember { mutableStateOf(false) } //pass = passRe
    val failToastMessageState = remember { mutableStateOf(false) }
    val successToastMessageState = remember { mutableStateOf(false) }

    if (failToastMessageState.value) {
        ToastMessaging(
            message = "비밀번호 변경에 실패하였습니다. \n정확한 비밀번호를 입력해주세요.",
            removeView = {
                failToastMessageState.value = false
            }
        )
    }
    if (successToastMessageState.value) {
        ToastMessaging(
            message = "비밀번호가 변경되었습니다.",
            removeView = {
                successToastMessageState.value = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.pass_left_side),
                contentDescription = null,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "비밀번호 설정",
                fontFamily = FontFamily(Font(R.font.roboto_bold, FontWeight.W700)),
                fontSize = 18.scaledSp(),
                color = colorResource(id = R.color._1A1D1D)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "보안을 위해 \n" +
                    "비밀번호를 변경하세요.",
            fontWeight = FontWeight.W700,
            fontFamily = robotoBold,
            fontSize = 20.scaledSp(),
            color = colorResource(id = R.color._1A1D1D)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Subject("현재 비밀번호")
        Spacer(modifier = Modifier.height(4.dp))
        EditTextBox("비밀번호", currentPass, mutableStateOf(true))
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "",
            fontWeight = FontWeight.W500,
            fontSize = 12.scaledSp(),
            fontFamily = robotoRegular,
            color = colorResource(id = R.color.not_valid_text_color)
        )
        Spacer(modifier = Modifier.height(6.dp))

        PasswordPolicyLayer(
            newPass,
            newPassRe,
            isSamePass,
            isValidPass,
            subjectName = "새 비밀번호"
        )

        Spacer(modifier = Modifier.height(24.dp))

        checkEnter =
            if (isValidPass.value && isSamePass.value
                && currentPass.value.text.isNotEmpty()
                && newPass.value.text.isNotEmpty()
                && newPassRe.value.text.isNotEmpty()
            ) 1.0f
            else 0.3f
        Surface(
            modifier = Modifier
                .alpha(checkEnter)
                .clickable {
                    if (checkEnter == 1.0f) {
                        passwordViewModel.updatePassword(
                            prevPassword = currentPass.value.text,
                            newPassword = newPass.value.text,
                            fail = {
                                failToastMessageState.value = true
                            },
                            success = {
                                successToastMessageState.value = true
                            }
                        )
                    }
                },
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(
                1.dp,
                colorResource(id = R.color.weak_color)
            ),
            color = colorResource(id = R.color.main_color)
        ) {
            Text(
                text = "변경 설정 완료",
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
}

@Composable
@Preview(showBackground = true)
fun SetNewPassPreview() {
    SetNewPassword(rememberNavController())
}
