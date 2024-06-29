package ac.food.myfooddiarybookaos.detail.mainUi.component

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.detail.tag.TagLayer
import ac.food.myfooddiarybookaos.model.detail.Tag
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DetailTag(
    tags: List<Tag>?,
    clickTag: () -> Unit
) {
    if (tags.isNullOrEmpty()) {
        Text(
            text = "#태그",
            fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.W500)),
            fontSize = 18.scaledSp(),
            color = colorResource(id = R.color.calender_next_color),
        )
    } else {
        TagLayer(tags.toMutableList(), clickTag)
    }
}
