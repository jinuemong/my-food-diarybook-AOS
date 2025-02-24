package ac.food.myfooddiarybookaos.detail.viewModel

import ac.food.myfooddiarybookaos.data.dataDetail.DetailRepository
import ac.food.myfooddiarybookaos.data.dataMap.repository.MapSearchRepository
import ac.food.myfooddiarybookaos.data.state.ApplicationState
import ac.food.myfooddiarybookaos.data.state.DetailFixState
import ac.food.myfooddiarybookaos.data.state.DiaryState
import ac.food.myfooddiarybookaos.data.state.LoadState
import ac.food.myfooddiarybookaos.model.detail.DiaryDetail
import ac.food.myfooddiarybookaos.model.map.MyLocation
import ac.food.myfooddiarybookaos.model.map.Place
import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
    private val mapSearchRepository: MapSearchRepository,
) : ViewModel() {
    private var _state: MutableStateFlow<LoadState> = MutableStateFlow(LoadState.Init)
    val state: StateFlow<LoadState> = _state.asStateFlow()

    private val _appState = MutableLiveData<ApplicationState>()
    val appState: LiveData<ApplicationState> get() = _appState

    private val _diaryState = MutableLiveData<DiaryState>()
    val diaryState: LiveData<DiaryState> get() = _diaryState

    private val _diaryDetail = MutableStateFlow<DiaryDetail>(DiaryDetail())
    val diaryDetail: StateFlow<DiaryDetail> = _diaryDetail.asStateFlow()

    private val _searchResult = MutableStateFlow<List<Place>>(emptyList())
    val searchResult: StateFlow<List<Place>> = _searchResult.asStateFlow()

    // 현재 검색 로케이션
    private val _currentLocationResult = MutableStateFlow<List<Place>>(emptyList())
    val currentLocationResult: StateFlow<List<Place>> = _currentLocationResult.asStateFlow()

    // 내 위치 정보
    private val _myLocation = MutableLiveData<MyLocation>()
    private val myLocation: LiveData<MyLocation> get() = _myLocation


    fun initAppState(state1: ApplicationState, state2: DiaryState) {
        _appState.value = state1
        _diaryState.value = state2
    }

    fun setDiaryDetail(
        initData: (DiaryDetail?) -> Unit
    ) = viewModelScope.launch {
        runCatching {
            _state.emit(LoadState.Loading)
            diaryState.value?.currentDiaryDetail?.let { currentId ->
                if (currentId.value != -1) {
                    detailRepository.getDetailDiary(currentId.value)
                        .collect {
                            _diaryDetail.value = it
                            initData(it)
                        }
                }
            }
        }
            .onSuccess {
                _state.emit(LoadState.Init)
            }
            .onFailure {
                _state.emit(LoadState.Fail)
            }
    }

    fun getSearchResult(
        data: String
    ) = viewModelScope.launch {
        runCatching {
            mapSearchRepository.getSearchKeyword(data, myLocation.value)
                .collect {
                    _searchResult.value = it
                }
        }
    }

    fun setMyLocation() {
        mapSearchRepository.initLocation(
            setLocation = {
                _myLocation.value = it
                getCurrentLocationData()
            }
        )
    }

    fun requestPermission(
        context: Context,
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
        permissionResult: (Boolean) -> Unit
    ) = mapSearchRepository.checkAndRequestPermissions(
        context,
        launcher,
        result = { permissionResult(it) })

    fun setFixResult(
        detailFixState: DetailFixState,
        initCurrentData: () -> Unit
    ) {
        detailFixState.tags = detailFixState.tags.filter { it.name.isNotEmpty() }.toMutableList()

        diaryState.value?.currentDiaryDetail?.value?.let { diaryId ->
            detailRepository.fixDetailDiary(
                diaryId,
                detailFixState.diaryToFix(),
                state = { change ->
                    if (change) {
                        _diaryDetail.value = detailFixState.submitResult(diaryDetail.value)
                        initCurrentData()
                    }
                }
            )
        }
    }

    private fun getCurrentLocationData() = viewModelScope.launch {
        mapSearchRepository.getCurrentLocationData(myLocation.value)
            .collect {
                _currentLocationResult.value = it
            }
    }


    fun addDiaryImages(
        diaryId: Int,
        file: List<MultipartBody.Part>,
        addState: (Boolean) -> Unit
    ) {
        detailRepository.addDetailDiaryImages(
            diaryId, file,
            isSuccess = { result ->
                addState(result)
            }
        )
    }

    fun fixDiaryImage(
        imageId: Int,
        file: MultipartBody.Part,
        addState: (Boolean) -> Unit
    ) {
        detailRepository.fixDetailDiaryImage(
            imageId, file,
            state = { result ->
                addState(result)
            }
        )
    }

    fun deleteDiary() = viewModelScope.launch {
        diaryState.value?.currentDiaryDetail?.let {
            detailRepository.deleteDiary(it.value)
            goBack()
        }
    }


    fun goBack() {
        appState.value?.navController?.popBackStack()
        diaryState.value?.resetDiaryDetail()
    }

}
