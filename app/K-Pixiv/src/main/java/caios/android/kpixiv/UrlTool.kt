package caios.android.kpixiv

import java.net.URLEncoder

object UrlTool {

    fun urlEncode(list: List<Pair<String, String>>): String {
        return list.joinToString(separator = "&") { "${URLEncoder.encode(it.first, Charsets.UTF_8.name())}=${URLEncoder.encode(it.second, Charsets.UTF_8.name())}" }
    }

    fun getParameter(url: String): Map<String, String> {
        val questionIndex = url.indexOf('?')
        if(questionIndex == -1) return emptyMap()

        val result = mutableMapOf<String, String>()
        for(parameter in url.substring(questionIndex + 1).split('&')) {
            val keyValue = parameter.split('=')
            if(keyValue.size != 2) continue

            result[keyValue[0]] = keyValue[1]
        }

        return result
    }
}