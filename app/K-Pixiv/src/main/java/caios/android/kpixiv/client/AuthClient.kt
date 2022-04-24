package caios.android.kpixiv.client

import android.content.Context
import android.util.Base64
import caios.android.kpixiv.Config
import caios.android.kpixiv.UrlTool
import caios.android.kpixiv.data.AuthResult
import caios.android.kpixiv.data.Code
import caios.android.kpixiv.data.UserAccount
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.httpUpload
import kotlinx.serialization.json.Json
import java.io.File
import java.security.MessageDigest
import kotlin.random.Random


class AuthClient private constructor() {

    companion object {
        private const val REDIRECT_URI = "https://app-api.pixiv.net/web/v1/users/auth/pixiv/callback"
        private const val USER_AGENT = "PixivAndroidApp/5.0.234 (Android 11; Pixel 5)"
        private const val ACCOUNT_FILE = "UserAccount.json"

        private var instance: AuthClient? = null

        fun getInstance(): AuthClient {
            return instance ?: AuthClient().also { instance = it }
        }
    }

    private val formatter = Json { ignoreUnknownKeys = true }
    private val random = Random(System.currentTimeMillis())

    fun getLoginCode(): Code {
        val codeVerifier = getCodeVerifier(32)
        val codeChallenge = getCodeChallenge(codeVerifier)
        val params = listOf(
            "code_challenge" to codeChallenge,
            "code_challenge_method" to "S256",
            "client" to "pixiv-android"
        )

        return Code(
            code = "",
            codeVerifier = codeVerifier,
            codeChallenge = codeChallenge,
            url = "${Config.API_ENDPOINT}/web/v1/login?${UrlTool.urlEncode(params)}"
        )
    }

    fun initAccount(context: Context, code: Code): UserAccount? {
        val header = mapOf(
            "User-Agent" to USER_AGENT
        )

        val params = listOf(
            "client_id" to Config.CLIENT_ID,
            "client_secret" to Config.CLIENT_SECRET,
            "code" to code.code,
            "code_verifier" to code.codeVerifier,
            "grant_type" to "authorization_code",
            "include_policy" to "true",
            "redirect_uri" to REDIRECT_URI
        )

        val (_, response, result) = "${Config.OAUTH_ENDPOINT}/auth/token".httpUpload(params, Method.POST)
            .header(header)
            .responseString()

        if(response.statusCode == 200) {
            val authResult = formatter.decodeFromString(AuthResult.serializer(), result.get())
            val userAccount = UserAccount(
                id = authResult.user.id,
                name = authResult.user.name,
                pixivID = authResult.user.account,
                mail = authResult.user.mailAddress,
                isPremium = authResult.user.isPremium,
                accessToken = authResult.accessToken,
                refreshToken = authResult.refreshToken
            )

            saveAccount(context, userAccount)

            return userAccount
        }

        return null
    }

    fun refreshToken(context: Context, refreshToken: String): UserAccount? {
        val header = mapOf(
            "User-Agent" to USER_AGENT
        )

        val params = listOf(
            "client_id" to Config.CLIENT_ID,
            "client_secret" to Config.CLIENT_SECRET,
            "grant_type" to "refresh_token",
            "include_policy" to "true",
            "refresh_token" to refreshToken
        )

        val (_, response, result) = "${Config.OAUTH_ENDPOINT}/auth/token".httpUpload(params, Method.POST)
            .header(header)
            .responseString()

        if(response.statusCode == 200) {
            val authResult = formatter.decodeFromString(AuthResult.serializer(), result.get())
            val newAccount = UserAccount(
                id = authResult.user.id,
                name = authResult.user.name,
                pixivID = authResult.user.account,
                mail = authResult.user.mailAddress,
                isPremium = authResult.user.isPremium,
                accessToken = authResult.accessToken,
                refreshToken = authResult.refreshToken
            )

            saveAccount(context, newAccount)

            return newAccount
        }

        return null
    }

    private fun getCodeVerifier(length: Int): String {
        return (('A'..'Z') + ('a'..'z') + ('0'..'9') + listOf('-', '.', '_', '~')).let { allowedChars ->
            (0 until length).map { allowedChars.random(random) }.joinToString(separator = "")
        }
    }

    private fun getCodeChallenge(codeVerifier: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(codeVerifier.toByteArray(Charsets.US_ASCII))
        return Base64.encodeToString(bytes, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }

    private fun saveAccount(context: Context, userAccount: UserAccount) {
        val file = File(context.filesDir, ACCOUNT_FILE)
        file.writeText(formatter.encodeToString(UserAccount.serializer(), userAccount))
    }

    fun getAccount(context: Context): UserAccount? {
        val file = File(context.filesDir, ACCOUNT_FILE)
        if(!file.exists()) return null

        val json = file.readText()
        if(json.isBlank()) return null

        return formatter.decodeFromString(UserAccount.serializer(), json)
    }
}