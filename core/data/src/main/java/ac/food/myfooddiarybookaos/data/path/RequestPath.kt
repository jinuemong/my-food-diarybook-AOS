package ac.food.myfooddiarybookaos.data.path

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


fun String?.toPlainRequestBody() =
    requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())

fun String.toApplicationRequestBody() =
    toRequestBody("application/json".toMediaType())
