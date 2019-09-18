package com.ztl.kotlin.widget.tablayout

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import q.rorbin.badgeview.QBadgeView

class TabBadgeView : QBadgeView {

    private constructor(context: Context) : super(context)

    companion object {
        var badgeView: TabBadgeView? = null
        fun bindTab(tabView: TabView): TabBadgeView? {
            for (i in 0 until tabView.childCount) {
                val childView = tabView.getChildAt(i)
                childView?.let {
                    if (childView is TabBadgeView) {
                        badgeView = childView
                        return badgeView
                    }
                }

            }
            return badgeView
        }
    }

    override fun screenFromWindow(screen: Boolean) {
        if (parent != null) {
            (parent as ViewGroup).removeAllViews()
        }

        if (screen) {
            mActivityRoot.addView(
                this,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            )
        } else {
            if (mTargetView is TabView) {
                (mTargetView as TabView).addView(
                    this,
                    FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                )
            } else {
                bindTarget(mTargetView)
            }
        }
    }
}