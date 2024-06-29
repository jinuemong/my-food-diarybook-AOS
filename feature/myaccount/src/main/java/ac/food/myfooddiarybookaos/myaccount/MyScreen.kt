package ac.food.myfooddiarybookaos.TabMyAccount

import ac.food.myfooddiarybookaos.data.state.ApplicationState
import ac.food.myfooddiarybookaos.data.state.rememberApplicationState
import ac.food.myfooddiarybookaos.myaccount.navi.MyGraph
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun MyScreen(
    isUpdateView: MutableState<Boolean>,
    appState: ApplicationState,
) {
    val myNavi = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MyGraph(
            mainNavi = appState.navController,
            myNavi = myNavi,
            isUpdateView = isUpdateView,
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewMyScreen() {
    MyScreen(
        isUpdateView = mutableStateOf(false),
        appState = rememberApplicationState()
    )
}
