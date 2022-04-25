package caios.android.kpixiv.client

import caios.android.kpixiv.Config
import caios.android.kpixiv.data.Restrict
import caios.android.kpixiv.data.UserDetail
import caios.android.kpixiv.data.UserPreview
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class ApiClient private constructor(private val callback: OnRequireCallback) {

    companion object {
        private var instance: ApiClient? = null

        fun getInstance(callback: OnRequireCallback): ApiClient {
            return instance ?: ApiClient(callback).also { instance = it }
        }
    }

    private val formatter = Json { ignoreUnknownKeys = true }

    private val manager = FuelManager().apply {
        addRequestInterceptor(object : FoldableRequestInterceptor {
            override fun invoke(next: RequestTransformer): RequestTransformer {
                return { request ->
                    val header = mapOf(
                        "app-os" to "ios",
                        "app-os-version" to "14.6",
                        "user-agent" to "PixivIOSApp/7.13.3 (iOS 14.6; iPhone13,2)",
                        "Authorization" to "Bearer ${callback.onRequireAccessToken() ?: ""}"
                    )

                    request.header(header)
                    next(request)
                }
            }
        })
    }

    suspend fun getUserDetails(userId: Long): UserDetail? {
        val (_, response, result) = manager.get("${Config.API_ENDPOINT}/v1/user/detail", listOf(
            "user_id" to userId,
            "filter" to "for_android"
        )).awaitStringResponseResult()

        return if(response.statusCode == 200) Json.decodeFromString(result.get()) else null
    }

    suspend fun getFollowingUsers(userId: Long, restrict: Restrict, _nextUrl: String? = null): List<UserPreview> {
        var url = "${Config.API_ENDPOINT}/v1/user/following"
        var params = listOf(
            "user_id" to userId,
            "filter" to "for_android",
            "restrict" to restrict.value
        )

        if(_nextUrl != null) {
            url = _nextUrl
            params = emptyList()
        }

        val (_, response, result) = manager.get(url, params).awaitStringResponseResult()

        if(response.statusCode == 200) {
            val jsonObject = formatter.decodeFromString(JsonElement.serializer(), result.get()).jsonObject
            val usersList = jsonObject["user_previews"]?.jsonArray?.map { formatter.decodeFromString(UserPreview.serializer(), it.toString()) }?.toMutableList()
            val nextUrl = jsonObject["next_url"]?.toString()

            if(nextUrl != null && nextUrl != "null") usersList?.addAll(getFollowingUsers(userId, restrict, nextUrl))

            return usersList ?: emptyList()
        }

        return emptyList()
    }

    interface OnRequireCallback {
        fun onRequireAccessToken(): String?
    }
}