package ac.food.myfooddiarybookaos.common.bottomaNavi

import ac.food.myfooddiarybookaos.data.state.ApplicationState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dnd_9th_3_android.gooding.data.root.ScreenRoot

@Composable
fun BottomNavigation(
    appState: ApplicationState,
) {
    // 생성한 Object 아이템 연결 (각 아이콘)
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.TimeLine,
        BottomNavItem.Spacer,
        BottomNavItem.Search,
        BottomNavItem.MyAccount
    )

    Box(
        Modifier
            .height(68.dp)
            .fillMaxWidth()
    ) {
        AnimatedVisibility(
            visible = appState.bottomBarState.value,
            modifier = Modifier
                .height(68.dp)
                .fillMaxWidth()
        ) {

            // Bottom Navi 틀 생성
            androidx.compose.material.BottomNavigation(
                backgroundColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier.fillMaxSize()
            ) {
                // 어떤 Bottom Navigation Item이 선택되었는지 저장
                // currentRoute에 현재 보고있는 Item 상태 저장
                // NavController의 currentBackStackEntryAsState() 을 통해
                // navBackStackEntry를 가져와 목적지의 route(위치 string)을 가져온 것
                val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    BottomNavigationItem(
                        icon = { //image icon
                            if (item.selectedIcon != null && item.unselectedIcon != null) {
                                if (currentRoute == item.screenRoute) {
                                    Icon(
                                        painter = painterResource(id = item.selectedIcon),
                                        contentDescription = item.title,
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = item.unselectedIcon),
                                        contentDescription = item.title,
                                    )
                                }
                            }
                        },
                        selected = currentRoute == item.screenRoute, //select 상태를 설정
                        alwaysShowLabel = false,

                        // 아이콘 클릭 동작

                        onClick = {
                            if (item.selectedIcon != null) {
                                appState.navController.navigate(item.screenRoute) {
//                            navController.graph.startDestinationRoute?.let {// 루트 이동
//                                // popUpTo를 통해서 start route만 스택에 쌓이게 함
//                                // 하나의 인스턴스만 뜨게 지정
//                                popUpTo(it) { saveState = true }
//                            }
                                    popUpTo(ScreenRoot.MAIN_GRAPH) {
                                        saveState = true
                                    }
                                    launchSingleTop = true // 화면 인스턴스 하나만 생성하게 하기
                                    restoreState = true // 버튼 재 클릭 시 이전 상태 남기기

                                }
                            }
                        }
                    )

                }
            }
        }
    }
}
