package ac.food.myfooddiarybookaos.login.navi

import ac.food.myfooddiarybookaos.data.component.password.SetNewPassword
import ac.food.myfooddiarybookaos.login.mainUi.InsertScreen
import ac.food.myfooddiarybookaos.login.mainUi.LoginScreen
import ac.food.myfooddiarybookaos.login.mainUi.SplashScreen
import ac.food.myfooddiarybookaos.login.passUi.FindPassScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenRoot.SPLASH
    ) {
        composable(LoginScreenRoot.SPLASH) { SplashScreen(navController) }
        composable(LoginScreenRoot.LOGIN_MAIN) { LoginScreen(navController) }
        composable(LoginScreenRoot.FIND_PASSWORD) { FindPassScreen(navController) }
        composable(LoginScreenRoot.NEW_PASSWORD) { SetNewPassword(navController = navController) }
        composable(LoginScreenRoot.INSERT) { InsertScreen(navController = navController) }
    }

}
