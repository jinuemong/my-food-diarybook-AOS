package ac.food.myfooddiarybookaos.model.login

data class LoginGoogleResponse(
    var access_token: String = "",
    var expires_in: Int = 0,
    var scope: String = "",
    var token_type: String = "",
    var refresh_token: String = "",
)
