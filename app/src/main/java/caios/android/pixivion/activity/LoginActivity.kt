package caios.android.pixivion.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import caios.android.pixivion.databinding.ActivityLoginBinding
import caios.android.pixivion.global.pixiv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tool.UrlTool
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), CoroutineScope {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val authCode = pixiv.authClient.getAuthCode()
        val webClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                fun getCode(url: String): String? {
                    return if(url.substring(0 until 8) == "pixiv://") UrlTool.getParameter(url)["code"] else null
                }

                launch {
                    val code = getCode(request.url.toString()) ?: return@launch
                    val account = pixiv.authClient.initAccount(authCode.apply { this.code = code })

                    withContext(Dispatchers.Main) {
                        if(account != null) {
                            setResult(Activity.RESULT_OK)
                            finish()
                        } else {
                            setResult(Activity.RESULT_CANCELED)
                            finish()
                        }
                    }
                }

                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        binding.webview.apply {
            settings.javaScriptEnabled = true
            webViewClient = webClient

            // ログイン画面へ遷移します
            loadUrl(authCode.url)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default
}