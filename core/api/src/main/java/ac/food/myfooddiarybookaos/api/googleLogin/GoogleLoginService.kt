package ac.food.myfooddiarybookaos.api.googleLogin

import ac.food.myfooddiarybookaos.model.login.LoginGoogleRequest
import ac.food.myfooddiarybookaos.model.login.LoginGoogleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleLoginService {

    @POST("token")
    suspend fun fetchGoogleAuthInfo(
        @Body request: LoginGoogleRequest
    ): Response<LoginGoogleResponse>?

}
