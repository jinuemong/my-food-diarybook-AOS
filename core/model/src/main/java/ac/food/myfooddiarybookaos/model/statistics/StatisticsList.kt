package ac.food.myfooddiarybookaos.model.statistics

import java.io.Serializable

class StatisticsList(
    val diarySubStatisticsList: List<Statistics>,
    val totalCount: Int
) : Serializable
