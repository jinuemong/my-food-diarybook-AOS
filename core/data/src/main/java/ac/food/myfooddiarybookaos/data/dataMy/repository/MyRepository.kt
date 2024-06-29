package ac.food.myfooddiarybookaos.data.dataMy.repository

import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.model.login.NewPasswordRequest
import ac.food.myfooddiarybookaos.model.my.PasswordChangeResponse
import ac.food.myfooddiarybookaos.model.statistics.StatisticsList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MyRepository @Inject constructor(networkManager: NetworkManager) {
    private val manager = networkManager.getMyApiService()

    suspend fun getUserStatistics(): Flow<StatisticsList> = flow {
        try {
            emit(manager.getUserStatistics())
        } catch (_: Exception) {
        }
    }

    suspend fun userLogout(): Boolean {
        return try {
            manager.logoutUser()
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun userDelete() {
        manager.deleteUser()
    }

    suspend fun deleteAllDiary() {
        manager.deleteAllImages()
    }

    suspend fun updatePassword(
        password: String,
        newPassword: String,
    ): PasswordChangeResponse {
        return manager.updatePassword(
            NewPasswordRequest(
                password,
                newPassword,
            )
        )
    }
}
