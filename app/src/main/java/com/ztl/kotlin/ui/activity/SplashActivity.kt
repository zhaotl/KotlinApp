package com.ztl.kotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.ztl.kotlin.base.BaseActivity
import com.ztl.kotlin.R
import com.ztl.kotlin.utils.KLogger
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity: BaseActivity() {

    private val TAG = "SplashActivity"
    private var alphaAnimation: AlphaAnimation? = null

    override fun layouRes(): Int = R.layout.activity_splash

    override fun enableEventBus(): Boolean = false

    override fun initView() {
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.run {
            duration = 4000
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                    KLogger.d(TAG, "onAnimationRepeat")
                }

                override fun onAnimationEnd(animation: Animation?) {
                    KLogger.d(TAG, "onAnimationEnd")
                    leaveSplash()
                }

                override fun onAnimationStart(animation: Animation?) {
                    KLogger.d(TAG, "onAnimationStart")
                }
            })

            splash_layout.startAnimation(alphaAnimation)
        }

    }

    private fun leaveSplash() {
        start<LoginActivity>()
        finish()
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
    }
}