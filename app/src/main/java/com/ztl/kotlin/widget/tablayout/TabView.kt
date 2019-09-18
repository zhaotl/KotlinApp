package com.ztl.kotlin.widget.tablayout

import android.content.Context
import android.widget.Checkable
import android.widget.FrameLayout
import android.widget.TextView
import q.rorbin.badgeview.Badge

abstract class TabView : FrameLayout, Checkable, ITabView {

    constructor(context: Context):super(context)

    override fun getTabView(): TabView = this

    abstract fun getTitleView(): TextView

    abstract fun getBadgeView(): Badge?

}