package caios.android.kpixiv.client

class ApiClient private constructor() {

    companion object {
        private var instance: ApiClient? = null

        fun getInstance(): ApiClient {
            return instance ?: ApiClient().also { instance = it }
        }
    }

}