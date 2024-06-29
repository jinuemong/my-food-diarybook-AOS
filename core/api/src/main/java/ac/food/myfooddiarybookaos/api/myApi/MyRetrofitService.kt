package ac.food.myfooddiarybookaos.api.myApi

import ac.food.myfooddiarybookaos.model.login.NewPasswordRequest
import ac.food.myfooddiarybookaos.model.my.PasswordChangeResponse
import ac.food.myfooddiarybookaos.model.statistics.StatisticsList
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyRetrofitService {

    @GET("notice/more")
    suspend fun getPagingNotice(
        @Query("startId") startId: Int,
        @Query("size") size: Int,
    ): NoticeData

    @GET("user/statistics")
    suspend fun getUserStatistics(): StatisticsList

    @POST("user/logout")
    suspend fun logoutUser()

    @POST("user/delete-all-images")
    suspend fun deleteAllImages()

    @POST("user/resign")
    suspend fun deleteUser()

    @POST("user/new-password")
    suspend fun updatePassword(
        @Body password: NewPasswordRequest,
    ): PasswordChangeResponse

}
