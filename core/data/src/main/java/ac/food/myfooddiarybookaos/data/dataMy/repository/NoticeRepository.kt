package ac.food.myfooddiarybookaos.data.dataMy.repository

import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.api.myApi.NoticeEntity
import ac.food.myfooddiarybookaos.data.dataMy.local.MyDatabase
import ac.food.myfooddiarybookaos.data.dataMy.remote.MyRemoteMediator
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoticeRepository @Inject constructor(
    private val db: MyDatabase,
    private val networkManager: NetworkManager
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getNoticePager(): Flow<PagingData<NoticeEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                db.getNoticeDao().getItemPager()
            },
            remoteMediator = MyRemoteMediator(db, networkManager)
        ).flow
    }
}
