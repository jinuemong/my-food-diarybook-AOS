package ac.food.myfooddiarybookaos.model.login

import java.io.Serializable

data class SsoToken(
    val accessToken: String,
    val oauthId: String,
) : Serializable
