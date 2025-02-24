package ac.food.myfooddiarybookaos.login.mainSubUi

import ac.food.myfooddiarybookaos.api.UserInfoSharedPreferences
import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.component.ToastMessaging
import ac.food.myfooddiarybookaos.data.ui.theme.TextBox
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.login.viewModel.LoginViewModel
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.auth.model.OAuthToken


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun BottomLayout(
    findPassword: () -> Unit,
    insertUser: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val loginUserState = remember { mutableStateOf(false) }
    val isGoogleLogin = remember { mutableStateOf(false) }
    val isKaKaoLogin = remember { mutableStateOf(false) }
    val toastMessageState = remember { mutableStateOf(false) }
    val userEmail = remember { mutableStateOf("") }
    val firebaseAuth = FirebaseAuth.getInstance()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            viewModel.setLauncher(result, firebaseAuth,
                loginState = { state ->
                    if (!state) isGoogleLogin.value = false
                    loginUserState.value = state
                },
                saveEmailState = {
                    userEmail.value = it
                }
            )
        }
    )
    val kaKaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (token == null) isKaKaoLogin.value = false
        viewModel.setCallback(error, token, loginState = {
            loginUserState.value = it
        })
    }

    if (isGoogleLogin.value) {
        viewModel.goggleLogin(context, launcher)
    }
    if (isKaKaoLogin.value) {
        viewModel.kaKaoLogin(
            kaKaoCallback,
            loginState = {
                if (it == null) {
                    isKaKaoLogin.value = false
                }
            }
        )
    }
    if (loginUserState.value) {
        if (isKaKaoLogin.value) {
            viewModel.getKaKaoUserEmail(email = {
                if (it != null) {
                    UserInfoSharedPreferences(context).userEmail = it
                    viewModel.goMain(context)
                } else {
                    Log.d("sdlfjsdlfjfdsllsdf", "sdf;jsdlfdsjlsdfjl")
                    isKaKaoLogin.value = false
                }
            })
        } else {
            viewModel.saveEmailState(context, userEmail.value)
            viewModel.goMain(context)
        }
    }

    Spacer(modifier = Modifier.height(17.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier.clickable { findPassword() }
        ) {
            TextBox(
                text = "비밀번호 찾기",
                fontWeight = 500,
                fontFamily = Font(R.font.roboto_light),
                14.scaledSp(),
                colorResource(id = R.color.login_weak_color),
            )
        }
        // 중앙 선 표현
        Spacer(modifier = Modifier.width(12.dp))
        Divider(
            color = colorResource(id = R.color.login_weak_color_40),
            modifier = Modifier
                .width(1.dp)
                .height(12.86.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            Modifier.clickable { insertUser() }
        ) {
            TextBox(
                text = "회원가입",
                fontWeight = 500,
                fontFamily = Font(R.font.roboto_light),
                14.scaledSp(),
                colorResource(id = R.color.login_weak_color)
            )
        }
    }
    Spacer(modifier = Modifier.height(51.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            start = 13.dp,
            end = 16.dp
        )
    ) {
        Divider(
            color = colorResource(id = R.color.login_weak_color_40),
            modifier = Modifier
                .weight(1F)
                .width(0.dp)
                .height(1.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        TextBox(
            text = "또는",
            fontWeight = 500,
            fontFamily = Font(R.font.roboto_light),
            fontSize = 14.scaledSp(),
            color = colorResource(id = R.color.login_weak_color),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Divider(
            color = colorResource(id = R.color.login_weak_color_40),
            modifier = Modifier
                .weight(1F)
                .width(0.dp)
                .height(1.dp)
        )
    }
    Spacer(modifier = Modifier.height(19.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.icon_google),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    isGoogleLogin.value = true
                }
        )
        Spacer(modifier = Modifier.width(21.dp))
        Image(
            painter = painterResource(id = R.drawable.icon_kakao),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    isKaKaoLogin.value = true
                }
        )
    }

    if (toastMessageState.value) {
        ToastMessaging(
            message = "카카오 이메일 정보가 없어서 로그인 할 수 없습니다. 다른 방법을 이용해 주세요.",
            removeView = {
                toastMessageState.value = false
            }
        )
    }

}
