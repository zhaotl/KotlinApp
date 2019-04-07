package com.ztl.kotlin.utils

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.ztl.kotlin.BuildConfig


object KLogger {

    fun init() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // 隐藏线程信息 默认：显示
            .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
            .tag("KLooger")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    fun i(msg: String) = Logger.i(msg)
    fun i(tag: String, msg: String) = Logger.t(tag).i(msg)

    fun d(msg: String) = Logger.d(msg)
    fun d(tag: String, msg: String) = Logger.t(tag).d(msg)

    fun w(msg: String) = Logger.w(msg)
    fun w(tag: String, msg: String) = Logger.t(tag).w(msg)

    fun e(msg: String) = Logger.e(msg)
    fun e(tag: String, msg: String) = Logger.t(tag).e(msg)
}