package com.ztl.kotlin.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ztl.kotlin.app.App
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.utils.Preferences
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : AppCompatActivity() {

    protected var isLogin: Boolean by Preferences(Constant.CONST_ISLOGIN_KEY, false)

    protected abstract fun layoutRes(): Int
    protected abstract fun initView()

    // 被继承必须 是open
    open fun enableEventBus() : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutRes())

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

    inline fun <reified T: Activity> start(bundle: Bundle? = null) {
        var intent = Intent(this, T::class.java)
        bundle?.let {
            intent.putExtras(it)
        }
        startActivity(intent)
    }
}