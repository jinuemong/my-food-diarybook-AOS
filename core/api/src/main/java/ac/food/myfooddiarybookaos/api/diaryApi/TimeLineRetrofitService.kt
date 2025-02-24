package ac.food.myfooddiarybookaos.api.diaryApi

import ac.food.myfooddiarybookaos.model.timeLine.TimeLine
import ac.food.myfooddiarybookaos.model.timeLine.TimeLineDiary
import retrofit2.http.GET
import retrofit2.http.Query

interface TimeLineRetrofitService {

    @GET("timeline/show")
    suspend fun getTimeLineShow(
        @Query("date") date: String
    ): List<TimeLine>

    @GET("timeline/show/more-diary")
    suspend fun getTimeLineFlicking(
        @Query("date") date: String,
        @Query("offset") offset: Int
    ): List<TimeLineDiary>

}
