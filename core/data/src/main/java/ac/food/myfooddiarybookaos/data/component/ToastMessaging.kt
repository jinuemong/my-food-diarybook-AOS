package ac.food.myfooddiarybookaos.data.component

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ToastMessaging(
    message: String,
    removeView: () -> Unit
) {
    val snackState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    scope.launch {
        snackState.showSnackbar(message)
        delay(1500)
        removeView()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SnackbarHost(
            hostState = snackState,
            modifier = Modifier.align(Alignment.BottomCenter),
        ) { snackbarData: SnackbarData ->
            Surface(
                modifier = Modifier.padding(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.toast_back),
                            shape = RoundedCornerShape(4.dp)
                        )
                ) {
                    Text(
                        text = snackbarData.message,
                        fontWeight = FontWeight.W400,
                        fontFamily = robotoRegular,
                        fontSize = 15.scaledSp(),
                        lineHeight = 20.scaledSp(),
                        letterSpacing = (-0.4).sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 14.dp,
                                end = 7.dp,
                                top = 14.dp,
                                bottom = 12.dp
                            )
                    )
                }
            }
        }
    }
}
