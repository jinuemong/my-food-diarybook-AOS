package ac.food.myfooddiarybookaos.detail.locationUi.component

import ac.food.myfooddiarybookaos.detail.locationUi.item.SearchResultItem
import ac.food.myfooddiarybookaos.model.map.Place
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchResultLayer(
    userInput: String,
    searchResult: State<List<Place>?>,
    selectedLocation: (Place) -> Unit
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier.padding(
            start = 53.dp, end = 19.dp, top = 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(11.dp),
    ) {
        searchResult.value?.let { results ->
            items(results) { place ->
                SearchResultItem(
                    query = userInput,
                    place = place,
                    onSelected = {
                        selectedLocation(place)
                    }
                )
            }
        }
    }
}
