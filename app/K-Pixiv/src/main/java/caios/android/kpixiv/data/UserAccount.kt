package caios.android.kpixiv.data

import kotlinx.serialization.Serializable

@Serializable
data class UserAccount(
    val id: String,
    val name: String,
    val pixivID: String,
    val mail: String,
    val isPremium: Boolean,
    val accessToken: String,
    var refreshToken: String
)