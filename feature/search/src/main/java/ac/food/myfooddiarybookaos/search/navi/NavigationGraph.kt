package ac.food.myfooddiarybookaos.search.navi

import ac.food.myfooddiarybookaos.data.state.ApplicationState
import ac.food.myfooddiarybookaos.data.state.DiaryState
import ac.food.myfooddiarybookaos.search.state.SearchDataState
import ac.food.myfooddiarybookaos.search.ui.CategoryScreen
import ac.food.myfooddiarybookaos.search.ui.MainSearchScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dnd_9th_3_android.gooding.data.root.ScreenRoot

@Composable
fun NavigationGraph(
    isUpdateView: MutableState<Boolean>,
    appState: ApplicationState,
    diaryState: DiaryState,
    searchDataState: SearchDataState
) {

    NavHost(
        navController = searchDataState.navController,
        startDestination = "mainSearchScreen"
    ) {

        composable("mainSearchScreen") {
            MainSearchScreen(
                isUpdateView = isUpdateView,
                searchState = searchDataState.searchState,
                queryChangeState = searchDataState.queryChangeState,
                searchQuery = searchDataState.searchQuery,
                selectItem = {
                    diaryState.setDiaryDetail(it.diaryId)
                    appState.navController.navigate(ScreenRoot.DETAIL_DIARY)
                }
            )
        }

        composable("categoryScreen") {
            CategoryScreen(
                categoryName = searchDataState.categoryName,
                categoryType = searchDataState.categoryType,
                selectDiary = {
                    diaryState.setDiaryDetail(it)
                    appState.navController.navigate(ScreenRoot.DETAIL_DIARY)
                }
            )
        }
    }
}
