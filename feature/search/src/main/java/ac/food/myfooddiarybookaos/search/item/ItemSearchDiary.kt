package ac.food.myfooddiarybookaos.search.item

import ac.food.myfooddiarybookaos.data.path.byteStringToBitmap
import ac.food.myfooddiarybookaos.model.search.SearchDiary
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ItemSearchDiary(
    searchDiary: SearchDiary,
    select: () -> Unit
) {

    val imageModel = remember { byteStringToBitmap(searchDiary.bytes) }

    Box(
        modifier = Modifier.clickable {
            select()
        }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageModel)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .size(112.dp)
        )
    }
}
