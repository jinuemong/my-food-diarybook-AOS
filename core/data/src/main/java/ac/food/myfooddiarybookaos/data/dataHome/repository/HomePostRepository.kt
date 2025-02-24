package ac.food.myfooddiarybookaos.data.dataHome.repository

import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.data.path.getMultipartFromUri
import ac.food.myfooddiarybookaos.data.path.toApplicationRequestBody
import ac.food.myfooddiarybookaos.model.diary.PlaceInfo
import ac.food.myfooddiarybookaos.model.diary.PlaceInfoBody
import ac.food.myfooddiarybookaos.model.response.NotDataResponse
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import retrofit2.HttpException

class HomePostRepository(
    private val networkManager: NetworkManager,
    @ApplicationContext private val context: Context
) {
    private val manager = networkManager.getDiaryMultiPartApiService()

    suspend fun postNewDiary(
        createTime: String,
        place: String?,
        longitude: Double?,
        latitude: Double?,
        fileList: List<MultipartBody.Part>,
        isSuccess: (Boolean) -> Unit,
        failState: (String) -> Unit
    ) {
        try {
            val placeRequest = PlaceInfoBody(
                placeInfo = PlaceInfo(place, longitude, latitude)
            )
            val json = Gson().toJson(placeRequest)
            val requestBody = json.toApplicationRequestBody()
            kotlin.runCatching {
                manager.newDiary(
                    createTime,
                    requestBody,
                    fileList
                )
            }
                .onSuccess {
                    isSuccess(true)
                }
                .onFailure { e ->
                    isSuccess(false)
                    when (e) {
                        is HttpException -> {
                            val data = Gson().fromJson(
                                e.response()?.errorBody()?.string(),
                                NotDataResponse::class.java
                            )
                            data?.let { failState(it.message) }
                        }
                    }
                }
        } catch (e: Exception) {
            isSuccess(false)
        }
    }


    fun makePartListFromUri(
        imageUriList: List<Uri>,
        isOneImage: Boolean
    ): List<MultipartBody.Part> {
        val files = ArrayList<MultipartBody.Part>()
        for (image in imageUriList) {
            getMultipartFromUri(context, image, isOneImage)?.let { files.add(it) }
        }
        return files
    }

    // 비트맵 형식 변환
    fun makePartListFromBitmap(
        imageBitmap: Bitmap
    ): List<MultipartBody.Part> {
        return listOf(makeMultiPartFromBitmap(imageBitmap))
    }


    private fun makeMultiPartFromBitmap(bitmap: Bitmap): MultipartBody.Part {
        val bitmapRequestBody = BitmapRequestBody(bitmap)
        val bitmapCode: String = bitmap.toString().split("@").last()
        return MultipartBody.Part.createFormData(
            "files",
            bitmapCode,
            bitmapRequestBody
        )
    }

    //비트맵 -> requsetbody 변환
    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
        }
    }
}
