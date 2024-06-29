package ac.food.myfooddiarybookaos.model.diary

import com.google.gson.annotations.SerializedName

data class PlaceInfoBody(
    @SerializedName("placeInfo") val placeInfo: PlaceInfo
) : java.io.Serializable

