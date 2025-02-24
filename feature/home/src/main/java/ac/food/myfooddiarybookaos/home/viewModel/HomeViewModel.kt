package ac.food.myfooddiarybookaos.home.viewModel

import ac.food.myfooddiarybookaos.data.dataHome.repository.HomePostRepository
import ac.food.myfooddiarybookaos.data.dataHome.repository.HomeRepository
import ac.food.myfooddiarybookaos.data.state.ApplicationState
import ac.food.myfooddiarybookaos.data.state.DiaryState
import ac.food.myfooddiarybookaos.data.state.LoadState
import ac.food.myfooddiarybookaos.model.diary.Diary
import ac.food.myfooddiarybookaos.model.home.HomeDay
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_9th_3_android.gooding.data.root.ScreenRoot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homePostRepository: HomePostRepository,
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private var _state: MutableStateFlow<LoadState> = MutableStateFlow(LoadState.Init)
    val state: StateFlow<LoadState> = _state.asStateFlow()

    private val _appState = MutableLiveData<ApplicationState>()
    private val appState: LiveData<ApplicationState> get() = _appState

    private val _diaryState = MutableLiveData<DiaryState>()
    val diaryState: LiveData<DiaryState> get() = _diaryState

    private val _homeDiaryList = MutableStateFlow<List<Diary>>(emptyList())
    val homeDiaryList: StateFlow<List<Diary>> = _homeDiaryList.asStateFlow()

    private val _homeDayInDiary = MutableStateFlow<List<HomeDay>>(emptyList())
    val homeDayInDiary: StateFlow<List<HomeDay>> = _homeDayInDiary.asStateFlow()

    private val _homeDayPrev = MutableStateFlow("")
    val homeDayPrev: StateFlow<String> = _homeDayPrev.asStateFlow()

    private val _homeDayNext = MutableStateFlow("")
    val homeDayNext: StateFlow<String> = _homeDayNext.asStateFlow()

    fun initState(
        state1: ApplicationState,
        state2: DiaryState,
    ) {
        _appState.value = state1
        _diaryState.value = state2
    }


    fun setDiaryList(
        yearMonth: String
    ) = viewModelScope.launch {
        _state.emit(LoadState.Loading)
        runCatching {
            homeRepository.getCurrentHomeDiary(yearMonth)
                .collectLatest {
                    _homeDiaryList.value = it
                }
        }
            .onSuccess {
                _state.emit(LoadState.Init)
            }
            .onFailure {
                _state.emit(LoadState.Fail)
            }
    }

    fun getHomeDayInDiary(
        date: String
    ) = viewModelScope.launch {
        _state.emit(LoadState.Loading)
        runCatching {
            homeRepository.getCurrentHomeDay(date)
                .collectLatest {
                    _homeDayInDiary.value = it.homeDayList
                    _homeDayPrev.value = it.beforeDay ?: ""
                    _homeDayNext.value = it.afterDay ?: ""
                }
        }
            .onSuccess {
                _state.emit(LoadState.Init)
            }
            .onFailure {
                _state.emit(LoadState.Fail)
            }
    }

    fun makeNewDiary(
        createTime: String,
        files: List<MultipartBody.Part>,
        diaryState: (Boolean) -> Unit,
        toastMessage: (String) -> Unit
    ) = viewModelScope.launch {
        homePostRepository.postNewDiary(
            createTime,
            null, null, null,
            files,
            isSuccess = {
                diaryState(it)
            },
            failState = {
                if (it == "register only 10 per day") {
                    toastMessage("하루에 식사일기는 최대10건까지 등록할 수 있어요.")
                }
            }
        )
    }

    // 비트맵 -> 멀티파트
    fun getMultiPartFromBitmap(
        cameraBitmap: Bitmap
    ): List<MultipartBody.Part> = homePostRepository.makePartListFromBitmap(cameraBitmap)

//    fun getMultiPartFromUri(
//        uriList: List<Uri>
//    ): List<MultipartBody.Part> {
//        return homePostRepository.makePartListFromUri(uriList)
//    }

    fun getCurrentDiary(
        yearMonth: String?,
        day: String
    ): Diary? {
        val currentDay = if (day.length == 1) "0$day" else day
        val timeData = "$yearMonth-$currentDay"
        return homeDiaryList.value.find { it.time == timeData }
    }

    fun goHomeDayView() {
        appState.value?.navController?.navigate(ScreenRoot.HOME_DAY)
    }

    fun goDetailView(diaryId: Int) {
        diaryState.value?.setDiaryDetail(diaryId)
        appState.value?.navController?.navigate(ScreenRoot.DETAIL_DIARY)
    }

}
