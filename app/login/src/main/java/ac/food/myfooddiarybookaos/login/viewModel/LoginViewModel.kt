package ac.food.myfooddiarybookaos.login.viewModel

import ac.food.myfooddiarybookaos.api.UserInfoSharedPreferences
import ac.food.myfooddiarybookaos.api.googleLogin.LoginResult
import ac.food.myfooddiarybookaos.data.dataLogin.repository.GoogleLoginRepository
import ac.food.myfooddiarybookaos.data.dataLogin.repository.KaKaoLoginRepository
import ac.food.myfooddiarybookaos.data.dataLogin.repository.LoginRepository
import ac.food.myfooddiarybookaos.data.state.LoadState
import ac.food.myfooddiarybookaos.model.login.LoginGoogleResponse
import ac.food.myfooddiarybookaos.model.login.LoginResponse
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val googleLoginRepository: GoogleLoginRepository,
    private val kaKaoLoginRepository: KaKaoLoginRepository
) : ViewModel() {

    private var _state: MutableStateFlow<LoadState> = MutableStateFlow(LoadState.Init)
    val state: StateFlow<LoadState> = _state.asStateFlow()

    fun isLogin(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _state.emit(LoadState.Loading)
            runCatching {
                repository.checkTokenState()
            }
                .onSuccess {
                    _state.emit(LoadState.Init)
                    onResult(true)
                }
                .onFailure {
                    _state.emit(LoadState.Fail)
                    onResult(false)
                }
        }
    }

    fun loginUser(
        email: String,
        pw: String,
        userState: (state: Boolean, pwState: Boolean) -> Unit,
    ) = viewModelScope.launch {
        repository.loginUserRequest(
            email, pw,
            result = { status, response ->
                if (response?.pwExpired == true || status == "PASSWORD_LIMIT_OVER") {
                    userState(false, true)
                } else {
                    if (status == "SUCCESS") {
                        userState(saveUserState(status, response), false)
                    } else {
                        userState(false, false)
                    }
                }
            }
        )

    }

    fun createUser(
        email: String, pw: String,
        userState: (Boolean) -> Unit,
        toastState: (Boolean) -> Unit
    ) {
        repository.createUserRequest(
            email, pw,
            result = { state, _ ->
                when (state) {
                    "SUCCESS" -> {
                        loginUser(
                            email, pw,
                            userState = { status, _ ->
                                userState(status)
                            },
                        )
                    }

                    "DUPLICATED_USER" -> {
                        toastState(true)
                    }

                    else -> {
                        userState(false)
                    }
                }
            }
        )
    }

    fun passReset(
        inputEmail: String,
        emailState: (String?) -> Unit
    ) = viewModelScope.launch {
        repository.resetUserPassword(
            userEmail = inputEmail,
            emailState = {
                emailState(it)
            }
        )
    }

    private fun saveUserState(
        status: String,
        response: LoginResponse?
    ): Boolean {
        return if (status == "SUCCESS") {
            // 토큰 저장 !
            repository.saveUserToken(response)
            true
        } else false
    }

    fun goMain(
        context: Context,
    ) {
        val intent = Intent(
            context,
            Class.forName("ac.food.myfooddiarybookaos.MainActivity")
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun saveEmailState(context: Context, userEmail: String) {
        UserInfoSharedPreferences(context).userEmail = userEmail
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun setLauncher(
        result: ActivityResult,
        firebaseAuth: FirebaseAuth,
        loginState: (Boolean) -> Unit,
        saveEmailState: (String) -> Unit
    ) {
        googleLoginRepository.setLauncher(result, firebaseAuth,
            loginCallback = {
                if (it != null) {
                    it.let { token ->
                        // token decode
                        CoroutineScope(Dispatchers.IO).launch {
                            googleLoginRepository.loginRequest(token).let { result ->
                                when (result) {
                                    is LoginResult.Success<LoginGoogleResponse> -> {
                                        repository.saveUserToken(
                                            LoginResponse(
                                                refreshToken = result.data.refresh_token,
                                                status = "성공",
                                                token = result.data.access_token,
                                                pwExpired = false
                                            ),
                                        )
                                        loginState(true)
                                    }

                                    else -> {
                                        loginState(false)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    loginState(false)
                }
            },
            saveEmail = {
                saveEmailState(it)
            }
        )
    }

    fun goggleLogin(context: Context, launcher: ActivityResultLauncher<Intent>) {
        googleLoginRepository.login(context, launcher)
    }


    fun kaKaoLogin(
        callback: (OAuthToken?, Throwable?) -> Unit,
        loginState: (String?) -> Unit
    ) {
        kaKaoLoginRepository.kaKaoLogin(
            callback,
            loginCallback = {
                loginState(it)
            })
    }

    fun checkKaKaoLogin(): Boolean {
        return kaKaoLoginRepository.checkLogin()
    }

    fun setCallback(
        error: Throwable?,
        token: OAuthToken?,
        loginState: (Boolean) -> Unit
    ) {
        if (token == null || error != null) {
            loginState(false)
        } else {
            repository.saveUserToken(
                response = LoginResponse(
                    token = token.accessToken,
                    status = "성공",
                    refreshToken = token.refreshToken,
                    pwExpired = false
                ),
            )
            loginState(true)
        }
    }

    fun getKaKaoUserEmail(email: (String?) -> Unit) {
        kaKaoLoginRepository.getUserInfo(
            email = {
                email(it)
            }
        )
    }
}
