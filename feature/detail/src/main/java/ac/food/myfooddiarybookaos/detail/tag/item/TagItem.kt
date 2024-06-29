package ac.food.myfooddiarybookaos.detail.tag.item

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TagItem(
    text: String
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
    ) {
        Text(
            text = "#$text",
            fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.W500)),
            fontSize = 18.scaledSp(),
            color = Color.Black,
            lineHeight = 18.scaledSp()
        )
    }
}
