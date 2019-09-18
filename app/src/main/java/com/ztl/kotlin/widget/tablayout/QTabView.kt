package com.ztl.kotlin.widget.tablayout

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import android.view.Gravity
import android.widget.TextView
import com.pdog.dimension.dp
import q.rorbin.badgeview.Badge

class QTabView : TabView {

    private var mTitle: TextView
    private var mBadgeView: Badge? = null
    private var mTabIcon: ITabView.TabIcon
    private var mTabTitle: ITabView.TabTitle
    private var mTabBadge: ITabView.TabBadge
    private var mDefaultBg: Drawable
    private var mChecked = false

    init {
        println("start init")

        mTabIcon = ITabView.TabIcon.Builder.build()
        mTabTitle = ITabView.TabTitle.Builder.build()
        mTabBadge = ITabView.TabBadge.Builder.build()

    }

    constructor(context: Context) : super(context) {

        println("start constructor")
        mTitle = TextView(context)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        addView(mTitle)

        initView(context)
        var attr: IntArray
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            attr = intArrayOf(android.R.attr.selectableItemBackgroundBorderless)
        } else {
            attr = intArrayOf(android.R.attr.selectableItemBackground)
        }

        val a = context.theme.obtainStyledAttributes(attr)
        mDefaultBg = a.getDrawable(0)
        a.recycle()

        setDefaultBackground()
    }

    private fun initView(context: Context) {
        minimumHeight = 25.dp
        initTabIcon()
        initTabBadge()
        initTabTitle()
    }

    private fun initTabIcon() {
        val iconRes = if (isChecked) mTabIcon.getSelectedIcon() else mTabIcon.getNormalIcon()
        var drawable: Drawable? = null
        if (iconRes != 0) {
            drawable = ActivityCompat.getDrawable(context, iconRes)
            drawable?.let {
                val r =
                    if (mTabIcon.getIconWidth() != -1) mTabIcon.getIconWidth() else it.intrinsicWidth
                var b =
                    if (mTabIcon.getIconHeight() != -1) mTabIcon.getIconHeight() else it.intrinsicHeight
                it.setBounds(0, 0, r, b)
            }
        }

        when (mTabIcon.getIconGravity()) {
            Gravity.LEFT ->
                mTitle.setCompoundDrawables(drawable, null, null, null)

            Gravity.TOP ->
                mTitle.setCompoundDrawables(null, drawable, null, null)

            Gravity.END ->
                mTitle.setCompoundDrawables(null, null, drawable, null)

            Gravity.BOTTOM ->
                mTitle.setCompoundDrawables(null, null, null, drawable)
        }

        refreshDrawablePadding()
    }

    private fun initTabTitle() {
        with(mTitle) {
            setTextColor(if (isChecked) mTabTitle.getColorSelect() else mTabTitle.getColorNormal())
            textSize = mTabTitle.getTextSize().toFloat()
            text = mTabTitle.getContent()
            gravity = Gravity.CENTER
            ellipsize = TextUtils.TruncateAt.END
            refreshDrawablePadding()

        }
    }

    private fun initTabBadge() {
        mBadgeView = TabBadgeView.bindTab(this)

        mBadgeView?.run {
            mTabBadge?.let {
                if (it.getBackgroundColor() != 0xFFE84E40.toInt()) {
                    setBackgroundColor(it.getBadgeTextColor())
                }

                if (it.getBadgeTextColor() != 0xFFFFFFFF.toInt()) {
                    setBadgeTextColor(it.getBadgeTextColor())
                }

                if (it.getStrokeColor() != Color.TRANSPARENT || it.getStrokeWidth() != 0) {
                    stroke(it.getStrokeColor(), it.getStrokeWidth().toFloat(), true)
                }

                it.getBackgroundDrawable().let { drawable ->
                    if (it.getBackgroundClip()) {
                        setBadgeBackground(drawable, it.getBackgroundClip())
                    }
                }

                if (it.getBadgeTextSize() != 11f) {
                    setBadgeTextSize(it.getBadgeTextSize(), true)
                }

                if (it.getBadgePadding() != 5f) {
                    setBadgePadding(it.getBadgePadding(), true)
                }

                if (it.getBadgeNumber() != 0) {
                    setBadgeNumber(it.getBadgeNumber())
                }

                it.getBadgeText()?.let { text ->
                    setBadgeText(text)
                }

                if (it.getBadgeGravity() != (Gravity.END or Gravity.TOP)) {
                    setBadgeGravity(it.getBadgeGravity())
                }

                if (it.getGravityOffsetX() != 5 || it.getGravityOffsetY() != 5) {
                    setGravityOffset(
                        it.getGravityOffsetX().toFloat(),
                        it.getGravityOffsetY().toFloat(),
                        true
                    )
                }

                if (it.getExactMode()) {
                    setExactMode(it.getExactMode())
                }

                if (!it.isShowShadow()) {
                    setShowShadow(it.isShowShadow())
                }

                it.getDragStateChangedListener()?.let {listener ->
                    setOnDragStateChangedListener(listener)
                }
            }
        }
    }

    private fun refreshDrawablePadding() {
        val iconRes = if (isChecked) mTabIcon.getSelectedIcon() else mTabIcon.getNormalIcon()
        mTitle.run {
            if (iconRes != 0) {
                if (text.isNotEmpty() && compoundDrawablePadding != mTabIcon.getIconMargin()) {
                    compoundDrawablePadding = mTabIcon.getIconMargin()
                } else {
                    compoundDrawablePadding = 0
                }
            } else {
                compoundDrawablePadding = 0
            }
        }
    }

    private fun setDefaultBackground() {
        if (background != mDefaultBg) {
            setBackground(mDefaultBg)
        }
    }

    override fun setBackground(background: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            super.setBackground(background)
        } else {
            super.setBackgroundDrawable(background)
        }
    }

    override fun setBackgroundResource(resid: Int) {
        setBackground(resid)
    }

    override fun setBackground(resid: Int): QTabView {
        if (resid == 0) {
            setDefaultBackground()
        } else if (resid <= 0) {
            setBackground(null)
        } else {
            setBackgroundResource(resid)
        }

        return this
    }

    override fun isChecked() = mChecked

    override fun setChecked(checked: Boolean) {
        mChecked = checked
        isSelected = checked
        refreshDrawableState()
        mTitle.setTextColor(if (checked) mTabTitle.getColorSelect() else mTabTitle.getColorNormal())

        initTabIcon()
    }

    override fun setIcon(icon: ITabView.TabIcon): QTabView {
        icon?.let {
            mTabIcon = icon
        }

        initTabIcon()
        return this
    }

    override fun setTitle(title: ITabView.TabTitle): QTabView {
        title?.let {
            mTabTitle = title
        }

        initTabTitle()
        return this
    }

    override fun setBadge(badge: ITabView.TabBadge): QTabView {
        badge?.let {
            mTabBadge = badge
        }

        initTabBadge()
        return this
    }

    override fun getIcon() = mTabIcon

    override fun getTitle() = mTabTitle

    override fun getBadge() = mTabBadge

    override fun getTitleView() = mTitle

    override fun getBadgeView() = mBadgeView

    override fun toggle() {
        setChecked(!mChecked)
    }
}