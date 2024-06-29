package ac.food.myfooddiarybookaos.data.dataLogin.repository

import ac.food.myfooddiarybookaos.api.BuildConfig
import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.api.googleLogin.LoginResult
import ac.food.myfooddiarybookaos.model.login.LoginGoogleResponse
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GoogleLoginRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val networkManager: NetworkManager
) {

    fun setLauncher(
        result: ActivityResult,
        firebaseAuth: FirebaseAuth,
        loginCallback: (String?) -> Unit,
        saveEmail: (String) -> Unit
    ) {
        var tokenId: String?
        var email: String
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                task.getResult(ApiException::class.java)?.let { account ->
                    tokenId = account.idToken
                    if (tokenId != null && tokenId != "") {
                        val credential: AuthCredential =
                            GoogleAuthProvider.getCredential(tokenId, null)

                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                            if (firebaseAuth.currentUser != null) {
                                val user: FirebaseUser = firebaseAuth.currentUser!!
                                email = user.email.toString()
                                Log.e(ContentValues.TAG, "email : $email")
                                val googleSignInToken = account.serverAuthCode ?: ""
                                if (googleSignInToken != "") {
                                    loginCallback(googleSignInToken)
                                    saveEmail(email)
                                } else {
                                    loginCallback(null)
                                }
                            } else {
                                Log.d("firebaseAuth", "notUser")
                                loginCallback(null)
                            }
                        }
                    } else {
                        Log.d("tokenId", "notUser")
                        loginCallback(null)
                    }
                } ?: throw Exception()
            } catch (e: Exception) {
                Log.d("eeeeeee", e.message.toString())
                e.printStackTrace()
                loginCallback(null)
            }
        } else {
            Log.d("eeeeeee", result.resultCode.toString())
            loginCallback(null)
        }
    }

    fun logout(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()
        GoogleSignIn.getClient(context, gso).signOut()
    }

    fun login(
        context: Context,
        launcher: ActivityResultLauncher<Intent>
    ) {
        networkManager.setLoginForm(NetworkManager.LOGIN_GOOGLE)
        CoroutineScope(Dispatchers.IO).launch {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_ID)
                .requestServerAuthCode(BuildConfig.GOOGLE_ID)
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            val signInIntent: Intent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    suspend fun loginRequest(idToken: String): LoginResult<LoginGoogleResponse> {
        networkManager
            .getGoogleLoginApiService()
            .fetchGoogleAuthInfo(
                networkManager.googleTokenRequest(idToken)
            )
            ?.run {
                return LoginResult.Success(this.body() ?: LoginGoogleResponse())
            } ?: return LoginResult.Error(Exception("Exception"))
    }

    fun revokeAccess(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()
        GoogleSignIn.getClient(context, gso).revokeAccess()
    }
}
