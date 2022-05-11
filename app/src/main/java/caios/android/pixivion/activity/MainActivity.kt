package caios.android.pixivion.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import caios.android.pixivion.R
import caios.android.pixivion.databinding.ActivityMainBinding
import caios.android.pixivion.global.pixiv
import caios.android.pixivion.utils.ThemeUtils
import caios.android.pixivion.utils.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(!pixiv.authClient.isInitialized()) {
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if(result.resultCode == Activity.RESULT_OK) {
                    ToastUtils.show(this, R.string.loginSuccess)
                } else {
                    ToastUtils.show(this, R.string.loginFailed)
                }
            }.launch(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        ThemeUtils.setFullScreen(window)
        ThemeUtils.setThemeIcon(this, window)
    }
}