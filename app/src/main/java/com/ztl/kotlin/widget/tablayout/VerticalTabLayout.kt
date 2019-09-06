package com.ztl.kotlin.widget.tablayout

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.ScrollView
import java.util.jar.Attributes

class VerticalTabLayout : ScrollView {

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int = android.R.attr.scrollViewStyle
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {

    }
}
