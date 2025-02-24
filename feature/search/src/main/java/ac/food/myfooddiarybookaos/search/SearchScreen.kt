package ac.food.myfooddiarybookaos.search

import ac.food.myfooddiarybookaos.data.state.ApplicationState
import ac.food.myfooddiarybookaos.data.state.DiaryState
import ac.food.myfooddiarybookaos.search.component.SearchBox
import ac.food.myfooddiarybookaos.search.navi.NavigationGraph
import ac.food.myfooddiarybookaos.search.state.SearchDataState
import ac.food.myfooddiarybookaos.search.state.SearchState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(
    isUpdateView: MutableState<Boolean>,
    appState: ApplicationState,
    diaryState: DiaryState,
    searchDataState: SearchDataState,
) {

    if (searchDataState.searchQuery.value.text.isNotEmpty()) {
        searchDataState.searchState.value = SearchState.QUERY_SEARCH
    } else {
        searchDataState.searchState.value = SearchState.MAIN_SEARCH
    }

    Column {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 33.dp,
                    bottom = 13.dp
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            SearchBox(
                searchDataState.searchQuery,
                searchDataState.searchState,
                onQueryChange = {
                    searchDataState.queryChangeState.value = true
                },
                onBackStage = {
                    if (searchDataState.navController.currentDestination?.route == "categoryScreen") {
                        searchDataState.navController.popBackStack()
                    }
                    searchDataState.searchQuery.value = TextFieldValue("")
                }
            )
        }

        NavigationGraph(
            isUpdateView = isUpdateView,
            appState = appState,
            diaryState = diaryState,
            searchDataState = searchDataState,
        )

    }
}
