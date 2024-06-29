package ac.food.myfooddiarybookaos.detail.tag

import ac.food.myfooddiarybookaos.detail.tag.item.TagItem
import ac.food.myfooddiarybookaos.model.detail.Tag
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TagLayer(
    tags: MutableList<Tag>,
    clickTag: () -> Unit
) {
    LazyRow(
        modifier = Modifier
            .clickable {
                clickTag()
            }
            .fillMaxWidth(),
        state = rememberLazyListState(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(tags) { tag ->
            TagItem(tag.name)
        }
    }
}
