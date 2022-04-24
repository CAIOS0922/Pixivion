package caios.android.kpixiv

import caios.android.kpixiv.client.ApiClient
import caios.android.kpixiv.client.AuthClient

class KPixiv private constructor() {

    companion object {
        private var instance: KPixiv? = null

        fun getInstance(): KPixiv {
            return instance ?: KPixiv().also { instance = it }
        }
    }

    val apiClient = ApiClient.getInstance()
    val authClient = AuthClient.getInstance()

}