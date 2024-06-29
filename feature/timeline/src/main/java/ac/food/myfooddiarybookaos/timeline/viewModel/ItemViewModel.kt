package ac.food.myfooddiarybookaos.timeline.viewModel

import ac.food.myfooddiarybookaos.data.dataTimeLine.repository.TimeLineRepository
import ac.food.myfooddiarybookaos.model.timeLine.TimeLineDiary
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val timeLineRepository: TimeLineRepository
) : ViewModel() {

    fun setTimeLineData(
        date: String,
        offset: Int,
        diaryList: (List<TimeLineDiary>) -> Unit
    ) = viewModelScope.launch {
        timeLineRepository.getTimeLineMoreData(
            date = date,
            offset = offset
        ).collect {
            diaryList(it)
        }
    }

}
