package ac.food.myfooddiarybookaos.myaccount.component

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun EmailInfo(email: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 7.dp,
                end = 9.17.dp,
                top = 17.dp,
                bottom = 17.dp
            )
    ) {
        Box(
            modifier = Modifier
                .width(270.dp)
                .align(Alignment.CenterStart)
        ) {
            Text(
                text = email,
                fontWeight = FontWeight(500),
                fontSize = 20.scaledSp(),
                color = colorResource(id = R.color.black),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 24.scaledSp()
            )
        }

        Box(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.right_side_my),
                contentDescription = "",
            )
        }
    }
}
