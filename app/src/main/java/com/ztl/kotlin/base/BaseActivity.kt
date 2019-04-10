package com.ztl.kotlin.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ztl.kotlin.app.App
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun layouRes(): Int
    protected abstract fun initView()

    // 被继承必须 是open
    open fun enableEventBus() : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layouRes())

        initView()

        if (enableEventBus()) {
            EventBus.getDefault().register(this)
        }


    }

    override fun onDestroy() {
        super.onDestroy()

        if(enableEventBus()) {
            EventBus.getDefault().unregister(this)
        }

        App.getRefWatcher(this)?.watch(this)
    }

    inline fun <reified T: Activity> start() {
        var intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}