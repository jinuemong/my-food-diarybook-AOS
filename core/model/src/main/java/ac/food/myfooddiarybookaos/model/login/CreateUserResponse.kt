package ac.food.myfooddiarybookaos.model.login

class CreateUserResponse(
    val token: String?,
    val status: String,
    val passwordStatus: String?
) : java.io.Serializable
