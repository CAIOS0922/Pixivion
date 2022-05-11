package caios.android.pixivion.utils

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.Size
import android.view.Window
import androidx.core.view.WindowInsetsCompat

object SystemUtils {

    var navigationBarHeight = 0
    var statusBarHeight = 0

    fun getSystemBarHeights(window: Window): Pair<Int, Int> {
        return Pair(getStatusBarHeight(window), getNavigationBarHeight(window))
    }

    fun getStatusBarHeight(window: Window): Int {
        /*val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)
        return rect.top*/

        return statusBarHeight
    }

    fun getNavigationBarHeight(window: Window): Int {
        /* val realPoint = Point()
         val displayPoint = Point()
         window.windowManager.defaultDisplay.getRealSize(realPoint)
         window.windowManager.defaultDisplay.getSize(displayPoint)
         return realPoint.y - displayPoint.y*/

        return navigationBarHeight
    }

    fun convertDpToPx(context: Context, dp: Float): Float {
        val metrics = context.resources.displayMetrics
        return dp * metrics.density
    }

    fun convertPxToDp(context: Context, px: Int): Float {
        val metrics = context.resources.displayMetrics
        return px / metrics.density
    }

    fun getDisplaySize(window: Window): Size {
        val display = window.windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        return Size(point.x, point.y)
    }

    fun getDisplaySizeDp(context: Context, window: Window): Size {
        val displayPx = getDisplaySize(window)
        return Size(convertPxToDp(context, displayPx.width).toInt(), convertPxToDp(context, displayPx.height).toInt())
    }

    fun getSystemBarInsets(windowInsets: WindowInsetsCompat): Position {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                windowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars()).let {
                    Position(it.top, it.bottom, it.left, it.right)
                }
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                Position(
                    windowInsets.systemWindowInsetLeft,
                    windowInsets.systemWindowInsetTop,
                    windowInsets.systemWindowInsetRight,
                    windowInsets.systemWindowInsetBottom
                )
            }
            else -> Position(0, 0, 0, 0)
        }
    }

    data class Position(val top: Int, val bottom: Int, val left: Int, val right: Int)
}