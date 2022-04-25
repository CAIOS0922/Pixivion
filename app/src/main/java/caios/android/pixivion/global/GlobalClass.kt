package caios.android.pixivion.global

import android.app.Application
import caios.android.kpixiv.KPixiv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO_PARALLELISM_PROPERTY_NAME
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
        pixiv = KPixiv.getInstance(applicationContext)

        System.setProperty(IO_PARALLELISM_PROPERTY_NAME, 1024.toString())

    }
}