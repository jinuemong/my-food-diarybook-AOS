package ac.food.myfooddiarybookaos.data.dataTimeLine.repository

import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.data.dataTimeLine.remote.TimeLinePagingSource
import ac.food.myfooddiarybookaos.model.timeLine.TimeLine
import ac.food.myfooddiarybookaos.model.timeLine.TimeLineDiary
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TimeLineRepository @Inject constructor(
    private val networkManager: NetworkManager
) {
    private val manager = networkManager.getTimeLineApiService()

    fun getTimeLineData(date: String): Flow<PagingData<TimeLine>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TimeLinePagingSource(
                    date = date,
                    manager = manager
                )
            }
        ).flow
    }

    fun getTimeLineMoreData(
        date: String,
        offset: Int
    ): Flow<List<TimeLineDiary>> = flow {
        try {
            emit(
                manager.getTimeLineFlicking(
                    date = date,
                    offset = offset
                )
            )
        } catch (_: Exception) {
            emit(emptyList())
        }
    }
}
