package ac.food.myfooddiarybookaos.api.diaryApi

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface DiaryPostRetrofitService {

    @Multipart
    @POST("diary/new")
    suspend fun newDiary(
        @Query("createTime") createTime: String,
        @Part("placeInfo") placeInfo: RequestBody,
        @Part files: List<MultipartBody.Part>
    )


    @Multipart
    @POST("diary/{diaryId}/images")
    fun addDiaryImages(
        @Path("diaryId") diaryId: Int,
        @Part file: List<MultipartBody.Part>
    ): Call<Unit>

    @Multipart
    @PATCH("diary/image/{imageId}")
    fun updateDiaryImage(
        @Path("imageId") imageId: Int,
        @Part file: MultipartBody.Part
    ): Call<Unit>
}
