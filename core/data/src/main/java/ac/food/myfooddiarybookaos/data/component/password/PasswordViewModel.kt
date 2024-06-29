package ac.food.myfooddiarybookaos.data.component.password

import ac.food.myfooddiarybookaos.data.dataMy.repository.MyRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val myRepository: MyRepository,
) : ViewModel() {
    fun updatePassword(
        prevPassword: String,
        newPassword: String,
        fail: () -> Unit,
        success: () -> Unit,
    ) {
        viewModelScope.launch {
            runCatching {
                myRepository.updatePassword(
                    prevPassword, newPassword
                )
            }
                .onSuccess {
                    if (it.status != "SUCCESS") {
                        fail()
                    } else {
                        success()
                    }
                }
                .onFailure {
                    fail()
                }
        }
    }
}
