package ac.food.myfooddiarybookaos.data.dataDetail

import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.model.detail.DiaryDetail
import ac.food.myfooddiarybookaos.model.detail.FixDiary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class DetailRepository @Inject constructor(
    private val networkManager: NetworkManager
) {
    private val manager = networkManager.getDiaryAppApiService()
    private val postManager = networkManager.getDiaryMultiPartApiService()

    suspend fun getDetailDiary(diaryId: Int): Flow<DiaryDetail> = flow {
        emit(manager.getDiaryDetail(diaryId))
    }

    fun fixDetailDiary(
        diaryId: Int,
        fixDiary: FixDiary,
        state: (Boolean) -> Unit
    ) {
        manager.setDiaryMemo(diaryId, fixDiary)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) state(true)
                    else state(false)
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    state(true)
                }

            })
    }

    fun fixDetailDiaryImage(
        imageId: Int,
        file: MultipartBody.Part,
        state: (Boolean) -> Unit
    ) {
        postManager.updateDiaryImage(
            imageId, file
        ).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) state(true)
                else state(false)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                state(false)
            }

        })
    }

    fun addDetailDiaryImages(
        diaryId: Int,
        fileList: List<MultipartBody.Part>,
        isSuccess: (Boolean) -> Unit
    ) {
        try {
            postManager.addDiaryImages(
                diaryId,
                fileList
            ).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) isSuccess(true)
                    else isSuccess(false)
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    isSuccess(false)
                }

            })
        } catch (e: Exception) {
            isSuccess(false)
        }
    }

    suspend fun deleteDiary(diaryId: Int) = manager.deleteDiary(diaryId)
}
