package ac.food.myfooddiarybookaos.api.searchApi

import ac.food.myfooddiarybookaos.model.search.SearchCategory
import ac.food.myfooddiarybookaos.model.search.SearchDiary
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRetrofitService {

    @GET("search/condition")
    suspend fun searchCondition(
        @Query("searchCond") searchCond: String
    ): List<SearchCategory>

    @GET("search")
    suspend fun searchShow(
        @Query("offset") offset: Int
    ): List<SearchCategory>

    @GET("search/more-diary")
    suspend fun searchMoreDiary(
        @Query("categoryName") categoryName: String,
        @Query("categoryType") categoryType: String,
        @Query("offset") offset: Int
    ): List<SearchDiary>

}
