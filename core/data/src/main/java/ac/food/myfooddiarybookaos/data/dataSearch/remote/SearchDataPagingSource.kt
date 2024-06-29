package ac.food.myfooddiarybookaos.data.dataSearch.remote

import ac.food.myfooddiarybookaos.api.searchApi.SearchRetrofitService
import ac.food.myfooddiarybookaos.model.search.SearchDiary
import androidx.paging.PagingSource
import androidx.paging.PagingState

class SearchDataPagingSource(
    private val categoryName: String,
    private val categoryType: String,
    private val manager: SearchRetrofitService
) : PagingSource<Int, SearchDiary>() {
    override fun getRefreshKey(state: PagingState<Int, SearchDiary>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchDiary> {
        return try {
            val next = params.key ?: 0
            val response = manager.searchMoreDiary(
                categoryName = categoryName,
                categoryType = categoryType,
                offset = next
            )
            if (response.isEmpty()) throw Exception()

            LoadResult.Page(
                data = response,
                prevKey = if (next == 0) null else next - 1,
                nextKey = next + 1
            )

        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }
}
