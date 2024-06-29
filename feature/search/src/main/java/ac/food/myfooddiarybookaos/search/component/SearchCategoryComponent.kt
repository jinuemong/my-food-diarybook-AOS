package ac.food.myfooddiarybookaos.search.component

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
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchCategoryComponent(
    selectItem: (SearchDiary) -> Unit,
    searchItems: State<List<SearchCategory>>,
) = if (searchItems.value.isEmpty()) {
    NoSearchDataComponent()
} else {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(28.dp),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 26.dp)
    ) {
        items(searchItems.value) {
            ItemSearchCategory(
                searchCategory = it,
                selectItem = selectItem
            )
        }

        item(1) {
            Box(Modifier.height(100.dp))
        }
    }

}
