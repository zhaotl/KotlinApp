package com.ztl.kotlin.http.retrofit

import com.ztl.kotlin.BuildConfig
import com.ztl.kotlin.http.api.Apis
import com.ztl.kotlin.utils.HTTPConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    private var retrofit: Retrofit? = null

    val apis: Apis by lazy {
       createRetrofit()!!.create(Apis::class.java)
    }

    private fun createRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitHelper::class.java) {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(HTTPConstant.BASE_URL)
                        .client(getOkHttpClient())
                        .addConverterFactory(MoshiConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                }
            }
        }

        return retrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()

        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        builder.run {
            addInterceptor(loggingInterceptor)
            connectTimeout(HTTPConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(HTTPConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(HTTPConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }

        return builder.build()
    }
}