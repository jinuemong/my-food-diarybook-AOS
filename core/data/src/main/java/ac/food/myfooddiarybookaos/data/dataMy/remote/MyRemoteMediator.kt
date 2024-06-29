package ac.food.myfooddiarybookaos.data.dataMy.remote

import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.api.myApi.NoticeEntity
import ac.food.myfooddiarybookaos.api.remoteKey.MyRemoteKeysEntity
import ac.food.myfooddiarybookaos.data.dataMy.local.MyDatabase
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class MyRemoteMediator constructor(
    private val db: MyDatabase,
    private val networkManager: NetworkManager
) : RemoteMediator<Int, NoticeEntity>() {

    private val startingPageIndex = 1
    private val manger = networkManager.getMyApiService()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NoticeEntity>
    ): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }

            else -> {
                pageKeyData as Int
            }
        }
        Log.d("page", page.toString())

        try {
            val response = manger.getPagingNotice(
                startId = page,
                size = state.config.pageSize
            ).list

            val endOfList = response.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeyDao().clearAll()
                    db.getNoticeDao().clearAll()
                }

                val prevKey = if (page == startingPageIndex) null else page - 1
                val nextKey = if (endOfList) null else page + 1
                Log.d("prevKey", prevKey.toString())
                Log.d("nextKey", nextKey.toString())
                Log.d("endOfList", endOfList.toString())
                val keys = response.map {
                    MyRemoteKeysEntity(it.id, prevKey, nextKey)
                }
                db.remoteKeyDao().insertRemote(keys)
                db.getNoticeDao().insert(response)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfList)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }


    }

    // load key 갱신
    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, NoticeEntity>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRefreshRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: startingPageIndex
            }

            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: MediatorResult.Success(
                    endOfPaginationReached = true
                )
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey ?: MediatorResult.Success(
                    endOfPaginationReached = true
                )
                nextKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, NoticeEntity>): MyRemoteKeysEntity? {
        return withContext(Dispatchers.IO) {
            state.pages
                .firstOrNull { it.data.isNotEmpty() }
                ?.data?.firstOrNull()
                ?.let { notice -> db.remoteKeyDao().getRemoteKeys(notice.id) }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, NoticeEntity>): MyRemoteKeysEntity? {
        return withContext(Dispatchers.IO) {
            state.pages
                .firstOrNull() { it.data.isNotEmpty() }
                ?.data?.lastOrNull()
                ?.let { notice -> db.remoteKeyDao().getRemoteKeys(notice.id) }
        }
    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, NoticeEntity>): MyRemoteKeysEntity? {
        return withContext(Dispatchers.IO) {
            state.anchorPosition?.let { index ->
                state.closestItemToPosition(index)?.id?.let { id ->
                    db.remoteKeyDao().getRemoteKeys(id)
                }
            }
        }
    }

}
