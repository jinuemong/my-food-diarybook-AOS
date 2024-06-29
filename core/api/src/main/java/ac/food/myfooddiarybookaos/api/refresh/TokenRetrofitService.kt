package ac.food.myfooddiarybookaos.api.refresh

import ac.food.myfooddiarybookaos.model.login.LoginResponse
import retrofit2.http.POST

interface TokenRetrofitService {

    @POST("user/refresh-token")
    suspend fun resetToken(): LoginResponse

}
