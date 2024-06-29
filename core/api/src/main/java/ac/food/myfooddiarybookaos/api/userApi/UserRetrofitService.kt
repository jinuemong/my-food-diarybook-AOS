package ac.food.myfooddiarybookaos.api.userApi

import ac.food.myfooddiarybookaos.model.login.CreateUserResponse
import ac.food.myfooddiarybookaos.model.login.LoginResponse
import ac.food.myfooddiarybookaos.model.login.PasswordResetRequest
import ac.food.myfooddiarybookaos.model.login.ResetPassState
import ac.food.myfooddiarybookaos.model.login.UserRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserRetrofitService {

    @GET("user/is-login")
    suspend fun userIsLogin()

    @POST("user/login")
    suspend fun userLogin(
        @Body userRequest: UserRequest
    ): Response<LoginResponse>


    @POST("user/new")
    fun createUser(
        @Body userRequest: UserRequest
    ): Call<CreateUserResponse>

    @GET("user/google-login-callback")
    suspend fun loginGoogle(
        @Query("code") code: String
    ): Response<Unit>

    @POST("user/reset-password")
    suspend fun resetUserPassword(
        @Body passwordResetRequest: PasswordResetRequest,
    ): Response<ResetPassState>
}
