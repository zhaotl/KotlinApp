package com.ztl.kotlin.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.ztl.kotlin.utils.KLogger
import kotlin.properties.Delegates

class App : MultiDexApplication() {

    private var refWatcher : RefWatcher? = null

    companion object {
        private val TAG = "App"

        var context: Context by Delegates.notNull()
            private set

        lateinit var instance : Application

        fun getRefWatcher(context: Context): RefWatcher? {
            val app = context.applicationContext as App
            return app.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        context = applicationContext

        refWatcher = installRefWatch()
        KLogger.init()

        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(base);
    }


    private fun installRefWatch():RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else {
            LeakCanary.installedRefWatcher()
        }
    }

    private val mActivityLifecycleCallbacks: ActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
            KLogger.i("onActivityPaused")
        }

        override fun onActivityResumed(activity: Activity?) {
            KLogger.i("onActivityResumed")
        }

        override fun onActivityStarted(activity: Activity?) {
            KLogger.i("onActivityStarted")
        }

        override fun onActivityDestroyed(activity: Activity?) {
            KLogger.i("onActivityDestroyed")
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            KLogger.i("onActivitySaveInstanceState")
        }

        override fun onActivityStopped(activity: Activity?) {
            KLogger.i("onActivityStopped")
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            KLogger.i("onActivityCreated")
        }
    }

}