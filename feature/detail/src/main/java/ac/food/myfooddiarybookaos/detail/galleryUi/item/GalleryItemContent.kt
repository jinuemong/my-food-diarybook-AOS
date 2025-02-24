package ac.food.myfooddiarybookaos.detail.galleryUi.component

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.model.image.GalleryImage
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun GalleryItemContent(
    galleryImage: GalleryImage,
    selectedImages: List<GalleryImage>,
    isMultiSelectView: Boolean,
    setSelectImage: (GalleryImage) -> Unit,
    removeImage: (Long) -> Unit
) {
    val selectIndex = selectedImages.indexOfFirst { it.id == galleryImage.id } + 1
    val isSelected = selectIndex != 0
    val multiColor = if (isMultiSelectView) Color.White else Color.Transparent

    val selectedModifier =
        if (isSelected) Modifier
            .aspectRatio(1f)
            .animateContentSize()
            .border(4.dp, colorResource(id = R.color.main_color))
            .clickable {
                removeImage(galleryImage.id)
            }
        else Modifier
            .aspectRatio(1f)
            .animateContentSize()
            .border(1.dp, Color.Black)
            .clickable {
                if (isMultiSelectView || selectedImages.isEmpty())
                    setSelectImage(galleryImage)
            }
    Box {

        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(galleryImage.uri)
                .crossfade(true)
                .build(),
            loading = {
                ListCircularProgressIndicator(0.2f)
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = selectedModifier
        )

        if (isSelected) {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp, end = 10.dp)
                    .size(24.dp)
                    .border(color = multiColor, width = 1.dp, shape = CircleShape)
                    .background(color = colorResource(id = R.color.main_color), shape = CircleShape)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                if (isMultiSelectView) {
                    Text(
                        text = selectIndex.toString(),
                        color = multiColor,
                        fontWeight = FontWeight.W500,
                        fontSize = 18.scaledSp(),
                        fontFamily = robotoRegular,
                        lineHeight = 18.scaledSp()

                    )
                }
            }
        }
    }
}
