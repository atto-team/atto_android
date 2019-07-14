package com.atto.android.util

object UnicodeUtil {
    fun unicodeConvert(str: String): String {
        val sb = StringBuilder()
        var ch: Char
        val len = str.length
        var i = 0
        while (i < len) {
            ch = str[i]
            if (ch == '\\' && str[i + 1] == 'u') {
                sb.append(Integer.parseInt(str.substring(i + 2, i + 6), 16).toChar())
                i += 5
                i++
                continue
            }
            sb.append(ch)
            i++
        }
        return checkStringWrongText(sb.toString())
    }

    private fun checkStringWrongText(s: String): String {
        return s.replace("\\n", " ")
            .replace(". ", ".\\n")
    }
}
