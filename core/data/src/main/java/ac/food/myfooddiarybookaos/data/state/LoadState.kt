package ac.food.myfooddiarybookaos.data.state


sealed interface LoadState {
    data object Loading : LoadState
    data object Init : LoadState
    data object Fail : LoadState
}
