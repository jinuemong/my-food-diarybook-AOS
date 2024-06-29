package ac.food.myfooddiarybookaos.data.dataHome.repository

import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.model.diary.Diary
import ac.food.myfooddiarybookaos.model.home.DiaryHomeDay
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepository(
    private val networkManager: NetworkManager,
    @ApplicationContext private val context: Context
) {
    private val manager = networkManager.getDiaryAppApiService()

    suspend fun getCurrentHomeDiary(yearMonth: String): Flow<List<Diary>> = flow {
        emit(manager.getHomeDiary(yearMonth))
    }

    suspend fun getCurrentHomeDay(date: String): Flow<DiaryHomeDay> = flow {
        emit(manager.getHomeDay(date))
    }
}
