package com.ztl.kotlin.widget.behavior

import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

class BottomNaviBehavior : CoordinatorLayout.Behavior<View> {

    private var outAnimator: ObjectAnimator? = null
    private var inAnimator: ObjectAnimator? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ) = axes == ViewCompat.SCROLL_AXIS_VERTICAL


    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) = when {
        dy > 0 -> { // 向上滑动 隐藏
            if (outAnimator == null) {
                outAnimator = ObjectAnimator.ofFloat(child, "translationY", 0f, child.height.toFloat())
            }

            outAnimator?.run {
                if (isRunning.not() && child.translationY <= 0) {
                    duration = 200
                    start()
                }
            }
            emptyRet()
        }
        dy < 0 -> { // 向下滑动 显示
            if (inAnimator == null) {
                inAnimator = ObjectAnimator.ofFloat(child, "translationY", child.height.toFloat(), 0f)
            }

            inAnimator?.run {
                if (isRunning.not() && child.translationY >= child.height) {
                    duration = 200
                    start()
                }
            }
            emptyRet()
        }
        else -> {
            emptyRet()
        }

    }

    private fun emptyRet() {

    }
}