package ac.food.myfooddiarybookaos.home.item

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.function.DiaryTime
import ac.food.myfooddiarybookaos.data.path.byteStringToBitmap
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.model.home.HomeDay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ItemHomeDay(
    homeDay: HomeDay,
    clickDiary: () -> Unit,
) {
    val homeDayTags = remember {
        mutableStateOf(
            if (homeDay.tags.isNotEmpty()) "#" +
                    homeDay.tags.joinToString(" #")
            else ""
        )
    }

    val imageBitmap = remember {
        mutableStateOf(byteStringToBitmap(homeDay.image.bytes))
    }
    Surface(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(146.dp)
                .clip(RoundedCornerShape(4.dp))
                .clickable {
                    clickDiary()
                }
        ) {
            Column(
                modifier = Modifier.background(Color.Transparent)
            ) {

                AsyncImage(
                    model = imageBitmap.value,
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .fillMaxWidth()
                        .height(96.dp),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    onSuccess = {}
                )

                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Transparent),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = DiaryTime.getDiaryTimeData(homeDay.diaryTime),
                            fontFamily = FontFamily(
                                Font(
                                    R.font.roboto_regular,
                                    FontWeight.W500
                                )
                            ),
                            fontSize = 16.scaledSp(),
                            color = Color.Black,
                            lineHeight = 16.scaledSp()
                        )
                        Text(
                            text = homeDayTags.value,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.roboto_regular,
                                    FontWeight.W500
                                )
                            ),
                            fontSize = 12.scaledSp(),
                            color = colorResource(id = R.color.main_color),
                            lineHeight = 12.scaledSp()
                        )
                    }
                }

            }
        }

    }
}


