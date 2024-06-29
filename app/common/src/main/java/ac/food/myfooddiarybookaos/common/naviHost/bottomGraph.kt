package ac.food.myfooddiarybookaos.common.bottomaNavi

import ac.food.myfooddiarybookaos.TabMyAccount.MyScreen
import ac.food.myfooddiarybookaos.data.state.ApplicationState
import ac.food.myfooddiarybookaos.data.state.DiaryState
import ac.food.myfooddiarybookaos.home.ui.HomeScreen
import ac.food.myfooddiarybookaos.search.SearchScreen
import ac.food.myfooddiarybookaos.search.state.SearchDataState
import ac.food.myfooddiarybookaos.timeline.TimeLineScreen
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dnd_9th_3_android.gooding.data.root.ScreenRoot

fun NavGraphBuilder.bottomGraph(
    appState: ApplicationState,
    diaryState: DiaryState,
    searchDataState: SearchDataState,
    homeUpdate: MutableState<Boolean>,
    timeLineUpdate: MutableState<Boolean>,
    searchUpdate: MutableState<Boolean>,
    myUpdate: MutableState<Boolean>
) {

    navigation(
        startDestination = BottomNavItem.Home.screenRoute,
        route = ScreenRoot.MAIN_GRAPH
    ) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreen(homeUpdate, diaryState, appState)
        }
        composable(BottomNavItem.TimeLine.screenRoute) {
            TimeLineScreen(timeLineUpdate, appState, diaryState)
        }
        composable(BottomNavItem.Search.screenRoute) {
            SearchScreen(searchUpdate, appState, diaryState, searchDataState)
        }
        composable(BottomNavItem.MyAccount.screenRoute) {
            MyScreen(myUpdate, appState)
        }

    }

}
