package caios.android.kpixiv.data

data class Code(
    var code: String,
    val codeVerifier: String,
    val codeChallenge: String,
    val url: String
)