package caios.android.pixivion.global

import KPixiv
import android.app.Application
import client.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO_PARALLELISM_PROPERTY_NAME
import java.io.File
import kotlin.coroutines.CoroutineContext

lateinit var global: GlobalClass
lateinit var setting: SettingClass
lateinit var pixiv: KPixiv
lateinit var savedUncaughtExceptionHandler: Thread.UncaughtExceptionHandler

class GlobalClass : Application(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    override fun onCreate() {
        super.onCreate()

        savedUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()!!
        Thread.setDefaultUncaughtExceptionHandler(ExceptionClass())

        global = this
        setting = SettingClass(applicationContext)
        pixiv = KPixiv.getInstance(Config(accountFile = File(createPrivateDirectory("Account"), "UserAccount.json")))

        System.setProperty(IO_PARALLELISM_PROPERTY_NAME, 1024.toString())
    }

    private fun createPrivateDirectory(name: String): String {
        val directory = File(filesDir, name)
        if(!directory.exists()) directory.mkdir()
        return directory.absolutePath
    }
}