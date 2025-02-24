package ac.food.myfooddiarybookaos.search.component

import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.model.search.SearchDiary
import ac.food.myfooddiarybookaos.search.SearchViewModel
import ac.food.myfooddiarybookaos.search.item.ItemSearchDiary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun PagingDiaryComponent(
    categoryName: MutableState<String>,
    selectDiary: (SearchDiary) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val pagingItems = viewModel.pagingDiaryList.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .padding(
                top = 26.dp,
                start = 6.dp,
                end = 6.dp,
                bottom = 100.dp
            )
            .wrapContentHeight()
            .fillMaxWidth()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 14.dp)
        ) {
            Text(
                text = categoryName.value,
                fontWeight = FontWeight.W700,
                fontFamily = robotoRegular,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = pagingItems.itemCount.toString(),
                fontWeight = FontWeight.W300,
                fontFamily = robotoRegular,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = Color.Black
            )

        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            content = {
                items(pagingItems.itemSnapshotList) {
                    it?.let {
                        ItemSearchDiary(
                            searchDiary = it,
                            select = {
                                selectDiary(it)
                            }
                        )
                    }
                }
            }
        )

    }
}
