package ac.food.myfooddiarybookaos.detail.memoUi.component

import ac.food.myfooddiarybookaos.data.function.DiaryTime
import ac.food.myfooddiarybookaos.detail.memoUi.item.DiaryTimeData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectTimeLayer(
    currentSelect: MutableState<String>,
) {
    LazyRow(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        state = rememberLazyListState(),
    ) {
        items(DiaryTime.getDiaryTimeData()) { diaryTime ->
            DiaryTimeData(
                diaryTime = diaryTime,
                click = {
                    currentSelect.value = diaryTime.name
                },
                currentSelect = currentSelect
            )
        }
    }
}
