package ac.food.myfooddiarybookaos.api.diaryApi

import ac.food.myfooddiarybookaos.model.detail.DiaryDetail
import ac.food.myfooddiarybookaos.model.detail.FixDiary
import ac.food.myfooddiarybookaos.model.diary.Diary
import ac.food.myfooddiarybookaos.model.home.DiaryHomeDay
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DiaryRetrofitService {

    @GET("diary/home")
    suspend fun getHomeDiary(
        @Query("yearMonth") yearMonth: String
    ): List<Diary>

    @GET("diary/home-day")
    suspend fun getHomeDay(
        @Query("date") date: String
    ): DiaryHomeDay

    @GET("diary/{diaryId}")
    suspend fun getDiaryDetail(
        @Path("diaryId") diaryId: Int,
    ): DiaryDetail

    @PUT("diary/{diaryId}/memo")
    fun setDiaryMemo(
        @Path("diaryId") diaryId: Int,
        @Body body: FixDiary
    ): Call<Unit>


    @DELETE("diary/{diaryId}")
    suspend fun deleteDiary(
        @Path("diaryId") diaryId: Int
    )
}
