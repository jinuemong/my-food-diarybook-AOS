package ac.food.myfooddiarybookaos.search.item

import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.model.search.SearchCategory
import ac.food.myfooddiarybookaos.model.search.SearchDiary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemSearchCategory(
    searchCategory: SearchCategory,
    selectItem: (SearchDiary) -> Unit,
) {

    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = searchCategory.categoryName,
                fontWeight = FontWeight.W700,
                fontFamily = robotoRegular,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = searchCategory.count.toString(),
                fontWeight = FontWeight.W300,
                fontFamily = robotoRegular,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = Color.Black
            )

        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(searchCategory.diaryList) { item ->
                ItemSearchDiary(searchDiary = item, select = { selectItem(item) })
            }
        }
    }
}
