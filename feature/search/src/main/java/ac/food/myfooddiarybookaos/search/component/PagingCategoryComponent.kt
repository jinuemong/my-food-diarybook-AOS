package ac.food.myfooddiarybookaos.search.component

import ac.food.myfooddiarybookaos.Layout.NotDataView
import ac.food.myfooddiarybookaos.model.search.SearchCategory
import ac.food.myfooddiarybookaos.model.search.SearchDiary
import ac.food.myfooddiarybookaos.search.item.ItemSearchCategory
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems

@Composable
fun PagingCategoryComponent(
    selectItem: (SearchDiary) -> Unit,
    pagingItems: LazyPagingItems<SearchCategory>
) {

    if (pagingItems.itemCount == 0) {
        NotDataView()
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(28.dp),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 26.dp)
        ) {
            items(pagingItems.itemSnapshotList) {
                it?.let {
                    ItemSearchCategory(
                        searchCategory = it,
                        selectItem = selectItem
                    )
                }
            }
            item(1) {
                Box(Modifier.height(100.dp))
            }
        }
    }
}
