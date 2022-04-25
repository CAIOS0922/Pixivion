package caios.android.pixivion.global

import android.util.Log
import caios.android.pixivion.utils.LogUtils.TAG

class ExceptionClass : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e(TAG, "Can't catch exception. app will be terminated.")
        savedUncaughtExceptionHandler.uncaughtException(t, e)
    }
}