package ac.food.myfooddiarybookaos.myaccount.component

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.TextBox
import ac.food.myfooddiarybookaos.data.robotoBold
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource


@Composable
fun Subject(text: String) {
    TextBox(
        text = text,
        fontFamily = robotoBold,
        fontSize = 14.scaledSp(),
        fontWeight = 700,
        color = colorResource(id = R.color._1A1D1D),
        lineHeight = 21.scaledSp()
    )
}
