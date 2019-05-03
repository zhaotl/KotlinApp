package com.ztl.kotlin.http.interceptor

import com.ztl.kotlin.utils.HTTPConstant
import com.ztl.kotlin.utils.Preferences
import okhttp3.Interceptor
import okhttp3.Response

class HeaderIntercepter: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val builder = request.newBuilder()

        builder.addHeader("Content-type", "application/json; charset=utf-8")
        val domain = request.url().host()
        val url = request.url().toString()

        if (domain.isNotEmpty() && (url.contains(HTTPConstant.COLLECTIONS_WEBSITE)
                    || url.contains(HTTPConstant.UNCOLLECTIONS_WEBSITE)
                    || url.contains(HTTPConstant.ARTICLE_WEBSITE)
                    || url.contains(HTTPConstant.TODO_WEBSITE))) {
            val cookie: String by Preferences(domain, "")
            if (cookie.isNotEmpty()) {
                builder.addHeader("Cookie", cookie)
            }
        }

        return chain.proceed(builder.build())
    }
}