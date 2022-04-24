package caios.android.kpixiv.activity

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import caios.android.kpixiv.KPixiv
import caios.android.kpixiv.UrlTool
import caios.android.kpixiv.databinding.ActivityAuthBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AuthActivity : AppCompatActivity(), CoroutineScope {

    private val binding by lazy { ActivityAuthBinding.inflate(layoutInflater) }
    private val pixiv by lazy { KPixiv.getInstance() }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val loginCode = pixiv.authClient.getLoginCode()
        val currentAccount = pixiv.authClient.getAccount(this)

        val webClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                launch {
                    val code = getCode(request.url.toString()) ?: return@launch
                    val account = pixiv.authClient.initAccount(this@AuthActivity, loginCode.apply { this.code = code })

                    setResult(if (account != null) Activity.RESULT_OK else Activity.RESULT_CANCELED)
                    finish()
                }

                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        launch(Dispatchers.Main) {
            if (currentAccount != null && currentAccount.refreshToken.isNotBlank()) {
                val account = withContext(Dispatchers.Default) {
                    pixiv.authClient.refreshToken(this@AuthActivity, currentAccount.refreshToken)
                }

                if (account != null) {
                    setResult(Activity.RESULT_OK)
                    finish()
                    return@launch
                }
            }

            binding.webView.apply {
                settings.javaScriptEnabled = true
                webViewClient = webClient

                loadUrl(loginCode.url)
            }
        }
    }

    private fun getCode(url: String): String? {
        return if(url.substring(0 until 8) == "pixiv://") UrlTool.getParameter(url)["code"] else null
    }
}