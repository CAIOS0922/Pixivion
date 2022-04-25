package caios.android.kpixiv.client

import android.util.Log
import androidx.annotation.WorkerThread
import caios.android.kpixiv.Config
import caios.android.kpixiv.data.UserAccount
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.core.interceptors.LogRequestAsCurlInterceptor
import com.github.kittinunf.fuel.core.interceptors.cUrlLoggingRequestInterceptor
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiClient private constructor(private val callback: OnRequireCallback) {

    companion object {
        private var instance: ApiClient? = null

        fun getInstance(callback: OnRequireCallback): ApiClient {
            return instance ?: ApiClient(callback).also { instance = it }
        }
    }

    private val manager = FuelManager().apply {
        addRequestInterceptor(object : FoldableRequestInterceptor {
            override fun invoke(next: RequestTransformer): RequestTransformer {
                return { request ->
                    request.header(mapOf("Authorization" to (callback.onRequireAccessToken() ?: "")))
                    next(request)
                }
            }
        })
    }

    @WorkerThread
    suspend fun getUserDetails(userId: Long): String? {
        val (request, response, result) = manager.get("${Config.API_ENDPOINT}/v1/user/detail", listOf(
            "user_id" to userId,
            "filter" to "for_android"
        )).awaitStringResponseResult()

        Log.d("", "getUserDetails: $request, $response, $result")

        return if(response.statusCode == 200) result.get() else null
    }

    interface OnRequireCallback {
        fun onRequireAccessToken(): String?
    }
}