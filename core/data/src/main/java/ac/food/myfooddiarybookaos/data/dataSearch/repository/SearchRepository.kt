package ac.food.myfooddiarybookaos.data.dataSearch.repository

import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.data.dataSearch.remote.SearchCategoryPagingSource
import ac.food.myfooddiarybookaos.data.dataSearch.remote.SearchDataPagingSource
import ac.food.myfooddiarybookaos.model.search.SearchCategory
import ac.food.myfooddiarybookaos.model.search.SearchDiary
import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    networkManager: NetworkManager,
    private val context: Context
) {

    private val manager = networkManager.getSearchApiService()

    suspend fun getCurrentSearch(
        searchCond: String
    ): Flow<List<SearchCategory>> = flow {
        try {
            emit(manager.searchCondition(searchCond = searchCond))
        } catch (_: Exception) {
        }
    }

    fun getMoreSearchData(

    ): Flow<PagingData<SearchCategory>> {
        return Pager(
            config = PagingConfig(pageSize = 4)
        ) {
            SearchCategoryPagingSource(manager)
        }.flow
    }

    fun getSearchDiary(
        categoryName: String,
        categoryType: String,
    ): Flow<PagingData<SearchDiary>> {
        return Pager(
            config = PagingConfig(pageSize = 3)
        ) {
            SearchDataPagingSource(
                categoryName,
                categoryType,
                manager
            )
        }.flow
    }


}
