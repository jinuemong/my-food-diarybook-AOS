package ac.food.myfooddiarybookaos

import ac.food.myfooddiarybookaos.common.addPicture.SelectAddScreen
import ac.food.myfooddiarybookaos.common.naviHost.MainNaviHost
import ac.food.myfooddiarybookaos.data.state.rememberApplicationState
import ac.food.myfooddiarybookaos.data.state.rememberDiaryState
import ac.food.myfooddiarybookaos.data.ui.theme.MyFoodDiaryBookAOSTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dnd_9th_3_android.gooding.common.state.ManageBottomBarState
import com.google.android.gms.ads.MobileAds
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)
        setContent {
            MyFoodDiaryBookAOSTheme {
                MainUi()
            }
        }
    }
}

@Composable
fun MainUi() {
    // 뒤로가기 제어
    BackHandler(enabled = true, onBack = {})

    // 이미지 추가 시 다이어리 상태 변경
    val diaryState = rememberDiaryState()
    // bottom state
    val appState = rememberApplicationState()
    // mid click event
    if (diaryState.showSelectView.value) {
        BottomSheetDialog(
            onDismissRequest = {
                diaryState.showSelectView.value = false
            },
            properties = BottomSheetDialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) {
            SelectAddScreen(
                diaryState = diaryState,
                appState = appState,
                closeLog = {
                    // 취소 버튼 or 선택화면으로 전환
                    diaryState.showSelectView.value = false
                }
            )
        }
    }

    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    ManageBottomBarState(
        navBackStackEntry = navBackStackEntry,
        appState = appState
    )
    MainNaviHost(appState = appState, diaryState = diaryState)

}


//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview2() {
//    MyFoodDiaryBookAOSTheme {
//        MainUi()
//
//    }
//}
