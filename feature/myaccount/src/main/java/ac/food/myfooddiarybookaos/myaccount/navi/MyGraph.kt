package ac.food.myfooddiarybookaos.myaccount.navi

import ac.food.myfooddiarybookaos.myaccount.myInfo.MyInfoScreen
import ac.food.myfooddiarybookaos.myaccount.myMain.MyMainScreen
import ac.food.myfooddiarybookaos.myaccount.myNotice.NoticeScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MyGraph(
    mainNavi: NavHostController,
    myNavi: NavHostController,
    isUpdateView: MutableState<Boolean>,
) {

    NavHost(
        navController = myNavi,
        startDestination = MyScreenRoot.MY
    ) {

        composable(MyScreenRoot.MY) {
            MyMainScreen(myNavi, isUpdateView)
        }

        composable(MyScreenRoot.NOTICE) {
            NoticeScreen(myNavi)
        }

        composable(MyScreenRoot.INFO) {
            MyInfoScreen(mainNavi, myNavi)
        }
    }
}
