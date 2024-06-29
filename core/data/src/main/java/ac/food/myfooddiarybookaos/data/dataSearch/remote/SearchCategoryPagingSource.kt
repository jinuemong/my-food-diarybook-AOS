package ac.food.myfooddiarybookaos.data.dataSearch.remote

import ac.food.myfooddiarybookaos.api.searchApi.SearchRetrofitService
import ac.food.myfooddiarybookaos.model.search.SearchCategory
import androidx.paging.PagingSource
import androidx.paging.PagingState

class SearchCategoryPagingSource(
    private val manager: SearchRetrofitService,
) : PagingSource<Int, SearchCategory>() {


    override fun getRefreshKey(state: PagingState<Int, SearchCategory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchCategory> {
        return try {
            val next = params.key ?: 0
            val response = manager.searchShow(next)
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
