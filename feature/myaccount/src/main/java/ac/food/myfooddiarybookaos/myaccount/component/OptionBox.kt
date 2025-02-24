package ac.food.myfooddiarybookaos.myaccount.component

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.TextBox
import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun OptionBox(text: String, drawable: Int?, version: String?) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 9.dp,
            )
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.black),
                shape = RoundedCornerShape(4.dp)
            ),
    ) {
        Box(
            modifier = Modifier.padding(
                start = 9.dp,
                top = 17.dp,
                bottom = 17.dp
            ),
        ) {
            Box(modifier = Modifier.align(Alignment.CenterStart)) {
                TextBox(
                    text = text,
                    fontWeight = 500,
                    fontFamily = robotoRegular,
                    fontSize = 20.scaledSp(),
                    color = colorResource(id = R.color.black),
                    lineHeight = 24.scaledSp()
                )
            }

            if (drawable != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = drawable),
                        contentDescription = "",
                        colorFilter = if (drawable == R.drawable.right_side_my) ColorFilter.tint(
                            colorResource(id = R.color.black_87)
                        )
                        else ColorFilter.tint(colorResource(id = R.color.black))
                    )
                }
            }
            if (version != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp)
                ) {
                    TextBox(
                        text = version,
                        fontWeight = 500,
                        fontFamily = robotoRegular,
                        fontSize = 20.scaledSp(),
                        color = colorResource(id = R.color.black),
                        lineHeight = 24.scaledSp()
                    )
                }
            }
        }
    }
}
