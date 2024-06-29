package ac.food.myfooddiarybookaos.data.function


enum class DiaryTime(private val nameCode: String) {
    BREAKFAST("아침"),
    BRUNCH("아점"),
    LUNCH("점심"),
    LINNER("점저"),
    SNACK("간식"),
    DINNER("저녁"),
    LATESNACK("야식"),
    ETC("기타");

    fun getNameCode() = this.nameCode

    companion object {
        fun getDiaryTimeData(code: String): String {
            return when (code) {
                "BREAKFAST" -> BREAKFAST.nameCode
                "BRUNCH" -> BRUNCH.nameCode
                "LUNCH" -> LUNCH.nameCode
                "SNACK" -> SNACK.nameCode
                "LINNER" -> LINNER.nameCode
                "DINNER" -> DINNER.nameCode
                "LATESNACK" -> LATESNACK.nameCode
                else -> ETC.nameCode
            }
        }


        fun getCode(name: String): String {
            return getDiaryTimeData().find {
                it.nameCode == name
            }?.name ?: "ETC"
        }

        fun getDiaryTimeData(): List<DiaryTime> = DiaryTime.values().toList()
    }
}
