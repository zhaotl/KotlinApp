package com.ztl.kotlin.http.interceptor

import com.ztl.kotlin.utils.HTTPConstant
import com.ztl.kotlin.utils.KLogger
import com.ztl.kotlin.utils.Preferences
import okhttp3.Interceptor
import okhttp3.Response

class CookieIntercepter: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url().toString()
        val domain = request.url().host()

        if ((requestUrl.contains(HTTPConstant.LOGIN_PATH)
            || requestUrl.contains(HTTPConstant.REGISTER_PATH))
            && response.headers(HTTPConstant.SET_COOKIE_KEY).isNotEmpty()) {
            val cookies = response.headers(HTTPConstant.SET_COOKIE_KEY)
            KLogger.d("cookies = $cookies")
            val cookie = enodeCookies(cookies)
            saveCookies(requestUrl, domain, cookie)
        }

        return response
    }

    private fun enodeCookies(cookies: List<String>): String {

        val sb = StringBuffer()
        val set = HashSet<String>()

        cookies.map { it ->
            it.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }
            .forEach {
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }

        val iterator = set.iterator()
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length -1 == last) {
            sb.deleteCharAt(last)
        }

        KLogger.d("string builder = ${sb.toString()}")
        return sb.toString()

    }

    private fun saveCookies(url:String, domain: String, cookie: String) {
        url ?: return
        var urlPre: String by Preferences(url, cookie)
        urlPre = cookie

        domain ?: return
        var domainPre: String by Preferences(domain, cookie)
        domainPre = cookie

    }
}