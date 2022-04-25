package caios.android.kpixiv

import android.annotation.SuppressLint
import android.content.Context
import caios.android.kpixiv.client.ApiClient
import caios.android.kpixiv.client.AuthClient

class KPixiv private constructor(private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: KPixiv? = null

        fun getInstance(context: Context): KPixiv {
            return instance ?: KPixiv(context).also { instance = it }
        }
    }

    val authClient = AuthClient.getInstance()
    val apiClient = ApiClient.getInstance(object : ApiClient.OnRequireCallback {
        override fun onRequireAccessToken(): String? {
            return currentAccount?.accessToken
        }
    })

    var currentAccount = authClient.getAccount(context)
}