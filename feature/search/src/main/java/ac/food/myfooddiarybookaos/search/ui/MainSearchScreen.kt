package ac.food.myfooddiarybookaos.search.ui

import ac.food.myfooddiarybookaos.data.component.ErrorPage
import ac.food.myfooddiarybookaos.data.component.LoadPage
import ac.food.myfooddiarybookaos.data.state.LoadState
import ac.food.myfooddiarybookaos.model.search.SearchDiary
import ac.food.myfooddiarybookaos.search.SearchViewModel
import ac.food.myfooddiarybookaos.search.component.PagingCategoryComponent
import ac.food.myfooddiarybookaos.search.component.SearchCategoryComponent
import ac.food.myfooddiarybookaos.search.state.SearchState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun MainSearchScreen(
    isUpdateView: MutableState<Boolean>,
    searchState: MutableState<SearchState>,
    queryChangeState: MutableState<Boolean>,
    searchQuery: MutableState<TextFieldValue>,
    selectItem: (SearchDiary) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    when (searchState.value) {
        SearchState.MAIN_SEARCH -> {
            if (isUpdateView.value) {
                viewModel.getPagingCategories()
                isUpdateView.value = false
            }
            LaunchedEffect(Unit) {
                viewModel.getPagingCategories()
            }
            val pagingItems = viewModel.pagingCategoryList.collectAsLazyPagingItems()
            when (state.value) {
                LoadState.Init -> {
                    PagingCategoryComponent(
                        selectItem = selectItem,
                        pagingItems = pagingItems
                    )
                }

                LoadState.Loading -> {
                    LoadPage()
                }

                LoadState.Fail -> {
                    ErrorPage {
                        viewModel.getPagingCategories()
                    }
                }
            }
        }

        SearchState.QUERY_SEARCH -> {
            if (queryChangeState.value) {
                viewModel.getSearchData(searchQuery.value.text)
                queryChangeState.value = false
            }
            val searchItems = viewModel.searchCategoryList.collectAsState()
            when (state.value) {
                LoadState.Init -> {
                    SearchCategoryComponent(
                        searchItems = searchItems,
                        selectItem = selectItem
                    )
                }

                LoadState.Loading -> {
                    LoadPage()
                }

                LoadState.Fail -> {
                    ErrorPage {
                        viewModel.getSearchData(searchQuery.value.text)
                    }
                }
            }
        }
    }
}
