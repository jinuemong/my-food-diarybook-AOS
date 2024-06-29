package ac.food.myfooddiarybookaos.detail.mainUi.item

import ac.food.myfooddiarybookaos.data.path.byteStringToBitmap
import ac.food.myfooddiarybookaos.model.image.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun OneDetailImage(
    image: Image
) {
    val imageModel = remember { byteStringToBitmap(image.bytes) }

    AsyncImage(
        model = imageModel,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
}
