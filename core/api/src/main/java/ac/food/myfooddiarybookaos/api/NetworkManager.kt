package ac.food.myfooddiarybookaos.api

import ac.food.myfooddiarybookaos.api.diaryApi.DiaryPostRetrofitService
import ac.food.myfooddiarybookaos.api.diaryApi.DiaryRetrofitService
import ac.food.myfooddiarybookaos.api.diaryApi.TimeLineRetrofitService
import ac.food.myfooddiarybookaos.api.googleLogin.GoogleLoginService
import ac.food.myfooddiarybookaos.api.myApi.MyRetrofitService
import ac.food.myfooddiarybookaos.api.refresh.AuthInterceptor
import ac.food.myfooddiarybookaos.api.searchApi.SearchRetrofitService
import ac.food.myfooddiarybookaos.api.userApi.UserRetrofitService
import ac.food.myfooddiarybookaos.model.login.LoginGoogleRequest
import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class NetworkManager(
    private val context: Context
) {
    companion object {
        private val instance: Retrofit? = null
        private const val CONTENT_APPLICATION = "application/json"
        private const val CONTENT_MULTI_PART = "multipart/form-data"
        const val GRANT_TYPE = "authorization_code"
        const val GOOGLE_REDIRECT_URI = ""
        const val LOGIN_NONE = "none"
        const val LOGIN_KAKAO = "kakao"
        const val LOGIN_GOOGLE = "google"

        private fun getRetrofit(
            context: Context,
            contentType: String,
        ): Retrofit {
            val userData = UserInfoSharedPreferences(context)
            val loginForm = userData.loginForm ?: LOGIN_NONE
            val header = Interceptor {
                val original = it.request()
                if (userData.accessToken != null && userData.accessToken != "") {
                    val request = original.newBuilder()
                        .header("token", "${userData.accessToken}")
                        .build()
                    it.proceed(request)
                } else {
                    it.proceed(original)
                }
            }

            return instance ?: Retrofit.Builder()
                .baseUrl("$BASE_URL/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(unsafeOkHttpClient(header, contentType, loginForm, context))
                .build()
        }

        private fun getGoogleRetrofit(): Retrofit {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return Retrofit.Builder()
                .baseUrl("$GOOGLE_BASE_URL/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient
                        .Builder()
                        .addInterceptor(logInterceptor)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build()
                )
                .build()
        }

        // SSL 인증서 체크 + 클라이언트
        private fun unsafeOkHttpClient(
            header: Interceptor,
            contentType: String,
            loginForm: String,
            context: Context,
        ): OkHttpClient {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out java.security.cert.X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<out java.security.cert.X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<out java.security.cert.X509Certificate>? {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { hostname, session -> true }

            builder.addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("login-from", loginForm)
                    it.addHeader("Content-Type", contentType)
                    it.addHeader("request-agent", "android; app-version $appVersion")
                }.build())
            }.also { client ->
                client.addInterceptor(header)
                client.addInterceptor(AuthInterceptor(context, loginForm))
                //로그 기록 인터셉터 등록
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                client
                    .addInterceptor(logInterceptor)
                    .connectTimeout(NETWORK_TIME_OUT_SECOND, TimeUnit.SECONDS)
                    .readTimeout(NETWORK_TIME_OUT_SECOND, TimeUnit.SECONDS)
            }
            return builder
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

        }

        private const val NETWORK_TIME_OUT_SECOND = 10L
    }

    fun getLoginApiService(): UserRetrofitService =
        getRetrofit(context, CONTENT_APPLICATION).create(UserRetrofitService::class.java)

    fun getDiaryMultiPartApiService(): DiaryPostRetrofitService =
        getRetrofit(context, CONTENT_MULTI_PART).create(DiaryPostRetrofitService::class.java)

    fun getDiaryAppApiService(): DiaryRetrofitService =
        getRetrofit(context, CONTENT_APPLICATION).create(DiaryRetrofitService::class.java)

    fun getTimeLineApiService(): TimeLineRetrofitService =
        getRetrofit(context, CONTENT_APPLICATION).create(TimeLineRetrofitService::class.java)

    fun getMyApiService(): MyRetrofitService =
        getRetrofit(context, CONTENT_APPLICATION).create(MyRetrofitService::class.java)

    fun getSearchApiService(): SearchRetrofitService =
        getRetrofit(context, CONTENT_APPLICATION).create(SearchRetrofitService::class.java)

    fun getGoogleLoginApiService(): GoogleLoginService {
        setLoginForm(LOGIN_GOOGLE)
        return getGoogleRetrofit().create(GoogleLoginService::class.java)
    }

    fun googleTokenRequest(
        authCode: String
    ): LoginGoogleRequest {
        return LoginGoogleRequest(
            grant_type = GRANT_TYPE,
            client_id = BuildConfig.GOOGLE_ID,
            client_secret = BuildConfig.GOOGLE_SECRET_ID,
            redirect_uri = GOOGLE_REDIRECT_URI,
            code = authCode
        )
    }

    fun setLoginForm(current: String) {
        UserInfoSharedPreferences(context).loginForm = current
    }
}
