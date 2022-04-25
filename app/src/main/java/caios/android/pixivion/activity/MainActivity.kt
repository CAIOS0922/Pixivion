package caios.android.pixivion.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import caios.android.kpixiv.activity.AuthActivity
import caios.android.pixivion.R
import caios.android.pixivion.global.pixiv
import caios.android.pixivion.utils.LogUtils.TAG
import caios.android.pixivion.utils.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                val account = pixiv.currentAccount
                ToastUtils.show(this, "Hello ${account?.name}")

                launch {
                    val json = pixiv.apiClient.getUserDetails(58290317)
                    Log.d(TAG, "onCreate: $json")
                }
            } else {
                ToastUtils.show(this, "Login failed.")
            }
        }.launch(Intent(this, AuthActivity::class.java))
    }
}