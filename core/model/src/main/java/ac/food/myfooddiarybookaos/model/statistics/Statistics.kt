package ac.food.myfooddiarybookaos.model.statistics

import java.io.Serializable

data class Statistics(
    val diaryTime: String,
    val count: Int
) : Serializable
