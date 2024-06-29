package ac.food.myfooddiarybookaos.detail.mainUi.component

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DetailMemo(
    memo: String?

) {
    if (memo == null || memo == "") {
        Text(
            text = "메모 남기기",
            fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.W500)),
            fontSize = 18.scaledSp(),
            color = colorResource(id = R.color.calender_next_color),
            lineHeight = 18.scaledSp()
        )
    } else {
        Text(
            text = memo,
            fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.W500)),
            fontSize = 18.scaledSp(),
            color = Color.Black,
            lineHeight = 18.scaledSp()
        )
    }
}
